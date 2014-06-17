package br.com.caelum.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.caelum.modelo.Cliente;


public class ClienteDAO {

	// busca cliente (autocomplete)
	@SuppressWarnings("unchecked")
	public List<Cliente> buscaPorNome(String cliente) {
		EntityManager em = new JPAUtil().getEntityManager();
		em.getTransaction().begin();
		cliente = cliente.toUpperCase();
		System.out.println(cliente);
		Query query = em.createQuery("from Cliente c where upper(c.nome) like :pCliente order by c.nome");
		query.setParameter("pCliente", cliente + "%");

		List<Cliente> list = query.getResultList();

		em.getTransaction().commit();
		em.close();

		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Cliente> buscaPorName(String nome) {
		EntityManager em = new JPAUtil().getEntityManager();
		String jpql = "select c from Cliente c where lower(c.nome) like :nome order by c.nome";
		return em.createQuery(jpql).setParameter("nome", nome+"%").getResultList();
	}
}
