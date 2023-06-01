package com.github.caioorleans.VO;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @EqualsAndHashCode
public class AccountCredentialsVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String username;
	private String password;
	
	public AccountCredentialsVO(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

}
