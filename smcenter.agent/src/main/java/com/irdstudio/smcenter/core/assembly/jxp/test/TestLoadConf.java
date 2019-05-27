package com.irdstudio.smcenter.core.assembly.jxp.test;

import com.irdstudio.smcenter.core.assembly.jxp.conf.ConfigureEntry;
import com.irdstudio.smcenter.core.assembly.jxp.loader.MainLoader;

public class TestLoadConf {
	
	public static void main(String[] args){
		ConfigureEntry cxd = MainLoader.loadCoreXml("risk.xml");
	}
}
