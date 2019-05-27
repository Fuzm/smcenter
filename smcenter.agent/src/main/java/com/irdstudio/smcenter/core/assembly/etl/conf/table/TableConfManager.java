package com.irdstudio.smcenter.core.assembly.etl.conf.table;

import java.util.ArrayList;
import java.util.List;

import com.irdstudio.smcenter.core.assembly.etl.conf.GlobalConf;

import java.io.File;

/**
 * @docRoot:
 * 配置文件管理类,用于列出当前目录的所有配置文件
 * @author 李广明
 * @version 1.0
 */
public class TableConfManager {
	
	/**
	 * 用于取得目录下的所有配置文件信息
	 * @return
	 */
	public static List<String> getFileList(String path){	
		
		File directory = new File(path);
		List<String> fileList  = new ArrayList<String>();		
		if(directory.isDirectory()){
			//如果是目录,分解,得到所有的配置文件
			String absPath = directory.getAbsolutePath();
			System.out.println("表配置文件:" + absPath);
			String[] files = directory.list();
			for(int i=0;i<files.length;i++){				
				//组合成文件的绝对路径并放到list中
				if (".conf".equals(files[i].substring(files[i].length() - 5,
						files[i].length()))) {
//					String tmp = absPath + GlobalConf.PATH_SEPARATOR + files[i];
					String tmp = absPath + File.separator + files[i];
					//LogUtil.out("[find table configure]:\n" + tmp);
					fileList.add(tmp);
				}
			}
		}		
		return fileList;
	}
}
