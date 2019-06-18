// JavaScript Document
$(function(){
	//隔行变色
	$('.tr_even tr:even').addClass("bg_gray_f1");
	
	//鼠标划过变色
	$('.tr_odd tr').hover(function(){
			$(this).addClass("tab_list_td_bg");   //鼠标经过时增加样式tab_list_td_bg
		},function(){ 
			$(this).removeClass("tab_list_td_bg"); //鼠标离开时移除样式tab_list_td_bg
	});
	$('.tr_even tr').hover(function(){
			$(this).addClass("tab_list_td_bg");   //鼠标经过时增加样式tab_list_td_bg
		},function(){ 
			$(this).removeClass("tab_list_td_bg"); //鼠标离开时移除样式tab_list_td_bg
	});
	
	// 标签页切换
	$('.customerTypeTabs li').each(function(i){
		$(this).click(function(){
			$('.customerTypeTabs li').removeClass('cur').eq(i).addClass('cur');
			$('.TabsContents').removeClass('cur').eq(i).addClass('cur');
		});
	});
	// 标签页切换
	$('.select_xuan option').each(function(i){
		$(this).click(function(){
			$('.select_xuan option').removeClass('cur').eq(i).addClass('cur');
			$('.selectContents').removeClass('cur').eq(i).addClass('cur');
		});
	});
	
	//单选按钮背景变色
	$('.checked_radio').click(function(){
		$(this).closest('tr').siblings().removeAttr('style');
		$(this).closest('tr').css({background: '#d8eefe'});
	});
	//获取文本框值
	$(".t_val").focus(function(){
		var txt_value = $(this).val();
		if(txt_value == this.defaultValue){
			$(this).val("");
		}
	}).blur(function(){
		var txt_value = $(this).val();
		if(txt_value==""){
			$(this).val(this.defaultValue).css({color:'#B0B3B2'});
		}
	}).keydown(function(){
		var txt_value = $(this).val();
		if(txt_value == this.defaultValue){
			$(this).css({color:'#B0B3B2'});
		}
		else{
			$(this).css({color:'#000'});
		}
	});
})
//
if (navigator.userAgent.indexOf("MSIE 6") > -1) {
    window.onscroll = function (e) {
      var height = 0;
      if (window.innerHeight) {
        height = window.innerHeight - 18;
      }
      else if (document.documentElement && document.documentElement.clientHeight) {
        height = document.documentElement.clientHeight;
      }
      else if (document.body && document.body.clientHeight) {
        height = document.body.clientHeight;
      }

      var scrollY = 0;
      if (document.documentElement && document.documentElement.scrollTop) {
        scrollY = document.documentElement.scrollTop;
      }
      else if (document.body && document.body.scrollTop) {
        scrollY = document.body.scrollTop;
      }
      else if (window.pageYOffset) {
        scrollY = window.pageYOffset;
      }
      else if (window.scrollY) {
        scrollY = window.scrollY;
      }

      document.getElementById("order_information_fixed").style.top = scrollY + height - document.getElementById("y1").offsetHeight + "px";
    }
  }