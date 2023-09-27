package com.jst.trajst.dashboard.tasks;

import com.jst.common.es.SystemConfig;
import com.jst.trajst.dashboard.enums.EnumBooleanResult;
import com.jst.trajst.dashboard.service.DefltExtend;
import com.jst.trajst.dashboard.service.ZbLoginService;
import io.github.hengyunabc.zabbix.api.ZabbixApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


@Component(value = "ZbLoginTask")
@DependsOn(value = {"ZabbixHostTask"})
public class ZbLoginTask {

    private static final Logger logger = LoggerFactory.getLogger(ZbLoginTask.class);
    public final static ConcurrentHashMap<String, DefltExtend> ZB_AUTH_MAP = new ConcurrentHashMap<>();

    @Resource(name = "idcConf")
    SystemConfig.IdcConf idcConf;
    @Resource
    ZbLoginService zbLoginService;

    @PostConstruct
    @Scheduled(cron = "0 */5 * * *")
    public EnumBooleanResult doPerform(){
        logger.debug("scheduled zb login task begins.");
        String idcName = new String();

        List<SystemConfig.IdcInfo> idcList = idcConf.activeIdcList;

        for(SystemConfig.IdcInfo z : idcList){
            idcName = z.name;
            try{
                DefltExtend defltExtend = zbLoginService.loginZb(idcName);
                ZB_AUTH_MAP.put(idcName, defltExtend);
            }catch (Exception e){
                logger.error("scheduled zb login error in idc = {},{}", idcName, e.getMessage());
            }
        }
        logger.debug("scheduled zb login task ends, AUTH_MAP= {}", ZB_AUTH_MAP.toString());
        return EnumBooleanResult.TRUE;


    }





}
