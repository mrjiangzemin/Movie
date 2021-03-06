package com.entity;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;

public class WxMaAuthResult extends WxMaJscode2SessionResult {

    private String code;

    private String token;

    private int expiresIn = 3600;

    Boolean isReg = false; //是否保存过用户信息

    private boolean success;

    private WxMaUserInfoExtends userInfo;



    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public Boolean getReg() {
        return isReg;
    }

    public void setIsReg(Boolean reg) {
        isReg = reg;
    }

    public void setReg(Boolean reg) {
        isReg = reg;
    }

    public WxMaUserInfoExtends getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(WxMaUserInfoExtends userInfo) {
        this.userInfo = userInfo;
    }
}
