package com.irdstudio.ssm.framework.exception;



/**
 * 应用异常
 * 
 * @author Joe
 */
public class ApplicationException extends Exception{

	private static final long serialVersionUID = -2678203134198782909L;
	
	public static final String MESSAGE = "应用系统异常";

	private String errorCode;

	public ApplicationException() {
		super(MESSAGE);
	}

	public ApplicationException(String message) {
		super(message);
	}
	
	public ApplicationException(String errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public ApplicationException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public ApplicationException(String errorCode, String message, Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
	}

	public ApplicationException(Throwable cause) {
		super(cause);
	}

	public String getErrorCode() {
		return errorCode;
	}

}
