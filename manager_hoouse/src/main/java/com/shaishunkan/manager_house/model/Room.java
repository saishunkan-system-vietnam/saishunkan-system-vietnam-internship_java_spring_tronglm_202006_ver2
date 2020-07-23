package com.shaishunkan.manager_house.model;

public class Room extends AbstractModel{

	private static final long serialVersionUID = 1L;
	
	private Integer id_home;
	private String name;
	private Integer numerical_order;
	private Integer status_flg;
	private Integer price;
	private Integer length;
	private Integer width;
	private String info;
	private Integer unit_of_time;
	
	
	public Integer getUnit_of_time() {
		return unit_of_time;
	}
	public void setUnit_of_time(Integer unit_of_time) {
		this.unit_of_time = unit_of_time;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public Integer getLength() {
		return length;
	}
	public void setLength(Integer length) {
		this.length = length;
	}
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	public Integer getId_home() {
		return id_home;
	}
	public void setId_home(Integer id_home) {
		this.id_home = id_home;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getNumerical_order() {
		return numerical_order;
	}
	public void setNumerical_order(Integer numerical_order) {
		this.numerical_order = numerical_order;
	}
	public Integer getStatus_flg() {
		return status_flg;
	}
	public void setStatus_flg(Integer status_flg) {
		this.status_flg = status_flg;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
}
