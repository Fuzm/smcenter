package com.irdstudio.smcenter.core.util.pub;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StreamGobbler extends Thread {
	private InputStream is;
	private String type;
	private StringBuffer info;
	
	/**
	 * 将输出信息最终汇集成字符串
	 * @return
	 */
	public String getInfo() {
		return info.toString();
	}

	public StreamGobbler(InputStream is, String type) {
		this.is = is;
		this.type = type;
		this.info = new StringBuffer();
	}

	public void run() {
		try {
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null) {
				if (type.toUpperCase().equals("ERROR")) {
					System.out.println("Error:" + line);
				} else {
					System.out.println("STDOUT:" + line);
				}
				info.append(line);
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}