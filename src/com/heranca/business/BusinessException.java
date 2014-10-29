package com.heranca.business;

import javax.faces.application.FacesMessage.Severity;


public class BusinessException extends Exception {
	
	public static final char SEVERITY_ERROR = 'E';
	public static final char SEVERITY_INFO = 'I';
	public static final char SEVERITY_FATAL = 'F';
	public static final char SEVERITY_WARNNING = 'W';
	
	private Severity severity;
	private Exception previousException;
	
	public BusinessException(String message) {
		super(message);
	}
	
	public BusinessException(String message, Severity severity) {
		super(message);
		this.severity = severity;
	}
		
	public BusinessException(String message, Exception previousException) {
		super(message);
		this.previousException = previousException;
	}
	
	public Exception getPreviousException() {
		return previousException;
	}
	
	public void setPreviousException(Exception previousException) {
		this.previousException = previousException;
	}

	public Severity getSeverity() {
		return severity;
	}

	public void setSeverity(Severity severity) {
		this.severity = severity;
	}
	
}
