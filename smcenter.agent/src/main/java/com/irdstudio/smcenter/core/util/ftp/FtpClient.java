package com.irdstudio.smcenter.core.util.ftp;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * ftp 组件
 * default with port mode
 * @author cuikaiyuan
 * @version 1.4
 * @promote
 * 			cuikaiyuan 2006-08-31 add some methods of stream compressing related 
 * 								  add some methods to enhance this pragram
 * 			cuikaiyuan 2006-08-32 add method boolean removefile(String filename)
 * 								  add method boolean renamefile(String filename,String newname)
 * 			cuikaiyuan 2006-11-04 add method boolean downloadFile(String ,OutputStream)
 * 								  add method boolean downloadFile(String ,OutputStream,boolean)
 * 								  add method boolean changeDirectoryEx(String)
 * @bugfixed
 * 			cuikaiyuan 2006-08-31 fix the method boolean isExists(String dir) which can't work correct 
 * 									while the ftp server response the symbol of "\r\n" as a delimiter
 * 			cuikaiyuan 2006-08-31 fix the file can't be uploaded correctly in compressed mode  
 * 			cuikaiyuan 2006-09-01 fix the delimeter can't work under some type of ftp server
 */

public class FtpClient {
	private final int SO_TIME_OUT = 3000;// socket 超时时间

	private final int BLOCK_SIZE = 1024;// 每次读写的块大小

	private String sp = "/";// server separator
	
	private String delimiter = "\r\n";// ftp server delimeter

	private boolean DEBUG = false;

	private PrintStream ps = System.out;// DEBUG输出的流

	private String localIp = null;// local socket address

	private Socket so = null;// the main socket lifed through all the session

	private BufferedReader socket_reader = null;

	private OutputStream socket_writer = null;

	StringBuffer response = new StringBuffer();// the response text

	public FtpClient() {
		try {
			localIp = InetAddress.getLocalHost().getHostAddress().replaceAll(
					"[.]", ",");// get the local address
		} catch (Exception e) {
			e.printStackTrace();
			debugPrint(e.getMessage());
			// unknowHostException
		}
	}

	/**
	 * 打开一个本地与ftp服务器之间的对话
	 * 
	 * @param remotehost
	 *            ftp服务器地址
	 * @param port
	 *            ftp 端口 默认为21
	 * @param user
	 *            ftp用户
	 * @param pass
	 *            ftp密码
	 * @return 是否验证成功
	 */
	public boolean openSession(String remotehost, int port, String user,
			String pass) {
		// if one session has been opend and forget to close it
		if (so != null && !so.isClosed()) {

			closeSession();
		}
		// to acquire a socket
		so = new Socket();
		try {
			so.connect(new InetSocketAddress(remotehost, port), SO_TIME_OUT);
			// get the welcome info
			socket_reader = new BufferedReader(new InputStreamReader(so
					.getInputStream()));
			socket_writer = so.getOutputStream();
			if (!isPositiveResponse())
				return false;
		} catch (IOException e) {
			e.printStackTrace();
			debugPrint(e.getMessage());
			return false;
		}
		// autenticate to the ftp server

		if (!authenticate(user, pass))
			return false;

		return true;
	}

	/**
	 * 关闭本次对话
	 * 
	 * @return
	 */
	public void closeSession() {
		if (so != null && so.isConnected()) {
			execCmd("quit"+delimiter);
			try {
				// isPositiveResponse();
				socket_writer.close();
				socket_reader.close();
				so.close();
			} catch (IOException e) {
				debugPrint(e.getMessage());
				e.printStackTrace();
			}
		}
	}

	/**
	 * 下载服务器指定目录文件及子目录
	 * 
	 * @param remotedir
	 *            ftp服务器目录
	 * @param localdir
	 *            本地目录
	 * @return
	 */
	public boolean downloadDirectory(String remotedir, String localdir) {
		if (!changeDirectory(remotedir))
			return false;
		ArrayList<String> files = new ArrayList<String>();
		ArrayList<String> dirs = new ArrayList<String>();
		try {
			getRemoteFileList(files, dirs);
		} catch (Exception e) {
			e.printStackTrace();
			debugPrint(e.getMessage());
			return false;
		}

		// download the files
		for (int i = 0, j = files.size(); i < j; i++) {
			// debugPrint(files.get(i).toString());
			if (!downloadFile(files.get(i).toString(), localdir))return false;
			
		}
		// download the files under the dirs recursively
		for (int i = 0, j = dirs.size(); i < j; i++) {
			String tmp_dir = dirs.get(i).toString();
			String tmp_localdir = localdir + File.separator + tmp_dir;
			// if the local directory is not exist ,create it
			File tmp_file = new File(tmp_localdir);
			if (!tmp_file.exists()) {
				tmp_file.mkdirs();
			}
			if (!downloadDirectory(remotedir + sp + tmp_dir, tmp_localdir))
				return false;
		}
		return true;
	}

	/**
	 * 上传整个目录文件到ftp服务器目录
	 * 
	 * @param localdir
	 *            本地目录
	 * @param remotedir
	 *            ftp服务器目录
	 * @return
	 */
	public boolean uploadDirectory(String localdir, String remotedir) {
		File dir = new File(localdir);
		if (!dir.isDirectory())
			return false;
		File[] files = dir.listFiles();// get the files and directories
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()){
				//check if the path exists on server ,if not ,create it
				//if(!isExistsOnServer(files[i].getName()))
				String tmp_dir=remotedir+sp+files[i].getName();
				makedir(tmp_dir);
				uploadDirectory(files[i].getAbsolutePath(),tmp_dir);
				
			}else{
				if (!uploadFile(files[i].getAbsolutePath(), remotedir))return false;
			}	
			
		}// end of for
		return true;
	}

	/**
	 * 从服务器端下载文件到本地目录
	 * 
	 * @param remotefile
	 *            ftp远程目录路径
	 * @param localdir
	 *            本地目录的路径
	 * @return
	 * @throws Exception
	 */
	public boolean downloadFile(String remotefile, String localdir){
		return downloadFile(remotefile,localdir,false);

	}
	
	/**
	 * 从服务器端下载文件到数据流
	 * @param remotefile
	 * 				ftp远程目录路径
	 * @param receiveStream
	 * 				指定的数据流
	 * @return
	 */
	public boolean downloadFile(String remotefile,OutputStream receiveStream){
		return downloadFile(remotefile,receiveStream,false);
	}
	
	/**
	 * 从服务器端下载文件到数据流
	 * @param remotefile
	 * 				ftp远程目录路径
	 * @param receiveStream
	 * 				指定的数据流
	 * @param isUnCompress
	 * 				是否解压缩文件数据流[gzip格式]
	 * @return
	 */
	public boolean downloadFile(String remotefile,OutputStream receiveStream,boolean isUnCompress){
		// split the path from the remotefile[AbsoluteFilePath]
		int d = remotefile.lastIndexOf('\\');
		if (d == -1) {
			d = remotefile.lastIndexOf('/');
		}
		if (d != -1) {
			// 说明文件名中带有路径，需要先进入到该路径下
			String remotedir = remotefile.substring(0, d);// 获得文件的路径
			changeDirectory(remotedir);// cd the remote directory
		}
		String remotefilename = remotefile.substring(d + 1);// 获得文件名
		setDataType("I");// set the transport type to the binary
		String cmd = "retr " + remotefilename ;
	
		try {
			OutputStream os = getOutputStream(receiveStream,false);
			return executeDataCmd(cmd, os,isUnCompress);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			debugPrint(e.getMessage());
		} catch (IOException e){
			e.printStackTrace();
			debugPrint(e.getMessage());
		}
		return false;
		
	}
	/**
	 * 从服务器端下载文件到本地目录
	 * @param remotefile
	 * 				ftp远程目录路径
	 * @param localdir
	 * 				本地目录的路径
	 * @param isUnCompress
	 * 				是否解压缩文件数据流[gzip格式]
	 * @return
	 */
	public boolean downloadFile(String remotefile,String localdir,boolean isUnCompress){
		// split the path from the remotefile[AbsoluteFilePath]
		int d = remotefile.lastIndexOf('\\');
		if (d == -1) {
			d = remotefile.lastIndexOf('/');
		}
		if (d != -1) {
			// 说明文件名中带有路径，需要先进入到该路径下
			String remotedir = remotefile.substring(0, d);// 获得文件的路径
			changeDirectory(remotedir);// cd the remote directory
		}
		String remotefilename = remotefile.substring(d + 1);// 获得文件名
		setDataType("I");// set the transport type to the binary
		String cmd = "retr " + remotefilename ;
		// test if the localdir is exists ,else create it
		File tmp_file = new File(localdir);
		if (!tmp_file.exists()) {
			tmp_file.mkdirs();
		}
		// debugPrint(localdir + File.separator + remotefilename);
		
		try {
			OutputStream os = getOutputStream(localdir + File.separator + remotefilename,false);
			return executeDataCmd(cmd, os,isUnCompress);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			debugPrint(e.getMessage());
		} catch (IOException e){
			e.printStackTrace();
			debugPrint(e.getMessage());
		}
		return false;
	}
	
	/**
	 * 上传本地文件到服务器制定目录中去
	 * @param localfile
	 * 				本地文件
	 * @param remotedir
	 * 				ftp服务器目录
	 * @param isCompress
	 * 				是否压缩文件数据流[gzip格式]
	 * @return
	 */
	public boolean uploadFile(String localfile,String remotedir,boolean isCompress){
		changeDirectory(remotedir);// cd the remote directory
		setDataType("I");// set the transport type to the binary
		String cmd = "stor " + new File(localfile).getName() ;
		
		try {
			InputStream is = getInputStream(localfile, false);
			return executeDataCmd(cmd, is,isCompress);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			debugPrint(e.getMessage());
		} catch (IOException e){
			e.printStackTrace();
			debugPrint(e.getMessage());
		}
		return false;
	}

	/**
	 * 上传本地文件到服务器制定目录中去，
	 * 默认不压缩文件数据流
	 * 
	 * @param localfile
	 *            本地文件
	 * @param remotedir
	 *            ftp服务器目录
	 * @return
	 * @throws Exception
	 */
	public boolean uploadFile(String localfile, String remotedir) {
		return uploadFile(localfile,remotedir,false);
	}
	/**
	 * 改变服务器当前工作目录
	 * 
	 * @param remotedir
	 *            ftp服务器当前工作路径
	 * @return
	 */
	public boolean changeDirectory(String remotedir) {
		execCmd("cwd " + remotedir );
		return (isPositiveResponse());
	}
	/**
	 * 改变服务器当前工作目录，如果没有存在就创建
	 * 
	 * @param remotedir
	 * 			ftp服务器当前工作路径
	 * @return
	 */
	public boolean changeDirectoryEx(String remotedir){
		
		//if the remotedir exists,change the dir to it 
		if(changeDirectory(remotedir))return true;
		
		String[] dirs=null;
		String tmpD=null;
		
		//conver the the dir to standard format
		dirs=remotedir.replaceAll("\\\\","/").split("/");
		//suppose the ftp server is unix like,and change to the root directory
		//if not ,it doesn't matter
		changeDirectory("/");	
		
		for(int i=0;i<dirs.length;i++){
			tmpD=dirs[i].trim();
			if(tmpD.equals(""))continue;
			if(!changeDirectory(tmpD)){
				makedir(tmpD);
				if(!changeDirectory(tmpD))return false;
				
			}
		}
		
		return true;
	}

	/**
	 * 打印服务器当前工作目录
	 * 
	 * @return
	 */
	public String getCurrentDirectory() {
		execCmd("pwd"+delimiter);
		if (!isPositiveResponse())
			return null;// if the cmd not executes correctly,retun null as the
						// wrong response
		// get the current working path quoted
		int begin = response.toString().indexOf('"') + 1;
		int end = response.toString().lastIndexOf('"');
		String path = response.toString().substring(begin, end);
		return path;
	}

	/**
	 * 新建远程服务器目录
	 * [注意]本方法每次只能在一个已存在的目录下新建一个目录
	 * 
	 * @param pathname
	 *            ftp服务器路径，若为相对路径，则在当前目录下创建
	 *            若为绝对路径，则不受限制
	 * @return
	 */
	
	public boolean makedir(String pathname) {
		//debugPrint("makedir "+pathname);
		execCmd("mkd " + pathname );
		return isPositiveResponse();

	}
	
	/**
	 * 删除远程服务器目录
	 * 
	 * @param pathname
	 *            ftp服务器路径
	 * @return
	 */
	public boolean removedir(String pathname) {
		execCmd("rmd " + pathname );
		return isPositiveResponse();

	}
	/**
	 * 删除远程服务器文件
	 * @param filename
	 * @return
	 */
	public boolean removefile(String filename){
		execCmd("dele "+filename+delimiter);
		return isPositiveResponse();
	}
	/**
	 * 更改远程服务器文件名
	 * @param filename
	 * @param newname
	 * @return
	 */
	public boolean renamefile(String filename,String newname){
		execCmd("rnfr "+filename+delimiter);
		if(!isPositiveResponse()){
			return false;
		}
		execCmd("rnto "+newname+delimiter);
		return isPositiveResponse();
	}
	/**
	 * 获得ftp服务器当前工作目录的文件列表
	 * 如想查看/easymis/cky下的所有文件，可先用changeDirectory(path),进入到该目录，然后调用本函数
	 * 
	 * @param files
	 *            将包含所有的文件列表
	 * @param dirs
	 *            将包含所有的目录列表
	 * @return
	 * @throws Exception
	 */
	public boolean getRemoteFileList(ArrayList<String> files, ArrayList<String> dirs)
			throws Exception {
		String cmd = "list"+delimiter;
		StringBuffer tmp_list = new StringBuffer();// mixture of directories
													// and files
		if(!executeDataCmd(cmd, tmp_list))return false;
		// to analisys the files and the dirs
		StringTokenizer token = new StringTokenizer(tmp_list.toString(), delimiter);
		String tmp_str;
		while (token.hasMoreTokens()) {
			tmp_str = token.nextToken();
			debugPrint(tmp_str);
			if (tmp_str.length() <= 0)
				continue;
			switch (tmp_str.charAt(0)) {
			case '-':
				// file
				files.add(parseListName(tmp_str));
				break;
			case 'd':
				// directory
				dirs.add(parseListName(tmp_str));
				break;
			// case 'l':
			// //symble of link,lower case of letter L
			// //just do nothing
			// break;
			}
		}
		return false;
	}
	/**
	 * 判断目录或文件是否存在于ftp服务器上　
	 * @param dir　指定目录名
	 * @return
	 * @throws IOException
	 */
	public boolean isExistsOnServer(String dir){
		String cmd = "nlst"+delimiter;
		StringBuffer tmp_list = new StringBuffer();// mixture of directories
		// and files
		
		try {
			if (!executeDataCmd(cmd, tmp_list))
				return false;
		} catch (IOException e) {
			e.printStackTrace();
			debugPrint(e.getMessage());
			return false;
		}
		
		// to analisys the files and the dirs
		StringTokenizer token = new StringTokenizer(tmp_list.toString(), delimiter);
		String tmp_str=dir;
		if(DEBUG){
			debugPrint(tmp_list.toString());
		}
		String tmp=null;
		while (token.hasMoreTokens()) {
			tmp=token.nextToken();
			if(tmp_str.equals(tmp.replaceFirst("\r",""))){
				return true;//if the specified directory exists,return true
			}
		}
		return false;
	}
	private String parseListName(String tmp_str) {
		int begin = tmp_str.lastIndexOf(' ');
		return tmp_str.substring(begin + 1);
	}
	
	/**
	 * 设置ftp服务器的文件分隔类型
	 * 默认为unix类型
	 * @param info
	 * 			ftp 服务器目录等
	 * @return
	 */
	public void setFtpServerSeparator(String info){
		if(info.indexOf("/")!=-1){
			sp="/";
			delimiter="\n";//unix system
		}
		if(info.indexOf("\\")!=-1){
			sp="\\";//windows system
		}
	}
	/**
	 * 获得当前ftp服务器的分隔符
	 * @return
	 */
	public String getFtpServerSeparator(){
		return sp;
	}
	/**
	 * 如果产生错误,可以调用此函数获得最后服务器的返回值
	 * 
	 * @return the last response of the ftp server
	 */
	public String getResponseText() {
		return response.toString();
	}

	/**
	 * 设置是否打印消息,默认为false
	 * 
	 * @param isDebug
	 */
	public void setDebug(boolean isDebug) {
		DEBUG = isDebug;
	}
	
	/**
	 * 设置系统打印流,默认是System.out,可以为设置文件流等
	 * 
	 * @param p
	 */
	public void setDebugStream(PrintStream p) {
		ps = p;
	}
	/**
	 * 设置系统日志文件
	 * @param filename
	 */
	public boolean setDebugStream(String logfile){
		try {
			ps = new PrintStream(new FileOutputStream(logfile));
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			debugPrint(e.getMessage());
			return false;
		}
		
	}

	private boolean executeDataCmd(String cmd, InputStream fis,boolean isCompress)
			throws IOException, SocketException {
		ServerSocket soc = new ServerSocket(0);// initiate a serversocket use a
												// random free port
		soc.setSoTimeout(SO_TIME_OUT);
		setServerPort(soc);
		execCmd(cmd);// use the local file name as the remote file name
		Socket so = soc.accept(); // blocks.....
		OutputStream ops = getOutputStream(so.getOutputStream(),isCompress);
		transferData(fis, ops);
		so.close();
		soc.close();
		return isPositiveResponse();

	}

	private boolean executeDataCmd(String cmd, OutputStream fos,boolean isUnCompress)
			throws IOException, SocketException {
		ServerSocket soc_tmp = new ServerSocket(0);// initiate a serversocket
													// use a random free port
		soc_tmp.setSoTimeout(SO_TIME_OUT);
		setServerPort(soc_tmp);
		execCmd(cmd);// use the local file name as the remote file name
		Socket so_tmp = soc_tmp.accept(); // blocks.....
		InputStream ios = getInputStream(so_tmp.getInputStream(),isUnCompress);
		transferData(ios, fos);
		
		so_tmp.close();
		soc_tmp.close();
		return isPositiveResponse();
	}

	private boolean executeDataCmd(String cmd, StringBuffer filelist)
			throws IOException, SocketException {
		ServerSocket soc_tmp = new ServerSocket(0);// initiate a serversocket
													// use a random free port
		soc_tmp.setSoTimeout(SO_TIME_OUT);
		setServerPort(soc_tmp);
		execCmd(cmd);// use the local file name as the remote file name
		Socket so_tmp = soc_tmp.accept(); // blocks.....
		InputStream ios = so_tmp.getInputStream();
		transferData(ios, filelist);
		so_tmp.close();
		soc_tmp.close();
		return isPositiveResponse();
	}

	private boolean setServerPort(ServerSocket so) {
		int port = so.getLocalPort();// get the localport
		execCmd("port " + localIp + "," + port / 256 + "," + port % 256 );
		return isPositiveResponse();
	}

	private boolean authenticate(String user, String pass) {
		execCmd("user " + user );
		if (!isPositiveResponse())
			return false;
		execCmd("pass " + pass );
		if (!isPositiveResponse())
			return false;
		return true;
	}

	private void execCmd(String cmd) {
		cmd+=delimiter;
		debugPrint(cmd);
		try {
			socket_writer.write(cmd.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			debugPrint(e.getMessage());
		}
	}

	private boolean isPositiveResponse() {

		getServerResponse();// full fill the stringbuffer with server response
		int errcode = getResponseErrorCode();// get the first int num
		if (isPositivePreliminaryResponse(errcode)) {
			getServerResponse();
			return isPositiveCompleteResponse(getResponseErrorCode());
		} else {
			return isPositiveCompleteResponse(errcode)
					|| isPositiveIntermediateResponse(errcode);
		}

	}

	private int getResponseErrorCode() {
		return Integer.parseInt(response.toString().substring(0, 3));
	}

	private boolean isPositivePreliminaryResponse(int errcode) {
		return (errcode >= 100 && errcode < 200);
	}

	private boolean isPositiveCompleteResponse(int errcode) {
		return (errcode >= 200 && errcode < 300);
	}

	private boolean isPositiveIntermediateResponse(int errcode) {
		return (errcode >= 300 && errcode < 400);
	}

	// private boolean isTransientNegativeResponse(int errcode){
	// return (errcode>=400&&errcode<500);
	// }
	// private boolean isPermanentNegativeResponse(int errcode){
	// return (errcode>=500&&errcode<600);
	//    	
	// }

	private void getServerResponse() {
		response.setLength(0);// clear the buffer
		String tmp = null;
		try {
			do {
				tmp = socket_reader.readLine();
				debugPrint(tmp);
				response.append(tmp);
			} while (Character.isDigit(tmp.charAt(0)) && tmp.charAt(3) == '-');

		} catch (IOException e) {
			e.printStackTrace();
			debugPrint(e.getMessage());
		}

	}

	private boolean setDataType(String tp) {
		try {
			execCmd("type " + tp );
			if (!isPositiveResponse())
				return false;
		} catch (Exception e) {
			e.printStackTrace();
			debugPrint(e.getMessage());
			return false;
		}
		return true;
	}

	private void transferData(InputStream is, OutputStream os) {
		int amount = 0;
		byte[] data = new byte[BLOCK_SIZE];
		try {
			while ((amount = is.read(data,0,BLOCK_SIZE)) > 0) {
				os.write(data, 0, amount);
			}
			is.close();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
			debugPrint(e.getMessage());
		}
	}

	private void transferData(InputStream is, StringBuffer sb) {
		int amount = 0;
		byte[] data = new byte[BLOCK_SIZE];
		try {
			while ((amount = is.read(data,0,BLOCK_SIZE)) > 0) {
				sb.append(new String(data, 0, amount));
			}
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
			debugPrint(e.getMessage());
		}
	}
	/**
	 * 获得文件的输入流 
	 * @param filename
	 * @param isCompress
	 * 			文件格式是否为压缩格式
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private OutputStream getOutputStream(String filename,boolean isCompress)
				throws FileNotFoundException,IOException{
		if(isCompress){
			return new GZIPOutputStream(new FileOutputStream(filename));
		}else{
			return new FileOutputStream(filename); 
		}
	}
	private OutputStream getOutputStream(OutputStream os,boolean isCompress)
			throws IOException{
		if(isCompress){
			return new GZIPOutputStream(os,BLOCK_SIZE);
		}else{
			return os;
		}
	}
	/**
	 * 获得文件的输出流
	 * @param filename
	 * @param isCompress
	 * 			文件格式是否为压缩格式
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private InputStream getInputStream(String filename,boolean isUnCompress)
			throws FileNotFoundException,IOException{
		if(isUnCompress){
			return new GZIPInputStream(new FileInputStream(filename));
		}else{
			return new FileInputStream(filename); 
		}
	}
	private InputStream getInputStream(InputStream is, boolean isCompress)
			throws IOException {
		if (isCompress) {
			return new GZIPInputStream(is,BLOCK_SIZE);
		} else {
			return is;
		}
	}
	private void debugPrint(String info){
		if (DEBUG) {
			ps.println("DEBUG-------------->" + info);
		}
	}

	
//	// 以下为一个完整的demo
//// 
//	public static void main(String args[]) throws Exception{
//		long beging = System.currentTimeMillis();
//		FtpClient f = new FtpClient();
//		f.setFtpServerSeparator("/");//设置分隔符,默认即为unix类型
//		f.setDebug(true);// 设置是否输出DEBUG输出
//		//f.setDebugStream("logfile");// 可以指定系统的调试信息输出，默认为System.out
//
//		if (!f.openSession("192.168.20.144", 21, "easymis", "easymis")) {
//			// 创建session失败，可能为错误的用户名密码等
//			f.getResponseText();//通过它来查看服务器返回
//		}
//	
////		if(!f.changeDirectory("/tmp/cky/ab/bb")){
////			f.debugPrint("cky--------------"+f.getResponseText());
////			if(!f.makedir("/tmp/cky/ab/bb")){
////				f.debugPrint("cky--------------"+f.getResponseText());
////			}
////			if(!f.changeDirectory("/tmp/cky/ab/bb")){
////				f.debugPrint("cky--------------"+f.getResponseText());
////			}
////			
////		}
////		f.changeDirectoryEx("e:/tmp/kkk/aaa");
////		System.out.println(f.getCurrentDirectory());
////		
////		OutputStream os = new FileOutputStream("e:/tmp.pdf");
////		
//		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
//		f.downloadFile("/easymis/tomcat5/platform_snx/tmp/wjfl_attach/conftab/0058650104265012006-10-12.pdf",byteStream);
//		System.out.println(byteStream.size()+"daxiao");
//		byteStream.writeTo(new FileOutputStream("e:/tmp.pdf"));
////		
////		if (!f.uploadFile("d:\\test.pdf", "c:\\tmp",false)) {
////			// 上传本地文件h:\b.txt到ftp服务器目录/easymis/cky/发生错误;
////			System.out.println(f.getResponseText());// 如果出错，可以查看当前的ftp状态
////		}
////		if (!f.downloadFile("c:\\tmps\\test.pdf", "d:\\cky\\",false)) {
////			// 下载ftp服务器的/easymis/cky/b.txt文件到本地目录d:\失败;
////			System.out.println(f.getResponseText());// 如果出错，可以查看当前的ftp状态
////		}
////		if (!f.uploadDirectory("E:\\ftp3", "/easymis/cky/kk")) {
////			// 上传本地目录E:\ftp3及其下所有文件到ftp服务器/easymis/cky/kk下;
////			System.out.println(f.getResponseText());// 如果出错，可以查看当前的ftp状态
////		}
////		if (!f.downloadDirectory("/easymis/cky", "e:\\ftp3")) {
////			// 下载ftp服务器/easymis/cky目录到本地目录e:\ftp3下;
////			System.out.println(f.getResponseText());//如果出错，可以查看当前的ftp状态
////		}
//
//		
//		f.closeSession();
//		long end = System.currentTimeMillis();
//		System.out.println(end-beging);
//	}

}