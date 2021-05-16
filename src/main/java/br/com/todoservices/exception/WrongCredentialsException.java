package br.com.todoservices.exception;

public class WrongCredentialsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public String getMessage(){
		return "Login e/ou senha inválidos";
	}
}
