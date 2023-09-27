package com.jst.trajst.dashboard.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.github.hengyunabc.zabbix.api.DefaultZabbixApi;
import io.github.hengyunabc.zabbix.api.Request;
import io.github.hengyunabc.zabbix.api.RequestBuilder;
import io.github.hengyunabc.zabbix.api.ZabbixApi;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class DefltExtend implements ZabbixApi {

    private static final Logger logger = LoggerFactory.getLogger(DefltExtend.class);

    private CloseableHttpClient httpClient;

    private URI uri;

    private volatile String auth;

    public DefltExtend(String url) {
        try {
            uri = new URI(url.trim());
        } catch (URISyntaxException e) {
            throw new RuntimeException("url invalid", e);
        }
    }

    public DefltExtend(URI uri) {
        this.uri = uri;
    }

    public DefltExtend(String url, CloseableHttpClient httpClient) {
        this(url);
        this.httpClient = httpClient;
    }

    public DefltExtend(URI uri, CloseableHttpClient httpClient) {
        this(uri);
        this.httpClient = httpClient;
    }


    public void init() {
        if (httpClient == null) {
            httpClient = HttpClients.custom().build();
        }
    }


    public void destroy() {
        if (httpClient != null) {
            try {
                httpClient.close();
            } catch (Exception e) {
                logger.error("close httpclient error!", e);
            }
        }
    }


    public boolean login(String user, String password) {
        this.auth = null;
        Request request = RequestBuilder.newBuilder().paramEntry("user", user).paramEntry("password", password)
                .method("user.login").build();
        JSONObject response = call(request);
        String auth = response.getString("result");
        if (auth != null && !auth.isEmpty()) {
            this.auth = auth;
            return true;
        }
        return false;
    }

    @Override
    public String apiVersion() {
        Request request = RequestBuilder.newBuilder().method("apiinfo.version").build();
        JSONObject response = call(request);
        return response.getString("result");
    }

    public boolean hostExists(String name) {
        Request request = RequestBuilder.newBuilder().method("host.exists").paramEntry("name", name).build();
        JSONObject response = call(request);
        return response.getBooleanValue("result");
    }

    public String hostCreate(String host, String groupId) {
        JSONArray groups = new JSONArray();
        JSONObject group = new JSONObject();
        group.put("groupid", groupId);
        groups.add(group);
        Request request = RequestBuilder.newBuilder().method("host.create").paramEntry("host", host)
                .paramEntry("groups", groups).build();
        JSONObject response = call(request);
        return response.getJSONObject("result").getJSONArray("hostids").getString(0);
    }

    public boolean hostgroupExists(String name) {
        Request request = RequestBuilder.newBuilder().method("hostgroup.exists").paramEntry("name", name).build();
        JSONObject response = this.call(request);
        return response.getBooleanValue("result");
    }

    /**
     *
     * @param name
     * @return groupId
     */
    public String hostgroupCreate(String name) {
        Request request = RequestBuilder.newBuilder().method("hostgroup.create").paramEntry("name", name).build();
        JSONObject response = call(request);
        return response.getJSONObject("result").getJSONArray("groupids").getString(0);
    }

    @Override
    public JSONObject call(Request request) {
        if (request.getAuth() == null) {
            request.setAuth(this.auth);
        }

        try {
            long startTime = System.currentTimeMillis();
            HttpUriRequest httpRequest = org.apache.http.client.methods.RequestBuilder.post().setUri(uri)
                    .addHeader("Content-Type", "application/json")
                    .setEntity(new StringEntity(JSON.toJSONString(request), ContentType.APPLICATION_JSON)).build();
            CloseableHttpResponse response = this.httpClient.execute(httpRequest);
            long endTime = System.currentTimeMillis();
            logger.info("zabbix response in call cost {} s, code is {} ", (endTime - startTime)/ 1000, JSON.toJSONString(response.getStatusLine()));
            HttpEntity entity = response.getEntity();
            byte[] data = EntityUtils.toByteArray(entity);
            return (JSONObject) JSON.parse(data);
        } catch (IOException e) {
            throw new RuntimeException("DefaultZabbixApi call exception!", e);
        }
    }


}
