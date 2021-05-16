package br.com.todoservices.dao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import br.com.todoservices.connection.dao.GenericDao;
import br.com.todoservices.entities.model.Municipio;
import br.com.todoservices.entities.model.UF;

public class MunicipioDao extends GenericDao<Municipio> {

	public MunicipioDao() {
		super(Municipio.class);
	}
	
	public List<Municipio> getByUf(UF uf){
		return super.getDatastore().createQuery(Municipio.class)
					.field("uf").equal(uf.getDescricao()).asList();		
	}
	
	public void carregaBase() throws IOException{
		
		List<Municipio> ms = new ArrayList<>();
		
		 Files.lines(Paths.get("templates/Municipios.html")).forEachOrdered(l -> ms.add(monta(l)));
		 
		 super.insertAll(ms);
		 
		 
	}

	private static Municipio monta(String l) {

		/* Abadia de Goiás (GO) */
		try {
		
		String[] split = l.split("\\|");
		
		Municipio municipio = new Municipio();
		
		municipio.setNome(split[0].trim());
		municipio.setUf(split[1].trim());
		
		return municipio; 
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
