package com.shaishunkan.manager_house.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class Account extends AbstractModel implements UserDetails{
	private static final long serialVersionUID = 1L;
	
	private Integer id_customer;
	private String password;
	private String role;
	private String mail;
	private Integer status_flg;
	private List<GrantedAuthority> roles;
		
	
	public Integer getId_customer() {
		return id_customer;
	}
	public void setId_customer(Integer id_customer) {
		this.id_customer = id_customer;
	}
	public Integer getStatus_flg() {
		return status_flg;
	}
	public void setStatus_flg(Integer status_flg) {
		this.status_flg = status_flg;
	}
	public List<GrantedAuthority> getRoles() {
		return roles;
	}
	public void setRoles(List<GrantedAuthority> roles) {
		this.roles = roles;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return roles;
	}
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return mail;
	}
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}	

}
