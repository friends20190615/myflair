package com.myflair.wechart.sharedata;

import java.util.HashMap;
import java.util.Map;


public class Constants {

    private static final String MYFLAIRLXIMG="http://www.myflair.com.cn/myflair/myflair/images/2018/coupontpl/hy.jpg";
	private static final String MYFLAIPYQRLXIMG="http://www.myflair.com.cn/myflair/myflair/images/2018/coupontpl/pyq.jpg";
    private static final String MYFLAIRLXCONTENT="myFlair美范， 自带高级感\n" +"去宠爱自己一下吧～";
    private static final String MYFLAIRLXTITLE="轻珠宝  无限次可替换\n" + "好友刚送你100元现金券！";
    private static final String MYFLAIRLXPYQCONTENT="myFlair 美范 | 无限次 可替换\n" +"高级感轻珠宝, 200元代金券, 快去看看！";
    private static final String MYFLAIRLXURL ="http://www.myflair.com.cn/myflair/myflair/coupontpl.html?origin=myflairlx&mobile={0}";

    
    private static final String MYFLAIRLXIMG2="http://www.myflair.com.cn/myflair/myflair2/images/2019/coupontpl/hy.jpg";
	private static final String MYFLAIPYQRLXIMG2="http://www.myflair.com.cn/myflair/myflair2/images/2019/coupontpl/pyq.jpg";
    private static final String MYFLAIRLXCONTENT2="myFlair美范， 自带高级感\n" +"去宠爱自己一下吧～";
    private static final String MYFLAIRLXTITLE2="轻珠宝  无限次可替换\n" + "好友刚送你100元现金券！";
    private static final String MYFLAIRLXPYQCONTENT2="myFlair 美范 | 无限次 可替换\n" +"高级感轻珠宝, 200元代金券, 快去看看！";
    private static final String MYFLAIRLXURL2 ="http://www.myflair.com.cn/myflair/myflair2/coupontpl.html?origin=myflairlx&mobile={0}";
    

    private static Map<String,Map<String,String>> paramsMap = new HashMap<String,Map<String,String>>();

    public static Map<String, Map<String, String>> getParamsMap() {
		return paramsMap;
	}

	static {
    	//修改了分享内容
    	Map<String,String> activityMap = new HashMap<String,String>();
    	activityMap.put("img", MYFLAIRLXIMG);
    	activityMap.put("title", MYFLAIRLXTITLE);
    	activityMap.put("content", MYFLAIRLXCONTENT);
    	activityMap.put("pyqcontent", MYFLAIRLXPYQCONTENT);
    	activityMap.put("pyqimg",MYFLAIPYQRLXIMG);
		activityMap.put("url", MYFLAIRLXURL);
    	paramsMap.put("myflairlx", activityMap);
    	
    	activityMap.put("img", MYFLAIRLXIMG2);
    	activityMap.put("title", MYFLAIRLXTITLE2);
    	activityMap.put("content", MYFLAIRLXCONTENT2);
    	activityMap.put("pyqcontent", MYFLAIRLXPYQCONTENT2);
    	activityMap.put("pyqimg",MYFLAIPYQRLXIMG2);
		activityMap.put("url", MYFLAIRLXURL2);
    	paramsMap.put("myflairlx2", activityMap);
    }
}
