package ec.edu.ups.Controller;

import java.nio.file.Paths;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.servlet.http.Part;

import com.lowagie.text.pdf.codec.Base64;

import ec.edu.ups.Dao.CategoriaDao;
import ec.edu.ups.Dao.ComentariosDAO;
import ec.edu.ups.Dao.PersonaDao;
import ec.edu.ups.Dao.PropiedadDao;
import ec.edu.ups.Dao.ProvinciaDAO;
import ec.edu.ups.Model.Categoria;
import ec.edu.ups.Model.Comentarios;
import ec.edu.ups.Model.Imagen;
import ec.edu.ups.Model.Persona;
import ec.edu.ups.Model.Propiedad;
import ec.edu.ups.Model.Provincia;
import ec.edu.ups.Model.Sector;
import ec.edu.ups.Model.Telefono;

/*
 * controlador de propiedad
 */
@ManagedBean
@ViewScoped
public class PropiedadController {
	
	//bean properties
	private Provincia provincia; //instancia Provincia
	private Sector sector;//instancia sector
	private Persona persona;// instancia persona
	private Categoria categoria;//instancia categoria
	private Comentarios comentario;
	
	private Propiedad propiedad;//instancia categoria
	private List<Propiedad> listpropiedades;//lista de propiedades
	private List<Propiedad> listpropiedadesMapa;//lista de propiedades
	private List<Provincia> provinciasDeBase;//lista provincias ya registradas
	private List<Categoria> categoriasDeBase;//lista de Categorias ya registradas
	private List<Comentarios> listComentarios;
	private Comentarios comSelec;
	
	
	private String campoSec="";
	private String campoCat="";
	
	//id me permite saber si es nuevo registro 
	private int id;
	
	//indice para obtener el sector de una lista de sectores que pertenece a la provincia
	private int indice;
	
	private String provin;
	
	private String com;
	private boolean privado;
	
	//codigo persona para validar existencia de persona
	private int codigoPersona;
	
	//correo para buscar persona propietaria 
	private String correo="";
	
	//lista de personas de la base
	private List<Persona> listaPersonas;
	
	/*
	 * listas y campos para manejar los combos
	 */
	private List<SelectItem> listaProvincias;
	private String provi;
	
	private List<SelectItem> listaSectores;
	private String sect;
	
	private List<SelectItem> listaCategorias;
	private String cat;
	
	private String uploads = "C:\\images";
	//private String uploads = "/home/imagenes";
		
	private String filename;
		
	
	//imagen
	private Part file;
	private String descImg;
	
	@Inject
	private PropiedadDao propiedadDao;
	
	@Inject
	private ProvinciaDAO provDao;
	
	@Inject
	private PersonaDao perDao;
	
	@Inject
	private CategoriaDao catDao;
	
	@Inject
	private ComentariosDAO comDao;
	
	
	@Inject
	private Sesion sesion;
	
	
	@PostConstruct
	public void init() {
		listComentarios=new ArrayList<Comentarios>();
		comentario=new Comentarios();
		persona =new Persona();
		provincia=new Provincia();
		sector=new Sector();
		propiedad =new Propiedad();
		categoria=new Categoria();
		//sector.addPropiedadr(new Propiedad());
		listaSectores= new ArrayList<SelectItem>();
		
		//carga los combos
		cargarProvincias();
		cargarCategorias();
		//carga la propiedad
		//propiedad.addImagen(new Imagen());
		loadPropiedades();
	}

	
	/*public String guardar() {
		System.out.println(toString());
		
		propiedadDao.guardar(propiedad);
		
		return null;
	}*/
	
	public String eliminar(int codigo) {
		System.out.println("Entro a eliminar "+codigo+toString());
		
			try {
				
				propiedadDao.eliminar(codigo);
				loadPropiedades();
				
			}
			catch (Exception e) {
				System.out.println("EERORRORO");
				
			}
		return null;
	}
	
	/*
	 * metodo que carga los datos propiedad a editar buscando por el id
	 */
	public String loadDatosEditar(int codigo) {
		
		propiedad =propiedadDao.leer(codigo);
		listComentarios =propiedadDao.leer2(codigo,false);
		
		System.out.println(propiedad.getDescripcion()+" tiene imagenes "+propiedad.getImagenes().size());
		return "EditarPropiedad";
	}
	


/*
 * metodo para obtener la lista de propiedades
 */
	private void loadPropiedades() {
		
		listpropiedades=propiedadDao.listadoPropiedades(campoSec,campoCat);
		listpropiedadesMapa=propiedadDao.listadoPropiedades2();
		
		for (int i = 0; i < listpropiedades.size(); i++) {
			System.out.println(listpropiedades.get(i).getCodigo()+"" +listpropiedades.get(i).getDescripcion());
			
			if(listpropiedades.get(i).getCodigo()==9) {
				System.out.println("imagen " +listpropiedades.get(i).getImagenes().get(0).getDescripcionImagen());
			}
			
		}
	}
	
	
	
/*
 * metodo que me busca la persona mediante el correo 
 * para agregarle al objeto propietario
 */
	public void añadirPersona(Persona per) {
		//listaPersonas=perDao.getPersonasXemail(correo);
		
		//if(per!=null) {
			this.setCodigoPersona(per.getCodigo());
			System.out.println("cod persona "+per.getCodigo());
			//persona=listaPersonas.get(0);
			//codigoPersona=persona.getCodigo();
			propiedad.setPersona(per);
		//}
		//init();
		
	}
	
	/*
	 * metodo para cargar categorias a la lista del combo
	 */
	private void cargarCategorias(){
		listaCategorias = new ArrayList<SelectItem>();
		categoriasDeBase=catDao.listadoCategoria();
		listaCategorias.add(new SelectItem("vacio","SELECCIONA la CATEGORIA"));
		for (int i = 0; i < categoriasDeBase.size(); i++) {
			listaCategorias.add(new SelectItem(categoriasDeBase.get(i).getAlias(),categoriasDeBase.get(i).getDescripcion()));
		}
		
	}
	
	/*
	 * metodo para cargar provincias a la lista del combo
	 */
	private void cargarProvincias(){
		listaProvincias = new ArrayList<SelectItem>();
		provinciasDeBase=provDao.listaProvincias();
		listaProvincias.add(new SelectItem("vacio","SELECCIONA la PROVINCIA"));
		for (int i = 0; i < provinciasDeBase.size(); i++) {
			listaProvincias.add(new SelectItem(provinciasDeBase.get(i).getAlias(),provinciasDeBase.get(i).getNombre()));
		}
		
	}
	
	/*
	 * metodo para cargar sectores a la lista del combo
	 */
	private void cargarSectores(List<Sector> sectores){
		listaSectores = new ArrayList<SelectItem>();
		System.out.println("Entro a la carga de sectores ");
		listaSectores.add(new SelectItem("vacio","SELECCIONA EL SECTOR"));
		for (int i = 0; i < sectores.size(); i++) {
			listaSectores.add(new SelectItem(sectores.get(i).getAlias(),sectores.get(i).getNombre()));
		}
	}
	
	/*
	 * metodo que me permite obtener la provincia seleccionada y llama a cargar los sectores
	 * de aquella provincia
	 */
	public void agregarSectoresCombo(){
		
		System.out.println("se obtiene ");
			provincia=provDao.getProvincias(provi).get(0);
			if(provincia.getSectores()!=null) {
				propiedad.setSector(provincia.getSectores().get(0));
			}
			System.out.println("se obtiene "+provincia);
			cargarSectores(provincia.getSectores());
	
	}
	
	/*
	 * metodo de selecion de sector y carga el sector a la propiedad
	 */
	public void seleccionarSectoresCombo(){
		System.out.println("entro selecion sector ");
		try {
			for (int i = 0; i < provincia.getSectores().size(); i++) {
				if(provincia.getSectores().get(i).getAlias().equals(sect)) {
					System.out.println("entro "+provincia.getSectores().get(i).getAlias());
					propiedad.setSector(provincia.getSectores().get(i));
					indice=i;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	/*
	 * metodo que obtiene la selecion de la categoria y la busca para agregarla a la propiedad
	 */
	public void seleccionarCategoriasCombo(){
		System.out.println("entro selecion Categoria "+ categoriasDeBase.size());
		try {
			for (int i = 0; i < categoriasDeBase.size(); i++) {
				if(categoriasDeBase.get(i).getAlias().equals(cat)) {
					
					propiedad.setCategoria(categoriasDeBase.get(i));
					System.out.println("entro selecion Categoria alias"+ categoriasDeBase.get(i).getAlias() + categoriasDeBase.get(i).getCodigo());
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	/*
	 * metodo ´para guardar la propiedad desde una actualizacion de la provincia
	public String registrarPropiedad() {
		System.out.println("Sector "+provincia.getSectores().get(indice).getAlias());
		provincia.getSectores().get(indice).getPropiedades().add(propiedad);
		provDao.actualizar(provincia);
		init();
		return null;
	}*/

	/*
	 * metodo que me permite guardar la propiedad una ves que se han cargado las entidades relacionadas
	 */
	public String savePropiedad() {
		System.out.println("propiedad a guradar");
		
		System.out.println("Propietario "+propiedad.getPersona().getNombres());
		System.out.println("Sector "+propiedad.getSector().getNombre());
		System.out.println("Categoria alias"+propiedad.getCategoria().getDescripcion());
		System.out.println("numero de imagenes"+propiedad.getImagenes().size());
		
		propiedadDao.guardar(propiedad);
		init();
		return null;
	}
	
	public String publicarPropiedad() {
		propiedad.setPersona(sesion.getUser());
		System.out.println("propiedad a guradar");
		System.out.println("Propietario "+propiedad.getPersona().getNombres());
		System.out.println("Sector "+propiedad.getSector().getNombre());
		System.out.println("Categoria alias"+propiedad.getCategoria().getDescripcion());
		System.out.println("numero de imagenes"+propiedad.getImagenes().size());
		
		propiedadDao.guardar(propiedad);
		init();
		return null;
	}
	
	public String agregaImagenes(){
		try(InputStream input = file.getInputStream()){
			//propiedad.setPathImg(uploadedFile.getSubmittedFileName());
	   		 filename = Paths.get(file.getSubmittedFileName()).getFileName().toString();
	   		 //filename = uploadedFile.getSubmittedFileName();
	   		 Files.copy(input, new File(uploads, filename).toPath());
	   		 //System.out.println(prop.toString());
	   		// pDAO.insertar(prop);
	   		 
	   		 System.out.println(""+filename+" - "+file.getSubmittedFileName());
	   		Imagen img = new Imagen();
			img.setDescripcionImagen(file.getSubmittedFileName());
			img.setNombreImagen(filename);
			
			byte[] b = new byte[(int) file.getSize()];
			file.getInputStream().read(b);
			//Imagen img=new Imagen();
			//img.setDescripcionImagen(descImg);
			img.setImg(b);
			//if (propiedad.getImagenes().isEmpty()) {
				//
				propiedad.addImagen(img);
				System.out.println("Imagenes "+propiedad.getImagenes().size());
			/*}else {
				System.out.println("Imagenes "+propiedad.getImagenes().size());
				propiedad.getImagenes().add(img);
			}*/
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	public String toBase64(Propiedad p){
		try {
			byte [] b=p.getImagenes().get(0).getImg();
			return "data:image/jpg;base64,"+Base64.encodeBytes(b);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		
	}
	
	public String imageToBase64(byte [] b){
		try {
			return "data:image/jpg;base64,"+Base64.encodeBytes(b);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		
	}
	public String irAPropiedad(Propiedad p){
		try {
			this.propiedad=p;
			return "PropiedadDetalle";
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		
	}
	
	public void seleccionausuari(Persona p){
		System.out.println("Usuario Seleccionado: " + p);
		this.setPersona(p);
		System.out.println("Usuario Seleccionado: ");
		System.out.println("Nombre: "+this.getPersona().getNombres());
	}
	public void filtrar(){
		loadPropiedades();
	}


	
	public void agregarComentario() {
		
		
		
		
		
		comentario.setComentario(com);
		System.out.println(com);
		comentario.setPersona(sesion.getUser());
		System.out.println(sesion.getUser());
		comentario.setPropiedad(propiedad);
		System.out.println(propiedad);
		comentario.setPrivado(Boolean.valueOf(privado));
		System.out.println(privado);
		comDao.insertar(comentario);
		System.out.println(comentario);
		com="";
		listComentarios =propiedadDao.leer2(propiedad.getCodigo(),false);
		
	}
	
	public void seleccionarComentario(Comentarios c) {
		this.comSelec=c;
	}
	public void limpiarComentario() {
		this.comSelec=null;
	}
	
	
	
	
	
	
	//getters and setters
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
		
		loadDatosEditar(id);
	}


	public List<Propiedad> getListpropiedades() {
		return listpropiedades;
	}


	public void setListpropiedades(List<Propiedad> listpropiedades) {
		this.listpropiedades = listpropiedades;
	}


	public Propiedad getPropiedad() {
		return propiedad;
	}


	public void setPropiedad(Propiedad propiedad) {
		this.propiedad = propiedad;
	}
	
	
	public Provincia getProvincia() {
		return provincia;
	}


	public void setProvincia(Provincia provincia) {
		this.provincia = provincia;
	}


	public Sector getSector() {
		return sector;
	}


	public void setSector(Sector sector) {
		this.sector = sector;
	}


	public List<Provincia> getProvinciasDeBase() {
		return provinciasDeBase;
	}


	public void setProvinciasDeBase(List<Provincia> provinciasDeBase) {
		this.provinciasDeBase = provinciasDeBase;
	}


	public String getProvin() {
		return provin;
	}


	public void setProvin(String provin) {
		this.provin = provin;
	}


	public List<SelectItem> getListaProvincias() {
		return listaProvincias;
	}


	public void setListaProvincias(List<SelectItem> listaProvincias) {
		this.listaProvincias = listaProvincias;
	}


	public String getProvi() {
		return provi;
	}


	public void setProvi(String provi) {
		this.provi = provi;
	}


	public List<SelectItem> getListaSectores() {
		return listaSectores;
	}


	public void setListaSectores(List<SelectItem> listaSectores) {
		this.listaSectores = listaSectores;
	}


	public String getSect() {
		return sect;
	}


	public void setSect(String sect) {
		this.sect = sect;
	}


	public PropiedadDao getPropiedadDao() {
		return propiedadDao;
	}


	public void setPropiedadDao(PropiedadDao propiedadDao) {
		this.propiedadDao = propiedadDao;
	}


	public ProvinciaDAO getProvDao() {
		return provDao;
	}


	public void setProvDao(ProvinciaDAO provDao) {
		this.provDao = provDao;
	}


	public int getIndice() {
		return indice;
	}


	public void setIndice(int indice) {
		this.indice = indice;
	}

	

	public Persona getPersona() {
		return persona;
	}


	public void setPersona(Persona persona) {
		this.persona = persona;
	}


	public int getCodigoPersona() {
		return codigoPersona;
	}


	public void setCodigoPersona(int codigoPersona) {
		this.codigoPersona = codigoPersona;
		System.out.println("cod persona +"+codigoPersona);
	}


	public List<Persona> getListaPersonas() {
		return listaPersonas;
	}


	public void setListaPersonas(List<Persona> listaPersonas) {
		this.listaPersonas = listaPersonas;
	}


	public PersonaDao getPerDao() {
		return perDao;
	}


	public void setPerDao(PersonaDao perDao) {
		this.perDao = perDao;
	}
	

	
	public String getCorreo() {
		return correo;
	}


	public void setCorreo(String correo) {
		this.correo = correo;
	}
	
	


	public Categoria getCategoria() {
		return categoria;
	}


	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}


	public List<Categoria> getCategoriasDeBase() {
		return categoriasDeBase;
	}


	public void setCategoriasDeBase(List<Categoria> categoriasDeBase) {
		this.categoriasDeBase = categoriasDeBase;
	}


	public List<SelectItem> getListaCategorias() {
		return listaCategorias;
	}


	public void setListaCategorias(List<SelectItem> listaCategorias) {
		this.listaCategorias = listaCategorias;
	}


	public String getCat() {
		return cat;
	}


	public void setCat(String cat) {
		this.cat = cat;
	}


	public CategoriaDao getCatDao() {
		return catDao;
	}


	public void setCatDao(CategoriaDao catDao) {
		this.catDao = catDao;
	}


	public String getDescImg() {
		return descImg;
	}


	public void setDescImg(String descImg) {
		this.descImg = descImg;
	}


	public Part getFile() {
		return file;
	}


	public void setFile(Part file) {
		this.file = file;
	}


	public List<Propiedad> getListpropiedadesMapa() {
		return listpropiedadesMapa;
	}


	public void setListpropiedadesMapa(List<Propiedad> listpropiedadesMapa) {
		this.listpropiedadesMapa = listpropiedadesMapa;
	}


	public Comentarios getComentario() {
		return comentario;
	}


	public void setComentario(Comentarios comentario) {
		this.comentario = comentario;
	}


	public String getCom() {
		return com;
	}


	public void setCom(String com) {
		this.com = com;
	}


	public boolean isPrivado() {
		return privado;
	}


	public void setPrivado(boolean privado) {
		this.privado = privado;
	}


	public Sesion getSesion() {
		return sesion;
	}


	public void setSesion(Sesion sesion) {
		this.sesion = sesion;
	}


	public List<Comentarios> getListComentarios() {
		return listComentarios;
	}


	public void setListComentarios(List<Comentarios> listComentarios) {
		this.listComentarios = listComentarios;
	}


	public Comentarios getComSelec() {
		return comSelec;
	}


	public void setComSelec(Comentarios comSelec) {
		this.comSelec = comSelec;
	}


	public String getCampoCat() {
		return campoCat;
	}


	public void setCampoCat(String campoCat) {
		this.campoCat = campoCat;
	}


	public String getCampoSec() {
		return campoSec;
	}


	public void setCampoSec(String campoSec) {
		this.campoSec = campoSec;
	}


	public String getUploads() {
		return uploads;
	}


	public void setUploads(String uploads) {
		this.uploads = uploads;
	}


	public String getFilename() {
		return filename;
	}


	public void setFilename(String filename) {
		this.filename = filename;
	}

	
}
