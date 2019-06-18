function mobileValidate(mobile){
	if (null === mobile || "" === mobile) {
		return false;
	} else {
		var reg = /^0?(13[0-9]|14[57]|15[012356789]|17[012356789]|18[0-9])[0-9]{8}$/;
		if (!reg.test(mobile)) {
			return false;
		} else {
			return true;
		}
	}
}


function IsEmail(str)     
{     
    if(str.length!=0){    
    	reg=/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;    
    	if(!reg.test(str)){    
    		return false;//请将“字符串类型”要换成你要验证的那个属性名称！    
    	}else{
    		return true;
    	}   
    }else{
    	return false;
    } 
} 