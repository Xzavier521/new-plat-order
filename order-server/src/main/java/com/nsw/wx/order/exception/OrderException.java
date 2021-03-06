package com.nsw.wx.order.exception;

import com.nsw.wx.order.enums.ResultEnum;

/**
 * Created by 廖师兄
 * 2017-12-10 17:27
 */
public class OrderException extends RuntimeException {

    private Integer code;

    public OrderException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public OrderException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    } public static void main(String[] args) {
        System.out.println("sss");
    }
}
