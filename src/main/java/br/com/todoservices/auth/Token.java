package br.com.todoservices.auth;

import java.security.interfaces.RSAPublicKey;

public class Token {

	private String token;
	private RSAPublicKey publicKey;

	public Token(String token, RSAPublicKey rsaPublicKey) {
		this.token = token;
		this.publicKey = rsaPublicKey;
	}
	
	public String getToken() {
		return token;
	}
	
	public RSAPublicKey getPublicKey() {
		return publicKey;
	}

	@Override
	public String toString() {
		return "Token [ token=" + token + ", publicKey=" + publicKey + "]";
	}

}
