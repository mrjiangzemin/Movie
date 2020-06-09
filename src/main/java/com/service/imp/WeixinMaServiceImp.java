package com.service.imp;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.entity.NCUUser;
import com.entity.WxMaAuthResult;
import com.entity.WxMaConfiguration;
import com.entity.WxMaUserInfoExtends;
import com.service.IUserService;
import com.service.WeixinMaService;
import com.util.TokenProccessor;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeixinMaServiceImp implements WeixinMaService {

    private static Logger logger = Logger.getLogger(WxMaService.class);

    @Autowired
    private IUserService iUserService;

    private WxMaService wxMaService = WxMaConfiguration.getWxMaService();



    @Override
    public WxMaAuthResult dealLoginAuth(String code, String data, String iv) {
        WxMaJscode2SessionResult session = new WxMaJscode2SessionResult();
        //调微信官方接口获得sesssion_key openid存到 session对象里
        try {
//code换取sessionKey
            session = wxMaService.getUserService().getSessionInfo(code);

        } catch (WxErrorException e) {

        }
        WxMaAuthResult wxMaAuthResult = new WxMaAuthResult();
//该类用来存储用户信息
        NCUUser ncuUser = new NCUUser();
        WxMaUserInfoExtends wxMaUserInfoExtends = new WxMaUserInfoExtends();
        WxMaUserInfo wxMaUserInfo = new WxMaUserInfo();
        //暂时写死失效时间
        int expire = 3600;
        String sessionKey = session.getSessionKey();
        String rawData = StringEscapeUtils.unescapeHtml4(data);
        // 解密用户信息
        try {
//sessionkey data iv 解密用户信息
            wxMaUserInfo = this.wxMaService.getUserService().getUserInfo(sessionKey, data, iv);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        BeanUtils.copyProperties(wxMaUserInfo, wxMaUserInfoExtends);
        String third_session = TokenProccessor.getInstance().makeToken();
        wxMaAuthResult.setToken(third_session);
        String user_id = TokenProccessor.getInstance().makeToken();
        wxMaUserInfoExtends.setUser_id(user_id);

        ncuUser.setOpenid(wxMaUserInfo.getOpenId());
        ncuUser.setUserName(wxMaUserInfo.getNickName());

        //通过openid获取或新增用户信息
        try {
            if(session.getOpenid()==null){
                throw new RuntimeException("获取openId失败");
            }
            if (iUserService.countAny(session.getOpenid()).size() > 0) {
                //存在 数据库不做操作
                wxMaAuthResult.setIsReg(true);
                wxMaAuthResult.setSuccess(true);
                wxMaAuthResult.setUserInfo(wxMaUserInfoExtends);
            } else {
                //不存在 数据库保存信息
                iUserService.addUser(ncuUser);
                wxMaAuthResult.setSuccess(true);
                wxMaAuthResult.setIsReg(false);
                wxMaAuthResult.setUserInfo(wxMaUserInfoExtends);
            }
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }


        return wxMaAuthResult;
    }
}
