package com.shaishunkan.manager_house.model;

import java.time.LocalDate;
import java.util.List;

public class Contract extends AbstractModel{

	private static final long serialVersionUID = 1L;
	
	private Integer id_room_2;
	private Integer time_payment;
	private Integer deposit;
	private LocalDate start_date;
	private Integer expired;
	private Integer status_flg;
	private Integer unit_of_time;
	private String info;
	private Integer price;
	
	private List<Customer> listCustomer;
	private List<ServiceContract> listServiceContract;
		
	
	
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Integer getUnit_of_time() {
		return unit_of_time;
	}
	public void setUnit_of_time(Integer unit_of_time) {
		this.unit_of_time = unit_of_time;
	}
	public List<Customer> getListCustomer() {
		return listCustomer;
	}
	public void setListCustomer(List<Customer> listCustomer) {
		this.listCustomer = listCustomer;
	}
	public List<ServiceContract> getListServiceContract() {
		return listServiceContract;
	}
	public void setListServiceContract(List<ServiceContract> listServiceContract) {
		this.listServiceContract = listServiceContract;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public Integer getStatus_flg() {
		return status_flg;
	}
	public void setStatus_flg(Integer status_flg) {
		this.status_flg = status_flg;
	}
	public Integer getId_room_2() {
		return id_room_2;
	}
	public void setId_room_2(Integer id_room_2) {
		this.id_room_2 = id_room_2;
	}
	public Integer getTime_payment() {
		return time_payment;
	}
	public void setTime_payment(Integer time_payment) {
		this.time_payment = time_payment;
	}
	public Integer getDeposit() {
		return deposit;
	}
	public void setDeposit(Integer deposit) {
		this.deposit = deposit;
	}
	public LocalDate getStart_date() {
		return start_date;
	}
	public void setStart_date(LocalDate start_date) {
		this.start_date = start_date;
	}
	public Integer getExpired() {
		return expired;
	}
	public void setExpired(Integer expired) {
		this.expired = expired;
	}
}
