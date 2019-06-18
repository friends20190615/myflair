package com.myflair.third.controller;

import com.alibaba.fastjson.JSONObject;
import com.myflair.common.intercepter.StartLister;
import com.myflair.common.pojo.MsgPushEntity;
import com.myflair.common.utils.Md5Util;
import com.myflair.third.service.YouZanDataService;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.concurrent.Callable;

/**
 * Created by wangjiulin on 2018/4/11.
 */
public class YouZanHandler implements Callable<Object> {

    private final static Logger logger = Logger.getLogger(YouZanHandler.class);


    private static final int mode = 1 ; //服务商
    private static final String clientId="4fe3d03d41efa1185c"; //服务商的秘钥证书
    private static final String clientSecret="59399c80cc8785ac3111773a27b4cfc6";//服务商的秘钥证书

    private YouZanDataService youZanDataService = (YouZanDataService) StartLister.getApplicationContext().getBean("youZanDataService");

    private MsgPushEntity entity;

    public YouZanHandler(MsgPushEntity entity) {
        this.entity = entity;
    }

    @Override
    public Object call() throws Exception {
        JSONObject res = new JSONObject();
        res.put("code", 0);
        res.put("msg", "success");
        /**
         *  判断是否为心跳检查消息
         *  1.是则直接返回
         */
        String msg="";
        try{
            if (entity.isTest()) {
                return res.toJSONString();
            }
            /**
             * 解析消息推送的模式  这步判断可以省略
             * 0-商家自由消息推送 1-服务商消息推送
             * 以服务商 举例
             * 判断是否为服务商类型的消息
             * 否则直接返回
             */
            if (entity.getMode() != mode ){
                return res.toJSONString();
            }
            /**
             * 判断消息是否合法
             * 解析sign
             * MD5 工具类开发者可以自行引入
             */
            //Todo 这个签名验证 自己在网上找的md5 可能和他们那边不一样 签名完了内容有变化。。先注释掉
            /*String sign= Md5Util.digest(clientId+entity.getMsg()+clientSecret);
            if (!sign.equals(entity.getSign())){
                return res.toJSONString();
            }*/
            /**
             * 对于msg 先进行URI解码
             */
            try {
                msg= URLDecoder.decode(entity.getMsg(), "utf-8");
            } catch (UnsupportedEncodingException e) {
                logger.error("解码失败："+e+",msg信息为："+msg);
            }
            /**
             *  ..........
             *  接下来是一些业务处理
             *  判断当前消息的类型 比如交易
             *
             */
            if ("TRADE_ORDER_STATE".equals(entity.getType())) {
                //订单状态事件
                youZanDataService.dataHandler(msg);

            }else if("SCRM_CUSTOMER_EVENT".equals(entity.getType()) /*&& "CUSTOMER_CREATED".equals(entity.getStatus())*/){
                //客户消息
                youZanDataService.handlerCustomer(msg);
            }else if("SCRM_CUSTOMER_CARD".equals(entity.getType())){
                //会员卡消息
                youZanDataService.handlerCustomer(msg);

            }else if("TRADE_ORDER_EXPRESS".equals(entity.getType())){
                youZanDataService.handlerExpress(msg);
            }
        }catch(Exception e){
            logger.error("接受消息出错："+e+"消息类型："+entity.getType()+",msg信息为："+msg);
            res.put("code", -1);
            res.put("msg", "error");

        }
        return res;
    }
}


