package com.jst.common.es;

import com.alibaba.fastjson.JSONObject;
import com.carrotsearch.hppc.predicates.FloatObjectPredicate;
import com.jst.common.utils.AESUtil;
import com.jst.common.utils.ResourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SystemConfig {

    private static final Logger logger = LoggerFactory.getLogger(SystemConfig.class);

    public static final String AES_KEY = "ZANYEBUZHIDAOZHESHISHA";

    public static class IdcInfo{
        public String city;
        public String cityName;
        public String name;
        public String role;
        public String esHosts;
        public String serverHosts;
        public String zabbixHosts;
        public String zabbixUsername;
        public String zabbixClrPwd;
        public String zabbixUnclrPwd;

    }

    public static class IdcConf{

        public String activeCity;
        public String activeIdc;
        public List<IdcInfo> idcList = new ArrayList<>();
        public List<IdcInfo> activeIdcList = new ArrayList<>();

    }

    // idcInfo 是配置文件中的格式，一个机房一个
    // idcConf 本机器上使用的idcinfo，还包括activeCity，用来和前端交户用的

    public IdcConf loadIdcConf(String path) throws IOException {
        String str = ResourceUtil.getFile(path, "/idcConfig.json");
        IdcConf idcConf = JSONObject.parseObject(str, IdcConf.class);
        idcConf.idcList.stream().filter(idcInfo -> idcConf.activeCity.equals(idcInfo.city)).collect(Collectors.toList());
        for(IdcInfo idcInfo :idcConf.activeIdcList){
            try {
                idcInfo.zabbixClrPwd = AESUtil.decrypt(idcInfo.zabbixUnclrPwd, AES_KEY);
            }catch (Exception e){
                logger.error("decrpty error in idc = {}", idcInfo.name);
            }
        }
        return idcConf;

    }


    public static class SecretInfo{
        public String aesKey;
        public String rsaPublicKey;
        public String rsaPrivateKey;
    }


    public SecretInfo loadSecretInfo(String path) throws IOException{
        SecretInfo secretInfo = JSONObject.parseObject(ResourceUtil.getFile(path, "/secret.json"), SecretInfo.class);
        secretInfo.aesKey = AESUtil.decrypt(secretInfo.aesKey, AES_KEY);
        secretInfo.rsaPrivateKey = AESUtil.decrypt(secretInfo.rsaPrivateKey, AES_KEY);
        secretInfo.rsaPublicKey = AESUtil.decrypt(secretInfo.rsaPublicKey, AES_KEY);
        return secretInfo;
    }


}
