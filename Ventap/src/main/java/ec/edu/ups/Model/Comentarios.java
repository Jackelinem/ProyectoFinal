package ec.edu.ups.Model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

/*
 * Entidad Comentarios que se realizaran en las propiedades
 */
@Entity
public class Comentarios implements Serializable {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name="com_id")
	private int id;
	
	private boolean privado;
	
	@Column(name="com_comentario")
	private String comentario;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="propiedad")
	@JsonIgnore
	private Propiedad propiedad;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="persona")
	@JsonIgnore
	private Persona persona;
	
	
	//getters and setters
	
	
	public int getId() {
		return id;
	}

	public boolean isPrivado() {
		return privado;
	}

	public void setPrivado(boolean privado) {
		this.privado = privado;
	}

	public Propiedad getPropiedad() {
		return propiedad;
	}

	public void setPropiedad(Propiedad propiedad) {
		this.propiedad = propiedad;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}



}
