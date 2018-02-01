package ec.edu.ups.Controller;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.hibernate.validator.constraints.Email;

import ec.edu.ups.Dao.PersonaDao;
import ec.edu.ups.Model.Persona;
import ec.edu.ups.beans.Util;

/*
 * controlador de logueo
 * se hace una busqueda de los usuarios o personas registradas para poder ingresar
 */
@ManagedBean
@SessionScoped
public class Login {
	
	/**
	 * bean properties
	 */
	
	private Persona persona;//instancia objeto persona
	/*
	 * datos para logueo
	 */
	@Email
	private  String email;
	private String pass;
	
	@Inject
    private FacesContext facesContext;
	
	
	@Inject
	private PersonaDao personaDao;
	
	
	@Inject
	private Sesion sesion;
	
	@PostConstruct
	private void init(){
		persona = new Persona();
	}
	

	/*
	 * metodo que me permite validar la informacion obtenida para logueo
	 */
	public String submit () {
		System.out.println("entro ");
		try {
			persona = personaDao.buscarUser(this.getEmail(),this.getPass());
			//System.out.println("entro "+persona.getRol().getTipo());
			
			 if(persona != null) {
				 HttpSession session =Util.getSession();
		         session.setAttribute("username", this.getEmail());

				 sesion.setUser(persona);
				 if(persona.getRol()==null)
					 return"/index";
				 if(persona.getRol().getTipo().equals("invitado")) {
					 System.out.println("redireccion Invitado");
					 return"/indexInvit";
					 
				 }
				 System.out.println("redireccion Admin ");
				 return"/index";
			
				 
			
			}else {
				FacesContext.getCurrentInstance().addMessage(
		                null,
		                new FacesMessage(FacesMessage.SEVERITY_WARN,
		                "Invalid Login!",
		                "Please Try Again!"));

		        // invalidate session, and redirect to other pages

				return null;
			}
			 
		} catch (Exception e) {
			// TODO: handle exception
			String errorMessage = getRootErrorMessage(e);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "login unsuccessful");
            facesContext.addMessage(null, m);
            
             
		}		 
		
			
		return "#";
	}
	
	public String logout() {
	    HttpSession session = Util.getSession();
	    session.invalidate();
	    return "login2";
	 }
	
	//getters and setters

		public Persona getPersona() {
			return persona;
		}


		public void setPersona(Persona persona) {
			this.persona = persona;
		}



		public String getEmail() {
			return email;
		}



		public void setEmail(String email) {
			this.email = email;
		}



		public String getPass() {
			return pass;
		}



		public void setPass(String pass) {
			this.pass = pass;
		}

		private String getRootErrorMessage(Exception e) {
	        // Default to general error message that registration failed.
	        String errorMessage = "Registration failed. See server log for more information";
	        if (e == null) {
	            // This shouldn't happen, but return the default messages
	            return errorMessage;
	        }

	        // Start with the exception and recurse to find the root cause
	        Throwable t = e;
	        while (t != null) {
	            // Get the message from the Throwable class instance
	            errorMessage = t.getLocalizedMessage();
	            t = t.getCause();
	        }
	        // This is the root cause message
	        return errorMessage;
	    }


		public Sesion getSesion() {
			return sesion;
		}


		public void setSesion(Sesion sesion) {
			this.sesion = sesion;
		}


}


