package com.nsw.wx.order.redis;

public class OrderKey extends BasePrefix {

	public OrderKey(String prefix) {
		super(prefix);
	}
	public static OrderKey getMiaoshaOrderByUidGid = new OrderKey("moug"); public static void main(String[] args) {
		System.out.println("sss");
	}
}
