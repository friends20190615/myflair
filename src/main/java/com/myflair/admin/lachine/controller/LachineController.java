package com.myflair.admin.lachine.controller;

import com.myflair.admin.lachine.service.LachineService;
import com.myflair.common.constant.NewsConstant;
import com.myflair.common.pojo.PullNewMember;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 2018/7/1.
 */
@Controller
@RequestMapping(value = "/lachine")
public class LachineController{

        private final static Logger logger = Logger.getLogger(LachineController.class);

        @Autowired
        private LachineService lachineService;


        @RequestMapping(value = "/lachinelist")
        public ModelAndView newslist(HttpServletRequest request) {
            ModelAndView mv = new ModelAndView();
            mv.setViewName("/WEB-INF/views/lachine/lachineList.jsp");
            String pageStr = request.getParameter("page");
            String statusStr = request.getParameter("status");
            int pageNo = NumberUtils.toInt(pageStr, 1);
            int status = NumberUtils.toInt(statusStr, 0);
            Map<String, Object> params = new HashMap<String, Object>();
            int start = (pageNo - 1) * NewsConstant.PAGESIZE;
            params.put("start", start);
            params.put("pageSize", NewsConstant.PAGESIZE);
            if(StringUtils.isNotBlank(request.getParameter("inviteMobile"))){
                params.put("inviteMobile", request.getParameter("inviteMobile"));
            }
            if(StringUtils.isNotBlank(request.getParameter("returnTicketStatus"))){
                params.put("returnTicketStatus",request.getParameter("returnTicketStatus"));
            }
            if(StringUtils.isNotBlank(request.getParameter("beInviteMobile"))){
                params.put("beInviteMobile",request.getParameter("beInviteMobile"));
            }
            if (status != 0) {
                params.put("status", status);
            }
            try {
                List<PullNewMember> list = lachineService.queryList(params);
                int total = lachineService.queryCount(params);
                mv.addObject("list", list);
                PullNewMember pullNewMember = new PullNewMember();
                pullNewMember.setInviteMobile((String) params.get("inviteMobile"));
                if( params.get("returnTicketStatus")!= null){
                    pullNewMember.setReturnTicketStatus(Integer.valueOf((String) params.get("returnTicketStatus")));
                }
                pullNewMember.setBeInviteMobile((String) params.get("beInviteMobile"));
                int totalPage = (total - 1) / NewsConstant.PAGESIZE + 1;
                mv.addObject("mem",pullNewMember);
                mv.addObject("count", total);// 总条数
                mv.addObject("totalPage", totalPage);// 总页数
                mv.addObject("dqPage", pageNo);// 当前页
                mv.addObject("pageSize", NewsConstant.PAGESIZE);// 当前页
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
            return mv;
        }
}
