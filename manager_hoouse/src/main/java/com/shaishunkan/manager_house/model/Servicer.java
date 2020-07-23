package com.shaishunkan.manager_house.model;

public class Servicer extends AbstractModel{

	private static final long serialVersionUID = 1L;
	
	private String name;
	private Integer id_home_2;
	private Integer price;
	private Integer per_flg;
	private String info;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getId_home_2() {
		return id_home_2;
	}
	public void setId_home_2(Integer id_home_2) {
		this.id_home_2 = id_home_2;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Integer getPer_flg() {
		return per_flg;
	}
	public void setPer_flg(Integer per_flg) {
		this.per_flg = per_flg;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
		
}
