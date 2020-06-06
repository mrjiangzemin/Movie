package com.api;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.alibaba.fastjson.JSONObject;
import com.entity.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.IMovieService;
import com.service.WeixinMaService;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/api")
public class WeChatController {


    @Resource
    private IMovieService movieService;

    @Autowired
    private WeixinMaService weixinMaService;


    /**
     * 获取所有电影信息
     *
     * @return
     */
    @RequestMapping(value = "/movies")
    @ResponseBody
    public JSONObject getAllMovies() {
        JSONObject obj = new JSONObject();
        List<Movie> list = movieService.findAllMovies(1);
        obj.put("data", list);
        return obj;
    }

    @RequestMapping(value = "/loginAuth", produces = "application/json;")
    @ResponseBody
    public JSONObject loginAuth(
            @RequestBody WxRequest wxRequest) throws WxErrorException {
        JSONObject obj = new JSONObject();
        if (StringUtils.isBlank(wxRequest.getCode())) {
            obj.put("data", null);
            return obj;
        }
        //处理登录授权 获得openid和sessionkey，生成userid 并将session存到缓存中
        WxMaAuthResult result =
                weixinMaService.dealLoginAuth(wxRequest.getCode(), wxRequest.getEncryptedData(), wxRequest.getIv());
        List<WxMaAuthResult> results = new ArrayList<WxMaAuthResult>();
        results.add(result);
        obj.put("data", results);
        return obj;

    }


}
