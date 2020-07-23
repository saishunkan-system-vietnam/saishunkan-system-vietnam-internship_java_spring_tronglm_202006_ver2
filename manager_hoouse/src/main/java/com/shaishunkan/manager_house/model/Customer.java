package com.shaishunkan.manager_house.model;

import java.util.List;

public class Customer extends AbstractModel{

	private static final long serialVersionUID = 1L;
	
	private String name;
	private String mail;
	private String phone;
	private String address;
	private String i_d;
	private String car_number;
	private Integer status_flg;
	private List<Account> listAccount;
	private Integer id_contract;
	private Integer model_sex;
	
	
	public Integer getModel_sex() {
		return model_sex;
	}
	public void setModel_sex(Integer model_sex) {
		this.model_sex = model_sex;
	}
	public Integer getStatus_flg() {
		return status_flg;
	}
	public void setStatus_flg(Integer status_flg) {
		this.status_flg = status_flg;
	}
	public Integer getId_contract() {
		return id_contract;
	}
	public void setId_contract(Integer id_contract) {
		this.id_contract = id_contract;
	}
	public List<Account> getListAccount() {
		return listAccount;
	}
	public void setListAccount(List<Account> listAccount) {
		this.listAccount = listAccount;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getI_d() {
		return i_d;
	}
	public void setI_d(String i_d) {
		this.i_d = i_d;
	}
	public String getCar_number() {
		return car_number;
	}
	public void setCar_number(String car_number) {
		this.car_number = car_number;
	}
	
	
	

}
