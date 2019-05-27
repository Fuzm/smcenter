package com.irdstudio.ssm.framework.util;

import java.util.UUID;

/**
 * 
 * UUID获取
 * 
 * @author Cytus_
 * @since 2018年5月7日 下午2:19:30
 * @version 1.0
 *
 */
public final class UUIDUtil {
	
	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "").toUpperCase();
	}

}
