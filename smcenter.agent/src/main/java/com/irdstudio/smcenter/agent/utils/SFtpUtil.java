package com.irdstudio.smcenter.agent.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

/**
 * <p>
 * Title: SFtp工具类
 * </p>
 * <p>
 * Description: 使用jsch实现sftp上传下载
 * </p>
 * <p>
 * Copyright: yuchengtech Copyright (c) 2014
 * </p>
 * <p>
 * Company:yucheng
 * </p>
 * 
 * @author shiy 2016-6-18
 * @version 1.0
 */
public class SFtpUtil {
	private static Logger logger = Logger.getLogger(SFtpUtil.class);
	private String sftpServer;
	private int port = 22;
	private String userName = "anonymous";
	private String password = "user@anonymous.com";
	public final static String BUSIMG_FILENAMESEPARATOR = "|";

	public final static String NCMIS_ROOT = PropertiesUtil.getApplicationKey("sftp.filepath.ncmis");// 管理端的SFTP根目录
	public final static String CHANNEL_ROOT = PropertiesUtil.getApplicationKey("sftp.filepath.channel");// 渠道的SFTP根目录
	public final static String YCLOANS_ROOT = PropertiesUtil.getApplicationKey("sftp.filepath.ycloans");// 核算的SFTP根目录

	/**
	 * 获取sftp工具实例
	 * @return
	 */
	public static SFtpUtil instance() {
		String ip = PropertiesUtil.getApplicationKey("sftp.ip");
		String port = PropertiesUtil.getApplicationKey("sftp.port");
		String user = PropertiesUtil.getApplicationKey("sftp.user");
		String pwd = PropertiesUtil.getApplicationKey("sftp.pwd");
		return new SFtpUtil(ip, Integer.valueOf(port), user, pwd);
	}

	/**
	 * 构造方法
	 * 
	 * @param sftpServer
	 * @param port
	 * @param userName
	 * @param password
	 */
	public SFtpUtil(String sftpServer, int port, String userName, String password) {
		super();
		this.sftpServer = sftpServer;
		this.port = port;
		this.userName = userName;
		this.password = password;
	}

	/**
	 * 打开Session
	 * 
	 * @return
	 * @throws Exception
	 */
	private Session initSession() throws Exception {
		Session sshSession = null;
		try {
			// 获取sshSession
			JSch jsch = new JSch();
			sshSession = jsch.getSession(this.userName, this.sftpServer, this.port);
			sshSession.setPassword(this.password);
			Properties sshConfig = new Properties();
			sshConfig.put("StrictHostKeyChecking", "no");
			sshSession.setConfig(sshConfig);
			sshSession.connect();
			// sshSession.setTimeout(timeout)
			logger.info("SFTP服务器[" + sftpServer + " : " + port + "]登录成功");
		} catch (Exception e) {
			logger.info("登录SFTP服务器IO异常", e);
			throw e;
		}

		return sshSession;
	}

	/**
	 * 打开Channel
	 * 
	 * @param sshSession
	 * @return
	 * @throws Exception
	 */
	private ChannelSftp initConnection(Session sshSession) throws Exception {
		ChannelSftp sftp = null;
		try {
			// 打开渠道
			Channel channel = sshSession.openChannel("sftp");
			channel.connect();
			sftp = (ChannelSftp) channel;
			logger.info("成功连接SFTP服务器[" + sftpServer + " : " + port + "]");
		} catch (Exception e) {
			logger.info("初始化SFTP服务器IO异常", e);
		}
		sftp.cd("/");// 登陆后默认路径切换到/
		return sftp;
	}

	/**
	 * 上传文件到SFTP服务器
	 * 
	 * @param localFileName 本地文件名称，若为多个文件，以|分开
	 * @param ftpFilePath FTP文件路径
	 * @param localFilePath 本地文件路径
	 * @throws Exception
	 */
	public void upLoad(String localFileName, String ftpFilePath, String localFilePath) throws Exception {
		Session sshSession = null;
		ChannelSftp sftp = null;
		try {
			// 初始化连接
			sshSession = this.initSession();
			sftp = initConnection(sshSession);

			// 建立目录结构
			this.makeAndCdDir(sftp, ftpFilePath);

			// 上传文件
			if (localFileName.indexOf(BUSIMG_FILENAMESEPARATOR) != -1) {
				// String[] files =
				// StringUtils.splitPreserveAllTokens(localFileName,
				// BusImgConstance.BUSIMG_FILENAMESEPARATOR);
				String[] files = localFileName.split("\\|");
				for (int i = 0; i < files.length; i++) {
					this.uploadFile(sftp, localFilePath + File.separator + files[i]);
				}
			} else {
				this.uploadFile(sftp, localFilePath + File.separator + localFileName);
			}
		} finally {
			if (sftp != null) {
				this.disconnect(sftp);
			}
			sshSession.disconnect();
		}
	}

	/**
	 * 批量上传文件到SFTP服务器
	 * 
	 * @param ftpFilePath 服务器上传路径
	 * @param files 上传文件对象
	 * @throws Exception
	 */
	public void upLoadBatch(String ftpFilePath, List<Map<String, String>> files) throws Exception {
		Session sshSession = null;
		ChannelSftp sftp = null;
		try {
			// 初始化连接
			sshSession = this.initSession();
			sftp = initConnection(sshSession);

			// 建立目录结构
			this.makeAndCdDir(sftp, ftpFilePath);
			// 上传文件
			if (files != null && files.size() > 0) {
				for (Map<String, String> map : files) {
					String path = map.get("path");
					String fileName = map.get("fileName");
					this.uploadFile(sftp, path + File.separator + fileName);
				}
			}
		} finally {
			if (sftp != null) {
				this.disconnect(sftp);
			}
			sshSession.disconnect();
		}
	}

	/**
	 * 上传文件到SFTP服务器
	 * 
	 * @param sftp
	 * @param localFilePathName
	 * @param remoteFilePathName
	 * @throws Exception
	 */
	private void uploadFile(ChannelSftp sftp, String localFilePathName) throws Exception {
		InputStream input = null;
		try {
			File file = new File(localFilePathName);
			if (!file.isDirectory()) {
				input = new FileInputStream(file);
				sftp.put(input, file.getName());
				logger.info("文件[" + localFilePathName + "]成功上传到SFTP服务器");
			}
		} catch (IOException ioe) {
			logger.info("上传文件[" + localFilePathName + "]IO异常", ioe);
			throw ioe;
		} catch (Exception e) {
			logger.info("上传文件[" + localFilePathName + "]其他异常", e);
			throw e;
		} finally {
			try {
				if (input != null) {
					input.close();
				}
			} catch (IOException ex) {
				logger.info("SFTP对象关闭异常", ex);
			}
		}
	}

	/**
	 * 在当前工作目录下建立多级目录结构
	 * 
	 * @param sftp
	 * @param dir
	 * @throws Exception
	 */
	public void makeAndCdDir(ChannelSftp sftp, String dir) throws Exception {
		try {
			StringTokenizer toke = new StringTokenizer(dir, "/");
			while (toke.hasMoreElements()) {
				String currentDir = (String) toke.nextElement();
				if (StringUtils.isNotEmpty(currentDir) && currentDir.startsWith("/")) {
					currentDir = currentDir.substring(1);
				}
				try {
					sftp.cd(currentDir);
				} catch (SftpException e1) {
					try {
						sftp.mkdir(currentDir);
						sftp.cd(currentDir);
					} catch (SftpException e) {
						throw new Exception(e);
					}
				}
			}
		} catch (Exception e) {
			logger.info("创建远程目录结构异常", e);
			throw e;
		}
	}

	/**
	 * Disconnect with server
	 * 
	 * @param sftp
	 */
	private void disconnect(ChannelSftp sftp) {
		if (sftp != null) {
			if (sftp.isConnected()) {
				sftp.disconnect();
			} else if (sftp.isClosed()) {
				logger.info("连接已经关闭！");
			}
		}
	}

	/**
	 * 下载文件到本地
	 * 
	 * @param remoteFileName FTP文件名称
	 * @param ftpFilePath FTP文件路径
	 * @param localFilePath 本地文件路径
	 * @throws Exception
	 */
	public void downLoad(String remoteFileName, String ftpFilePath, String localFilePath) throws Exception {
		if (!new File(localFilePath).isDirectory()) {
			// 如果本地文件夹不存在，尝试建立文件夹
			this.makeMultiDirectory(localFilePath);
			if (!new File(localFilePath).isDirectory()) {
				// 创建文件夹失败，抛出异常
				logger.info("创建本地文件夹[" + localFilePath + "]失败");
				throw new Exception("创建本地文件夹[" + localFilePath + "]失败");
			}
		}

		Session sshSession = null;
		ChannelSftp sftp = null;
		try {
			// 初始化连接
			sshSession = this.initSession();
			sftp = this.initConnection(sshSession);

			// 建立目录结构
			this.makeAndCdDir(sftp, ftpFilePath);

			// 下载文件
			if (remoteFileName.indexOf(BUSIMG_FILENAMESEPARATOR) != -1) {
				String[] files = StringUtils.splitPreserveAllTokens(remoteFileName, BUSIMG_FILENAMESEPARATOR);
				for (int i = 0; i < files.length; i++) {
					this.downloadFile(sftp, files[i], localFilePath + File.separator + files[i]);
				}
			} else if (remoteFileName.equalsIgnoreCase("*")) {
				logger.info("--------进入了} else if (remoteFileName.equalsIgnoreCase(*)) 中{------", null);
				String[] fileNames = getAllFilesInPath(sftp);
				for (int i = 0; i < fileNames.length; i++) {
					if ("..".equals(fileNames[i].trim()) || ".".equals(fileNames[i].trim())) {
						logger.info("-----“..”和“.”不下载-----", null);
						continue;
					}
					try {
						// 是否是文件夹
						sftp.cd(fileNames[i]);
						String[] nextFileNames = getAllFilesInPath(sftp);
						for (int j = 0; j < nextFileNames.length; j++) {
							if ("..".equals(nextFileNames[j].trim()) || ".".equals(nextFileNames[j].trim())) {
								logger.info("-----“..”和“.”不下载-----", null);
								continue;
							}
							this.downloadFile(sftp, nextFileNames[j], localFilePath + File.separator + nextFileNames[j]);
						}
						sftp.cd("..");
					} catch (Exception e) {
						this.downloadFile(sftp, fileNames[i], localFilePath + File.separator + fileNames[i]);
					}

				}
			} else {
				this.downloadFile(sftp, remoteFileName, localFilePath + File.separator + remoteFileName);
			}
		} finally {
			if (sftp != null) {
				this.disconnect(sftp);
			}
			sshSession.disconnect();
		}
	}

	/**
	 * 下载文件到本地
	 * 
	 * @param sftp
	 * @param remoteFilePathName
	 * @param localFilePathName
	 * @throws Exception
	 */
	private void downloadFile(ChannelSftp sftp, String remoteFileName, String localFilePathName) throws Exception {
		OutputStream output = null;
		try {
			File file = new File(localFilePathName);
			output = new FileOutputStream(file);
			sftp.get(remoteFileName, output);
			logger.info("文件[" + remoteFileName + "]成功从SFTP服务器下载");
		} catch (IOException ex) {
			logger.info("下载文件[" + remoteFileName + "]IO异常", ex);
			throw ex;
		} catch (SftpException e) {
			logger.info("文件[" + remoteFileName + "]不存在", e);
			throw e;
		} catch (Exception e) {
			logger.info("下载文件[" + remoteFileName + "]其他异常", e);
			throw e;
		} finally {
			try {
				if (output != null) {
					output.flush();
					output.close();
				}
			} catch (IOException ex) {
				logger.info("SFTP对象关闭异常", ex);
			}
		}
	}

	/**
	 * 在指定位置（本地）建立多级目录结构
	 * 
	 * @param path
	 */
	private void makeMultiDirectory(String path) {
		// 检查根目录是否存在
		if (!(new File(path).isDirectory())) {
			// 依次建立各级目录
			String[] dirStrArray = StringUtils.splitPreserveAllTokens(path, File.separator);
			StringBuffer dirStr = new StringBuffer(dirStrArray[0]);
			for (int i = 0; i < dirStrArray.length - 1; i++) {
				dirStr.append(File.separator);
				dirStr.append(dirStrArray[i + 1]);
				dirStr.append(File.separator);
				if (!(new File(dirStr.toString()).isDirectory())) {
					boolean flag = new File(dirStr.toString()).mkdirs();
					if (!flag)
						logger.info(dirStr + "创建目录异常", new Exception(dirStr + "创建目录异常"));
				}
			}
		}
	}

	/**
	 * 路径下的全部文件
	 * 
	 * @param sftp
	 * @return
	 * @throws EMPException
	 */
	private String[] getAllFilesInPath(ChannelSftp sftp) throws Exception {
		String[] names = null;
		String curPath = null;
		try {
			curPath = sftp.pwd();

			@SuppressWarnings("rawtypes")
			Vector files = sftp.ls(".");
			logger.info("--------总共有" + files.size() + "个文件------", null);
			for (int i = 0; i < files.size(); i++) {
				logger.info("--------进入for循环中了！------", null);
				LsEntry entry = (LsEntry) files.get(i);
				if ("..".equals(entry.getFilename().trim()) || ".".equals(entry.getFilename().trim())) {
					logger.info("------------进入“..”和“.”的 if else 判断中", null);
					files.removeElementAt(i);
				}
				logger.info("文件名称为:" + entry.getFilename(), null);
			}

			names = new String[files.size()];
			for (int i = 0; i < files.size(); i++) {
				LsEntry entry = (LsEntry) files.get(i);
				names[i] = entry.getFilename();
			}
		} catch (Exception e) {
			logger.info("获取路径[" + curPath + "]下的全部文件出现IO异常", e);
		}

		return names;
	}

	/**
	 * 下载文件到本地
	 * 
	 * @param downFileMap 要下载的文件map<ftpfilename, localfilename>
	 * @param ftpFilePath FTP文件路径
	 * @param localFilePath 本地文件路径
	 * @throws Exception
	 */
	public void downLoad(HashMap<String, String> downFileMap, String ftpFilePath, String localFilePath) throws Exception {
		if (!new File(localFilePath).isDirectory()) {
			// 如果本地文件夹不存在，尝试建立文件夹
			this.makeMultiDirectory(localFilePath);
			if (!new File(localFilePath).isDirectory()) {
				// 创建文件夹失败，抛出异常
				logger.info("创建本地文件夹[" + localFilePath + "]失败");
				throw new Exception("创建本地文件夹[" + localFilePath + "]失败");
			}
		}

		Session sshSession = null;
		ChannelSftp sftp = null;
		try {
			// 初始化连接
			sshSession = this.initSession();
			sftp = this.initConnection(sshSession);

			// 建立目录结构
			this.makeAndCdDir(sftp, ftpFilePath);

			// 要下载的文件从map中解析
			String localName = null;
			String remoteName = null;
			Iterator<Entry<String, String>> iter = downFileMap.entrySet().iterator();
			Entry<String, String> entry = null;
			while (iter.hasNext()) {
				entry = iter.next();
				remoteName = (String) entry.getKey();

				localName = (String) entry.getValue();
				// 父目录是否存在
				File pFile = new File(localFilePath + File.separator + localName).getParentFile();
				if (!pFile.exists()) {
					boolean flag = pFile.mkdirs();
					if (!flag)
						throw new Exception("创建父目录[" + pFile.getAbsolutePath() + "]失败");
				}
				pFile = null;
				// 下载文件
				this.downloadFile(sftp, remoteName, localFilePath + File.separator + localName);
			}
		} finally {
			if (sftp != null) {
				this.disconnect(sftp);
			}
			sshSession.disconnect();
		}
	}

	/**
	 * sftp复制文件
	 * 
	 * @param fileName 文件名
	 * @param fromPath 来源目录
	 * @param tragetPath 目标目录
	 * @throws Exception
	 */
	public void copyFile(String fileName, String fromPath, String tragetPath) throws Exception {
		String cmd1 = "cd " + fromPath + ";";// 进入目标路径
		String cmd2 = "cp " + fileName + " " + tragetPath;// 复制目标文件到目标路径
		Session sshSession = null;
		ChannelSftp sftp = null;
		ChannelExec execChannel = null;
		try {
			// 初始化连接
			sshSession = this.initSession();
			Channel channel = sshSession.openChannel("exec");// 设置渠道模式
			sftp = initConnection(sshSession);
			sftp.cd("/");// 切换到根目录
			checkFileIsExist(fileName, fromPath, sftp);// 校验文件来源路径和文件是否存在
			makeAndCdDir(sftp, tragetPath);// 在根目录下面创建目标路径
			execChannel = (ChannelExec) channel;
			execChannel.setCommand(cmd1 + cmd2);// 设置指令
			logger.info("执行指令[" + cmd1 + cmd2 + "]");
			execChannel.connect();// 执行指令
		} catch (Exception e) {
			logger.info(e.getMessage());
			throw new Exception("复制文件异常");
		} finally {
			if (sftp != null) {
				this.disconnect(sftp);
			}
			sshSession.disconnect();
			execChannel.disconnect();// 关闭渠道
		}
	}

	/**
	 * 判断来源路径和文件是否存在
	 * 
	 * @param fileName
	 * @param fromPath
	 * @param sftp
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public void checkFileIsExist(String fileName, String fromPath, ChannelSftp sftp) throws SftpException {
		boolean isExist = false;
		try {
			// 判断来源文件夹是否存在
			StringTokenizer toke = new StringTokenizer(fromPath, "/");
			while (toke.hasMoreElements()) {
				String currentDir = (String) toke.nextElement();
				if (StringUtils.isNotEmpty(currentDir) && currentDir.startsWith("/")) {
					currentDir = currentDir.substring(1);
				}
				try {
					sftp.cd(currentDir);
				} catch (SftpException e1) {
					logger.info(e1.getMessage());
					throw new SftpException(0, currentDir + "目录不存在");
				}
			}
			// 列出当前文件下所以文件，判断原文件是否存在
			Vector files = sftp.ls(".");
			for (int i = 0; i < files.size(); i++) {
				LsEntry entry = (LsEntry) files.get(i);
				if (fileName.equals(entry.getFilename())) {
					isExist = true;
				}
			}
			// 原文件不存在抛出异常
			if (!isExist) {
				throw new SftpException(0, fromPath + "目录下复制来源文件不存在，请检查");
			}
		} catch (SftpException e) {
			logger.info(e.getMessage());
			throw new SftpException(0, e.getMessage());
		}
	}
}
