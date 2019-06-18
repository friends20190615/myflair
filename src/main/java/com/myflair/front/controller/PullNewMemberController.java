package com.myflair.front.controller;

import com.alibaba.fastjson.JSONObject;
import com.myflair.common.pojo.PullNewMember;
import com.myflair.common.utils.EncryptUtils;
import com.myflair.front.dao.PullNewMemberDao;
import com.myflair.front.service.PullNewMemberService;
import com.myflair.third.dao.YouZanDataDao;
import com.youzan.open.sdk.exception.KDTException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 2018/3/25.
 */
@Controller
@RequestMapping("/pull")
public class PullNewMemberController {

    private final static Logger logger = Logger.getLogger(PullNewMemberController.class);

    @Autowired
    private PullNewMemberService pullNewMemberService;
    @Autowired
    private YouZanDataDao youZanDataDao;
    @Autowired
    private PullNewMemberDao pullNewMemberDao;


    @RequestMapping("newMember")
    @ResponseBody
    public String pullNewMember(HttpServletRequest request){
        JSONObject res = new JSONObject();
        res.put("code", 0);
        res.put("msg", "success");
        try {
            String inviteMobile = request.getParameter("inviteMobile");
            String beInviteMobile = request.getParameter("beInviteMobile");
            Map<String,Object> params = new HashMap<String, Object>();
            if(StringUtils.isNotBlank(inviteMobile)){
                params.put("inviteMobile",StringUtils.isNotBlank(EncryptUtils.decrypt(EncryptUtils.LX_KEY,inviteMobile))?EncryptUtils.decrypt(EncryptUtils.LX_KEY,inviteMobile):inviteMobile);
            }else{
                params.put("inviteMobile","13264257229");
            }

            params.put("beInviteMobile",beInviteMobile);
            if(params.get("inviteMobile").equals(params.get("beInviteMobile"))){
                res.put("code", -2);
                res.put("msg", "亲，自己不能邀请自己哟！");
                return res.toJSONString();
            }
            if(StringUtils.isNotBlank(beInviteMobile)){
                 res = (JSONObject) pullNewMemberService.pullNewMemHandler(params);
            }
        }catch (KDTException e){
            if(e.getMessage().contains("已经领取过了")){
                res.put("code",2);
                res.put("result","success");
                res.put("msg","老用户");
            }
            logger.error(e.getMessage());
        }catch (Exception e){
            logger.error("拉新H5接口报错："+e.getMessage());
            res.put("code", -1);
            res.put("msg", "error");
        }

        return res.toJSONString();
    }



    @RequestMapping("regist")
    @ResponseBody
    public String regist(HttpServletRequest request,String mobile){
        JSONObject res = new JSONObject();
        res.put("code", 0);
        res.put("success",true);
        res.put("msg", "success");
        try {
            if(StringUtils.isNotBlank(mobile)){
                Map<String,Object> params = new HashMap<String, Object>();
                params.put("mobile",mobile);
                Integer count = youZanDataDao.getRegistCouponByMobile(params);
                if(count != null && count > 0){
                    res.put("code",2);
                    res.put("success",true);
                }else{
                    pullNewMemberService.pullRegistHandler(res,mobile);
                }
                List<PullNewMember> pullNewMembers = pullNewMemberDao.getBimListByInviteMobile(params);
                res.put("result",pullNewMembers);
            }
        }catch (KDTException e){
            if(e.getMessage().contains("已经领取过了")){
                res.put("code",2);
                res.put("success",true);
            }
            logger.error(e.getMessage());
        }catch (Exception e){
            logger.error("拉新H5接口报错："+e.getMessage());
            res.put("code", -1);
            res.put("success",false);
            res.put("msg","系统繁忙");
        }

        return res.toJSONString();
    }
}
