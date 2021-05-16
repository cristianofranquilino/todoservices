package br.com.todoservices.service;

import java.time.LocalDateTime;
import java.util.List;

import br.com.todoservices.api.LoginApi;
import br.com.todoservices.api.MailApi;
import br.com.todoservices.api.TokenAPI;
import br.com.todoservices.auth.Sessao;
import br.com.todoservices.auth.Token;
import br.com.todoservices.base.utils.AuthUtils;
import br.com.todoservices.base.utils.Utils;
import br.com.todoservices.dao.LoginDao;
import br.com.todoservices.entities.model.Login;
import br.com.todoservices.entities.model.Usuario;
import br.com.todoservices.exception.CredentialsNotFoundException;
import br.com.todoservices.exception.WrongCredentialsException;

public class LoginService {

	private LoginDao dao;
	private UsuarioService usuarioService;
	
	public LoginService(){
		dao = new LoginDao();
		usuarioService = new UsuarioService();
	}
	
	public Usuario realizarLogin(Login login) throws Exception {
		if (dao.isRegistred(login)){
			List<Login> logins = dao.getLogin(login);
			if (!logins.isEmpty()){
				Usuario usuario = logins.get(0).getUsuario();
				
				Token token = new TokenAPI<Usuario>().toToken(usuario);
				Sessao sessaoUser = new Sessao();
				
				sessaoUser.setToken(token);
				sessaoUser.setUltimaAtividade(LocalDateTime.now());

				LoginApi.addUsuarioSession(sessaoUser);
			
				usuario.setToken(token.getToken());
				
				return usuario;
			}else{
				throw new WrongCredentialsException();
			}
		}else{
			throw new CredentialsNotFoundException();
		}
	}

	public boolean isEmailRegistrado(String email) {
		Login login = new Login(email);
		return dao.isRegistred(login);
	}

	public void criar(Usuario usuario) {
		
		usuarioService.inserir(usuario); 
		
		Login login = usuario.getLogin();
				
		if (this.valida(login)){
			login.setUsuario(usuario);	
			dao.insert(usuario.getLogin());
		}else{
			throw new RuntimeException("Login nulo ou vazio.");
		}
	}

	private boolean valida(Login login) {
		if (Utils.isValido(login)){
			if (!Utils.isValido(login.getEmail()))
				return false;
			if (!Utils.isValido(login.getSenha()))
				return false;
			
			return true;
		}
		return false;
	}

	public void processaEsquecimentoSenha(Login login) throws Exception {
		Login loginUsuario = dao.getLoginByEmail(login.getEmail());
		if (loginUsuario != null){
			String novaSenha = AuthUtils.generateNewPass();
			System.out.println(novaSenha);
			loginUsuario.setSenha(novaSenha);
			loginUsuario.setTrocaSenha(true);
			dao.atualiza(loginUsuario);
			MailApi.sendForgotPass(loginUsuario);
		}else{
			throw new CredentialsNotFoundException();
		}
	}
}
