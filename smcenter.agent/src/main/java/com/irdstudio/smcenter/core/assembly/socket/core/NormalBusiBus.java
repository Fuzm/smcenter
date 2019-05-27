package com.irdstudio.smcenter.core.assembly.socket.core;

import java.sql.SQLException;

import com.irdstudio.smcenter.core.assembly.socket.core.util.CoreXmlParser;
import com.irdstudio.smcenter.core.assembly.socket.element.BusinessAccept;
import com.irdstudio.smcenter.core.assembly.socket.element.BusinessConf;
import com.irdstudio.smcenter.core.assembly.socket.element.BusinessDeal;
import com.irdstudio.smcenter.core.assembly.socket.element.BusinessDesc;
import com.irdstudio.smcenter.core.assembly.socket.element.BusinessReturn;
import com.irdstudio.smcenter.core.assembly.socket.logic.IBusiLogic;
import com.irdstudio.smcenter.core.assembly.socket.util.PackUtil;
import com.irdstudio.smcenter.core.util.vo.UniKeyValueObject;

/**
 * 标准(通用)的交易处理总线
 * @author 李广明
 * @version 1.0
 * @date 2008-07-07 
 */
public class NormalBusiBus extends AbstractBusinessBus{
	
	public UniKeyValueObject doExecute(UniKeyValueObject inUvo) throws SQLException {
		
		CoreXmlParser cxp = new CoreXmlParser(logger);

		//根据交易码获取其对应的执行描述信息
		String jym = inUvo.getValue(KEY_FIELD);
		BusinessDesc desc = (BusinessDesc) BusinessConf.busiMap.get(jym);
		logger.info(desc.getDesc());
		
		//判断是否需要使用自定义类,如果需要则引用自定义的类进行处理并返回结果
		if(!"".equals(desc.getDealClass())){
			IBusiLogic customLogic = null;
			try {
				customLogic = (IBusiLogic) Class.forName(
						desc.getDealClass()).newInstance();
				return customLogic.execute(inUvo);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}catch(Exception e){
				e.printStackTrace();
			}
			return PackUtil.backErrorPack(jym, PackUtil.CUSTOM_CLASS_FAILD);
		}

		//校验其所需要接收的参数是否齐全并符合要求
		if (desc.getAcceptParams().size() > 0) {
			BusinessAccept accept = null;
			for (int i = 0; i < desc.getAcceptParams().size(); i++) {
				accept = (BusinessAccept) desc.getAcceptParams().get(i);
				if (!cxp.toCheck(inUvo, accept)) {
					return PackUtil.backParamErrorPack(inUvo
							.getValue(KEY_FIELD), accept.getParamDesc(), accept
							.getParamName());
				}
			}
		}

		//执行处理段中的SQL
		BusinessDeal deal = desc.getBd();
		if (deal != null) {
			try {
				cxp.executeSql(deal, inUvo, null);
			} catch (Exception e) {
				e.printStackTrace();
				return PackUtil.backErrorPack(jym, e.getMessage());
			}
		}

		//执行返回数据的SQL并进行组包
		UniKeyValueObject outUvo = null;
		BusinessReturn br = desc.getBr();
		if (br != null) {
			outUvo = cxp.toPack(br, inUvo);
		} else {
			outUvo = PackUtil.backSuccessfulPack(jym);
		}
		if (outUvo == null)
			outUvo = PackUtil.backErrorPack(jym, PackUtil.NO_RETURN_DATA);
		return outUvo;
	}

}
