package com.myflair.third.util;

import com.alibaba.fastjson.JSONObject;
import com.myflair.common.utils.HttpUtil;
import com.youzan.open.sdk.client.auth.Token;
import com.youzan.open.sdk.client.core.DefaultYZClient;
import com.youzan.open.sdk.client.core.YZClient;
import com.youzan.open.sdk.gen.v3_0_0.api.*;
import com.youzan.open.sdk.gen.v3_0_0.model.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by wangjiulin on 2018/3/29.
 */
public class YouZanUtil {

    private static final Logger log = LoggerFactory.getLogger(YouZanUtil.class);

    private static final String CLIENT_ID="4fe3d03d41efa1185c";

    private static final String CLIENT_SECRET="59399c80cc8785ac3111773a27b4cfc6";

    private static final String GRANT_TYPE="silent";

    private static final String KDT_ID="40542735";

    private static String YOUZANTOKEN;

    //获取参数的时刻
    private static Long getTokenTime = 0L;
    //参数的有效时间,单位是秒(s)
    private static Long tokenExpireTime = 0L;

    private  static final Long COUPON_GROUP_ID= null;

    private static final String  RESPONSE_TYPE="code";

    private static final String CODE_URL = "https://open.youzan.com/oauth/authorize?client_id="+CLIENT_ID+"&response_type="+RESPONSE_TYPE+"&state=wjl";

    private static final String TOKEN_URL = "https://open.youzan.com/oauth/token";

    public static String getToken() throws Exception {
        long now = System.currentTimeMillis();
        try{
            if(StringUtils.isBlank(YOUZANTOKEN)||(now - getTokenTime > tokenExpireTime*1000)) {
                JSONObject postData = new JSONObject();
                postData.put("client_id", CLIENT_ID);
                postData.put("client_secret", CLIENT_SECRET);
                postData.put("grant_type", GRANT_TYPE);
                postData.put("kdt_id", KDT_ID);
                String tokenJsonStr = HttpUtil.doPostHttps(TOKEN_URL, postData.toString());
                if (tokenJsonStr != null) {
                    JSONObject tokenJson = JSONObject.parseObject(tokenJsonStr);
                    YOUZANTOKEN = tokenJson.getString("access_token");
                    getTokenTime=System.currentTimeMillis();
                    tokenExpireTime=tokenJson.getLong("expires_in");
                }
            }
        }catch (Exception e){
            log.error("获取token 失败" +e.getMessage());
            throw new Exception(e);
        }
        return  YOUZANTOKEN;
    }


    public static YouzanTradeGetResult getOrderDetail(String orderNo) throws Exception {
        YZClient client = new DefaultYZClient(new Token(YouZanUtil.getToken()));
        YouzanTradeGetParams youzanTradeGetParams = new YouzanTradeGetParams();
        youzanTradeGetParams.setTid(orderNo);
        YouzanTradeGet youzanTradeGet = new YouzanTradeGet(youzanTradeGetParams);
        YouzanTradeGetResult result = client.invoke(youzanTradeGet);
       return result;
    }


    public static String getMemberMobileByFansId(Long fansId) throws Exception {
        String mobile = null;
        YZClient client = new DefaultYZClient(new Token(YouZanUtil.getToken()));
        YouzanScrmCustomerGetParams youzanScrmCustomerGetParams = new YouzanScrmCustomerGetParams();
        youzanScrmCustomerGetParams.setMobile("");
        youzanScrmCustomerGetParams.setFansType(1L);
        youzanScrmCustomerGetParams.setFansId(fansId);
        YouzanScrmCustomerGet youzanScrmCustomerGet = new YouzanScrmCustomerGet();
        youzanScrmCustomerGet.setAPIParams(youzanScrmCustomerGetParams);
        String  jsonObjectStr = client.execute(youzanScrmCustomerGet);
        if(jsonObjectStr != null && jsonObjectStr.length()>0){
            JSONObject jsonObject = JSONObject.parseObject(jsonObjectStr);
            JSONObject result = (JSONObject) jsonObject.get("response");
            mobile = result.getString("mobile");
        }
        if(StringUtils.isBlank(mobile)){
            com.youzan.open.sdk.gen.v3_1_0.model.YouzanScrmCustomerGetParams ym = new com.youzan.open.sdk.gen.v3_1_0.model.YouzanScrmCustomerGetParams();
            JSONObject account = new JSONObject();
            account.put("account_type","Mobile");
            account.put("account_id",mobile);
            ym.setAccount(account.toJSONString());
            com.youzan.open.sdk.gen.v3_1_0.api.YouzanScrmCustomerGet yg = new com.youzan.open.sdk.gen.v3_1_0.api.YouzanScrmCustomerGet();
            yg.setAPIParams(ym);
            com.youzan.open.sdk.gen.v3_1_0.model.YouzanScrmCustomerGetResult result = client.invoke(yg);
            if(result != null) {
                mobile = result.getMobile();
            }
        }
        return mobile;
    }


    public static YouzanUmpCouponTakeResult.UmpPromocardUserTakedetail couponPull(String mobile, Long groupId) throws Exception {
        YouzanUmpCouponTakeResult.UmpPromocardUserTakedetail promocard=null;
        YZClient client = new DefaultYZClient(new Token(YouZanUtil.getToken())); //new Sign(appKey, appSecret)
        YouzanUmpCouponTakeParams youzanUmpCouponTakeParams = new YouzanUmpCouponTakeParams();
        youzanUmpCouponTakeParams.setMobile(Long.valueOf(mobile));
        youzanUmpCouponTakeParams.setCouponGroupId(groupId);
        YouzanUmpCouponTake youzanUmpCouponTake = new YouzanUmpCouponTake();
        youzanUmpCouponTake.setAPIParams(youzanUmpCouponTakeParams);
        YouzanUmpCouponTakeResult result = client.invoke(youzanUmpCouponTake);
        if(result != null){
            promocard = result.getPromocard();
        }
        return  promocard;
    }


    public static Boolean userIsExistsOrder(Long fansId) throws Exception {
        YZClient client = new DefaultYZClient(new Token(YouZanUtil.getToken())); //new Sign(appKey, appSecret)
        YouzanUsersWeixinFollowerGetParams youzanUsersWeixinFollowerGetParams = new YouzanUsersWeixinFollowerGetParams();
        youzanUsersWeixinFollowerGetParams.setFansId(fansId);
        YouzanUsersWeixinFollowerGet youzanUsersWeixinFollowerGet = new YouzanUsersWeixinFollowerGet();
        youzanUsersWeixinFollowerGet.setAPIParams(youzanUsersWeixinFollowerGetParams);
        YouzanUsersWeixinFollowerGetResult re = client.invoke(youzanUsersWeixinFollowerGet);
        if (re != null && re.getUser()!=null && re.getUser().getTradedNum()>0 && re.getUser().getTradedMoney()>0){
            return true;
        }
        return false;
    }


    public static Boolean isMemOrCustomByMobile(String mobile) throws Exception {
        YZClient client = new DefaultYZClient(new Token(YouZanUtil.getToken())); //new Sign(appKey, appSecret)
        com.youzan.open.sdk.gen.v3_1_0.model.YouzanScrmCustomerGetParams youzanScrmCustomerGetParams = new com.youzan.open.sdk.gen.v3_1_0.model.YouzanScrmCustomerGetParams();
        JSONObject account = new JSONObject();
        account.put("account_type","Mobile");
        account.put("account_id",mobile);
        youzanScrmCustomerGetParams.setAccount(account.toJSONString());
        com.youzan.open.sdk.gen.v3_1_0.api.YouzanScrmCustomerGet youzanScrmCustomerGet = new com.youzan.open.sdk.gen.v3_1_0.api.YouzanScrmCustomerGet();
        youzanScrmCustomerGet.setAPIParams(youzanScrmCustomerGetParams);
        com.youzan.open.sdk.gen.v3_1_0.model.YouzanScrmCustomerGetResult result = client.invoke(youzanScrmCustomerGet);
        if(result != null){
            return true;
        }else{
            YouzanScrmCustomerGetParams youzanScrmCustomerGetParams1 = new YouzanScrmCustomerGetParams();
            youzanScrmCustomerGetParams1.setMobile(mobile);
            youzanScrmCustomerGetParams1.setFansType(1L);
            youzanScrmCustomerGetParams1.setFansId(0L);
            YouzanScrmCustomerGet youzanScrmCustomerGet1 = new YouzanScrmCustomerGet();
            youzanScrmCustomerGet1.setAPIParams(youzanScrmCustomerGetParams1);
            String  jsonObjectStr = client.execute(youzanScrmCustomerGet1);
            if(jsonObjectStr != null && jsonObjectStr.length()>0){
                JSONObject jsonObject = JSONObject.parseObject(jsonObjectStr);
                JSONObject result1 = (JSONObject) jsonObject.get("response");
                if(result1 != null){
                    String resMobile =  result1.getString("mobile");
                    if(StringUtils.isNotBlank(resMobile)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static Long getFansIdByMobile(String mobile) throws Exception {
        Long fansId = null;
        YZClient client = new DefaultYZClient(new Token(YouZanUtil.getToken())); //new Sign(appKey, appSecret)
        YouzanUserWeixinOpenidGetParams youzanUserWeixinOpenidGetParams = new YouzanUserWeixinOpenidGetParams();
        youzanUserWeixinOpenidGetParams.setMobile(mobile);
        youzanUserWeixinOpenidGetParams.setCountryCode("+86");
        YouzanUserWeixinOpenidGet youzanUserWeixinOpenidGet = new YouzanUserWeixinOpenidGet();
        youzanUserWeixinOpenidGet.setAPIParams(youzanUserWeixinOpenidGetParams);
        String result = client.execute(youzanUserWeixinOpenidGet);
        if(result != null){
            JSONObject jsonObject = JSONObject.parseObject(result);
            JSONObject re = (JSONObject) jsonObject.get("response");
            if(re != null){
                String openId = re.getString("open_id");
                if(StringUtils.isNotBlank(openId)){
                    YouzanUsersWeixinFollowerGetParams youzanUsersWeixinFollowerGetParams = new YouzanUsersWeixinFollowerGetParams();
                    youzanUsersWeixinFollowerGetParams.setWeixinOpenid(openId);
                    YouzanUsersWeixinFollowerGet youzanUsersWeixinFollowerGet = new YouzanUsersWeixinFollowerGet();
                    youzanUsersWeixinFollowerGet.setAPIParams(youzanUsersWeixinFollowerGetParams);
                    YouzanUsersWeixinFollowerGetResult res = client.invoke(youzanUsersWeixinFollowerGet);
                    fansId = res.getUser().getUserId();
                }
            }
        }
        return fansId;
    }


    public static YouzanUmpCouponSearchResult.CouponGroup getCoupon(String title) throws Exception {

        YZClient client = new DefaultYZClient(new Token(YouZanUtil.getToken())); //new Sign(appKey, appSecret)
        YouzanUmpCouponSearchParams youzanUmpCouponSearchParams = new YouzanUmpCouponSearchParams();
        youzanUmpCouponSearchParams.setPageNo(1L);
        youzanUmpCouponSearchParams.setPageSize(500L);
        youzanUmpCouponSearchParams.setStatus("ON");

        YouzanUmpCouponSearch youzanUmpCouponSearch = new YouzanUmpCouponSearch();
        youzanUmpCouponSearch.setAPIParams(youzanUmpCouponSearchParams);
        YouzanUmpCouponSearchResult result = client.invoke(youzanUmpCouponSearch);
        YouzanUmpCouponSearchResult.CouponGroup[] coupons = result.getGroups();
        if(coupons != null && coupons.length > 0){
            for (YouzanUmpCouponSearchResult.CouponGroup coupon: coupons) {
                if(coupon.getGroupType().intValue() == 7 && coupon.getTitle().equals(title)){
                    return coupon;
                }
            }
        }
        return null;
    }


    public static void cretateCoustom(String mobile) throws Exception {
        YZClient client = new DefaultYZClient(new Token(YouZanUtil.getToken())); //new Sign(appKey, appSecret)
        YouzanScrmCustomerCreateParams youzanScrmCustomerCreateParams = new YouzanScrmCustomerCreateParams();

        youzanScrmCustomerCreateParams.setMobile(mobile);
        JSONObject account = new JSONObject();
        account.put("name","");
        youzanScrmCustomerCreateParams.setCustomerCreate(account.toJSONString());

        YouzanScrmCustomerCreate youzanScrmCustomerCreate = new YouzanScrmCustomerCreate();
        youzanScrmCustomerCreate.setAPIParams(youzanScrmCustomerCreateParams);
        client.invoke(youzanScrmCustomerCreate);
    }

    public static boolean addJf(String mobile) throws Exception {
        YZClient client = new DefaultYZClient(new Token(YouZanUtil.getToken()));
        YouzanCrmCustomerPointsIncreaseParams youzanCrmCustomerPointsIncreaseParams = new YouzanCrmCustomerPointsIncreaseParams();

        youzanCrmCustomerPointsIncreaseParams.setPoints(100L);
        youzanCrmCustomerPointsIncreaseParams.setMobile(mobile);

        YouzanCrmCustomerPointsIncrease youzanCrmCustomerPointsIncrease = new YouzanCrmCustomerPointsIncrease();
        youzanCrmCustomerPointsIncrease.setAPIParams(youzanCrmCustomerPointsIncreaseParams);
        YouzanCrmCustomerPointsIncreaseResult result = client.invoke(youzanCrmCustomerPointsIncrease);
        if(result != null && result.getIsSuccess().equals("true")){
                  return true;
        }else{
            return false;
        }
    }

    public static Long getWlStat(String orderNo) throws Exception {
        YZClient client = new DefaultYZClient(new Token(YouZanUtil.getToken())); //new Sign(appKey, appSecret)
        YouzanLogisticsExpressbyordernoGetParams youzanLogisticsExpressbyordernoGetParams = new YouzanLogisticsExpressbyordernoGetParams();

        youzanLogisticsExpressbyordernoGetParams.setTid(orderNo);

        YouzanLogisticsExpressbyordernoGet youzanLogisticsExpressbyordernoGet = new YouzanLogisticsExpressbyordernoGet();
        youzanLogisticsExpressbyordernoGet.setAPIParams(youzanLogisticsExpressbyordernoGetParams);
        YouzanLogisticsExpressbyordernoGetResult result = client.invoke(youzanLogisticsExpressbyordernoGet);
        return result.getState();
    }




    public static void main(String[] args) throws Exception {
        //获取订单详情
       /* String orderNO = "E20180328172954079600002";
        YZClient client = new DefaultYZClient(new Token(YouZanUtil.getToken()));
        YouzanTradeGetParams youzanTradeGetParams = new YouzanTradeGetParams();
        youzanTradeGetParams.setTid(orderNO);
        YouzanTradeGet youzanTradeGet = new YouzanTradeGet(youzanTradeGetParams);
        YouzanTradeGetResult result = client.invoke(youzanTradeGet);
        System.out.println(result.getTrade().getFansInfo().getFansId());*/

        //获取用户信息
      /*  YZClient client = new DefaultYZClient(new Token(YouZanUtil.getToken()));
        com.youzan.open.sdk.gen.v3_1_0.model.YouzanScrmCustomerGetParams youzanScrmCustomerGetParams = new com.youzan.open.sdk.gen.v3_1_0.model.YouzanScrmCustomerGetParams();
        JSONObject account = new JSONObject();
        account.put("account_type","FansID");
        account.put("account_id",5172977475L);
        youzanScrmCustomerGetParams.setAccount(account.toString());
        YouzanScrmCustomerGet youzanScrmCustomerGet = new YouzanScrmCustomerGet();
        youzanScrmCustomerGet.setAPIParams(youzanScrmCustomerGetParams);
        YouzanScrmCustomerGetResult result = client.invoke(youzanScrmCustomerGet);
        System.out.println(result.getMobile());*/

        //获取卖家卖出交易列表
        /*YZClient client = new DefaultYZClient(new Token(YouZanUtil.getToken())); //new Sign(appKey, appSecret)
        YouzanTradesSoldGetParams youzanTradesSoldGetParams = new YouzanTradesSoldGetParams();

        youzanTradesSoldGetParams.setBuyerId(5172977475L);
        youzanTradesSoldGetParams.setPageSize(20L);
        youzanTradesSoldGetParams.setPageNo(1L);
     //   youzanTradesSoldGetParams.setStatus("WAIT_SELLER_SEND_GOODS");
        youzanTradesSoldGetParams.setEndCreated(DateUtils.dateString2Date("2018-04-03 18:00:00"));
        youzanTradesSoldGetParams.setStartCreated(DateUtils.dateString2Date("2018-02-03 00:00:00"));

        YouzanTradesSoldGet youzanTradesSoldGet = new YouzanTradesSoldGet();
        youzanTradesSoldGet.setAPIParams(youzanTradesSoldGetParams);
        YouzanTradesSoldGetResult result = client.invoke(youzanTradesSoldGet);
        System.out.printf(result.toString());*/

        //获取会员卡信息
      /*  YZClient client = new DefaultYZClient(new Token(YouZanUtil.getToken())); //new Sign(appKey, appSecret)
        YouzanScrmCustomerGetParams youzanScrmCustomerGetParams = new YouzanScrmCustomerGetParams();
        youzanScrmCustomerGetParams.setMobile("13264257229");
        youzanScrmCustomerGetParams.setFansType(1L);
        youzanScrmCustomerGetParams.setFansId(0L);
        YouzanScrmCustomerGet youzanScrmCustomerGet = new YouzanScrmCustomerGet();
        youzanScrmCustomerGet.setAPIParams(youzanScrmCustomerGetParams);
        String jsonObjectStr = client.execute(youzanScrmCustomerGet);
        JSONObject jsonObject = JSONObject.parseObject(jsonObjectStr);
        System.out.println(jsonObject.toJSONString());
*/
        //获取openid
  /*      System.out.println(YouZanUtil.getToken());
        YZClient client = new DefaultYZClient(new Token(YouZanUtil.getToken())); //new Sign(appKey, appSecret)
        YouzanUserWeixinOpenidGetParams youzanUserWeixinOpenidGetParams = new YouzanUserWeixinOpenidGetParams();

        youzanUserWeixinOpenidGetParams.setMobile("18611085423");
        youzanUserWeixinOpenidGetParams.setCountryCode("+86");
        YouzanUserWeixinOpenidGet youzanUserWeixinOpenidGet = new YouzanUserWeixinOpenidGet();
        youzanUserWeixinOpenidGet.setAPIParams(youzanUserWeixinOpenidGetParams);
        String result = client.execute(youzanUserWeixinOpenidGet);
        System.out.println(result);
        System.out.println(YouZanUtil.getToken());
        //获取 fanId
        YouzanUsersWeixinFollowerGetParams youzanUsersWeixinFollowerGetParams = new YouzanUsersWeixinFollowerGetParams();
        youzanUsersWeixinFollowerGetParams.setWeixinOpenid("oHFCX1a7raufk4MBeDYmzYLTlAc8");
        YouzanUsersWeixinFollowerGet youzanUsersWeixinFollowerGet = new YouzanUsersWeixinFollowerGet();
        youzanUsersWeixinFollowerGet.setAPIParams(youzanUsersWeixinFollowerGetParams);
        YouzanUsersWeixinFollowerGetResult res = client.invoke(youzanUsersWeixinFollowerGet);
        System.out.println(res.getUser().getUserId());
        System.out.println(YouZanUtil.getToken());*/

        //15770396

/*
        YZClient client = new DefaultYZClient(new Token(YouZanUtil.getToken())); //new Sign(appKey, appSecret)
        YouzanUsersWeixinFollowerTagsGetParams youzanUsersWeixinFollowerTagsGetParams = new YouzanUsersWeixinFollowerTagsGetParams();
        youzanUsersWeixinFollowerTagsGetParams.setFansId(String.valueOf(5172977475L));
        YouzanUsersWeixinFollowerTagsGet youzanUsersWeixinFollowerTagsGet = new YouzanUsersWeixinFollowerTagsGet();
        youzanUsersWeixinFollowerTagsGet.setAPIParams(youzanUsersWeixinFollowerTagsGetParams);
        YouzanUsersWeixinFollowerTagsGetResult result = client.invoke(youzanUsersWeixinFollowerTagsGet);
        String tagStr = new String();
        if (result!=null){
            YouzanUsersWeixinFollowerTagsGetResult.CrmFansTag[] tags= result.getTags();
            if(tags != null && tags.length > 0){
                for (int i = 0;i<tags.length;i++ ) {
                    YouzanUsersWeixinFollowerTagsGetResult.CrmFansTag tag= tags[i];
                    if(i != tags.length-1){
                        tagStr +=tag.getTagId()+",";
                    }else{
                        tagStr +=tag.getTagId()+"";
                    }
                }
            }
        }
        YouzanUsersWeixinFollowerTagsAddParams youzanUsersWeixinFollowerTagsAddParams = new YouzanUsersWeixinFollowerTagsAddParams();
        youzanUsersWeixinFollowerTagsAddParams.setFansId(5172977475L);
        youzanUsersWeixinFollowerTagsAddParams.setTags(tagStr);
        YouzanUsersWeixinFollowerTagsAdd youzanUsersWeixinFollowerTagsAdd = new YouzanUsersWeixinFollowerTagsAdd();
        youzanUsersWeixinFollowerTagsAdd.setAPIParams(youzanUsersWeixinFollowerTagsAddParams);
        YouzanUsersWeixinFollowerTagsAddResult re = client.invoke(youzanUsersWeixinFollowerTagsAdd);
        if (re != null && re.getUser()!=null && re.getUser().getTradedNum()>0 && re.getUser().getTradedMoney()>0){
            System.out.printf("true");
        }
        System.out.printf("false");*/


       // cretateCoustom("15201224652");

      //  YouzanUmpCouponSearchResult.CouponGroup coupon = YouZanUtil.getCoupon("邀请有礼");
        //YouzanUmpCouponTakeResult.UmpPromocardUserTakedetail result =YouZanUtil.couponPull("13264257229", coupon.getId());

       // addJf("13264257229");
        // getWlStat("E2018051104060704890001")+""
        YouzanUmpCouponSearchResult.CouponGroup coupon = YouZanUtil.getCoupon("分悦代金券");
        System.out.printf(coupon.toString());
    }

}
