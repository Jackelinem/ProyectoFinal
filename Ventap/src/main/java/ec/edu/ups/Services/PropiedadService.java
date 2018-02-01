package ec.edu.ups.Services;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import ec.edu.ups.Dao.CategoriaDao;
import ec.edu.ups.Dao.ComentariosDAO;
import ec.edu.ups.Dao.PersonaDao;
import ec.edu.ups.Dao.PropiedadDao;
import ec.edu.ups.Dao.SectorDao;
import ec.edu.ups.Data.PropiedadRepository;
import ec.edu.ups.Model.Categoria;
import ec.edu.ups.Model.Comentarios;
import ec.edu.ups.Model.Persona;
import ec.edu.ups.Model.Propiedad;
import ec.edu.ups.Model.Sector;
import ec.edu.ups.Utils.Response;

@Path("/propiedad")
public class PropiedadService {
	
	@Inject
	CategoriaDao categoriaDao;
	
	@Inject
	SectorDao sectorDao;
	
	@Inject
	PropiedadDao propiedadDao;
	
	@Inject
	CategoriaDao cdao;
	
	@Inject
	SectorDao sdao;
	//private Propiedad propiedad;
	
	@Inject
	PropiedadRepository repository;
	
	@Inject 
	PersonaDao personaDao;
	
	@Inject 
	ComentariosDAO comentariosDAO;
	
	@POST
	@Path("/register")
	@Produces("application/json")
	@Consumes("application/json")
	public Response register(Propiedad propiedad) {
		Response rs= new Response();
		
		try {
			
			propiedadDao.guardar(propiedad);
			rs.setCodigo(405);
			rs.setMsj("datos guardados");
			return rs;		
			
		}
		catch (Exception e) {
			// TODO: handle exception
			rs.setCodigo(402);
			rs.setMsj("error al inserar");
			
			return rs;
		}
		
	}
	
	
	@GET
	@Path("/{id:[0-9][0-9]*}")//cualquier codigo que sea int 
	@Produces(MediaType.APPLICATION_JSON)
	public Propiedad lookupUserById(@PathParam("id") int id) {
		Propiedad propiedad = repository.findById(id);
		if (propiedad == null) {
			//throw new WebApplicationException(Response.Status.NOT_FOUND);
			System.out.println("no existe");
		}
		return propiedad;
	}
	
	
	///cambiamos los sets
	
	@GET
	@Path("/listabyUser")//cualquier codigo que sea int 
	@Produces(MediaType.APPLICATION_JSON)
	public List<Propiedad> listadopropiedad(@QueryParam("id") int id){
		List<Propiedad> ltsPropiedad= personaDao.listaPropiedadesbyUser(id).getPropiedades();
		return ltsPropiedad;
	}

	
	//desarrollo
	
	@GET
	@Path("/categoria")
	@Produces("application/json")
	public Propiedad getPropiedad(@QueryParam("costo") int id) {
		Propiedad propiedad = new Propiedad();
		propiedad.setCodigo(id);
		//propiedad.setCosto("");
		propiedad.setDireccion("cuenca");
		return propiedad;
	}

	@GET
	@Path("/categoriaid")
	@Produces("application/json")
	public Categoria getCategoriaId(@QueryParam("id") int id) {
		Categoria cat = cdao.read(id);
		System.out.println(cat);
		return cat;
	}
	
	@GET
	@Path("/sectorid")
	@Produces("application/json")
	public Sector getSectorId(@QueryParam("id") int id) {
		Sector s = sdao.leer(id);
		System.out.println(s);
		return s;
	}

	@GET
	@Path("/sectores")
	@Produces("application/json")
	public List<Sector> getSectores() {
		List<Sector> sectores = sdao.getSectores();		
		return sectores;
	}
	
	@GET
	@Path("/categorias")
	@Produces("application/json")
	public List<Categoria> getCategorias() {
		List<Categoria> categorias = cdao.getCategorias2();		
		return categorias;
	}
	
	@GET
	@Path("/propiedades")
	@Produces("application/json")
	public List<Propiedad> getPropiedades() {
		List<Propiedad> propiedades = propiedadDao.listadoPropiedades();		
		return propiedades;
	}
	
	@GET
	@Path("/propiedadid")
	@Produces("application/json")
	public Propiedad getPropiedadId(@QueryParam("id") int id) {
		Propiedad s = propiedadDao.leer(id);
		System.out.println(s);
		return s;
	}
	
	@GET
	@Path("/propiedadesbyprecio")
	@Produces("application/json")
	public List<Propiedad> getPropiedadesByPrecio(@QueryParam("valmax") int valmax,@QueryParam("valmin") int valmin) {
		List<Propiedad> propiedades = propiedadDao.ltsPropiedadByPrecio((double)valmin,(double)valmax);		
		return propiedades;
	}
	
	@GET
	@Path("/propiedadesbysector")
	@Produces("application/json")
	public List<Propiedad> getPropiedadesBySector(@QueryParam("idsector") int idsector) {
		List<Propiedad> propiedades = propiedadDao.ltsPropiedadBySector(idsector);		
		return propiedades;
	}
	
	@GET
	@Path("/propiedadesbycategoria")
	@Produces("application/json")
	public List<Propiedad> getPropiedadesByCategoria(@QueryParam("idscategoria") int idcategoria) {
		List<Propiedad> propiedades = propiedadDao.ltsPropiedadByCategorias(idcategoria);	
		return propiedades;
	}
	
	@GET
	@Path("/ltsComentarios")
	@Produces("application/json")
	public List<Comentarios> getComentariosId(@QueryParam("id") int id) {
		
		return propiedadDao.getComentario(id);
	}
	
	@GET
	@Path("/ltsPropiedadByPerson")
	@Produces("application/json")
	public List<Propiedad> getComentariosByPerson(@QueryParam("idPerson") int idPerson) {
		
		return propiedadDao.ltsPropiedadesByPerson(idPerson);
	}
	
	@GET
	@Path("/comentarioPropiedad")
	@Produces("application/json")
	public boolean comentarioBypropiedad(@QueryParam("idPropiedad") int idPropiedad, 
			@QueryParam("comentario") String comentario,@QueryParam("idPersona") int idPersona) {
		boolean inserto=false;
		Propiedad pro = propiedadDao.leer(idPropiedad);
		Persona persona = personaDao.leer(idPersona);
		Comentarios c = new Comentarios();
		c.setComentario(comentario);
		c.setPropiedad(pro);
		c.setPersona(persona);
		try {

			comentariosDAO.insertar(c);
			inserto = true;
		}catch (Exception e) {
			// TODO: handle exception
			inserto = false;
		}
		return inserto;
	}
	@GET
	@Path("/registroPrpiedad")
	@Produces("application/json")
	public boolean registrarPropiedad(@QueryParam("costo") String costo,@QueryParam("des") String des,
			@QueryParam("estado") String estado,@QueryParam("direccion") String direccion,@QueryParam("lati") String lati,
			@QueryParam("longui") String longui,@QueryParam("tipo") String tipo,@QueryParam("video") String video,
			@QueryParam("idcategoria") int idcategoria,@QueryParam("idpersona") int idpersona,
			@QueryParam("idsector") int idsector) {
		boolean inserto=false;
		
		Categoria cat = categoriaDao.leer(idcategoria);
		Persona per = personaDao.leer(idpersona);
		Sector sec = sectorDao.leer(idsector);
		
		Propiedad pro= new Propiedad();
		
		
		return inserto;
	}
	
	
	

}
