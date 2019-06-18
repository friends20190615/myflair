package com.myflair.admin.activeconfig.controller;

import com.alibaba.fastjson.JSONObject;
import com.myflair.admin.activeconfig.service.ActiceService;
import com.myflair.common.constant.NewsConstant;
import com.myflair.common.pojo.ActiveConfigInfo;
import com.myflair.common.pojo.Result;
import com.myflair.common.utils.CookieUtil;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 2018/5/20.
 */
@Controller
@RequestMapping(value = "/activeConfig")
public class ActiveConfigController {
    private final static Logger logger = Logger.getLogger(ActiveConfigController.class);

    @Autowired
    private ActiceService acticeService;


    @RequestMapping(value = "/activelist")
    public ModelAndView newslist(HttpServletRequest request,
                                 HttpServletResponse response, Model model) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/WEB-INF/views/active/activeList.jsp");
        String pageStr = request.getParameter("page");
        String statusStr = request.getParameter("status");
        int pageNo = NumberUtils.toInt(pageStr, 1);
        int status = NumberUtils.toInt(statusStr, 0);
        Map<String, Object> params = new HashMap<String, Object>();
        int start = (pageNo - 1) * NewsConstant.PAGESIZE;
        params.put("start", start);
        params.put("pageSize", NewsConstant.PAGESIZE);
        if (status != 0) {
            params.put("status", status);
        }
        try {
            List<ActiveConfigInfo> list = acticeService.queryList(params);
            int total = acticeService.queryCount(params);
            mv.addObject("list", list);

            int totalPage = (total - 1) / NewsConstant.PAGESIZE + 1;
            mv.addObject("count", total);// 总条数
            mv.addObject("totalPage", totalPage);// 总页数
            mv.addObject("dqPage", pageNo);// 当前页
            mv.addObject("pageSize", NewsConstant.PAGESIZE);// 当前页
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return mv;
    }

    @RequestMapping(value = "/activeDetail")
    public ModelAndView useradd(HttpServletRequest request,
                                HttpServletResponse response, Model model) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/WEB-INF/views/active/activeDetail.jsp");
        String action = request.getParameter("action");
        mv.addObject("action", action);
        if ("modify".equals(action)) {
            String idStr = request.getParameter("id");
            if (null != idStr && !"".equals(idStr)) {
                Long id = Long.parseLong(idStr);
                ActiveConfigInfo activeConfigInfo  = acticeService.selectById(id);
                mv.addObject("active", activeConfigInfo);
            }
        }
        return mv;
    }

    @RequestMapping(value = "/saveActive")
    @ResponseBody
    public String saveUpdate(@RequestParam(value = "req") String req,
                           @RequestParam(value = "action") String action,
                           HttpServletRequest request, HttpServletResponse response,
                           Model model) {

        Result<String> result = new Result<String>();
        logger.error(req);
        ActiveConfigInfo activeConfigInfo = (ActiveConfigInfo) JSONObject.parseObject(req, ActiveConfigInfo.class);
        activeConfigInfo.setModifyEmp(Long.valueOf(String.valueOf(CookieUtil.getCookie("uid",request).getValue())));
        activeConfigInfo.setCreateEmp(Long.valueOf(String.valueOf(CookieUtil.getCookie("uid",request).getValue())));
        if ("modify".equals(action)) {
            result = acticeService.update(activeConfigInfo);
        } else {
            result = acticeService.addActiveConfigInfo(activeConfigInfo);
        }
        return JSONObject.toJSONString(result);
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public String delete(@RequestParam(value = "id") Long id,
                         HttpServletRequest request, HttpServletResponse response,
                         Model model) {

        Result<String> result = new Result<String>();

        int ret = acticeService.delete(id);
        if (ret > 0) {
            result.setStatus(0);
            result.setMsg("删除成功!");
        } else {
            result.setMsg("删除失败，请重试!");
            result.setStatus(-1);
        }
        return JSONObject.toJSONString(result);
    }
}
