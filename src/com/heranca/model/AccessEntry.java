package com.heranca.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="T_ACESSENTRY", schema="ACESS")
@SequenceGenerator(name="SEQ_ID_ACESSENTRY", sequenceName="SEQ_ID_ACESSENTRY", schema="ACESS")
public class AccessEntry implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public final static char TYPE_DENIED = 'D';
	public final static char TYPE_SUCESSED = 'S';
	public final static char TYPE_BLOCKED = 'B';
	
	@Id
	@Column(name="ID_ACESSENTRY")
	private String id;
	
	@Column(nullable=false, length=30)
	private String login;
	
	@Column(nullable=false, length=50)
	private String senha;
	
	@Column(nullable=false)
	private String ip;
	
	private String browser;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar time;
	
	private char type;
	
	public AccessEntry() {}
	
	public AccessEntry(String login, String senha, String ip, String browser, Calendar time, char type) {
		super();
		this.login = login;
		this.senha = senha;
		this.ip = ip;
		this.browser = browser;
		this.time = time;
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public Calendar getTime() {
		return time;
	}

	public void setTime(Calendar time) {
		this.time = time;
	}

	public char getType() {
		return type;
	}

	public void setType(char type) {
		this.type = type;
	}
	
}
