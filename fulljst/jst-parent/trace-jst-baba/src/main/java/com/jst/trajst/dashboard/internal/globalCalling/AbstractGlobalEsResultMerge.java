package com.jst.trajst.dashboard.internal.globalCalling;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.jst.common.constant.CommonConstant;
import com.jst.common.dto.GlobalResult;
import com.jst.trajst.dashboard.vo.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public abstract class AbstractGlobalEsResultMerge<T> implements IGlobalResultMerge<GlobalResult<List<T>>> {

    private static Logger logger = LoggerFactory.getLogger(AbstractGlobalEsResultMerge.class);

    private Class<T> tClass;

    public AbstractGlobalEsResultMerge(Class<T> tClass) {
        this.tClass = tClass;
    }

    @Override
    public GlobalResult<List<T>> mergeResult(List<MultiThreadResult> input) throws Exception {

        Map<String, String> status = new HashMap<>();
        List<GlobalResult> globalResultList = extractGlobalRes(input, status);
        List<JSONObject> mergeResultDatas = mergeResultDatas(status, globalResultList);
        List<T> resList = JSON.parseArray(JSON.toJSONString(mergeResultDatas), tClass);
        return new GlobalResult(status,resList);
    }

    protected abstract List<JSONObject> mergeResultDatas(Map<String, String> status, List<GlobalResult> globalResultList);

    private List<GlobalResult> extractGlobalRes(List<MultiThreadResult> input, Map<String, String> status){
        List<GlobalResult> globalResultList = new ArrayList<>();
        for(Iterator<MultiThreadResult> itor = input.iterator(); itor.hasNext(); ){
            MultiThreadResult result = itor.next();
            String idcName = result.getIdcName();
            try{
                if(!Objects.equals(result.getStatusCode(), 200)){
                    status.put(idcName, result.getException() == null ? CommonConstant.FAIL : result.getException().getMessage());
                }else{
                    BaseResponse<GlobalResult> response = JSON.parseObject(JSONObject.toJSONString(result.getResult()), new TypeReference<BaseResponse<GlobalResult>>() {
                    });
                    if(response != null && Objects.equals(response.getCode(),0)){
                        globalResultList.add(response.getData());
                    }else{
                        status.put(idcName, response != null ? response.getMessage() : "unknown error");
                    }
                }
            }catch (Exception e){
                status.put(idcName, "unknown error: " + e.getMessage());
                logger.error("extractGlobalRes ex: {}", e.getMessage());
            }
        }
        return globalResultList;
    }

}
