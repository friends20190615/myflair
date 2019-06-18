var localgetUrlParam=function(key) {
    if (!key) {}
    var url = window.location.search;
    url = url.split("?")[1];
    if (!url) {
        return null;
    }
    var value = null;
    var params = url.split("&");
    $.each(params,
        function(i, param) {
            var kv = param.split("=");
            if (kv[0] == key) {
                value = decodeURIComponent(kv[1]);
                return false;
            }
        });
    return value;
};

function getWeixinShareData(mobile){
    var url = encodeURIComponent(location.href.split('#')[0]);
    var params = {};
    params.url = url;
    params.origin = localgetUrlParam("origin");
    if(mobile){
        params.mobile = mobile;
    }else{
        params.mobile = localgetUrlParam("mobile");
    }
    $.ajax({
        url: '/weixinShare/getShareData.do',
        type: "post",
        cache: false,
        dataType: "json",
        async : false,
        data: params,
        error:function(){
        },
        success: function (data) {
            if(data && data.status === 0 && data.result){
                var hrefUrl = location.href;
                if(data.result.url){
                    hrefUrl = data.result.url;
                }
            	var shareTitle = '';
            	var pyqContent = '';
            	var shareWXContent = '';
            	if(data.result.shareTitle){
            		shareTitle = data.result.shareTitle;
            	}
            	if(data.result.shareWXContent){
            		shareWXContent = data.result.shareWXContent;
            	}
                if(data.result.sharePYQContent){
                	pyqContent =data.result.sharePYQContent;
            	}
                var config = data.result.weixinJSConfigVO;
                if(typeof(wx) === "undefined" ){
                    var loadWX = setInterval(function(){
                        if(typeof(wx) !==  "undefined"){
                            window.clearInterval(loadWX);
                            initWX();
                        }
                    },1000);
                }else{
                    initWX();
                }

                function initWX(){
                    wx.config({
                        debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
                        appId: config.appId, // 必填，公众号的唯一标识
                        timestamp: config.timestamp, // 必填，生成签名的时间戳
                        nonceStr: config.nonceStr, // 必填，生成签名的随机串
                        signature: config.signature,// 必填，签名，见附录1
                        jsApiList: ['onMenuShareTimeline','onMenuShareAppMessage','hideOptionMenu','showOptionMenu'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
                    });
                    wx.ready(function(){
                        wx.onMenuShareTimeline({
                            title: pyqContent, // 分享标题
                            link: hrefUrl, // 分享链接
                            imgUrl:data.result.sharePyqImg, // 分享图标
                            success: function () {
                                // 用户确认分享后执行的回调函数
                            },
                            cancel: function () {
                                // 用户取消分享后执行的回调函数
                            },
                            trigger: function (res) {
                                // 不要尝试在trigger中使用ajax异步请求修改本次分享的内容，因为客户端分享操作是一个同步操作，这时候使用ajax的回包会还没有返回
                            }
                        });
                        wx.onMenuShareAppMessage({
                            title: shareTitle, // 分享标题
                            desc: shareWXContent, // 分享描述
                            link: hrefUrl, // 分享链接
                            imgUrl:data.result.shareImg, // 分享图标
                            type: 'link', // 分享类型,music、video或link，不填默认为link
                            dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
                            success: function () {
                                // 用户确认分享后执行的回调函数
                            },
                            cancel: function () {
                                // 用户取消分享后执行的回调函数
                            }
                        });
                    });
                    wx.error(function(res){
                       // alert("errormsg="+ JSON.stringify(res));
                    });
                }
            }
        }
    });
}

function isWeixin(){
    var userAgent = navigator.userAgent;
    if(userAgent.indexOf("MicroMessenger") > 0){
        return true;
    }
    return false;
}
//if(isWeixin()){
    getWeixinShareData();
//}
