package com.nsw.wx.order.redis;

public class GoodsKey extends BasePrefix{
	public static void main(String[] args) {
		System.out.println("sss");
	}
	private GoodsKey(int expireSeconds, String prefix) {
		super(expireSeconds, prefix);
	}
	public static GoodsKey getGoodsList = new GoodsKey(60, "gl");
	public static GoodsKey getGoodsDetail = new GoodsKey(60, "gd");
	public static GoodsKey getMiaoshaGoodsStock= new GoodsKey(0, "gs");
}
