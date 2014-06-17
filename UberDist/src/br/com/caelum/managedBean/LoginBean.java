package br.com.caelum.managedBean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.caelum.dao.UsuarioDAO;
import br.com.caelum.modelo.Usuario;

@ManagedBean
@SessionScoped
public class LoginBean {
	
	private Usuario usuario = new Usuario();
	
	public String efetuaLogin() {
		UsuarioDAO usuarioDao = new UsuarioDAO();
		boolean loginValido = usuarioDao.existe(this.usuario);
		 if(loginValido) {
			 System.out.println("O login era valido? " + loginValido);
			 return "produto?faces-redirect=true";
		 } else {
			 System.out.println("O login era valido? " + loginValido);
			this.usuario = new Usuario();
			return "login";
		 }
	}

	public boolean isLogado() {
		return usuario.getLogin() != null;
	}
	
	// getters and setters
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	

}
