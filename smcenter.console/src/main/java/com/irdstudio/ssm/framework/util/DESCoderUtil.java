package com.irdstudio.ssm.framework.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DESCoderUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(DESCoderUtil.class);
	
	private static final String SECURITY_KEY = "D31KDKDKFICI468BZPMVK68OBKE78JR";

	public static String encrypt(String securityKey, String data) {
		String result = null;
		try {
			result = getCoder(securityKey).encrypt(data);
		} catch (Exception e) {
			logger.error("encrypt data error.");
			throw new RuntimeException("encrypt data error.", e);
		}
		return result;
	}
	
	public static String encrypt(String data) {
		return DESCoderUtil.encrypt(SECURITY_KEY, data);
	}

	public static String decrypt(String securityKey, String data) {
		String result = null;
		try {
			result = getCoder(securityKey).decrypt(data);
		} catch (Exception e) {
			logger.error("decrypt data error.");
			throw new RuntimeException("decrypt data error.", e);
		}
		return result;
	}
	
	public static String decrypt(String data) {
		return DESCoderUtil.decrypt(SECURITY_KEY, data);
	}

	private static DESCoder getCoder(String securityKey) {
		DESCoder coder = null;
		try {
			coder = StringUtils.isBlank(securityKey) ? new DESCoder() : new DESCoder(securityKey);
		} catch (Exception e) {
			logger.error("new coder error.");
			throw new RuntimeException("new coder error.", e);
		}
		return coder;
	}
}
