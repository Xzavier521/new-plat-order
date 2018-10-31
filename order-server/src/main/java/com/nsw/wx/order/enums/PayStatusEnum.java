package com.nsw.wx.order.enums;

import lombok.Getter;

/**
 * 张维维
 * 2018-10-20 16:18
 */
@Getter
public enum PayStatusEnum implements CodeEnum {
    WAIT(0, "等待支付"),
    SUCCESS(1, "支付成功"),
    ;
    private Integer code;

    private String message;

    PayStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    } public static void main(String[] args) {
        System.out.println("sss");
    }
}
