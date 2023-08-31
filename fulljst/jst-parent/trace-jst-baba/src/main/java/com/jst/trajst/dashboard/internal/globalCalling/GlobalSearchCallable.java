package com.jst.trajst.dashboard.internal.globalCalling;

import com.alibaba.fastjson.JSON;
import com.jst.common.Constances;
import com.jst.common.es.SystemConfig;
import com.jst.common.utils.AESUtil;
import com.jst.common.utils.RSAUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

public class GlobalSearchCallable implements Callable<MultiThreadResult>, Supplier<MultiThreadResult> {
    private static Logger logger = LoggerFactory.getLogger(GlobalSearchCallable.class);

    private String idcName;
    private Map<Object, Object> paraMap;
    private String finalUrlString;
    private RestTemplate restTemplate;
    private SystemConfig.SecretInfo secretInfo;

    public GlobalSearchCallable(String idcName, String finalUrlString, Map<Object, Object> paraMap, RestTemplate restTemplate, SystemConfig.SecretInfo secretInfo) {
        this.idcName = idcName;
        this.paraMap = paraMap;
        this.finalUrlString = finalUrlString;
        this.restTemplate = restTemplate;
        this.secretInfo = secretInfo;
    }



    @Override
    public MultiThreadResult call() throws Exception {

        MultiThreadResult multiThreadResult = new MultiThreadResult();

        try{
            logger.info("MuktiThreadResult calls start");
            StopWatch stopWatch = new StopWatch();
            long startTime = System.currentTimeMillis();
            multiThreadResult.setIdcName(idcName);
            multiThreadResult.setCurrentUrl(finalUrlString);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            String json = JSON.toJSONString(paraMap);
            String timestampStr = Long.toString(System.currentTimeMillis());

            stopWatch.start("encrypt");
            String encrypt = AESUtil.encrypt(json, secretInfo.aesKey);
            stopWatch.stop();
            stopWatch.start("sign");
            String sign = RSAUtil.sign(json + timestampStr, secretInfo.rsaPrivateKey);
            stopWatch.stop();

            httpHeaders.add(Constances.REQUEST_HEADER_SIGN, sign);
            httpHeaders.add(Constances.REQUEST_HEADER_TIME, timestampStr);

            HttpEntity<String> httpEntity = new HttpEntity<>(encrypt, httpHeaders);
            logger.info("restTemplate start url ={}", finalUrlString);

            stopWatch.start("http");
            ResponseEntity<Object> resultPerThread = restTemplate.postForEntity(finalUrlString, httpEntity, Object.class);
            stopWatch.stop();
            logger.info("restTemplate ends cost: {}ms, {}", System.currentTimeMillis()-startTime, StringUtils.left(String.valueOf(resultPerThread.getBody()), 1000));
            logger.info(stopWatch.prettyPrint());

            multiThreadResult.setStatusCode(resultPerThread.getStatusCodeValue());
            multiThreadResult.setResult(resultPerThread.getBody());

        }catch (Exception e){
            logger.error("restTemplate error ", e);
            multiThreadResult.setException(e);
        }

        return multiThreadResult;
    }

    @Override
    public MultiThreadResult get() {
        return null;
    }
}
