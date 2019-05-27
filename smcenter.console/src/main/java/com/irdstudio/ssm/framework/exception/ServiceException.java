package com.irdstudio.ssm.framework.exception;

/**
 * 
 * 服务异常
 * 
 * @author Cytus_
 * @since 2018年5月24日 下午2:54:18
 * @version 1.0
 *
 */
public class ServiceException extends ApplicationException {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ServiceException() {
		super();
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(String errorCode, String message, Throwable cause) {
		super(errorCode, message, cause);
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public String getMessage() {
		return super.getMessage();
	}

}
