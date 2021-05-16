package br.com.todoservices.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import br.com.todoservices.api.LoginApi;
import br.com.todoservices.api.ResponseAPI;
import br.com.todoservices.base.annotations.NotSecure;
import br.com.todoservices.entities.model.Login;
import br.com.todoservices.entities.model.Usuario;
import br.com.todoservices.exception.CredentialsNotFoundException;
import br.com.todoservices.exception.WrongCredentialsException;
import br.com.todoservices.service.LoginService;

@Path("/login")
@Produces("application/json")
@Consumes("application/json")
public class LoginResource {

	private LoginService loginService = new LoginService();
	private ResponseAPI response = new ResponseAPI();
	
	@POST
	@Path("entrar")
	@NotSecure
	public Response login(Login login) {
		
		try {
			Usuario user = loginService.realizarLogin(login);
			return response.success().data(user).build();
		} catch (WrongCredentialsException e) {
			return response.information().message(e.getMessage()).build();
		} catch (CredentialsNotFoundException c) {
			return response.information().message(c.getMessage()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return response.error().message(e.getMessage()).build();
		}
	}
	
	@GET
	@Path("logout")
	public Response logout(@HeaderParam("Authorization") String _token) throws Exception {
		LoginApi.logoff(_token);
		return response.success().build();
	}
	
	@GET
	@Path("usuario-logado")
	public Response getUsuarioLogado(@HeaderParam("Authorization") String _token) throws Exception {
		Usuario usuarioAutenticado = LoginApi.getUsuarioAutenticado(_token);
		return response.success().data(usuarioAutenticado).build();
	}
	
	@POST
	@Path("verifica-email")
	@NotSecure
	public Response verificaEmail(String email){
		boolean isCadastrado = loginService.isEmailRegistrado(email);
		return response.success().data(isCadastrado).build();
	}
	
	@POST
	@Path("inserir")
	@NotSecure
	public Response insert(Usuario usuario) throws Exception {
		loginService.criar(usuario);
		return response.success().build();
	}
	
	@POST
	@Path("forgot")
	@NotSecure
	public Response esqueci(Login login) {
		try {			
			loginService.processaEsquecimentoSenha(login);
			return response.success().build();
		} catch (CredentialsNotFoundException e) {
			e.printStackTrace();
			return response.information().message(e.getMessage()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return response.error().message(e.getMessage()).build();
		}
	}
}
