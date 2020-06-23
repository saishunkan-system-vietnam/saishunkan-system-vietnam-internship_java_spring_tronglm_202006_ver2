package com.saishunken.maneger_game.model;

import org.springframework.stereotype.Component;


public class Response {
	private static final long serialVersionUID = 1L;
	
//	private Integer page = 1;
//	private int maxPageItem = 5;
//	private int totalItem;
//	private int offset;
	
	
	private String code;
	private String message;
	private int totalItem;
	private Object payload;
	
	
	public Response() {
		super();
	}
	public Response(String code, String message, int totalItem, Object payload) {
		super();
		this.code = code;
		this.message = message;
		this.totalItem = totalItem;
		this.payload = payload;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getTotalItem() {
		return totalItem;
	}
	public void setTotalItem(int totalItem) {
		this.totalItem = totalItem;
	}
	public Object getPayload() {
		return payload;
	}
	public void setPayload(Object payload) {
		this.payload = payload;
	}
	
	
	
}
