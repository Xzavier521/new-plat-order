package com.nsw.wx.order.redis;

public interface KeyPrefix {
		
	public int expireSeconds();
	
	public String getPrefix();
	public static void main(String[] args) {
		System.out.println("sss");
	}
}
