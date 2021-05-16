package br.com.todoservices.dao;

import br.com.todoservices.connection.dao.GenericDao;
import br.com.todoservices.entities.model.Usuario;

public class UsuarioDao extends GenericDao<Usuario> {

	public UsuarioDao() {
		super(Usuario.class);
	}
	
}
