package br.com.caelum.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.caelum.dao.DAO;
import br.com.caelum.modelo.Item;
import br.com.caelum.modelo.NotaFiscal;
import br.com.caelum.modelo.Produto;
import br.com.caelum.util.Msg;

@ManagedBean
@ViewScoped
public class NotaFiscalBean implements Serializable {

	private NotaFiscal notaFiscal = new NotaFiscal();
	private Item item = new Item();
	private List<Item> itensDaNota;
	private Long idProduto;
	private List<NotaFiscal> notasfiscais;
	private DAO<NotaFiscal> dao = new DAO<NotaFiscal>(NotaFiscal.class);

	private List<Item> itens;
	private List<Item> itensEmpty;

	@PostConstruct
	public void init() {

		notaFiscal = new NotaFiscal();
		item = new Item();
	}

	public List<NotaFiscal> getNotasFiscais() {
		if (notasfiscais == null) {
			System.out.println("carregando notas fiscais...");
			notasfiscais = new DAO<NotaFiscal>(NotaFiscal.class).listaTodos();
		}
		return notasfiscais;
	}
	
	public List<Item> getItens() {
		if (itens == null) {
			System.out.println("Carregando itens...");
			itens = new DAO<Item>(Item.class).listaTodos();
		}
		return itens;
	}
	
	

	// log lista para a consulta de itens da nota fiscal
	public List<Item> getLogsItensDaNotaFiscal() {
		itensEmpty = new ArrayList<Item>();
		List<Item> item = new ArrayList<Item>();
		item = this.getItens();
		for (int i = 0; i < item.size(); i++) {
			if (item.get(i).getNotaFiscal().getId().equals(getNotaFiscal().getId())) {
				itensEmpty.add(item.get(i));				
			}
		}
		return itensEmpty;
	}
	
	// lista para a consulta de itens da nota fiscal
	public List<Item> getItensDaNotaFiscal() {
		itensEmpty = new ArrayList<Item>();
		List<Item> item = new ArrayList<Item>();
		item = this.getItens();
		for (int i = 0; i < item.size(); i++) {
			if (item.get(i).getNotaFiscal().getId().equals(getNotaFiscal().getId()) &&
					item.get(i).getStatus().equals(true)) {
				itensEmpty.add(item.get(i));				
			}
		}
		return itensEmpty;
	}
	
	// grava dados da nota no banco
	public void gravarNotaFiscal() {
		notaFiscal.setValorTotal(getValorTotalDaNota());
		Msg.addMsgInfo("Nota fiscal cadastrada com sucesso");
		System.out.println("nota fiscal cadastrada...");
		dao.adiciona(notaFiscal);
		this.notaFiscal = new NotaFiscal();
		this.item = new Item();

	}
	
	// atualizar nota fiscal
	public String atualizarNotaFiscal() {
		DAO<NotaFiscal> dao = new DAO<NotaFiscal>(NotaFiscal.class);
		Msg.addMsgInfo("Nota fiscal atualizada com sucesso");
		dao.atualiza(notaFiscal);
		System.out.println("nota fiscal atualizada...");
		this.notaFiscal = new NotaFiscal();
		this.item = new Item();
		
		return "listanotas?faces-redirect=true";
	}
	
	// guarda item a nota nova nota fiscal
	public void addItem() {
		DAO<Produto> dao = new DAO<Produto>(Produto.class);
		Produto produto = dao.buscaPorId(idProduto);
		item.setProduto(produto);
		item.setValorUnitario(produto.getPreco());

		if (item.getQuantidade() == 0) {
			item.setQuantidade(1);
		}
		item.getProduto().setQuantidade(item.getProduto().getQuantidade() - item.getQuantidade());
		System.out.println("quantidade retirada do estoque: "
				+ getItem().getQuantidade() + "\n" + "TOTAL: "
				+ getItem().getProduto().getQuantidade());
		notaFiscal.getItens().add(item);
		item.setStatus(true);
		item.setNotaFiscal(notaFiscal);
		dao.atualiza(produto);

		this.item = new Item();
	}
	
	// guarda item a nota fiscal ja existente
	public void addItemNotaFiscalExistente() {
		DAO<Produto> dao = new DAO<Produto>(Produto.class);
		Produto produto = dao.buscaPorId(idProduto);
		item.setProduto(produto);
		item.setValorUnitario(produto.getPreco());
		
		if (item.getQuantidade() == 0) {
			item.setQuantidade(1);
		}
//		item.getProduto().setQuantidade(item.getProduto().getQuantidade() - item.getQuantidade());
		System.out.println("quantidade retirada do estoque: "
				+ getItem().getQuantidade() + "\n" + "TOTAL: "
				+ getItem().getProduto().getQuantidade());
//		getItens().add(item);
		getItens().add(item);
		item.setStatus(true);
		item.setNotaFiscal(notaFiscal);
		Msg.addMsgInfo("Item adicionado");
//		dao.atualiza(produto);
		
		this.item = new Item();
	}

	// remove item da lista ao cadastrar nota fiscal
	public void removeItem() {
		DAO<Produto> dao = new DAO<Produto>(Produto.class);
		Produto produto = dao.buscaPorId(idProduto);
		item.getProduto().setQuantidade(item.getProduto().getQuantidade() + item.getQuantidade());
		System.out.println("quantidade devolvida ao estoque: "
				+ getItem().getQuantidade() + "\n" + "TOTAL: "
				+ getItem().getProduto().getQuantidade());
		notaFiscal.getItens().remove(item);
		dao.atualiza(produto);

		this.item = new Item();
	}
	
	// remove item da lista depois da nota cadastrada
	public void removeItemDaNota() {
		DAO<Item> dao = new DAO<Item>(Item.class);
		this.item.setStatus(false);
		dao.atualiza(item);
		
		this.item = new Item();
		
		System.out.println("Item removido com sucesso");
		
	}
		
	
	// valor total da compra
	public Double getValorTotalDaNota() {
		Double total = 0.00;
		for (Item i : notaFiscal.getItens()) {
			total += i.getTotal();
		}
		return total;
	}

	// getters and setters
	public NotaFiscal getNotaFiscal() {
		return notaFiscal;
	}

	public void setNotaFiscal(NotaFiscal notaFiscal) {
		this.notaFiscal = notaFiscal;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Long getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(Long idProduto) {
		this.idProduto = idProduto;
	}

	public List<Item> getItensDaNota() {
		return itensDaNota;
	}

	public void setItensDaNota(List<Item> itensDaNota) {
		this.itensDaNota = itensDaNota;
	}

	public List<NotaFiscal> getNotasfiscais() {
		return notasfiscais;
	}

	public void setNotasfiscais(List<NotaFiscal> notasfiscais) {
		this.notasfiscais = notasfiscais;
	}

	public void setItens(List<Item> itens) {
		this.itens = itens;
	}

	public List<Item> getItensEmpty() {
		return itensEmpty;
	}

	public void setItensEmpty(List<Item> itensEmpty) {
		this.itensEmpty = itensEmpty;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idProduto == null) ? 0 : idProduto.hashCode());
		result = prime * result + ((item == null) ? 0 : item.hashCode());
		result = prime * result + ((itens == null) ? 0 : itens.hashCode());
		result = prime * result
				+ ((itensDaNota == null) ? 0 : itensDaNota.hashCode());
		result = prime * result
				+ ((itensEmpty == null) ? 0 : itensEmpty.hashCode());
		result = prime * result
				+ ((notaFiscal == null) ? 0 : notaFiscal.hashCode());
		result = prime * result
				+ ((notasfiscais == null) ? 0 : notasfiscais.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NotaFiscalBean other = (NotaFiscalBean) obj;
		if (idProduto == null) {
			if (other.idProduto != null)
				return false;
		} else if (!idProduto.equals(other.idProduto))
			return false;
		if (item == null) {
			if (other.item != null)
				return false;
		} else if (!item.equals(other.item))
			return false;
		if (itens == null) {
			if (other.itens != null)
				return false;
		} else if (!itens.equals(other.itens))
			return false;
		if (itensDaNota == null) {
			if (other.itensDaNota != null)
				return false;
		} else if (!itensDaNota.equals(other.itensDaNota))
			return false;
		if (itensEmpty == null) {
			if (other.itensEmpty != null)
				return false;
		} else if (!itensEmpty.equals(other.itensEmpty))
			return false;
		if (notaFiscal == null) {
			if (other.notaFiscal != null)
				return false;
		} else if (!notaFiscal.equals(other.notaFiscal))
			return false;
		if (notasfiscais == null) {
			if (other.notasfiscais != null)
				return false;
		} else if (!notasfiscais.equals(other.notasfiscais))
			return false;
		return true;
	}

	public DAO<NotaFiscal> getDao() {
		return dao;
	}

	public void setDao(DAO<NotaFiscal> dao) {
		this.dao = dao;
	}

}
