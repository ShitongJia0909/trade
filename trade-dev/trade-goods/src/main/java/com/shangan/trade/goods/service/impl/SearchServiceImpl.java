package com.shangan.trade.goods.service.impl;

import com.alibaba.fastjson.JSON;
import com.shangan.trade.goods.domain.Goods;
import com.shangan.trade.goods.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.naming.directory.SearchResult;
import javax.swing.tree.ExpandVetoException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class SearchServiceImpl implements SearchService {

    @Resource
    private RestHighLevelClient client;

    @Override
    public void addGoodsToEs(Goods goods) {

        try{
            String data = JSON.toJSONString(goods);
            IndexRequest request = new IndexRequest("goods").source(data, XContentType.JSON);
            IndexResponse response = client.index(request, RequestOptions.DEFAULT);
            log.info("addGoods to es result : {}",response);
        }catch(Exception e){
            log.error("SearchService addGoods error", e);
        }

    }

    @Override
    public List<Goods> searchGoodsList(String keyword, int from, int size) {
        try{
            SearchRequest searchRequest = new SearchRequest("goods");
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            QueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(keyword,
                    "title","description","keyword");
            sourceBuilder.query(queryBuilder);


            sourceBuilder.from(from);
            sourceBuilder.size(size);

            sourceBuilder.sort("saleNum", SortOrder.DESC);
            searchRequest.source(sourceBuilder);

            SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
            System.out.println(JSON.toJSONString(response));

            SearchHits searchHits = response.getHits();
            long totalNum = searchHits.getTotalHits().value;
            log.info("search total records:{}", totalNum);

            List<Goods> list = new ArrayList<>();
            SearchHit[] hits = searchHits.getHits();
            for(SearchHit searchHit: hits){
                String sourceAsString = searchHit.getSourceAsString();
                Goods goods = JSON.parseObject(sourceAsString, Goods.class);
                list.add(goods);
            }

            log.info("search result {}", JSON.toJSONString(list));
            return list;



        }catch (Exception e){
            log.error("search goods error", e);
            return null;
        }

    }
}
