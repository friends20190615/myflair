$(function(){
	var pnl = {
		init:function(){
			$(".friend_list_wrap").hide();
			var wh = $(window).height();
			$(".mf_pnmb_wrap").css('min-height',wh);
		}
	},
	Jget = {
		friendResult: function(mobile){
            
			var url="/myflair/pull/regist.do";
			$.ajax({
				url : url,
				type : "post",
				data : {
					mobile:mobile
				},
				dataType:"json",
				cache : false,
				success:function(data){
					if(data.success){
                        var result = data.result;
                        if(result && result.length > 0){
                            $('.rule_list_f').html('');
                            for(var i =0;i<=result.length-1;i++){
                                $('.rule_list_f').append('<dd><span class="l-w">'+result[i].beInviteMobile+'</span><span class="r-w">已获得您分享的美好</span></dd>');
                            }
                        }
                        $(".mobile_wrap,.rule_wrap").hide();
						$(".friend_list_wrap").show().css("z-index","33");
			            document.body.scrollTop = document.documentElement.scrollTop = 0;
			            $(".share_wrap,.pop-save_wrap").show();
			            $("#shareQRcode").attr("src","http://www.myflair.com.cn/getERWMImage1.do?mobile="+mobile);
					}else{
						alert(data.msg);
					}

				}
			});
		}
	}
	pnl.init();
    var $phone = $('#orgmobileId');
    var $submit = $('#goto-btn');
    $phone.on('input propertychange', function () {
        if (myflair.tools.mobileValidate($("#orgmobileId").val())) {
            $submit.removeClass('blue').addClass('blue');
        } else {
            $submit.removeClass('blue');
        }
    });
	
	$('#goto-btn').click(function(){
		if(myflair.tools.checkTel($("#orgmobileId"))){
			var mobileval = $("#orgmobileId").val();
			myflair.tools.setStorage('orgMobile', mobileval);
            getWeixinShareData($("#orgmobileId").val());
            if(mobileval){
                Jget.friendResult(mobileval);//实际调用数据方法
            }	
		}
	});
	$(".save_btn,.pop-save_wrap .close").click(function(oEvent){
		$('.pop-save_wrap').hide();
		oEvent.preventDefault(); 
	});
	var rlh = $(".rule_list_f").height();
	$(".friend_list_wrap a.updown").on("click",function(){
		var parent = $(this).parents(".friend_list_wrap");
		var	wh = $(window).height(),
			showh = (wh/2)-rlh,
			smoveh = rlh;
		if (parent.hasClass('more') == false) {
            $(".friend_list_wrap").addClass("more");
			$(".rule_list_f").animate({"height":showh});  
        }else{
			$(".friend_list_wrap").removeClass("more");
			$(".rule_list_f").animate({"height":smoveh});  	 
        }
	})
})