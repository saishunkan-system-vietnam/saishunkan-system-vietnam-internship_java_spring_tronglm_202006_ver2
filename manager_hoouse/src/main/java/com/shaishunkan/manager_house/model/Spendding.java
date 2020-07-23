package com.shaishunkan.manager_house.model;

public class Spendding extends AbstractModel{

	private static final long serialVersionUID = 1L;
	
	private Integer id_home_1;
	private String name;
	private String price;
	private Integer amount;
	private String info;
	public Integer getId_home_1() {
		return id_home_1;
	}
	public void setId_home_1(Integer id_home_1) {
		this.id_home_1 = id_home_1;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}

}
