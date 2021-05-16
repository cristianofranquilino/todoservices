package br.com.todoservices.service;

import java.util.List;

import br.com.todoservices.dao.LocalDao;
import br.com.todoservices.dao.MunicipioDao;
import br.com.todoservices.dao.UfDao;
import br.com.todoservices.entities.model.Local;
import br.com.todoservices.entities.model.Municipio;
import br.com.todoservices.entities.model.UF;
import br.com.todoservices.entities.model.Usuario;

public class LocalidadeService {

	private UfDao ufDao;
	private MunicipioDao municipioDao;
	private LocalDao localDao;
	
	public LocalidadeService() {
		ufDao = new UfDao();
		municipioDao = new MunicipioDao();
		localDao = new LocalDao();
	}
	
	public List<UF> getAllUfs() {
		return ufDao.getAll();
	}

	public List<Municipio> getMunicipiosBy(String uf) {
		return municipioDao.getByUf(new UF(uf));
	}

	public void persistir(Local local) {

		local.setAtivo(true);
		localDao.insert(local);
		
		if (local.getId() == null){
			mudarPrioridade(local); 
		}
	}

	public List<Local> getAll(Usuario usuario) {
		List<Local> locais = localDao.getAll(usuario);
		locais.sort((l1, l2) -> l1.isPrincipal() ? -1 : 0);
		return locais; 
	}

	public List<Local> mudarPrioridade(Local local) {
		localDao.tiraPrincipalTodos(local);
		localDao.changePrincipal(local);
		return getAll(local.getUsuario());
	}

	public List<Local> inativar(Local local) {
		localDao.inativar(local);
		return getAll(local.getUsuario());
	}

}
