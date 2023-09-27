package com.jst.trajst.dashboard.config;

import com.jst.common.es.SystemConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class RuntimeConfig {

    @Value("${sms.buzId}")
    private String buzId;
    @Value("${sms.templateId}")
    private String templateId;
    @Value("${sms.signKey}")
    private String signKey;
    @Value("${sms.url}")
    private String url;
    @Value("${spring.config.location}")
    private String configLocation;

    @Bean(name = "idcConf")
    public SystemConfig.IdcConf loadIdcConf(SystemConfig config) throws IOException{
        return config.loadIdcConf(configLocation);
    }




}
