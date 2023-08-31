package com.shangan.trade.goods;


import com.alibaba.fastjson.JSON;
import com.shangan.trade.goods.dao.GoodsDao;
import com.shangan.trade.goods.domain.Goods;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class GoodsTest {

    @Resource
    private GoodsDao goodsDao;

    @Resource
    private RestHighLevelClient client;

    @Test
    public void insertGoodsTest(){
        System.out.println("hello insert goods test..");
        Goods goods = new Goods();
        goods.setTitle("iphone Test");
        goods.setBrand("iphone");
        goods.setCategory("cell phone");
        goods.setAvailableStock(100);
        goods.setKeyword("iphone");
        goods.setPrice(5000);
        goods.setStatus(0);
        goods.setSaleNum(10);
        boolean b = goodsDao.insertGoods(goods);
        System.out.println("result is : " + b);

    }


    @Test
    public void testEs(){
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(
                new HttpHost("http://127.0.0.1/",9200,"http")
        ));
        System.out.println(JSON.toJSONString(client));
    }

    @Test
    public void addDoc() throws Exception{
        Person person = new Person();
        person.setId("123");
        person.setName("xiaowang");
        person.setAge("40");
        person.setAddress("Beijing");
        String data = JSON.toJSONString(person);
        IndexRequest request = new IndexRequest("person")
                .id(person.getId())
                .source(data, XContentType.JSON);

        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        System.out.println(response.getId());

    }


    @Test
    public void matchAll() throws IOException{
        SearchRequest searchRequest = new SearchRequest("person");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        MatchAllQueryBuilder query = QueryBuilders.matchAllQuery();

        searchSourceBuilder.query(query);
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(2);

        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(JSON.toJSONString(searchResponse));

        SearchHits searchHits = searchResponse.getHits();
        long value = searchHits.getTotalHits().value;
        System.out.println("总记录数：" + value);


        List<Person> list = new ArrayList<>();
        SearchHit[] hits = searchHits.getHits();
        for(SearchHit s : hits){
            String sourceAsString = s.getSourceAsString();
            Person person = JSON.parseObject(sourceAsString, Person.class);
            list.add(person);
        }

        System.out.println(JSON.toJSONString(list));




    }


}
