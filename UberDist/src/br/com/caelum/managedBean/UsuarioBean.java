package br.com.caelum.managedBean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.caelum.dao.DAO;
import br.com.caelum.modelo.Usuario;

@ManagedBean
@ViewScoped
public class UsuarioBean implements Serializable {
	
	private Usuario usuario = new Usuario();
	private List<Usuario> usuarios;
	
	public List<Usuario> getUsuarios(){
		if(usuarios == null) {
			System.out.println("Carregando usuarios...");
			usuarios = new DAO<Usuario>(Usuario.class).listaTodos();
		}
		return usuarios;
	}

	// cadastra usuario no banco
	public String novoUsuario() {
	DAO<Usuario> dao = new DAO<Usuario>(Usuario.class);
	
		if(usuario.getId()!=null) {
			dao.adiciona(usuario);
		} else {
			dao.atualiza(usuario);
		}
		this.usuarios = dao.listaTodos();
		this.usuario = new Usuario();
		
		return "usuario?faces-redirect=true";
	}
	
	// getters and setters
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
}
