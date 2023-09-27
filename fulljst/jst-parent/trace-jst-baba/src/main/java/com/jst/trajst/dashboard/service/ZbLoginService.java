package com.jst.trajst.dashboard.service;


import com.alibaba.fastjson.JSONObject;
import com.jst.common.es.SystemConfig;
import com.jst.trajst.dashboard.common.exceptions.ZabbixLoginException;
import com.jst.trajst.dashboard.common.exceptions.ZabbixOperationException;
import com.jst.trajst.dashboard.tasks.ZbLoginTask;
import io.github.hengyunabc.zabbix.api.Request;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import static com.jst.trajst.dashboard.tasks.ZabbixHostTask.ZB_API_MAP;

@DependsOn(value = {"ZabbixHostTask"})
@Service
public class ZbLoginService {

    private final static Logger logger = LoggerFactory.getLogger(ZbLoginService.class);

    @Resource(name = "idcConf")
    SystemConfig.IdcConf idcConf;

    @Resource(name = "httpClient")
    CloseableHttpClient httpClient;

    private SystemConfig.IdcInfo getIdcInfo(String idc){
        try{
            List<SystemConfig.IdcInfo> activeIdcList = idcConf.activeIdcList;
            List<SystemConfig.IdcInfo> idcInfos = activeIdcList.stream().filter(idcInfo -> idcInfo.name.equals(idc)).collect(Collectors.toList());
            return idcInfos.get(0);
        } catch (Exception e) {
            logger.error("get clearPwd error. " + e.getMessage());
            throw new ZabbixLoginException("无法获取zabbix用户密码，idc = " + idc);
        }
    }

    public DefltExtend loginZb(String idc){
        logger.debug("zabbix in dc {} login...", idc);
        if(ZB_API_MAP.containsKey(idc)){
            throw new ZabbixLoginException("idc" + idc + "不存在");
        }

        String zbUrl = ZB_API_MAP.get(idc);
        DefltExtend defaultZabbixApi = new DefltExtend(zbUrl, httpClient);
        defaultZabbixApi.init();
        SystemConfig.IdcInfo idcInfo = getIdcInfo(idc);
        logger.info("zabbix login user = {}", idcInfo.zabbixUsername);
        boolean login = defaultZabbixApi.login(idcInfo.zabbixUsername, idcInfo.zabbixClrPwd);
        logger.debug("login zabbix status is {}", login);
        if(!login){
            logger.error("login zabbix status is {}",login);
            throw new ZabbixLoginException("zabbix登陆失败， 机房= " + idc);
        } else{
            return defaultZabbixApi;
        }
    }



    public JSONObject call(Request request, String idc) {

        try{
            DefltExtend defltExtend = ZbLoginTask.ZB_AUTH_MAP.get(idc);
            JSONObject jsonObject = defltExtend.call(request);

            logger.info("Result from zabbix: {}", StringUtils.left(jsonObject.toJSONString(), 150));
            if(jsonObject.containsKey("error")){
                logger.error("call zabbix error. {}", jsonObject.getJSONObject("error").toJSONString());
                throw new ZabbixOperationException(jsonObject.getJSONObject("error").toJSONString());
            }
            return jsonObject;
        }catch (Exception e){
            logger.error("call zabbix error. ", e);
            throw new ZabbixOperationException(e.getMessage());
        }

    }




}
