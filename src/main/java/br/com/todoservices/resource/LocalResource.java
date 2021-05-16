package br.com.todoservices.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import br.com.todoservices.api.LoginApi;
import br.com.todoservices.api.ResponseAPI;
import br.com.todoservices.entities.model.Local;
import br.com.todoservices.entities.model.Municipio;
import br.com.todoservices.entities.model.UF;
import br.com.todoservices.entities.model.Usuario;
import br.com.todoservices.service.LocalidadeService;

@Path("/local")
@Produces("application/json")
@Consumes("application/json")
public class LocalResource {

	private LocalidadeService localidadeService = new LocalidadeService();
	private ResponseAPI response = new ResponseAPI();
	
	@GET
	public Response todos(@HeaderParam("Authorization") String _token) throws Exception {
		Usuario usuarioAutenticado = LoginApi.getUsuarioAutenticado(_token);
		List<Local> locais = localidadeService.getAll(usuarioAutenticado);
		return response.success().data(locais).build();
	}
	
	@POST
	@Path("priorizar")
	public Response priorizar(Local local) throws Exception {
		List<Local> locais = localidadeService.mudarPrioridade(local);
		return response.success().data(locais).build();
	}
	
	@GET
	@Path("ufs")
	public Response ufs() {
		List<UF> ufs = localidadeService.getAllUfs();
		return response.success().data(ufs).build();
	}
	
	@GET
	@Path("municipios/{uf}")
	public Response municipios(@PathParam("uf") String uf) {
		List<Municipio> municipios = localidadeService.getMunicipiosBy(uf);
		return response.success().data(municipios).build();
	}
	
	@POST
	@Path("save")
	public Response salvar(@HeaderParam("Authorization") String _token, Local local) throws Exception {
		Usuario usuarioAutenticado = LoginApi.getUsuarioAutenticado(_token);
		local.setUsuario(usuarioAutenticado);
		localidadeService.persistir(local);
		return response.success().build();
	}
	
	@POST
	@Path("excluir")
	public Response excluir(Local local) throws Exception {
		List<Local> ativos = localidadeService.inativar(local);
		return response.success().data(ativos).build();
	}
	
}
