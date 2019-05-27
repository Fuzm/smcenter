package com.irdstudio.smcenter.core.util.pub;

import java.io.IOException;

/**
 * @docRoot:
 * 用来接收用户输入的辅助类
 * @author 李广明
 * @version 1.0
 *
 */
public class ReadKeyUtil {
	
	public final static int YORN   = 1;		//只允许输入Y或者N,大小写不限
	public final static int NUMBER = 2;		//只允许输入数字
	
	public final static char getSingleKey(int restrict){
		char key = '*';
		try {			
			switch (restrict) {
				case ReadKeyUtil.YORN:
					key = (char) System.in.read();
					if(key != 10) System.in.skip(100);
					while (!(key == 'Y' || key == 'y' || key == 'N' || key == 'n')) {					
						System.out.print("请输入选择(y or n):");
						key = (char) System.in.read();
						if(key != 10) System.in.skip(100);
					}
					break;
				case ReadKeyUtil.NUMBER:
					key = (char) System.in.read();
					if(key != 10) System.in.skip(100);
					while (key < 48 || key > 57) {
						System.out.print("请输入选择(0 ~ 9):");
						key = (char) System.in.read();
						if(key != 10) System.in.skip(100);
					}
					break;
			}
		} catch (IOException e) {			
			e.printStackTrace();
			return '*';
			
		}
		return key;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
