package ec.edu.ups.Dao;

import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import ec.edu.ups.Model.Categoria;
import ec.edu.ups.Model.Comentarios;
import ec.edu.ups.Model.Persona;
import ec.edu.ups.Model.Propiedad;
import ec.edu.ups.Model.Sector;


/*
 * objeto de acceso a datos de propiedad
 */
@Stateless
public class PropiedadDao {
	
	@Inject 
	private EntityManager em;
	
	
	/*
	 * metodo de guardar o actualizar Propiedad
	 */
	public void guardar(Propiedad propiedad) {
		Propiedad p =em.find(Propiedad.class,propiedad.getCodigo());
		
		System.out.println(propiedad.getCodigo());
		if(p==null) {
			System.out.println("Inserta ");
			insertar(propiedad);
		}else {
			System.out.println("actualiza ");
			actualizar(propiedad);
		}
	
	}	
	
	/*
	 * insertar nueva Propiedad
	 */
	public void insertar(Propiedad propiedad) {	
		em.persist(propiedad);
	}
	
	/*
	 * metodo de actualizar Propiedad
	 */
	public void actualizar(Propiedad propiedad) {
		em.merge(propiedad);
	}

	/*
	 * metodo de eliminar Propiedad
	 */
	public void eliminar(int codigo) {
		em.remove(leer(codigo));
	}
	
	/*
	 * metodo de leer propiedad por ID
	 */
	public Propiedad leer(int codigo) {
		System.out.println("codigo "+ codigo);
		Propiedad p =em.find(Propiedad.class, codigo);
		p.getImagenes().size();
		p.getPersona();
		p.getComentarios().size();
		p.getPersona().getTelefonos().size();
		return p;
		
	}
	
	/*
	 * metodo de listar propiedades
	 */
	/*public List<Propiedad> listadoPropiedades(){
		String jpql = "Select p From Propiedad p";
		Query query = em.createQuery(jpql,Propiedad.class);
		List<Propiedad> listado = query.getResultList();
		
		
		System.out.println(listado.size());
		return listado;
		
	}*/
	
	public List<Propiedad> listadoPropiedades(){
		String jpql = "Select p From Propiedad p";
		Query query = em.createQuery(jpql,Propiedad.class);
		List<Propiedad> listado = query.getResultList();
		
		
		System.out.println(listado.size());
		return listado;
		
	}
	
	
	public List<Propiedad> listadoPropiedades(String sec,String cat){
		String jpql = "Select p.* From Propiedad p, Categoria c "
				+ "where c.descripcion like '%"+cat+"%'"
						+ " and p.direccion like '%"+sec+"%'"
								+ " and c.codigo=p.categoria";
		Query query = em.createNativeQuery(jpql,Propiedad.class);
		List<Propiedad> listado = query.getResultList();
		System.out.println(listado.size());
		return listado;
		
	}
	
	public List<Comentarios> leer2(int idp, Boolean privado) {
		String jpql = "Select p From Comentarios p JOIN FETCH p.persona do "
				+ "JOIN FETCH p.propiedad pr "
				+ "where p.privado= :privado "
				+ "and pr.codigo= :idp";
				Query query = em.createQuery(jpql,Comentarios.class);
				query.setParameter("privado", privado);
				query.setParameter("idp", idp);
				List<Comentarios> listado =query.getResultList();
				return listado;
	}
	
	public List<Propiedad> listadoPropiedades2(){
		String jpql = "Select p From Propiedad p left JOIN FETCH p.imagenes do "
		+ "JOIN FETCH p.persona per "
		+ "JOIN FETCH p.categoria c "
		+ "JOIN FETCH p.sector s";
		Query query = em.createQuery(jpql,Propiedad.class);
		List<Propiedad> listado = query.getResultList();
		System.out.println(listado.size());
		return listado;
		
	}
	
	
	
	// para web services
	public List<Propiedad> ltsPropiedadByPrecio(double valminimo,double valmaximo){
		String jpql = "Select p From Propiedad p where p.costo > :valminimo and p.costo < :valmaximo";
		Query query = em.createQuery(jpql,Propiedad.class);
		query.setParameter("valminimo", valminimo);
		query.setParameter("valmaximo", valmaximo);
		List<Propiedad> listado = query.getResultList();
		System.out.println(listado.size());
		return listado;
		
	}
	
	public List<Propiedad> ltsPropiedadBySector(int idsector){
		Sector p = em.find(Sector.class, idsector);
		p.getPropiedades().size();
		List<Propiedad> listado = p.getPropiedades();
		System.out.println(listado.size());
		return listado;
		
	}
	
	//cambiamos sets
	public List<Propiedad> ltsPropiedadByCategorias(int idcategoria){
		Categoria cat = em.find(Categoria.class, idcategoria);
		cat.getPropiedades().size();
		List<Propiedad> listado = cat.getPropiedades();
		System.out.println(listado.size());
		return listado;
		
	}
	
	public List<Propiedad> ltsPropiedadesByPerson(int codigo) {
		Persona p =em.find(Persona.class, codigo);
		p.getPropiedades().size();
		return p.getPropiedades();
	}
	
	//cambiamos comentarios de ltsComentarios
	public List<Comentarios> getComentario(int codigo) {
		Propiedad p =em.find(Propiedad.class, codigo);
		p.getComentarios().size();
		return p.getComentarios();
	}
	
}
