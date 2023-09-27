package com.jst.common.es;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jst.common.dto.EsDcResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

public abstract class AbstractBaseRepository implements BaseRepository{

    private static final Logger logger = LoggerFactory.getLogger(AbstractBaseRepository.class);

    public abstract EsSourceSelector getSelector();

    public abstract Executor getExecutor();

    @Override
    public JSONObject query(RestHighLevelClient esClient, String sql){
        String requestBody = "{\"query\":\"" + sql + "\",\"fetch_size\":10000}";
        String strResponse = "";

        String idcName = getIdcNameByEsClient(esClient);
        try{
            Request scriptRequest = new Request("POST", "_sql?format=json");
            scriptRequest.setJsonEntity(requestBody);
            Response scriptResponse = esClient.getLowLevelClient().performRequest(scriptRequest);
            strResponse = EntityUtils.toString(scriptResponse.getEntity(), "UTF-8");
            logger.info("queryFromEs idc:{}, requestBody:{}, response:{}",idcName,requestBody, StringUtils.left(strResponse, 1000)");
        }catch (Exception e){
            logger.error("queryFromEs idc:{}, requestBody:{}, response:{}",idcName,requestBody, StringUtils.left(strResponse, 1000), e);
            throw new RuntimeException(e);
        }
        return JSON.parseObject(strResponse);
    }

    private String getIdcNameByEsClient(RestHighLevelClient esClient){
        try{
            //return this.getSelector().getIdcNameByEsClient(esClient);
        }catch (Exception e){
            logger.error("getIdcNameByEsClient");
            return null;
        }
    }

    public EsDcResult<JSONObject> query(String sql){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Map<String, String> idcStatusMap = new HashMap<>();
        Map<String, JSONObject> resultObject = new HashMap<>();
        try{

        }catch (Exception e){

        }

    }





}
