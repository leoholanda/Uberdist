package br.com.caelum.managedBean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.caelum.dao.ClienteDAO;
import br.com.caelum.dao.DAO;
import br.com.caelum.modelo.Cliente;
import br.com.caelum.util.Msg;

@ManagedBean
@ViewScoped
public class ClienteBean implements Serializable {

	private Cliente cliente = new Cliente();
	private List<Cliente> clientes;

	// lista clientes quando nao for carregado
	public List<Cliente> getClientes() {
		if (clientes == null) {
			clientes = new DAO<Cliente>(Cliente.class).listaTodos();
		}
		return clientes;
	}

	// autoComplete
	public List<Cliente> buscaCliente(String query) {
		ClienteDAO clienteDAO = new ClienteDAO();
		List<Cliente> opcoes = clienteDAO.buscaPorNome(query);

		return opcoes;
	}
	
	// autoComplete
		public List<Cliente> busca(String nome) {
			ClienteDAO clienteDAO = new ClienteDAO();
			List<Cliente> opcoes = clienteDAO.buscaPorName(nome);

			return opcoes;
		}

	// metodo para add dados do cliente no banco
	public void gravarDados() {
		DAO<Cliente> dao = new DAO<Cliente>(Cliente.class);
		if (cliente.getId() == null) {
			Msg.addMsgInfo("Cliente cadastrado com sucesso!");
			dao.adiciona(cliente);
		} else {
			Msg.addMsgInfo("Cliente atualizado com sucesso!");
			dao.atualiza(cliente);
		}
		this.cliente = new Cliente();
		this.clientes = dao.listaTodos();
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

}
