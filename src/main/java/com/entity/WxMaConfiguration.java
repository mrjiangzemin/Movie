package com.entity;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.WxMaConfig;
import cn.binarywang.wx.miniapp.config.WxMaInMemoryConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WxMaConfiguration {

    // 此处获取配置的方式可以改成你自己的方式，也可以注解等方式获取配置等。
    @Value("#{configProperties['wechat.appId']}")
    private  String appId;
    @Value("#{configProperties['wechat.secret']}")
    private  String secret;
    @Value("#{configProperties['wechat.token']}")
    private  String token ;
    @Value("#{configProperties['wechat.aesKey']}")
    private String aesKey ;

    private static WxMaService wxMaService = null;

    @Bean
    public Object services(){
        WxMaInMemoryConfig config = new WxMaInMemoryConfig();
        config.setAppid(appId);
        config.setSecret(secret);
        config.setToken(token);
        config.setAesKey(aesKey);

        wxMaService = new WxMaServiceImpl();
        wxMaService.setWxMaConfig(config);

        return Boolean.TRUE;
    }

    public static WxMaService getWxMaService(){
        return wxMaService;
    }
}