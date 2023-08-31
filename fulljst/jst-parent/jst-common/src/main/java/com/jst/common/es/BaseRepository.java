package com.jst.common.es;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.elasticsearch.client.RestHighLevelClient;

public interface BaseRepository {

    JSONObject query(RestHighLevelClient esClient,String sql);

}
