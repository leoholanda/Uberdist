package br.com.caelum.dataModel;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;

import br.com.caelum.dao.DAO;
import br.com.caelum.modelo.NotaFiscal;

// paginacao de dados das notas
public class DataModelNotasFiscais extends LazyDataModel<NotaFiscal> {
//	private List<NotaFiscal> notasFiscais;
	private DAO<NotaFiscal> dao = new DAO<NotaFiscal>(NotaFiscal.class);

	public List<NotaFiscal> load(int inicio, int quantidade,
			String campoOrdenacao, boolean sentidoOrdenacao,
			Map<String, String> filtros) {
		return dao.listaTodosPaginada(inicio, quantidade);
		
		
//		if(getRowCount() <= 0 || (filtros != null && !filtros.isEmpty())) {
//			setRowCount(dao.contaTodos(filtros));
//		}
//		setPageSize(5);
//		return notasFiscais;

	}

	public DAO<NotaFiscal> getDao() {
		return dao;
	}

	public void setDao(DAO<NotaFiscal> dao) {
		this.dao = dao;
	}
	
//	public NotaFiscal getRowData(String rowKey) {
//		for (NotaFiscal notaFiscal : notasFiscais) {
//			if(rowKey.equals(String.valueOf(notaFiscal.getId())))
//				return notaFiscal;
//		}
//		return null;
//	}
//	
//	@Override
//	public Object getRowKey(NotaFiscal notaFiscal) {
//		return notaFiscal.getId();
//	}
//	
//	@Override
//	public void setRowIndex(int rowIndex) {
//		if(rowIndex == -1 || getPageSize() == 0) {
//			super.setRowIndex(-1);
//		} else
//			super.setRowIndex(rowIndex % getPageSize());
//	}
}
