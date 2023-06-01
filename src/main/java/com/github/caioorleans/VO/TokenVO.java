package com.github.caioorleans.VO;

import java.io.Serializable;
import java.util.Date;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @EqualsAndHashCode
public class TokenVO implements Serializable{

	private static final long serialVersionUID = 1L;

	private String username;
	private Boolean authenticated;
	private Date created;
	private Date expiration;
	private String acessToken;
	private String refreshToken;
	
	public TokenVO() {}
	
	public TokenVO(String username, Boolean authenticated, Date created, Date expiration, String acessToken,
			String refreshToken) {
		super();
		this.username = username;
		this.authenticated = authenticated;
		this.created = created;
		this.expiration = expiration;
		this.acessToken = acessToken;
		this.refreshToken = refreshToken;
	}

}
