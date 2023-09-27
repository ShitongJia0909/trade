package com.jst.trajst.dashboard.service;

import com.alibaba.fastjson.JSONObject;
import com.jst.jstdao.model.ZbHost;
import com.jst.jstdao.repository.ZbHostRepository;
import com.jst.trajst.dashboard.utils.ZbConvertUtil;
import com.jst.trajst.dashboard.vo.zabbix.dto.TrendResps;
import com.jst.trajst.dashboard.vo.zabbix.dto.ZbVo;
import io.github.hengyunabc.zabbix.api.Request;
import io.github.hengyunabc.zabbix.api.RequestBuilder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ZabbixServiceImpl implements ZabbixService{

    private final static Logger logger = LoggerFactory.getLogger(ZabbixServiceImpl.class);

    @Resource
    ZbHostRepository zbHostRepository;

    @Override
    public List<ZbHost> findHostsFrom(DefltExtend defaultZabbixApi, String idc) {
        logger.info("getting allzbHost from zabbix. idc is {}", idc);
        Request request = RequestBuilder.newBuilder().method("host.get")
                .paramEntry("output", new String[]{"interfaceid", "host"})
                .paramEntry("selectInterfaces", new String[]{"interfaceid", "ip"})
                .build();
        JSONObject jsonResponse = defaultZabbixApi.call(request);
        logger.info("getting zaHost from zabbix finished. result ={}", StringUtils.left(jsonResponse.toString(), 1000));
        List<ZbHost> zbHosts = ZbConvertUtil.convertToHostMapping(jsonResponse, idc);
        return zbHosts;
    }

    @Override
    public List<TrendResps> getHistoryData(ZbVo zbVo) {
        logger.info("get historydata begin. input param = {}", zbVo.toString());
        List<ZbHost> findHistoryByIp = zbHostRepository.findAllByIpAndDc(zbVo.getIp(), zbVo.getIdc());
        if(findHistoryByIp.size() > 0){
            List<TrendResps> results = new ArrayList<>();
            String hostid = findHistoryByIp.get(0).getHostid();

            //根据hostid和name查出itemid

            JSONObject itemFilter = new JSONObject();
            itemFilter.put("hostid", new String[]{hostid});
            itemFilter.put("name", new String[]{zbVo.getName()});
            Request request = RequestBuilder.newBuilder().method("item.get")
                    .paramEntry("filter", itemFilter)
                    .build();




        }


        return null;
    }
}
