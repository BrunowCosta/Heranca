package com.heranca.business;

import java.util.Calendar;

import javax.faces.application.FacesMessage;
import javax.servlet.http.HttpServletRequest;

import org.owasp.esapi.errors.EncryptionException;
import org.owasp.esapi.reference.crypto.JavaEncryptor;

import com.heranca.model.AccessEntry;
import com.heranca.model.User;
import com.heranca.persistence.AccessEntryDAO;
import com.heranca.persistence.UserDAO;
import com.heranca.utils.MessageBundleUtils;


public class AccessBusiness {
	
	// Sigleton
	private static AccessBusiness instance;
	
	private AccessBusiness(){}
	UserDAO userDAO = new UserDAO();
	
	public static synchronized AccessBusiness getInstance() {
		if(instance == null) {
			instance = new AccessBusiness();
		}
		
		return instance;
	}
	
	// TODO Validar dados da entraga para evitar sql enjection
	public User authenticate(String login, String password, HttpServletRequest request) throws BusinessException {
		
		// TODO ver um padrõa de projeto para isso tipo fabrica.
		User user = null;
		user = userDAO.findOneByField("login", "=", login);
		
		try {
			// Usuário informou um login valido
			if(user != null) { 
				// Caso a autenticação esteja correta
				if(this.applyHash(password, user.getSaltAgent()).equals(user.getPassword())) {
					
					if(!checkStatus(user)) {
						this.registerAccess(AccessEntry.TYPE_BLOCKED, login, password, request);
						throw new BusinessException(MessageBundleUtils.getInstance().getMessage("login.error.acessDenied.blocked"), FacesMessage.SEVERITY_ERROR);
					} else {
						this.registerAccess(AccessEntry.TYPE_SUCESSED, login, password, request);
						return user;
					}
					
				// O usuário errou apenas a senha
				} else {
					this.registerAccess(AccessEntry.TYPE_DENIED, login, password, request);
					if(this.updateLoginAttempt(user)) {
						throw new BusinessException(MessageBundleUtils.getInstance().getMessage("login.error.acessDenied.gotBlocked"), FacesMessage.SEVERITY_ERROR);
					} else {
						// TODO arrumar um jeito de parametrizar mensagens pelo addMensage/
						// Ter cuidado com validação de crosside script
						throw new BusinessException("Acesso Negado: dados inválidos, restão apenas " + user.getLoginAttempt() + " tentativas", FacesMessage.SEVERITY_ERROR);
					}
				}
			}
		} catch (EncryptionException e) {
			e.printStackTrace();
			throw new BusinessException(MessageBundleUtils.getInstance().getMessage("system.error.generic"), e);
		}
		
		// Não existe usuário com o login informado
		this.registerAccess(AccessEntry.TYPE_DENIED, login, password, request);
		throw new BusinessException(MessageBundleUtils.getInstance().getMessage("login.error.acessDenied"), FacesMessage.SEVERITY_ERROR);
		
	}
	
	public String applyHash(String password, String saltAgent) throws EncryptionException {
		// Aplica o Hash + Salt a senha do usuário.
		JavaEncryptor enc = (JavaEncryptor) JavaEncryptor.getInstance();
		return enc.hash(password,saltAgent);
	}
	
	public boolean checkStatus(User user) throws BusinessException {
		boolean canAccess = false;
		
		// O usuário foi autenticado com sucesso e se encontra ativo.
		if(User.STATUS_ATIVO == user.getStatus()) {
			canAccess = true;
		} else if(User.STATUS_BLOQUEADO == user.getStatus()) {
			// Caso o usuário esteja bloqueado uma mensagem será exibida
			if(this.userCanBeReleased(user)) {
				this.releasingUser(user);
				canAccess = true;
			} 
		}
		
		return canAccess;
	}
	
	public boolean userCanBeReleased(User user) {
		boolean canBeReleased = false;
		
		Calendar maximumTimeBlocked = user.getBlockDate();
		maximumTimeBlocked.add(Calendar.MINUTE, User.NUMERO_MINUTOS_BLOQUEIO);
		
		if(maximumTimeBlocked.before(Calendar.getInstance())) {
			canBeReleased = true;
		}
		
		return canBeReleased;
	}
	
	public void releasingUser(User user) {
		user.setBlockDate(null);
		user.setStatus(User.STATUS_ATIVO);
		user.setLoginAttempt(0);
		userDAO.merge(user);
	}
	
	public boolean updateLoginAttempt(User user) {
		boolean isBlocked = false;
		
		// Verifica se já esgotaram as tentativas de login.
		// Caso tenham esgotado, cadastra um bloqueio e zera as tentativas.
		if(user.getLoginAttempt() > User.NUMERO_TENTATIVAS_MAX) {
			user.setStatus(User.STATUS_BLOQUEADO);
			user.setBlockDate(Calendar.getInstance());
			isBlocked = true;
		} else {
			user.setLoginAttempt(user.getLoginAttempt() + 1);
		}
		
		userDAO.merge(user);
		
		return isBlocked;		
	}
	
	// TODO VER COMO PEGAR INFORMAÇÔES DO REQUEST
	public void registerAccess(char type, String login, String password, HttpServletRequest request) {
		AccessEntryDAO acessDAO = new AccessEntryDAO();
		AccessEntry entry = new AccessEntry(login, password, "ip", "browser", Calendar.getInstance(), type);
		acessDAO.persist(entry);
	}
}
