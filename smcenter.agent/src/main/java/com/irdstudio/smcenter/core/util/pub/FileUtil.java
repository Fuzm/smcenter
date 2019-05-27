package com.irdstudio.smcenter.core.util.pub;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;

import com.irdstudio.smcenter.core.util.vo.UniKeyValueObject;

/**
 * 文件辅助类
 * @author 李广明
 * @version 1.0
 * @date 2008-06-20
 *
 */
public class FileUtil {
	
	/**
	 * 将记录集的记录保存到文本文件中
	 * @param rs
	 * @param fileFullName
	 * @param split
	 * @return
	 */
	public final static boolean rsToFile(ResultSet rs, String fileFullName,
			String split) {

		BufferedOutputStream fo = null;
		//建立该文件并取到该文件的文件对象
		try {
			File newFile = new File(fileFullName);
			if (newFile.exists()) {
				newFile.delete();
				newFile.createNewFile();
			}
			fo = new BufferedOutputStream(new FileOutputStream(newFile));
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("初始化文件时出错!");
			return false;
		}

		StringBuffer sb = null;
		boolean state = true;
		try {
			int iCount = rs.getMetaData().getColumnCount();
			while (rs.next()) {
				sb = new StringBuffer();
				for (int i = 1; i <= iCount; i++) {
					sb.append(rs.getObject(i).toString().trim());
					sb.append(split);
				}
				//				if (sb.length() > 0)
				//					sb.deleteCharAt(sb.length() - 1);
				sb.append((char)10);
				fo.write(sb.toString().getBytes());
			}
		} catch (SQLException e) {
			e.printStackTrace();
			state = false;
		} catch (IOException ie) {
			ie.printStackTrace();
			state = false;
		} finally {
			try {
				fo.close();
				fo = null;
			} catch (IOException e) {
			}
		}
		return state;
	}
	
	/**
	 * 将集合的记录保存到文本文件中
	 * @param coll collection 集合
	 * @param fileFullName 文件名
	 * @param split 分隔符
	 * @param fields 字段(注意要与接口规范保持一致)
	 * @return
	 */
	public final static boolean collToFile(Collection<?> coll, String fileFullName,
			String split, String[] fields) {
		
		BufferedOutputStream fo = null;
		//建立该文件并取到该文件的文件对象
		try {
			File newFile = new File(fileFullName);
			if (newFile.exists()) {
				newFile.delete();
				newFile.createNewFile();
			}
			fo = new BufferedOutputStream(new FileOutputStream(newFile));
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("初始化文件时出错!");
			return false;
		}
		
		StringBuffer sb = null;
		boolean state = true;
		try {
			for (Iterator<?> iter = coll.iterator(); iter.hasNext();) {
				UniKeyValueObject uvo = (UniKeyValueObject) iter.next();
				sb = new StringBuffer();
				for (int i=0;i<fields.length;i++) {
					sb.append(uvo.getValue(fields[i]));
					sb.append(split);
				}
				sb.append((char)10);
				fo.write(sb.toString().getBytes());				
			}
		} catch (IOException ie) {
			ie.printStackTrace();
			state = false;
		} finally {
			try {
				fo.close();
				fo = null;
			} catch (IOException e) {
			}
		}
		return state;
	}
}
