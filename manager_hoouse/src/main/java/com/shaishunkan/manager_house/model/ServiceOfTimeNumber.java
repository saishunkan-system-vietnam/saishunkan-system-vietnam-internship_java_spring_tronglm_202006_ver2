package com.shaishunkan.manager_house.model;

public class ServiceOfTimeNumber extends AbstractModel{
	
	private static final long serialVersionUID = 1L;
	
	private Integer id_ser_contract;
	private Integer id_price_use;
	private Integer number_old;
	private Integer number_new;
	
	public Integer getId_ser_contract() {
		return id_ser_contract;
	}
	public void setId_ser_contract(Integer id_ser_contract) {
		this.id_ser_contract = id_ser_contract;
	}
	public Integer getId_price_use() {
		return id_price_use;
	}
	public void setId_price_use(Integer id_price_use) {
		this.id_price_use = id_price_use;
	}
	public Integer getNumber_old() {
		return number_old;
	}
	public void setNumber_old(Integer number_old) {
		this.number_old = number_old;
	}
	public Integer getNumber_new() {
		return number_new;
	}
	public void setNumber_new(Integer number_new) {
		this.number_new = number_new;
	}
}
