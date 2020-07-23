package com.shaishunkan.manager_house.model;

import org.springframework.web.multipart.MultipartFile;

public class Home extends AbstractModel {
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String address;
	private String info;
	private String img_home;
	private Integer numerical_order;	
	private MultipartFile multipartFile;
	
	
	
	public MultipartFile getMultipartFile() {
		return multipartFile;
	}
	public void setMultipartFile(MultipartFile multipartFile) {
		this.multipartFile = multipartFile;
	}
	public String getImg_home() {
		return img_home;
	}
	public void setImg_home(String img_home) {
		this.img_home = img_home;
	}
	public Integer getNumerical_order() {
		return numerical_order;
	}
	public void setNumerical_order(Integer numerical_order) {
		this.numerical_order = numerical_order;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	

}
