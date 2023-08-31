package com.jst.trajst.dashboard.view.controller.global;

import com.google.common.collect.Maps;
import com.jst.common.config.CustomerExecutorConfig;
import com.jst.common.constant.CommonConstant;
import com.jst.common.dto.GlobalResult;
import com.jst.common.es.SystemConfig;
import com.jst.trajst.dashboard.internal.globalCalling.AbstractGlobalEsResultMerge;
import com.jst.trajst.dashboard.internal.globalCalling.GlobalSearchCallable;
import com.jst.trajst.dashboard.internal.globalCalling.MultiThreadResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class BaseGlobalController {
    private static Logger logger = LoggerFactory.getLogger(BaseGlobalController.class);

    @Resource
    RestTemplate restTemplate;

    @Resource
    private SystemConfig.IdcConf idcConf;

    @Resource
    private SystemConfig.SecretInfo secretInfo;

    //不和es共用线程池
    private Executor baseControllerExecutor;

    @Value("${async.timeout:60}")
    private int asyncTimeout;

    @Resource
    private CustomerExecutorConfig.AsyncExeConfig baseControllerExecutorConfig;

    @PostConstruct
    public void init() {
        this.baseControllerExecutor = CustomerExecutorConfig.getAsyncExecutor(baseControllerExecutorConfig);
    }

    public <T> GlobalResult<List<T>> globalRequestV1(String requestUrl, Map<Object, Object> paraMap, AbstractGlobalEsResultMerge<T> esResultMerge) throws Exception {
        Map<String, String> finalUrlMap = composeUrl(requestUrl);
        GlobalResult<List<T>> globalResult = doGlobalRequestV1(paraMap,(AbstractGlobalEsResultMerge<T>) esResultMerge, finalUrlMap);
        return globalResult;
    }

    private  <T> GlobalResult<List<T>> doGlobalRequestV1(Map<Object,Object> paraMap, AbstractGlobalEsResultMerge<T> esResultMerge, Map<String,String> finalUrlMap) throws Exception {
        List<MultiThreadResult> multiThreadResults = callLocalController(finalUrlMap, paraMap);
        return esResultMerge.mergeResult(multiThreadResults);
    }





    private Map<String, String> composeUrl(String requestUrl) {
        if (!requestUrl.startsWith("/")) {
            requestUrl = "/" + requestUrl;
        }

        //选择城市
        Map<String, String> hashMap = new HashMap<>();
        Map<String, List<SystemConfig.IdcInfo>> cityIdcMap = idcConf.idcList.stream().collect(Collectors.groupingBy(idc -> idc.city));
        List<SystemConfig.IdcInfo> chooseIdc = new ArrayList<>();

        for(Map.Entry<String, List<SystemConfig.IdcInfo>> entry : cityIdcMap.entrySet()){
            String city = entry.getKey();
            List<SystemConfig.IdcInfo> idcInfos = entry.getValue();
            if(Objects.equals(idcConf.activeCity,city)){
                Optional<SystemConfig.IdcInfo> first = idcInfos.stream().filter(dc -> Objects.equals(dc.name, idcConf.activeIdc)).findFirst();
                chooseIdc.add(first.orElse(idcInfos.get(0)));
            }else{
                Optional<SystemConfig.IdcInfo> first = idcInfos.stream().filter(dc -> CommonConstant.MASTER.equals(dc.role)).findFirst();
                chooseIdc.add(first.orElse(idcInfos.get(0)));
            }
        }

        for(SystemConfig.IdcInfo idcInfo : chooseIdc){
            hashMap.put(idcInfo.name, String.format("%s%s", idcInfo.serverHosts,requestUrl));
        }
        return  hashMap;
    }


    //调用localController
    private List<MultiThreadResult> callLocalController(Map<String, String> finalUrlMap, Map<Object, Object> paraMap){
        long startTime = System.currentTimeMillis();
        logger.info("calling localcontroller");
        List<MultiThreadResult> results = new ArrayList<>();
        Map<String, CompletableFuture<MultiThreadResult>> futureMap = Maps.newHashMapWithExpectedSize(finalUrlMap.size());

        try{
            for(Map.Entry<String, String> entry : finalUrlMap.entrySet()){
                GlobalSearchCallable myCallable = new GlobalSearchCallable(entry.getKey(), entry.getValue(), paraMap, restTemplate, secretInfo);
                CompletableFuture<MultiThreadResult> future = CompletableFuture.supplyAsync(myCallable, baseControllerExecutor);
                futureMap.put(entry.getKey(), future);
            }
            logger.info("MultiThread task HashSet invoking...");

            try {
                CompletableFuture.allOf(futureMap.values().toArray(new CompletableFuture[0])).get(asyncTimeout, TimeUnit.SECONDS);
            }catch (Exception e){
                logger.error("A thread error from getting results, e={}", e.getMessage());
            }finally {
                for(Map.Entry<String, CompletableFuture<MultiThreadResult>> entry: futureMap.entrySet() ){
                    String idcName = entry.getKey();
                    CompletableFuture<MultiThreadResult> future = entry.getValue();

                    if(future.isCompletedExceptionally()){
                        future.exceptionally(getThrowableMultiThreadFunction(results,idcName));
                    }else if(future.isDone()){
                        MultiThreadResult multiThreadResult = future.get();
                        if(multiThreadResult.getIdcName() != null){
                            multiThreadResult.setIdcName(idcName);
                        }
                        results.add(future.get());
                        logger.debug("A thread result is putting into resultBeforeMergeMap key ={},value={}", multiThreadResult.getIdcName(),multiThreadResult.getResult());
                    }else{
                        try {
                            logger.error("A thread result is timeout, cancel");
                            MultiThreadResult multiThreadResult = new MultiThreadResult();
                            multiThreadResult.setIdcName(idcName);
                            multiThreadResult.setException(new RuntimeException("thread timeout, cancle task"));
                            results.add(multiThreadResult);
                            future.cancel(true);
                        }catch (Exception e){
                            logger.warn("GlobalSearchCallable task cancel error. e:{}", e.getMessage());
                        }
                    }
                }
            }

        } catch (Exception e) {
            logger.error("MultiThread error from callLocalController. ", e);
        }
        logger.info("calling localcontroller end, cost:{} ms", System.currentTimeMillis() - startTime);
        return results;
    }


    private Function<Throwable, MultiThreadResult> getThrowableMultiThreadFunction(List<MultiThreadResult> results, String idcName){
        return throwable -> {
            logger.error("A thread executor error:{}", throwable.getMessage());
            MultiThreadResult multiThreadResult = new MultiThreadResult();
            multiThreadResult.setIdcName(idcName);
            multiThreadResult.setException(new RuntimeException(throwable.getCause()));
            results.add(multiThreadResult);
            return null;
        };
    }




}
