package com.shaishunkan.manager_house.model;

import java.time.LocalDate;

public class PriceUse extends AbstractModel{

	private static final long serialVersionUID = 1L;
	
	private Integer id_contract_1;
	private LocalDate from_date;
	private LocalDate to_date;
	private Integer price_all;
	private Integer price_service;
	public Integer getId_contract_1() {
		return id_contract_1;
	}
	public void setId_contract_1(Integer id_contract_1) {
		this.id_contract_1 = id_contract_1;
	}
	public LocalDate getFrom_date() {
		return from_date;
	}
	public void setFrom_date(LocalDate from_date) {
		this.from_date = from_date;
	}
	public LocalDate getTo_date() {
		return to_date;
	}
	public void setTo_date(LocalDate to_date) {
		this.to_date = to_date;
	}
	public Integer getPrice_all() {
		return price_all;
	}
	public void setPrice_all(Integer price_all) {
		this.price_all = price_all;
	}
	public Integer getPrice_service() {
		return price_service;
	}
	public void setPrice_service(Integer price_service) {
		this.price_service = price_service;
	}
	
	
	
	

}
