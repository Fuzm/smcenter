package com.irdstudio.smcenter.core.assembly.socket.client;

import com.irdstudio.smcenter.core.util.vo.UniKeyValueObject;

public interface IBusiClient {
	
	public UniKeyValueObject doExecuteBusi(UniKeyValueObject inUvo)
			throws Exception;
	
}
