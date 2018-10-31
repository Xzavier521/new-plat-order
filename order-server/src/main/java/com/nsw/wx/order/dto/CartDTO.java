package com.nsw.wx.order.dto;

import lombok.Data;

/**
 * 张维维
 * 2018-10-22 22:36
 */
@Data
public class CartDTO {
    /**
     * 商品id
     */
    private String productId;

    /**
     * 商品数量
     */
    private Integer productQuantity;

    public CartDTO() {
    }

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
    public static void main(String[] args) {
        System.out.println("sss");
    }
}
