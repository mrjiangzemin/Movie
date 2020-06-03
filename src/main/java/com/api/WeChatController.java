package com.api;

import com.alibaba.fastjson.JSONObject;
import com.entity.Comment;
import com.entity.Movie;
import com.service.IMovieService;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping(value = "/api")
public class WeChatController {
    @Resource
    private IMovieService movieService;
    /**
     * 获取所有电影信息
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
//    @RequestMapping(value = "/loginAuth", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
//    public String loginAuth(String code,String encryptedData,String iv) throws WxErrorException {
//        if (StringUtils.isBlank(code)) {
//            return "empty jscode";
//        }
//        //处理登录授权 获得openid和sessionkey，生成userid 并将session存到缓存中
//        WxMaAuthResult result =
//                weixinMaService.dealLoginAuth(code,encryptedData,iv);
//        return JsonUtils.objectToJson(result);
//
//    }
}
