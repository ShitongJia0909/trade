package com.jst.trajst.dashboard.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jst.jstdao.model.ZbHost;
import com.jst.jstdao.repository.ZbHostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ZbConvertUtil {

    private static final Logger logger = LoggerFactory.getLogger(ZbConvertUtil.class);

    @Resource
    ZbHostRepository zbHostRepository;

    private static ZbConvertUtil zbConvertUtil;

    public void init(){
        zbConvertUtil = this;
        zbConvertUtil.zbHostRepository = this.zbHostRepository;
    }

    public static List<ZbHost> convertToHostMapping(JSONObject jsonObject, String idc){
        List<ZbHost> list = new ArrayList<>();
        if(jsonObject != null){
            JSONArray result = jsonObject.getJSONArray("result");
            String hostid;
            for(int i = 0; i < result.size(); i ++){
                ZbHost zbHost = new ZbHost();
                zbHost.setHost(result.getJSONObject(i).getString("host"));
                hostid = result.getJSONObject(i).getString("hostid");
                zbHost.setHostid(hostid);
                zbHost.setIp(result.getJSONObject(i).getJSONArray("interfaces").getJSONObject(0).getString("ip"));
                zbHost.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                ZbHost byHostid = zbConvertUtil.zbHostRepository.findByHostidAndDc(hostid, idc);
                if(null == byHostid){
                    zbHost.setCreateTime(new Timestamp(System.currentTimeMillis()));
                }else{
                    zbHost.setCreateTime(byHostid.getCreateTime());
                }
                zbHost.setDc(idc);
                list.add(zbHost);
            }
        }
        return list;
    }




}
