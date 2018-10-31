package com.nsw.wx.order.VO;

import lombok.Data;

/**
 * 张维维
 * 2017-12-10 18:02
 */
@Data
public class ResultVO<T> {

    private Integer code;

    private String msg;

    private T data;
    public static void main(String[] args) {
        System.out.println("sss");
    }
}
