package br.com.todoservices.builder;

import javax.ws.rs.core.Response;

public class MyResponse {

	//Response.status(200).entity(new Gson().toJson(all)).build();
	
	private static Response response;
	
	
	
	public MyResponse success(){
		response.status(200);
		return this;
	}



	
}
