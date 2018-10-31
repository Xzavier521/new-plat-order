package com.nsw.wx.order.redis;

public class MiaoshaKey extends BasePrefix{

	private MiaoshaKey(String prefix) {
		super(prefix);
	}
	public static MiaoshaKey isGoodsOver = new MiaoshaKey("go"); public static void main(String[] args) {
		System.out.println("sss");
	}
}
