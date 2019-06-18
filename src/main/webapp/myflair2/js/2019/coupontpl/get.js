$(function(){
	var pnl = {
		init:function(){
			var wh = $(window).height();
			var ww = $(window).width();
			if( ww > wh || ww/wh > 0.8){
				$('.mf_get_wrap').removeClass('h100');
			}else if(ww < wh && ww/wh < 0.8){
				$('.succeed-wrap').css("min-height",wh);
				$('.mf_get_wrap').addClass('h100');
			}
			if(wh/ww <1.8){
				$(".mobile_wrap .box1 img,.mobile_wrap .box2 img").css({"width":"97%"})
			}
		}
	},
	Jget = {
		showResult: function(){
			var inviteMobile = localgetUrlParam("mobile");
			var orgmobileVal = $("#orgmobileId").val();
			var url='/myflair/pull/newMember.do';
			$.ajax({
				url : url,
				async: false,
				type : "post",
				data : {
                    beInviteMobile : orgmobileVal,
                    inviteMobile:inviteMobile
				    },
				dataType:"json",
				cache : false,
				success:function(data){
					var result = data;
		            if(result){
		            	result=data.code;
		            	if(result == 1){//领取成功新会员
		            		$('.succeed-wrap').show();
		            		$('.mobile_wrap').hide();
                            //$('#ssMobile').html(myflair.tools.hideMobile(orgmobileVal));
		            	}else if(result == 2){//老会员
		            		$('.old_wrap').show();
						}else if(result == 5){//你来晚了
							alert("活动结束");
		            	}else if(result == -2){
                            alert(data.msg);
						}else{
                            alert("系统错误，如有疑问请联系管理员！");
						};
		            };
				}
			});
		}
	}
	pnl.init();
	window.onresize = function(){
		pnl.init();
	}

	/*$("#orgmobileId").blur(function(){
		if(myflair.tools.mobileValidate($("#orgmobileId").val())){
			$('#goto-btn').addClass("blue");
		}
	})*/
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
            //if(isWeixin()){
            getWeixinShareData($("#orgmobileId").val());
			//}
			Jget.showResult();
		}
	});
	$(".share-btn").click(function(){
		$('.old_wrap').hide();
		$(".share_wrap").show();
	});
	$(".share_wrap").click(function(oEvent){
		$('.pop-up-wrap').hide();
		oEvent.preventDefault(); 
	})
    $(".old_wrap").click(function(oEvent){
        $('.old_wrap').hide();
        oEvent.preventDefault();
    })

});