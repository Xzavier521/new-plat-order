package com.nsw.wx.order.enums;

import lombok.Getter;

/**
 * 张维维
 * 2018-10-20 16:18
 */
@Getter
public enum OrderStatusEnum implements CodeEnum {
    NEW(0, "新订单"),
    FINISHED(1, "完结"),
    DCANCEL(2, "待取消"),
    CANCEL(3, "取消"),
    ;
    private Integer code;

    private String message;

    OrderStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
