package com.jst.trajst.dashboard.service;


import com.jst.jstdao.model.ZbHost;
import com.jst.trajst.dashboard.vo.zabbix.dto.TrendResps;
import com.jst.trajst.dashboard.vo.zabbix.dto.ZbVo;

import java.util.List;

// 接口上不用加注解
public interface ZabbixService {

    List<TrendResps> getHistoryData(ZbVo zbVo);
    List<ZbHost> findHostsFrom(DefltExtend defaultZabbixApi, String idc);

}
