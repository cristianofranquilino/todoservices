package br.com.todoservices.dao;

import br.com.todoservices.connection.dao.GenericDao;
import br.com.todoservices.entities.model.UF;

public class UfDao extends GenericDao<UF> {

	public UfDao() {
		super(UF.class);
	}
}
