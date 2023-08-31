package com.jst.common.dto;

import com.jst.common.constant.CommonConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DefaultEsDcResultMerge<T> implements EsDcResultMerge<List<T>> {

    private static final Logger logger = LoggerFactory.getLogger(DefaultEsDcResultMerge.class);

    public DefaultEsDcResultMerge() {
    }

    @Override
    public GlobalResult<List<T>> mergeResultList(EsDcResult<List<T>> esDcResult) {
        Map<String,String> statusMap = esDcResult.getStatusMap();
        Map<String, List<T>> resultMap = esDcResult.getResultMap();

        List<T> res = new ArrayList<>();
        for(Map.Entry<String, String> entry : statusMap.entrySet()){
            String idc = entry.getKey();
            String status = entry.getValue();
            if(CommonConstant.OK.equals(status)){
                res.addAll(resultMap.get(idc));
            } else{
                logger.warn("idc query failed. idc {}, msg : {}",idc,status);
            }
        }

        return new GlobalResult(statusMap,res);
    }
}
