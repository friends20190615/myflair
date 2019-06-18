$(function(){
	var pnl = {
		init:function(){
			/*for(var i =0;i<=50;i++){
				$('.rule_list_f').append('<dd><span class="l-w">00000000000</span><span class="r-w">已获得您分享的美好</span></dd>');
		    }*/
			$(".friend_list_wrap").hide();
		    /*var mobile =myflair.tools.getStorage('orgMobile');
            $("#orgmobileId").val(mobile);
			if(mobile){
             //   getWeixinShareData(mobile);
                $('#goto-btn').addClass("blue");
                Jget.friendResult(mobile);//实际调用数据方法
			}*/
		/*	var orgMobileVal = myflair.tools.getStorage("orgMobile");
			var telshow = myflair.tools.getUrlParam("tel");
			if ( telshow && telshow == orgMobileVal){
				$(".share_wrap").show();
				$("#orgmobileId").val(orgMobileVal);
				$("#goto-btn").addClass("blue");
			}*/
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
                        $(".friend_list_wrap").show();
                        $(".share_wrap").show();
					}else{
						alert(data.msg);
					}

				}
			});
		}
	}
	pnl.init();
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
			var mobileval = $("#orgmobileId").val();
			myflair.tools.setStorage('orgMobile', mobileval);
            getWeixinShareData($("#orgmobileId").val());
            if(mobileval){
                Jget.friendResult(mobileval);//实际调用数据方法
            }
		/*	var url = window.location.href;
			var searchif = window.location.search;
			if ( searchif == "" ){
				window.location.href=url + "?tel="+mobileval;
			}else{
				window.location.href=url.split("?")[0] + "?tel="+mobileval;
			}*/
			
		}
	});
	$(".share_wrap").click(function(oEvent){
		$('.pop-up-wrap').hide();
		oEvent.preventDefault(); 
	});
	$(".friend_list_wrap a.updown").on("click",function(){
		var parent = $(this).parents(".friend_list_wrap");
		var rlh = $(".rule_list_f").height(),
			wh = $(window).height(),
			showh = wh - 2*rlh,
			smoveh = (wh - rlh)/2;
		if (parent.hasClass('more') == false) {
            $(".friend_list_wrap").addClass("more");
			$(".rule_list_f").animate({height:showh});  
        }else{
			$(".friend_list_wrap").removeClass("more");
			$(".rule_list_f").animate({height:smoveh});  	 
        }
	})
})