package ec.edu.ups.Model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Mensaje  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public Mensaje() {
		// TODO Auto-generated constructor stub
	}
	
	

	@Id
	private int codigo;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="origen")
	@JsonIgnore
	private Persona source;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="destino")
	@JsonIgnore
	private Persona destin;

	
	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public Persona getSource() {
		return source;
	}

	public void setSource(Persona source) {
		this.source = source;
	}

	public Persona getDestin() {
		return destin;
	}

	public void setDestin(Persona destin) {
		this.destin = destin;
	}

	@Override
	public String toString() {
		return "Mensaje [codigo=" + codigo + ", source=" + source + ", destin=" + destin + "]";
	}
	
	
	
	
	
}
