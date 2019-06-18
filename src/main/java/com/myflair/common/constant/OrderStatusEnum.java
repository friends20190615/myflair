package com.myflair.common.constant;


/**
 * Created by user on 2018/3/24.
 */
public enum OrderStatusEnum {

/*     <br>TRADE_NO_CREATE_PAY (没有创建支付交易)
    <br>  WAIT_BUYER_PAY (等待买家付款)
    <br> WAIT_PAY_RETURN (等待支付确认)
    <br> WAIT_GROUP（等待成团，即：买家已付款，等待成团）
    <br> WAIT_SELLER_SEND_GOODS (等待卖家发货，即：买家已付款)
    <br> WAIT_BUYER_CONFIRM_GOODS (等待买家确认收货，即：卖家已发货)
    <br> TRADE_BUYER_SIGNED (买家已签收)
    <br> TRADE_CLOSED (*/

    TRADE_NO_CREATE_PAY("TRADE_NO_CREATE_PAY",1),
    WAIT_BUYER_PAY("WAIT_BUYER_PAY",2),
    WAIT_PAY_RETURN("WAIT_PAY_RETURN",3),
    WAIT_GROUP("WAIT_GROUP",4),
    WAIT_SELLER_SEND_GOODS("WAIT_SELLER_SEND_GOODS",5),
    WAIT_BUYER_CONFIRM_GOODS("WAIT_BUYER_CONFIRM_GOODS",6),
    TRADE_BUYER_SIGNED("TRADE_BUYER_SIGNED",7),
    TRADE_CLOSED("TRADE_CLOSED",8);


    private String name;
    private Integer index;

    private OrderStatusEnum(String name, Integer index) {
        this.name = name;
        this.index = index;
    }


    public static Integer getIndex(String nameStr) {
        for (OrderStatusEnum v : OrderStatusEnum.values()) {
            if (nameStr.equals(v.getName())) {
                return v.getIndex();
            }
        }
        return null;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }
}
