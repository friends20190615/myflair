<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fun"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>用户管理</title>
    <link href="${baseContextPath}static/css/global.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${baseContextPath}static/jquery/jquery-1.7.1.min.js"></script>
    <script type="text/javascript" src="${baseContextPath}static/jquery/intranetCss_js.js"></script>
</head>
<body>
<div>
    <div class="brumbtext gray_666">
        <h1>  拉新数据> <span class="bold">数据列表</span></h1>
    </div>
    <div class="p10">
    </div>
    <div class="p0_10" >
        <form action="lachinelist.do" id="lachineForm" name="lachineForm"  <%--onsubmit="return query();"--%> method="post" style="">
            <table class="tab_inquiry m10_0" cellpadding="0" cellspacing="0">
                <tr>
                    <th>邀请人手机号：</th>
                    <td><input type="text" name="inviteMobile" id="inviteMobile" class="ipt_150" value="${mem.inviteMobile}"/></td>
                    <th>被邀请人手机号：</th>
                    <td><input type="text" name="beInviteMobile" id="beInviteMobile" class="ipt_150" value="${mem.beInviteMobile}"/></td>
                </tr>
                <tr>
                    <th>返回积分情况：</th>
                    <td>
                        <select name="returnTicketStatus" class="u181" id="returnTicketStatus"  >
                            <option selected="selected" value="">请选择</option>
                            <option value="0" <c:if test="${mem.returnTicketStatus == 0}">selected="selected"</c:if>>未返积分</option>
                            <option value="1" <c:if test="${mem.returnTicketStatus == 1}">selected="selected"</c:if>>已返积分</option>
                        </select>
                    </td>
                </tr>
                <tfoot>
                <tr>
                    <td colspan="8" class="ac p10_0"><input name="input2" type="submit" value="查　询" class="orange_btn_100"/></td>
                </tr>
                </tfoot>
            </table>
            <input type="hidden" name="page" id="page" value="${dqPage}">
            <input type="hidden" name="totalPage" id="totalPage" value="${totalPage}">
        </form>
        <!--列表信息-->
        <table class="tab_list tr_even ml" cellpadding="0" cellspacing="0" width="100%">
            <tr>
                <th>邀请人</th>
                <th>被邀请人</th>
                <th>邀请人返券</th>
                <th>邀请人返券时间</th>
                <th>被邀请人返券</th>
                <th>被邀请人返券时间</th>
            </tr>
            <tbody>
            <c:if test="${fun:length(list) > 0}">
                <c:forEach items="${list}" var="member">
                    <tr>
                        <td>${member.inviteMobile }</td>
                        <td>${member.beInviteMobile }</td>
                        <td>
                            <c:if test="${member.returnTicketStatus == 1}">以返积分</c:if>
                            <c:if test="${member.returnTicketStatus == 0}">未返积分</c:if>
                        </td>

                        <td><fmt:formatDate value="${member.returnTicketTime }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                        <td>
                            <c:if test="${member.beInviteReturnTicketStatus == 1}">以返券</c:if>
                            <c:if test="${member.beInviteReturnTicketStatus == 0}">未返券</c:if>
                        </td>
                        <td><fmt:formatDate value="${member.beInviteReturnTicketTime }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                    </tr>
                </c:forEach>
            </c:if>
            </tbody>
        </table>
    </div>
    <!-- 分页内容 -->
    <div class="height20"></div>
    <div class="page-nav">
        <ol>
            <li style="width:auto">每页 ${pageSize}条，共 ${count } 条</li>
            <li class="first"><a href="javascript:goPage(${dqPage },'','')">上一页</a></li>
            <c:if test="${totalPage > 0 }">
                <c:forEach  var="i" begin="1" end="${totalPage }">
                    <c:if test="${totalPage <= 5 }">
                        <li><a  <c:if test="${dqPage==i}"> class="cur"</c:if> href="javascript:goPage('',${i},'')">${i}</a></li>
                    </c:if>
                    <c:if test="${totalPage > 5 }">
                        <c:if test="${dqPage < 5 }" >
                            <li  <c:if test="${i > 5  }"> style="display:none;"</c:if>>
                                <a  <c:if test="${dqPage==i }"> class="cur"</c:if> href="javascript:goPage('',${i},'')">${i}</a></li>
                        </c:if>
                        <c:if test="${dqPage >= 5 }" >
                            <c:if test="${dqPage+2>totalPage }">
                                <li <c:if test="${i < totalPage-4 }"> style="display:none;"</c:if>>
                            </c:if>
                            <c:if test="${dqPage+2<=totalPage }">
                                <li <c:if test="${dqPage<=i+2 and dqPage>=i-2 }"> style="display:block;"</c:if>
                                <c:if test="${(dqPage>i+2 or dqPage<i-2)  }"> style="display:none;"</c:if>>
                            </c:if>
                            <a  <c:if test="${dqPage==i }"> class="cur"</c:if> href="javascript:goPage('',${i},'')">${i}</a></li>
                        </c:if>
                    </c:if>
                </c:forEach>
                <%-- <c:if test="${totalPage > 5 }">
                    <li>...</li>
                </c:if> --%>
            </c:if>
            <li class="last"><a href="javascript:goPage('','',${dqPage })">下一页</a></li>
            <li style="width:auto">共${totalPage}页</li>
            <li style="width:auto">到第
                <input type="text" style="width:20px;" id="inputpage" name="inputpage" />
                页
                <input class="serbacktlsub" style="margin-top:0" type="button" value="确定" onclick="inputpage();"/>
            </li>
        </ol>
    </div>
    <div class="height20"></div>
    <!-- 分页内容 结束 -->
</div>
<script type="text/javascript">
    $(document).ready(function(){
      /*  $(".edit").click(function(){
            var id = $(this).attr("itemid");
            //console.log("id:"+id);
            window.open ('activeDetail.do?action=modify&id='+id, 'newwindow', 'height=500, width=760, top=100, left=100, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, status=no');
        });

        $(".delete").on("click",function(){
            if(confirm("您确定要删除该条记录？")){
                var id = $(this).attr("itemid");
                $.ajax({
                    type: "POST",
                    url: "activeConfig/delete.do",
                    data: {id:id},
                    dataType: "json",
                    success:function(resp){
                        if(resp.status==0){
                            alert(resp.msg);
                            location.href = "activelist.do";//window.opener.location.href;
                            window.close();
                        }else{
                            alert(resp.msg);
                        }

                    }
                });
            }

        });*/

      /*  $("#addBtn").click(function(){
            openAddUserWindow();
        });*/
    });


   /* function openAddUserWindow(){
        window.open ('activeDetail.do?action=create', 'newwindow', 'height=500, width=760, top=100, left=300, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, status=no');

    }*/
    function inputpage(){
        var inputpage = $("#inputpage").val();
        var intPage = parseInt(inputpage);
        var totalPage = parseInt($("#totalPage").val());
        if(intPage){
            if(intPage<1 || intPage>totalPage){
                alert("错误的页码，请重新输入");
                $("#inputpage").val("");
            }else{
                $("#page").val(inputpage);
                $("#lachineForm").submit();
            }
        }else{
            alert("错误的页码，请重新输入");
            $("#inputpage").val("");
        }
    }
    function goPage(qpage,page,hpage){
        var p="";
        if(qpage!=""){
            if(qpage>1){
                p = eval(qpage-1);
            }else{
                p=qpage;
            }
        }else if(hpage!=""){
            if(hpage=='${totalPage}'){
                p=hpage;
            }else{
                p = eval(hpage+1);
            }
        }else if(page){
            p = page;
        }

        $("#page").val(p);
        $("#lachineForm").submit();
    }
    function query(){
        $("#lachineForm").submit();
        //return true;
    }


    /*function viewuser(userid){
        window.open ('activeDetail.do?type=view&id='+userid, 'newwindow', 'height=500, width=700, top=100, left=300, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, status=no');
    }*/
    function inputpage(){
        var inputpage = $("#inputpage").val();
        var intPage = parseInt(inputpage);
        var totalPage = parseInt($("#totalPage").val());
        if(intPage){
            if(intPage<1 || intPage>totalPage){
                alert("错误的页码，请重新输入");
                $("#inputpage").val("");
            }else{
                $("#page").val(inputpage);
                $("#lachineForm").submit();
            }
        }else{
            alert("错误的页码，请重新输入");
            $("#inputpage").val("");
        }
    }
</script>
</body>
</html>
