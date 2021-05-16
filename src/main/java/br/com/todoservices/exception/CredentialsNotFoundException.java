package br.com.todoservices.exception;

public class CredentialsNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4000988445868624990L;

	@Override
	public String getMessage(){
		return "Login não encontrado.";
	}
	
}
