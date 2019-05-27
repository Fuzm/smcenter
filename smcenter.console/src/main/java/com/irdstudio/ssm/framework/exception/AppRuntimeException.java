package com.irdstudio.ssm.framework.exception;

/**
 * 
 * 应用运行时异常
 * 
 * @author Cytus_
 * @since 2018年5月24日 下午3:05:06
 * @version 1.0
 *
 */
public class AppRuntimeException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public AppRuntimeException() {
		super();
	}

	public AppRuntimeException(String message) {
		super(message);
	}

	public AppRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public String getMessage() {
		return super.getMessage();
	}

}
