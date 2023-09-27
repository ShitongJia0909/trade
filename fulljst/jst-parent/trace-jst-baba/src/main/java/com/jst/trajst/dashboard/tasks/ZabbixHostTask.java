package com.jst.trajst.dashboard.tasks;

import com.jst.common.es.SystemConfig;
import com.jst.jstdao.model.ZbHost;
import com.jst.trajst.dashboard.enums.EnumBooleanResult;
import com.jst.trajst.dashboard.server.schedule.ITask;
import com.jst.trajst.dashboard.service.DefltExtend;
import com.jst.trajst.dashboard.service.GroupIpService;
import com.jst.trajst.dashboard.service.ZabbixService;
import com.jst.trajst.dashboard.service.ZbLoginService;
import com.sun.java.swing.plaf.windows.WindowsInternalFrameTitlePane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component(value = "ZabbixHostTask")
public class ZabbixHostTask implements ITask {

    private static final Logger logger = LoggerFactory.getLogger(ZabbixHostTask.class);
    public final static ConcurrentHashMap<String, String> ZB_API_MAP = new ConcurrentHashMap<>();

    @Resource
    ZbLoginService zbLoginService;
    @Resource
    ZabbixService zabbixService;
    @Resource
    GroupIpService groupIpService;

    @Resource(name = "idcConf")
    SystemConfig.IdcConf idcConf;


    @Override
    public EnumBooleanResult doPerform()  {
        logger.info("ZabbixHostTask task begins");
        if(ZB_API_MAP.isEmpty()){
            logger.error("ZB_API_MAP is empty.");
            return EnumBooleanResult.FALSE;
        }
        for(Map.Entry<String, String> entry : ZB_API_MAP.entrySet()){
            String idc = entry.getKey();
            logger.info("zabbixHostTask begins in dc = {}", idc);
            DefltExtend defaultZbApi = null;
            try{
                defaultZbApi = zbLoginService.loginZb(idc);
                logger.info("current defaultZbApi is {}", defaultZbApi.toString());
                List<ZbHost> hostsFromZb = zabbixService.findHostsFrom(defaultZbApi, idc);
                groupIpService.updateZbHosts(hostsFromZb,idc);
            } catch (Exception e){
                logger.error("schedule task error in idc = {}", idc);
            }
            logger.info("cheduleHostTask task finished. ");
        }

        return EnumBooleanResult.TRUE;
    }

    @PostConstruct
    public void init() {
        try{
            buildZbUrl();
        }catch (Exception e){
            logger.error("init zbHost error. detail msg = {}", e.getMessage());
        }
    }

    private void buildZbUrl(){
        List<SystemConfig.IdcInfo> idcList = idcConf.activeIdcList;
        for(SystemConfig.IdcInfo z : idcList){
            String zabbixHosts = z.zabbixHosts;
            String zabbixUrl = new StringBuilder(zabbixHosts).toString();
            ZB_API_MAP.put(z.name, zabbixUrl);
        }
        logger.info("zb_url_map build finished, map = {}", ZB_API_MAP.toString());
    }
}
