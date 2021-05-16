package br.com.todoservices.dao;

import java.util.List;

import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import br.com.todoservices.connection.dao.GenericDao;
import br.com.todoservices.entities.model.Local;
import br.com.todoservices.entities.model.Usuario;

public class LocalDao extends GenericDao<Local> {

	public LocalDao() {
		super(Local.class);
	}

	public List<Local> getAll(Usuario usuario) {
		return super.getDatastore().createQuery(Local.class)
		.field("usuario").equal(usuario).field("ativo").equal(true)
		.asList();		
	}

	public void tiraPrincipalTodos(Local local) {		
		Query<Local> query = getDatastore().createQuery(Local.class).field("usuario").equal(local.getUsuario());
		UpdateOperations<Local> ops = getDatastore().createUpdateOperations(Local.class).set("principal", false);
		getDatastore().update(query, ops);
	}

	public void changePrincipal(Local local) {
		Query<Local> query = getDatastore().createQuery(Local.class).field("id").equal(local.getId());
		UpdateOperations<Local> ops = getDatastore().createUpdateOperations(Local.class).set("principal", true);
		getDatastore().update(query, ops);
	}

	public void inativar(Local local) {
		Query<Local> query = getDatastore().createQuery(Local.class).field("id").equal(local.getId());
		UpdateOperations<Local> ops = getDatastore().createUpdateOperations(Local.class).set("ativo", false);
		getDatastore().update(query, ops);
		
	}
}
