package com.irdstudio.smcenter.core.assembly.jxp.test;
/**
 * @docRoot
 * 装载者的测试类
 * @author 李广明
 * @version 1.0
 * @date 2006-11-13
 *
 */
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import com.irdstudio.smcenter.core.assembly.jxp.conf.ConfigureEntry;
import com.irdstudio.smcenter.core.assembly.jxp.loader.MainLoader;
import com.irdstudio.smcenter.core.assembly.jxp.parser.CoreParser;
import com.irdstudio.smcenter.core.assembly.jxp.util.LogUtil;
import com.irdstudio.smcenter.core.util.vo.VariableValue;
public class TestLoader {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if(args.length < 1){
			LogUtil.out("Usage :\n    MainLoader <XML文件名>");
			return;
		}
		
			//加载配置文件
			//Config.getInstance().loadGlobal();
			
			//定义日志文件
			//LogUtil.initFile("jxp");
			
			//初始化连接池
			//ConnectionFactory.buildFactory();
			
			LogUtil.out("文件名：" + args[0]);
		ConfigureEntry cxd = MainLoader.loadCoreXml(args[0]);
		VariableValue vv = new VariableValue();
		
		if (args.length >= 2 && !"".equals(args[1]))
			vv.addVariable("JGM",args[1]);
		else
			vv.addVariable("JGM","00586501");
		
		if (args.length >= 3 && !"".equals(args[2]))
			vv.addVariable("PARENT_JGM",args[2]);
		else
			vv.addVariable("PARENT_JGM","00586500");
		
		if (args.length >= 4 && !"".equals(args[3]))
			vv.addVariable("nf",args[3]);
		else
			vv.addVariable("nf","2006");
		
		if (args.length >= 5 && !"".equals(args[4]))
			vv.addVariable("yf",args[4]);
		else
			vv.addVariable("yf","9");
		
		CoreParser parser = new CoreParser();
		System.out.println("开始解释...");
		try {
		      File newFile = new File("00586805.xml");
		      if (newFile.exists()){
		    	  newFile.delete();
		    	  newFile.createNewFile();
		      }
		      OutputStreamWriter fo = new OutputStreamWriter(
						new FileOutputStream(newFile));
		      fo.write("<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?>");
		      parser.linkConfigureEntry(cxd);
		      parser.linkFileObject(fo);
		      parser.linkVariableValue(vv);
		      if(parser.run()){
		    	  LogUtil.out("生成成功!");
		      }
		      else{
		    	  LogUtil.out("生成失败!");
		      }
		      fo.close();
		      fo = null;
		}
		catch(IOException e){
			e.printStackTrace();
			System.out.println("初始化文件时出错!");
		}
	}

}
