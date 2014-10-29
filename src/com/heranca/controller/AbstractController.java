package com.heranca.controller;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.heranca.business.BusinessException;
import com.heranca.model.User;

public class AbstractController {

	protected User getLoggedUser() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		User userLogged = (User) session.getAttribute(UserController.LOGGEDUSER);
		return userLogged;
	}
	
	// Metódos utilizados para exibir um mensagem amigavel na interface.
	protected void addInterfaceMessage(BusinessException e) {
		//TODO ver como botar um prefixo.
		FacesContext.getCurrentInstance().addMessage(null, 
				new FacesMessage((e.getSeverity() == null ? FacesMessage.SEVERITY_ERROR : e.getSeverity()),
				"", e.getMessage()));
	}
	
	protected void addInterfaceMessage(String mensagem, Severity severity) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, "", mensagem));
	}
}
