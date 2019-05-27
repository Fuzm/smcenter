package com.irdstudio.smcenter.core.assembly.socket.core.file;

import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.irdstudio.smcenter.core.assembly.socket.client.corba.CEasyAppClient;
import com.irdstudio.smcenter.core.assembly.socket.client.corba.CEasyPackage;
import com.irdstudio.smcenter.core.assembly.socket.element.BusinessConf;
import com.irdstudio.smcenter.core.util.vo.UniKeyValueObject;

/**
 * 中转发送 - 使用Corba中转
 * @author 李广明
 * @version 1.0
 * @date 2008-06-21
 *
 */
public class CorbaSend implements IFileSend{
	
	private static Logger logger = Logger.getLogger("[交易接口]:[文件传送类交易]:[Corba传送]");

	public String toSend(UniKeyValueObject outUvo, String fileFullName) {		
		String SvrName = "yygl";
		String Location = BusinessConf.coreServerIp + ":"
				+ BusinessConf.coreServerPort;
		//String Location = "192.168.20.144:9001";
		CEasyAppClient client = new CEasyAppClient();
		if (client.connServer(SvrName, Location) == 1) {
			logger.info("connServer错误 ");
			return IFileSend.CONNECT_FAILD;
		}

		client.UpPkg = new CEasyPackage();
		client.DownPkg = new CEasyPackage();

		String prefixFileName = outUvo.getValue("JGM") == null
				|| "".equals(outUvo.getValue("JGM")) ? "" : outUvo
				.getValue("JGM"); 

		client.UpPkg.addFilename(prefixFileName + "sendFile.txt");
		Iterator it = outUvo.getMap().entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			Object key = entry.getKey();
			Object value = entry.getValue();
			client.UpPkg.addString(key.toString().toLowerCase(), value
					.toString(), "DEFAULT");
		}
		client.UpPkg.addString("TradeName", SvrName, "DEFAULT");
		
		client.callTrade();

		//String st_hostcode =  client.DownPkg.getString("_hostcode", "DEFAULT");
		//String st_errcode = client.DownPkg.getString("_errmsg", "DEFAULT");
		try{
		logger.debug(client.DownPkg.getString("_errmsg", "DEFAULT"));
		logger.debug(client.DownPkg.getString("_hostcode", "DEFAULT"));
		} catch(Exception e){
			e.printStackTrace();
			return IFileSend.ACCEPT_RETURN_FAILD;
		}

		//String st_operlevel = client.DownPkg.getString("I020", "DEFAULT");
		//System.err.println("st_hostcode: " + st_hostcode);
		//System.err.println("st_errcode: " + st_errcode);

//		if (client.orb != null)
//			client.orb.destroy();
//		client.DownPkg.data.clear();
//		client.UpPkg.data.clear();
//		client.DownPkg.mns.clear();
//		client.UpPkg.mns.clear();
		client = null;
		// }
		try {
			Thread.currentThread().sleep(1000);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
			return IFileSend.ACCEPT_RETURN_FAILD;
		}

		System.gc();
		try {
			Thread.currentThread().sleep(1000);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
			return IFileSend.ACCEPT_RETURN_FAILD;
		}
		return IFileSend.SUCCESSFAL;
	}
}
