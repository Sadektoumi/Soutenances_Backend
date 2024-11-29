package com.soutenances.soutenances.DTO;

public class AuthenticationResponse {
	private String message;
	 private String token;
	public AuthenticationResponse(String token,String message) {

		this.message = message;
		this.token=token;
	}

	public String getMessage() {
		return message;
	}
	public String getToken() {
		return token;
	}
	
}
