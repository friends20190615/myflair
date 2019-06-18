package com.myflair.wechart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.myflair.common.utils.SysUtil;
import com.myflair.common.vo.JSONResult;
import com.myflair.common.vo.WeixinJSConfigVO;
import com.myflair.common.vo.WeixinShareData;
import com.myflair.wechart.service.IWeixinJSConfigService;
import com.myflair.wechart.sharedata.OriginShare;

@Controller
@RequestMapping("/weixinShare")
public class WeixinShare {

    @Autowired
    private IWeixinJSConfigService weixinJSConfigService;
    
	@RequestMapping("/getShareData")
    @ResponseBody
    public String getShareData(String mobile,String url,String origin){
		String env = SysUtil.getResourceValue("env");
        JSONResult<Object> result = JSONResult.getCommonResult(null);
        WeixinShareData weixinShareData;
        weixinShareData = OriginShare.OriginShareMethod(mobile, url, origin);
        WeixinJSConfigVO weixinJSConfigVO =  weixinJSConfigService.getWeixinJSConfig(weixinShareData.getUrl());;
//        if("prod".equals(env)){
//        	weixinJSConfigVO = weixinJSConfigService.getWeixinJSConfig(weixinShareData.getUrl());
//        }else{
//        	weixinJSConfigVO =  new WeixinJSConfigVO();
//        }
        weixinShareData.setWeixinJSConfigVO(weixinJSConfigVO);
        result.setResult(weixinShareData);
        result.setStatus(JSONResult.SUCCUESS_CODE);
        return JSON.toJSONString(result);
    }
}
