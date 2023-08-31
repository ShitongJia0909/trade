package com.jst.trajst.dashboard.internal.globalCalling;

import com.alibaba.fastjson.JSONObject;
import com.jst.common.dto.GlobalResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GlobalEsResultAddMerge<T> extends AbstractGlobalEsResultMerge<T> {
    private static Logger logger = LoggerFactory.getLogger(GlobalEsResultAddMerge.class);

    public GlobalEsResultAddMerge(Class<T> tClass){
        super(tClass);
    }

    @Override
    protected List<JSONObject> mergeResultDatas(Map<String, String> status, List<GlobalResult> globalResultList) {
        List<JSONObject> resList = new ArrayList<>();
        for(GlobalResult globalResult : globalResultList){
            if(null != globalResult){
                if(globalResult.getStatus() != null ){
                    status.putAll(globalResult.getStatus());
                } else {
                    logger.error("mergeResult failed: the globalResult status can't be null");
                }
                Object resultObj = globalResult.getResultObj();
                if(resultObj instanceof List){
                    resList.addAll((List<JSONObject>)resultObj);
                }else{
                    logger.error("mergeResult failed: globalResult resultObj must be List");
                }

            }else{
                logger.error("mergeResult failed: the result must be GlobalResult");
            }
        }
        return resList;
    }
}
