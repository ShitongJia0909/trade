package com.jst.trajst.dashboard.service;

import com.jst.jstdao.model.ZbHost;
import com.jst.jstdao.repository.ZbGroupRepostory;
import com.jst.jstdao.repository.ZbHostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GroupIpService {

    private static final Logger logger = LoggerFactory.getLogger(GroupIpService.class);

    @Resource
    ZbGroupRepostory zbGroupRepostory;

    @Resource
    ZbHostRepository zbHostRepository;

    public List<ZbHost> updateZbHosts(List<ZbHost> zbHostsListFromZb, String idc){
        logger.info("deleting hosts in dc = {}", idc);
        zbHostRepository.deleteByDc(idc);
        logger.info("saving new Hosts in dc ={}", idc);
        return zbHostRepository.saveAll(zbHostsListFromZb);
    }






}
