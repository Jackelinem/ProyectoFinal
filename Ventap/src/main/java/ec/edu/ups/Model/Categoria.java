package ec.edu.ups.Model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;


/*
 * Entidad categoria que a la que pertenecera una propiedad
 */
@Entity
public class Categoria implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codigo;
	
	//allias me para reconocimiento de categoria posteriormente en un combo
	private String alias;
	
	
	private String descripcion;
	
	
	//relacion uno a muchos una categoria puede estar en muchas propiedades
	@OneToMany(mappedBy="categoria")
	private List<Propiedad> propiedades;
	
	
	//getters and setters
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public List<Propiedad> getPropiedades() {
		return propiedades;
	}
	public void setPropiedades(List<Propiedad> propiedades) {
		this.propiedades = propiedades;
	}
	
	
}
