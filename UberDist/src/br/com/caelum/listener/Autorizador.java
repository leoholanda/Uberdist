package br.com.caelum.listener;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import br.com.caelum.managedBean.LoginBean;

public class Autorizador implements PhaseListener {

	public void beforePhase(PhaseEvent event) {

	}

	public void afterPhase(PhaseEvent event) {
		FacesContext context = event.getFacesContext();
		
		if("/login.xhtml".equals(context.getViewRoot().getViewId())) {
			return;
		}
		
		// Obtendo o login da sessao
		LoginBean loginBean = context.getApplication().evaluateExpressionGet(context, "#{loginBean}", LoginBean.class);
		
		if(!loginBean.isLogado()) {
			NavigationHandler handler = context.getApplication().getNavigationHandler();
			handler.handleNavigation(context, null, "login?faces-redirect=true");
			
			// efetua a renderizacao da tela
			context.renderResponse();
		}
	}

	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}
	
}
