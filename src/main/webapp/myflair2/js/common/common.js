var myflair = {};
myflair.tools = {
	getUrlParam: function(key) {
        if (!key) {}
        var url = window.location.search;
        url = url.split("?")[1];
        if (!url) {
            return null;
        }
        var value = null;
        var params = url.split("&");
        $.each(params,function(i, param) {
            var kv = param.split("=");
            if (kv[0] == key) {
                value = decodeURIComponent(kv[1]);
                return false;
            }
        });
        return value;
    },
    canStorage: function() {
        return !!window.localStorage
    },
    setStorage: function(key, value) {
        try {
            myflair.tools.canStorage() && (localStorage.removeItem(key),
            "string" != typeof value && (value = JSON.stringify(value)),
                localStorage.setItem(key, value))
        } catch (e) {}
    },
    getStorage: function(key) {
        if (myflair.tools.canStorage()) {
            var value = localStorage.getItem(key);
            value && "string" == typeof value && "undefined" === value && (value = null);
            try {
                return value ? JSON.parse(value) : null
            } catch (err) {
                return value
            }
        }
    },
    mobileValidate: function(mobile){
		if (null === mobile || "" === mobile) {
			return false;
		} else {
			var reg = /(^0?(13[0-9]|14[57]|15[012356789]|17[012356789]|18[0-9]|166|198|199)[0-9]{8}$)|(^886[0-9]{9}$)/;
			if (!reg.test(mobile)) {
				return false;
			} else {
				return true;
			}
		}
	},
	checkTel: function(inputtor) { // 手机号输入检查
		var tel =  $.trim($(inputtor).val());

		console.log('Check Tel[', tel, ']');

		if (null === tel || "" === tel || tel.length === 0 || tel==='输入您的手机号') {
			alert("请输入手机号!");
			return false;
		}
		if(!myflair.tools.mobileValidate(tel) ){
			alert("手机号填写错误!");
			return false;
		}else{
			return true;
		}
	},
	hideMobile: function(mobile) { // 手机号省略显示
		if(mobile){
            mobile = mobile.toString();
        	mobile = mobile.substr(0,3)+"****"+mobile.substr(7,4);
        };
        return mobile;
	}

}