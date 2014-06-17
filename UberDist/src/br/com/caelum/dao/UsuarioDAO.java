package br.com.caelum.dao;

import javax.persistence.EntityManager;

import br.com.caelum.modelo.Usuario;

public class UsuarioDAO {

	public boolean existe(Usuario usuario) {
		EntityManager em = new JPAUtil().getEntityManager();
		em.getTransaction().begin();
		
		javax.persistence.Query query = em.createQuery("from Usuario u where u.login = :pLogin and u.senha = :pSenha");
		query.setParameter("pLogin", usuario.getLogin());
		query.setParameter("pSenha", usuario.getSenha());
		
		boolean encontrado = !query.getResultList().isEmpty();
		
		em.getTransaction().commit();
		em.close();
		
		return encontrado;
	}

}
