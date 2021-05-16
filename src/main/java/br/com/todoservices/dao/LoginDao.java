package br.com.todoservices.dao;

import java.util.List;

import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import br.com.todoservices.connection.dao.GenericDao;
import br.com.todoservices.entities.model.Login;

public class LoginDao extends GenericDao<Login>{

	public LoginDao() {
		super(Login.class);
	}

	public List<Login> getLogin(Login login) {
		return super.getDatastore().createQuery(Login.class)
					.field("email").equal(login.getEmail())
					.field("senha").equal(login.getSenha())
					.asList();		
	}	
	
	public Login getLoginByEmail(String email) {
		return super.getDatastore().createQuery(Login.class)
					.field("email").equal(email).get();		
	}	
	
	public boolean isRegistred(Login login) {
		return !super.getDatastore().createQuery(Login.class)
					.field("email").equal(login.getEmail())
					.asList().isEmpty();		
	}

	public void atualiza(Login login) {
		
		Query<Login> query = getDatastore().createQuery(Login.class).field("id").equal(login.getId());
		UpdateOperations<Login> ops = getDatastore().createUpdateOperations(Login.class).set("senha", login.getSenha()).set("trocaSenha", login.isTrocaSenha());

		getDatastore().update(query, ops);

	}	
	
	
}
