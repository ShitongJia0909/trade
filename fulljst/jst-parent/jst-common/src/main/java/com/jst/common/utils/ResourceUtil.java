package com.jst.common.utils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;

import java.io.*;

public class ResourceUtil {

    public static String getFileFromResource(String path) throws IOException {
        StringBuffer strBuffer = new StringBuffer();
        ClassPathResource classPathResource = new ClassPathResource(path);
        try(InputStream inputStream = classPathResource.getInputStream()){
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String str;
            while((str =reader.readLine()) != null){
                strBuffer.append(str);
            }
        }
        return strBuffer.toString();
    }

    public static String getFile(String dirPath, String fileName) throws IOException{
        String str;
        if(StringUtils.isEmpty(dirPath)){
            str = ResourceUtil.getFileFromResource(fileName);
        }else{
            str = ResourceUtil.getFileFromResource(dirPath + fileName);
        }
        return str;
    }

    public static String getFile(String path) throws IOException{
        StringBuffer strBuffer = new StringBuffer();
        File file = new File(path);
        try(InputStream inputStream = new FileInputStream(file)){
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String str;
            while((str = reader.readLine()) != null){
                strBuffer.append(str);
            }
        }
        return strBuffer.toString();
    }

}
