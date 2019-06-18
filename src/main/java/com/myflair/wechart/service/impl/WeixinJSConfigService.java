package com.myflair.wechart.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.myflair.common.utils.HttpUtil;
import com.myflair.common.vo.WeixinJSConfigVO;
import com.myflair.wechart.service.IWeixinJSConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: GrayF(jy.feng@zuche.com))
 * Date: 2015/4/14
 * Time: 11:08
 */
@Service
public class WeixinJSConfigService implements IWeixinJSConfigService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private String appId = "wx7f8b14b82bb30bec";

    private String appSecret = "82f5be50436c27cf0ddd72e3b54d26a1";

    private String accessTokenUrl="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    private String apiTicketUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";

    //微信参数
    private String accessToken;
    private String jsApiTicket;
    //获取参数的时刻
    private Long getTiketTime = 0L;
    private Long getTokenTime = 0L;
    //参数的有效时间,单位是秒(s)
    private Long tokenExpireTime = 0L;
    private Long ticketExpireTime = 0L;


    @Override
    public WeixinJSConfigVO getWeixinJSConfig(String url) {
        Map<String, String> wechatParam = getWechatParam(url);
        WeixinJSConfigVO weixinJSConfigVO = new WeixinJSConfigVO();
        weixinJSConfigVO.setAppId(wechatParam.get("appid"));
        weixinJSConfigVO.setNonceStr(wechatParam.get("nonceStr"));
        weixinJSConfigVO.setSignature(wechatParam.get("signature"));
        weixinJSConfigVO.setTimestamp(wechatParam.get("timestamp"));
        return weixinJSConfigVO;
    }

    public Map<String, String> getWechatParam(String url){
        //当前时间
        long now = System.currentTimeMillis();
        log.info("currentTime====>"+now+"ms");

        //判断accessToken是否已经存在或者token是否过期
        if(org.apache.commons.lang.StringUtils.isBlank(accessToken)||(now - getTokenTime > tokenExpireTime*1000)){
            JSONObject tokenInfo = getAccessToken();
            if(tokenInfo != null){
                accessToken = tokenInfo.getString("access_token");
                tokenExpireTime = tokenInfo.getLongValue("expires_in");
                //获取token的时间
                getTokenTime = System.currentTimeMillis();
                log.info("accessToken====>"+accessToken);
                log.info("tokenExpireTime====>"+tokenExpireTime+"s");
                log.info("getTokenTime====>"+getTokenTime+"ms");
            }else{
                log.error("====>tokenInfo is null~");
                log.error("====>failure of getting tokenInfo,please do some check~");
            }
        }

        //判断jsApiTicket是否已经存在或者是否过期
        if(org.apache.commons.lang.StringUtils.isBlank(jsApiTicket)||(now - getTiketTime > ticketExpireTime*1000)){
            JSONObject ticketInfo = getJsApiTicket();
            if(ticketInfo!=null){
                log.info("ticketInfo====>"+ticketInfo.toJSONString());
                jsApiTicket = ticketInfo.getString("ticket");
                ticketExpireTime = ticketInfo.getLongValue("expires_in");
                getTiketTime = System.currentTimeMillis();
                log.info("jsApiTicket====>"+jsApiTicket);
                log.info("ticketExpireTime====>"+ticketExpireTime+"s");
                log.info("getTiketTime====>"+getTiketTime+"ms");
            }else{
                log.error("====>ticketInfo is null~");
                log.error("====>failure of getting tokenInfo,please do some check~");
            }
        }

        //生成微信权限验证的参数
        Map<String, String> wechatParam= makeWXTicket(jsApiTicket,url);
        return wechatParam;
    }

    //获取accessToken
    private JSONObject getAccessToken(){
        //String accessTokenUrl = https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET
        String requestUrl = accessTokenUrl.replace("APPID",appId).replace("APPSECRET",appSecret);
        log.info("getAccessToken.requestUrl====>"+requestUrl);
        JSONObject result = HttpUtil.doGetJson(requestUrl);
        return result ;
    }

    //获取ticket
    private JSONObject getJsApiTicket(){
        //String apiTicketUrl = https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi
        String requestUrl = apiTicketUrl.replace("ACCESS_TOKEN", accessToken);
        log.info("getJsApiTicket.requestUrl====>"+requestUrl);
        JSONObject result = HttpUtil.doGetJson(requestUrl);
        return result;
    }

    //生成微信权限验证的参数
    public Map<String, String> makeWXTicket(String jsApiTicket, String url) {
        Map<String, String> ret = new HashMap<String, String>();
        String nonceStr = createNonceStr();
        String timestamp = createTimestamp();
        String string1;
        String signature = "";

        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsApiTicket +
                "&noncestr=" + nonceStr +
                "&timestamp=" + timestamp +
                "&url=" + url;
        log.info("String1=====>"+string1);
        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
            log.info("signature=====>"+signature);
        }catch (NoSuchAlgorithmException e){
            log.error("WeChatController.makeWXTicket=====Start");
            log.error(e.getMessage(),e);
            log.error("WeChatController.makeWXTicket=====End");
        }catch (UnsupportedEncodingException e){
            log.error("WeChatController.makeWXTicket=====Start");
            log.error(e.getMessage(),e);
            log.error("WeChatController.makeWXTicket=====End");
        }
        ret.put("url", url);
        ret.put("jsapi_ticket", jsApiTicket);
        ret.put("nonceStr", nonceStr);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);
        ret.put("appid", appId);
        return ret;
    }
    //字节数组转换为十六进制字符串
    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
    //生成随机字符串
    private static String createNonceStr() {
        return UUID.randomUUID().toString();
    }
    //生成时间戳
    private static String createTimestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

}
