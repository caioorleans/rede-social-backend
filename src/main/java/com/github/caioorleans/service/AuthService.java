package com.github.caioorleans.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.github.caioorleans.VO.AccountCredentialsVO;
import com.github.caioorleans.VO.TokenVO;
import com.github.caioorleans.repository.UserRepository;
import com.github.caioorleans.security.JWT.JwtTokenProvider;

@Service
public class AuthService {

	@Autowired
	private JwtTokenProvider tokenProvider;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserRepository repository;
	
	@SuppressWarnings("rawtypes")
	public ResponseEntity signin(AccountCredentialsVO data) {
		try {
			var username = data.getUsername();
			var password = data.getPassword();
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(username, password));
			var user = repository.findByUsername(username);
			
			var tokenResponse = new TokenVO();
			
			if (user != null) {
				tokenResponse = tokenProvider.createAcessToken(username, user.getRoles());
			} else {
				throw new UsernameNotFoundException("username " + username + "not found!");
			}
			
			return ResponseEntity.ok(tokenResponse);
		}
		catch(Exception e) {
			throw new BadCredentialsException("Invalid username/password supplied!");
		}
	}
	
	@SuppressWarnings("rawtypes")
	public ResponseEntity refreshToken(String username, String refreshToken) {

		var user = repository.findByUsername(username);

		var tokenResponse = new TokenVO();

		if (user != null) {
			tokenResponse = tokenProvider.refreshToken(refreshToken);
		} else {
			throw new UsernameNotFoundException("username " + username + "not found!");
		}

		return ResponseEntity.ok(tokenResponse);
	}
}
