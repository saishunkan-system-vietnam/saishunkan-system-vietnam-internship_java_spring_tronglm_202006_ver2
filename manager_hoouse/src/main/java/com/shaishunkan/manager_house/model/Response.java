package com.shaishunkan.manager_house.model;

import java.io.Serializable;

public class Response implements Serializable {

	private static final long serialVersionUID = 1L;
	private String code;
	private String message;
	private Object payload;
	
	
	public Response() {
		super();
	}
	
	public Response(String code, String message, Object payload) {
		super();
		this.code = code;
		this.message = message;
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
	public Object getPayload() {
		return payload;
	}
	public void setPayload(Object payload) {
		this.payload = payload;
	}	
}
