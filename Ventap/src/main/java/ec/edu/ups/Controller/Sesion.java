package ec.edu.ups.Controller;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import ec.edu.ups.Model.Persona;


@Named("sesion")
@SessionScoped
public class Sesion implements Serializable{

	public Sesion() {
		// TODO Auto-generated constructor stub
	}
	
	private Persona user;
	
	private String usuario;

	public Persona getUser() {
		return user;
	}

	public void setUser(Persona user) {
		this.user = user;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	

	
}
