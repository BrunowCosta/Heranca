package com.heranca.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.heranca.business.BusinessException;
import com.heranca.business.AccessBusiness;
import com.heranca.model.User;

@ManagedBean(name="userController")
@RequestScoped
public class UserController extends AbstractController {
	
	public static final String LOGGEDUSER = "LoggedUser";
	
	// Campos da tela
	private String login;
	private String password;
	private String confirmPassword;
	private String email;
	private String secretQuestion;
	private String secretAnwser;
	
	public UserController() {}
	
	public String logon() {
		try {
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();  
			HttpSession session = (HttpSession) context.getSession(true);
			HttpServletRequest request = (HttpServletRequest)context.getRequest();
			
			User user = AccessBusiness.getInstance().authenticate(this.login, this.password, request);
			session.setAttribute(UserController.LOGGEDUSER, user);
			
			// TODO Ver um maneira legal de botar o caminho das telas.
			// Provavelmente vai ser utilizado no prettyface.
			return "sucesso";
		} catch (BusinessException e) {
			this.addInterfaceMessage(e);
		}
		
		return null;
	}
	
	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "pagina_inicio";
	}
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSecretQuestion() {
		return secretQuestion;
	}
	public void setSecretQuestion(String secretQuestion) {
		this.secretQuestion = secretQuestion;
	}
	public String getSecretAnwser() {
		return secretAnwser;
	}
	public void setSecretAnwser(String secretAnwser) {
		this.secretAnwser = secretAnwser;
	}
	
}
