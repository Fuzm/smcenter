package com.irdstudio.smcenter.core.tinycore.log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 文件文件日志记录者(日志输出到指定文件)
 * @author guangming.li
 * @version 1.0
 * @date 2014-04-27
 */
public class TextFileLogger implements ILogger {
	
	/* 分类/模块名称 */
	private String categoryName = "";
	/* 当前用来记录日志信息的文件 */
	private OutputStreamWriter logFile = null;
	/* 是否仍然在控制台输出日志 */
	private boolean isStillOnConsole = false;
	
	public TextFileLogger(String fullLogFileName, boolean isStillOnConsole) {
		this.isStillOnConsole = isStillOnConsole;
		// 建立该文件并取到该文件的文件对象
		try {
			logFile = new OutputStreamWriter(new FileOutputStream(
					fullLogFileName), "utf-8");
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("初始化自定义日志文件失败!");
		}
	}
	
	public TextFileLogger(String fullLogFileName) {
		new TextFileLogger(fullLogFileName, false);
	}
	
	/**
	 * 自定义日志输出格式化
	 * @param type
	 * @param message
	 * @param te
	 */
	private void writeToLogFile(int type, Object message,Throwable te) {
		StringBuffer msg = new StringBuffer("[");
		msg.append((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))
				.format(Calendar.getInstance().getTime()));
		msg.append("][").append(categoryName);
		msg.append("][").append(TLogger.typeInfo[type]).append("]:");
		msg.append(message).append("\r\n");
		if (isStillOnConsole) {
			System.out.print(msg);
		}
		if (te != null) {
			StringWriter sw = new StringWriter();
			te.printStackTrace(new PrintWriter(sw));
			msg.append(sw.getBuffer());
		}
		try {
			logFile.write(msg.toString());
			logFile.flush();
		} catch (IOException e) {
			System.err.println("记录日志到文件文件发生错误!");
			e.printStackTrace();
		}
	}

	/**
	 * 自定义日志输出格式化
	 * @param type
	 * @param message
	 * @param te
	 */	
	private void writeToLogFile(int type, Object message){
		writeToLogFile(type,message,null);
	}

	public void debug(Object message) {
		writeToLogFile(TLogger.DEBUG,message);
	}

	public void debug(Object message, Throwable t) {
		writeToLogFile(TLogger.DEBUG, message, t);
	}

	public void error(Object message) {
		writeToLogFile(TLogger.ERROR,message);
	}

	public void error(Object message, Throwable t) {
		writeToLogFile(TLogger.ERROR, message, t);
	}

	public void fatal(Object message) {
		writeToLogFile(TLogger.FATAL,message);
	}

	public void fatal(Object message, Throwable t) {
		writeToLogFile(TLogger.FATAL, message , t);
	}

	public void info(Object message) {
		writeToLogFile(TLogger.INFO,message);
	}

	public void info(Object message, Throwable t) {
		writeToLogFile(TLogger.INFO, message, t);
	}

	public void setName(String categoryName) {
		this.categoryName = categoryName;
	}

	public void warn(Object message) {
		writeToLogFile(TLogger.WARNING,message);
	}	

	public void warn(Object message, Throwable t) {
		writeToLogFile(TLogger.WARNING, message, t);
	}
	
	/**
	 * 用于关闭文件
	 */
	public void closeLogFile(){
		try {
			logFile.flush();
			logFile.close();
		} catch (IOException e) {
			System.err.println("关闭自定义日志文件时失败!");
			e.printStackTrace();
		}		
	}
	
	/**
	 * 用于GC回收时关闭日志文件
	 */
	public void finalize(){
		closeLogFile();
	}

	/**
	 * 产生子级的日志记录者
	 */
	public ILogger makeSubLogger(String subCategoryName) {
		ILogger subLogger = null;
		try {
			subLogger = (ILogger) this.clone();
			subLogger.setName(this.categoryName + "-" + subCategoryName);
			return subLogger;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return subLogger;
	}

}
