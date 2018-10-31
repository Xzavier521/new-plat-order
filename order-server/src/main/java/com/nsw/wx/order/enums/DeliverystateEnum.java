package com.nsw.wx.order.enums;

import lombok.Getter;

/**
 * Created by IntelliJ IDEA.
 *
 * @author 张维维
 * date: 2018/10/23/023 11:23
 */
@Getter
public enum  DeliverystateEnum implements CodeEnum{
    NEW(0, "待发货"),
    FINISHED(1, "已发货"),
    CANCEL(2, "取消"),
    ;
    private Integer code;

    private String message;

    DeliverystateEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    public static void main(String[] args) {
        System.out.println("sss");
    }
}
