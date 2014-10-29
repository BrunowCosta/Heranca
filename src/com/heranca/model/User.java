package com.heranca.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="T_USER", schema="ACESS")
@SequenceGenerator(name="SEQ_ID_USER", sequenceName="SEQ_ID_USER", schema="ACESS")

@NamedQuery(name = "User.authenticate", query = "select u from User u where u.login = :login and u.password = :password")
public class User implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8221347861916518793L;
	
	public final static String QUERY_AUTHENTICATE = "User.authenticate";
	
	public final static int NUMERO_TENTATIVAS_MAX = 5;
	public final static int NUMERO_MINUTOS_BLOQUEIO = 15;
	public final static char STATUS_INATIVO = 'I';
	public final static char STATUS_ATIVO = 'A';
	public final static char STATUS_CANCELADO = 'C';
	public final static char STATUS_BLOQUEADO = 'B';
	
	@Id
	@Column(name="ID_USER")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ID_USER")
	private Integer id;
	
	//TODO botar unique
	@Column(nullable=false, length=30)
	private String login;
	
	//É hashado com salt.
	@Column(nullable=false, length=32)
	private String password;
	
	private Integer loginAttempt;
	
	@Column(nullable=false, length=50)
	private String secretQuestion;
	
	@Column(nullable=false, length=50)
	private String secretAnwser;
	
	@Column(nullable=false, length=30)
	private String email;
	
	@Column(nullable=false)
	private char status;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar blockDate;
	
	// Milisegundos do cadastro ou ultima alteração de senha do usuário.
	private String saltAgent;
	
	public User() {}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
	
	public Integer getLoginAttempt() {
		return loginAttempt;
	}

	public void setLoginAttempt(Integer loginAttempt) {
		this.loginAttempt = loginAttempt;
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

	public String getSaltAgent() {
		return saltAgent;
	}

	public void setSaltAgent(String saltAgent) {
		this.saltAgent = saltAgent;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}

	public Calendar getBlockDate() {
		return blockDate;
	}

	public void setBlockDate(Calendar blockDate) {
		this.blockDate = blockDate;
	}

} 
