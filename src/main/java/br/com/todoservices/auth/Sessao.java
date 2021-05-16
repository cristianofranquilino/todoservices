package br.com.todoservices.auth;

import java.time.LocalDateTime;

public class Sessao {

	private Token token;
	private LocalDateTime ultimaAtividade;
	
	public Token getToken() {
		return token;
	}
	public void setToken(Token token) {
		this.token = token;
	}
	public LocalDateTime getUltimaAtividade() {
		return ultimaAtividade;
	}
	public void setUltimaAtividade(LocalDateTime ultimaAtividade) {
		this.ultimaAtividade = ultimaAtividade;
	}	
}
