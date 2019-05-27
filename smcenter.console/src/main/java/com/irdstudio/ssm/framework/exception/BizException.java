package com.irdstudio.ssm.framework.exception;

public class BizException extends ApplicationException {
	
	private static final long serialVersionUID = 1L;

	public BizException() {
		super();
	}

	public BizException(String message) {
		super(message);
	}

	public BizException(String errorCode, String message, Throwable cause) {
		super(errorCode, message, cause);
	}

	public BizException(String message, Throwable cause) {
		super(message, cause);
	}

	public String getMessage() {
		return super.getMessage();
	}

}
