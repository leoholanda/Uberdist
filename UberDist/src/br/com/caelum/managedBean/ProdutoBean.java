package br.com.caelum.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.PieChartModel;

import br.com.caelum.dao.DAO;
import br.com.caelum.modelo.Produto;

@ManagedBean
@ViewScoped
public class ProdutoBean implements Serializable {

	private Produto produto = new Produto();
	private List<Produto> produtos;
	private PieChartModel modelo = new PieChartModel();
	private BarChartModel modeloBar = new BarChartModel();
	
	public ProdutoBean() {
		criarModeloGrafico();
	}

	// lista de produtos
	public List<Produto> getProdutos() {
		if (produtos == null) {
			System.out.println("carregando produtos...");
			produtos = new DAO<Produto>(Produto.class).listaTodos();
		}
		return produtos;
	}
	
	// autoComplete
//	public List<Produto> busca(String nome) {
//		DAO<Produto> dao = new DAO<Produto>(Produto.class);
//		List<Produto> opcoes = dao.buscaPorNome(nome);
//		
//		return opcoes;
//	}

	// metodo para add dados do produto no banco
	public void gravarDados() {
		DAO<Produto> dao = new DAO<Produto>(Produto.class);
		if (produto.getId() == null) {
			dao.adiciona(produto);
		} else {
			dao.atualiza(produto);
		}
		this.produto = new Produto();
		this.produtos = dao.listaTodos();
	}

	// metodo para remover o objeto no banco
	public void remove(Produto produto) {
		DAO<Produto> dao = new DAO<Produto>(Produto.class);
		dao.remove(produto);
		this.produtos = dao.listaTodos();
	}

	// soma os valores dos produtos e exibe o total
	public Double getTotal() {
		Double total = 0.0;
		for (Produto p : getProdutos()) {
			total += p.getPreco();
		}
		return total;
	}

	// total de produtos cadastrados
	public int getTotalQuantidade() {
		int totalQuantidade = 0;
		for(Produto p : getProdutos()) {
			totalQuantidade += p.getQuantidade();
		}
		return totalQuantidade;

	}

	// gerar grafico para quantidade de produto estilo pie
	private void criarModeloGrafico() {
		modelo = new PieChartModel();
		List<Produto> listaProduto = new ArrayList<Produto>();
		DAO<Produto> dao = new DAO<Produto>(Produto.class);
		listaProduto = dao.listaTodos();
		for (Produto p : listaProduto) {
			modelo.set(p.getNome(), p.getQuantidade());
		}
	}

	// getter

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public PieChartModel getModelo() {
		return modelo;
	}

	public void setModelo(PieChartModel modelo) {
		this.modelo = modelo;
	}

	public BarChartModel getModeloBar() {
		return modeloBar;
	}

	public void setModeloBar(BarChartModel modeloBar) {
		this.modeloBar = modeloBar;
	}

}
