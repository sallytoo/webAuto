package com.lemon.web.auto.util;

import java.io.IOException;
import java.util.Properties;

public class PropertiesUtils {

    private static Properties urlProperties;
//    private static Properties xxxpProperties;

    static {
        if (urlProperties==null){
            urlProperties = new Properties();
            try {
                urlProperties.load(PropertiesUtils.class.getResourceAsStream("/url.properties"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //获得完整的URL路径
    public static String getUrl(String urlKey){
        return urlProperties.getProperty("base_url")+urlProperties.getProperty(urlKey);


    }

    public static void main(String[] args) {
//         System.out.println(urlProperties.getProperty("base_url") );
//         System.out.println(urlProperties.getProperty("login_url") );
 System.out.println(getUrl("login_url") );

        }

}
