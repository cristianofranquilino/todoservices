package br.com.todoservices.api;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.inject.Singleton;

import br.com.todoservices.auth.Sessao;
import br.com.todoservices.base.utils.Utils;
import br.com.todoservices.entities.model.Usuario;

@Singleton
public class LoginApi {

	private static TokenAPI<Usuario> tokenApi = new TokenAPI<>();
	private static Map<String, Sessao> usuariosSession;

	private static final int TEMPO_USUARIO_OCIOSO = 60;

	private static Map<String, Sessao> getUsuariosSessao() {
		if (usuariosSession == null) {
			usuariosSession = new HashMap<>();
		}
		return usuariosSession;
	}

	private static boolean isValid(String _token) throws Exception {
		if (!Utils.isValido(_token))
			return false;

		Sessao sessaoUser = getSessaoByToken(_token);

		if (sessaoUser == null)
			return false;

		return TokenAPI.validaTokenCom(sessaoUser.getToken(), _token);
	}

	public static boolean isLogado(String _token) throws Exception {
		return isValid(_token);
	}

	public static Map<String, Sessao> getUsuariosSessionLogados() {
		return getUsuariosSessao();
	}

	public static void addUsuarioSession(Sessao _usuarioSession) {
		getUsuariosSessao().put(_usuarioSession.getToken().getToken(), _usuarioSession);
	}

	public static Sessao getSessaoByToken(String _token) {
		if (!Utils.isValido(_token))
			return null;

		return getUsuariosSessao().get(_token);
	}

	public static Usuario getUsuarioAutenticado(String _token) throws Exception {
		Usuario usuarioAutenticado = tokenApi.toObject(_token);
		return usuarioAutenticado;
	}

	public static void logoff(String _token) throws Exception {
		if (Utils.isValido(_token)) {
			removeUsuarioSession(getSessaoByToken(_token));
		}
	}

	private static void removeUsuarioSession(Sessao _usuarioSession) {
		if (getUsuariosSessao().containsKey(_usuarioSession.getToken().getToken())) {
			getUsuariosSessao().remove(_usuarioSession.getToken().getToken());
		}
	}

	public static void removeUsuariosOciosos() {
		for (Iterator<Sessao> i = getUsuariosSessao().values().iterator(); i.hasNext();) {
			Sessao u = i.next();
			if (estaComAcessoOcioso(u)) {
				i.remove();
			}
		}
	}

	public static void atualizaAtividade(String _token) {
		if (Utils.isValido(_token)) {
			if (getUsuariosSessao().containsKey(_token)){
				Sessao u = getUsuariosSessao().get(_token);
				u.setUltimaAtividade(LocalDateTime.now());
			}
		}
	}

	public static boolean estaComAcessoOcioso(Sessao _usuarioSession) {
		LocalDateTime agora = LocalDateTime.now();
		LocalDateTime dataUltimaAtividade = _usuarioSession.getUltimaAtividade();
		
		Duration duracao = Duration.between(dataUltimaAtividade, agora);
		
		return (duracao.getSeconds() / 60) > TEMPO_USUARIO_OCIOSO;
	}
}
