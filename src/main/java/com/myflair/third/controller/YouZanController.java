package com.myflair.third.controller;

import com.alibaba.fastjson.JSONObject;
import com.myflair.common.pojo.MsgPushEntity;
import com.myflair.third.service.YouZanDataService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutionException;

/**
 * Created by user on 2018/3/24.
 */
@Controller
@RequestMapping("/youzan")
public class YouZanController {

    private final static Logger logger = Logger.getLogger(YouZanController.class);


    @Autowired
    private YouZanDataService youZanDataService;

    @RequestMapping(value = "/pull")
    @ResponseBody
    public String receiveData(HttpServletRequest request) {
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        JSONObject res= null;
        try{
            reader = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
            String line = null;
            while ((line = reader.readLine()) != null){
                sb.append(line);
            }
            JSONObject jsonObject = JSONObject.parseObject(sb.toString());
            MsgPushEntity entity = JSONObject.toJavaObject(jsonObject,MsgPushEntity.class);
            res = (JSONObject) CommonThreadPool.execute(new YouZanHandler(entity));
        } catch (IOException e){
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            try{
                if (null != reader){ reader.close();}
            } catch (IOException e){
                e.printStackTrace();
                logger.error("解析数据出错：{}",e);
            }
        }
        return res.toJSONString();
    }
}
