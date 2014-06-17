package br.com.caelum.validador;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator(value = "comecaComMaiuscula")
public class ComecaComMaiuscula implements Validator {
	
	public void validate(FacesContext fc, UIComponent component, Object value) throws ValidatorException {
		String valor = value.toString();
		if(!valor.matches("[A-Z].*")) {
			// da erro
			throw new ValidatorException(new FacesMessage("Deveria começar com maiúscula"));
		}
	}
}
