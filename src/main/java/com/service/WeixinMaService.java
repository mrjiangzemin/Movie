package com.service;

import com.entity.WxMaAuthResult;

public interface WeixinMaService {
    WxMaAuthResult dealLoginAuth(String code, String encryptedData, String iv);
}
