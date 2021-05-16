package br.com.todoservices.api;

import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.Gson;

import br.com.todoservices.auth.Token;
import br.com.todoservices.entities.model.Usuario;

public class TokenAPI<T> {

	private Class<T> classe;
	public static String AUTH0 = "cristiano"; 
	public Token tokenInfo;
	
	
	public Token toToken(Usuario data) throws Exception {
		
		String json = new Gson().toJson(data);
		
		KeyPair keyPair = getKeyPair();
		
		Algorithm algorithm = Algorithm.RSA512((RSAPublicKey)keyPair.getPublic(), (RSAPrivateKey)keyPair.getPrivate());
	    String token = JWT.create().withIssuer(AUTH0).withSubject(json).sign(algorithm);
		
	    tokenInfo = new Token(token, (RSAPublicKey)keyPair.getPublic());
	    return tokenInfo;
		
	}	
	
	public Usuario toObject(String stringToken) throws Exception {
		try {
		    DecodedJWT jwt = JWT.decode(stringToken);
		    String jsonUsuario = jwt.getSubject();
			
		    return new Gson().fromJson(jsonUsuario, Usuario.class);
		} catch (JWTDecodeException exception){
		    return null;
		}
	}
	
	
	public static Boolean validaTokenCom(Token t, String tokenHeader) throws GeneralSecurityException {
		
		try {
			Algorithm algorithm = Algorithm.RSA512(t.getPublicKey(), (RSAPrivateKey)getKeyPair().getPrivate());
			JWT.require(algorithm).withIssuer(AUTH0).build().verify(tokenHeader); 
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	private static KeyPair getKeyPair() throws NoSuchAlgorithmException{
		KeyPairGenerator keyInstance = KeyPairGenerator.getInstance("RSA");
		keyInstance.initialize(1024);
		return keyInstance.generateKeyPair();
	}
	
}
