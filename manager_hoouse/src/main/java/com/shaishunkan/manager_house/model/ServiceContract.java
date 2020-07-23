package com.shaishunkan.manager_house.model;

public class ServiceContract extends AbstractModel{

	private static final long serialVersionUID = 1L;
	
	private Integer id_contract;
	private Integer id_service;
	private Integer price;
	private Integer amount;
	
	
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public Integer getId_contract() {
		return id_contract;
	}
	public void setId_contract(Integer id_contract) {
		this.id_contract = id_contract;
	}
	public Integer getId_service() {
		return id_service;
	}
	public void setId_service(Integer id_service) {
		this.id_service = id_service;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	} 
}
