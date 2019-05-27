package com.irdstudio.smcenter.core.assembly.socket.client;

import java.util.Iterator;
import java.util.Map;

import com.irdstudio.smcenter.core.assembly.socket.client.corba.CEasyAppClient;
import com.irdstudio.smcenter.core.assembly.socket.client.corba.CEasyPackage;
import com.irdstudio.smcenter.core.assembly.socket.util.PackUtil;
import com.irdstudio.smcenter.core.util.vo.UniKeyValueObject;
/**
 * 调用corba核心交易
 * @author 刘智瑞
 * @version 1.0
 * @date 2008-08-01
 */

public class CorbaClient implements IBusiClient{
	/*corba服务器地址:端口*/
	private String sLocation = "";
	/*corba服务名*/
	private String sService="yygl";
	
	public CorbaClient(String shost,int iport){
		 this.sLocation=shost+":"+iport;
	}
	
	public CorbaClient(String shost,int iport,String serviceName){
		this.sLocation = shost + ":" + iport;
		this.sService = serviceName;
	}
	/**
	 * 调用核心交易
	 * @param inUvo
	 * @return
	 * @throws Exception
	 */
	public UniKeyValueObject doExecuteBusi(UniKeyValueObject inUvo) throws Exception{
		UniKeyValueObject outUvo=null;
		CEasyAppClient client = new CEasyAppClient();
		client.connServer(this.sService, this.sLocation);
		client.UpPkg = new CEasyPackage();
		client.DownPkg = new CEasyPackage();
		/** 调交易必要的参数*/
		client.UpPkg.addString("TradeName", this.sService, "DEFAULT");
		client.UpPkg.addString("_gyh", inUvo.getValue("GYH"), "DEFAULT");
		client.UpPkg.addString("_hostseqno", " ", "DEFAULT");
		client.UpPkg.addString("_jgm", inUvo.getValue("JGM"), "DEFAULT");
		client.UpPkg.addString("_jym", inUvo.getValue("JYM"), "DEFAULT");
		/** 业务参数*/
		if (inUvo != null) {
			Iterator it=inUvo.getMap().entrySet().iterator();
			while(it.hasNext()){
				Map.Entry me=(Map.Entry)it.next();
				String st_key=(String)me.getKey();
				String st_value=(String)me.getValue();
				if (st_key != null && !st_key.trim().equals("")) {
	                  client.UpPkg.addString(st_key, st_value, "DEFAULT");
	            }
			}
		}
		/**如果存在文件,则向核心系统发送文件**/
		if(!"".equals(inUvo.getValue("FullFileName"))){
			client.UpPkg.addFilename(inUvo.getValue("FullFileName"));
		}
		try {
			/* 调用corba交易 */
			client.callTrade();
			/* 取下传包 */
			outUvo = this.transfPkg2Uvo(client.DownPkg);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return outUvo;
	}
	
	private UniKeyValueObject transfPkg2Uvo(CEasyPackage pkg) throws Exception {
		UniKeyValueObject uvoReturn = null;
		if (pkg != null) {
			try {
				uvoReturn = new UniKeyValueObject();
				for (Iterator itr = pkg.getFields().iterator(); itr.hasNext();) {
					String[] st_v = (String[]) itr.next(); 
					String st_ns = st_v[CEasyPackage.FIELDINDEX_NS];
					String st_key = st_v[CEasyPackage.FIELDINDEX_KEY]; 
					if (st_key != null && !st_key.trim().equals("")) {
						uvoReturn.addVariable(st_key, pkg.getString(st_key, st_ns));
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new  Exception(ex);
			}
		}
		String ydm = uvoReturn.getValue("_HOSTCODE");
		if (ydm.length() < 2 || "".equals(ydm)) {
			uvoReturn.addVariable("YDM", "01");
			uvoReturn.addVariable("YDXX", "核心系统的应答码不符合要求"
					+ uvoReturn.getValue("_errmsg"));
		} else {
			uvoReturn.addVariable("YDM", ydm.substring(0, 2));
			uvoReturn.addVariable("YDXX", uvoReturn.getValue("_errmsg"));
		}
		if ("0000".equals(ydm)) {
			uvoReturn.addVariable("YDXX", "交易成功");
		}
		
		uvoReturn.remove("_HOSTCODE");
		uvoReturn.remove("_ERRMSG");
		return uvoReturn;
	}
	
	public static void main(String[] args) throws Exception{
		UniKeyValueObject uvo = new UniKeyValueObject();
		uvo.addVariable("JYM", "70016");
		uvo.addVariable("JYRQ", "2008-07-30");
		uvo.addVariable("CKZH", "0000000001249011");
		uvo.addVariable("JGM", "9999990116");
		CorbaClient client = new CorbaClient("168.168.211.205",9001);
		UniKeyValueObject backUvo = client.doExecuteBusi(uvo);
		System.out.println(PackUtil.parsePack(backUvo));
		//System.out.println(backUvo.getValue("jym"));
	}

}