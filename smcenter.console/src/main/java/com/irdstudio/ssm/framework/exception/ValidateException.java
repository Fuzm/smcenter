package com.irdstudio.ssm.framework.exception;

/**
 * 
 * 验证异常
 * 
 * @author Cytus_
 * @since 2018年5月24日 下午3:08:08
 * @version 1.0
 *
 */
public class ValidateException extends AppRuntimeException {
	
	private static final long serialVersionUID = 1L;

	public ValidateException() {
		super();
	}

	public ValidateException(String message) {
		super(message);
	}

	public ValidateException(String message, Throwable cause) {
		super(message, cause);
	}

	public String getMessage() {
		return super.getMessage();
	}

}
