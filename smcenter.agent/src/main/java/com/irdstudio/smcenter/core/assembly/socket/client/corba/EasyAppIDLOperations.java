package com.irdstudio.smcenter.core.assembly.socket.client.corba;

/**
* EasyAppIDLOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from EasyEai.idl
* Monday, March 15, 2004 8:32:43 PM CST
*/

public interface EasyAppIDLOperations
{

  /*
  	@roseuid 3FB0912B007D */
  int openFile (String filename, int mode);

  /*
  	@roseuid 3FB0912B0091 */
  int readFile (int fd, block_tHolder buf);

  /*
  	@roseuid 3FB0912B0087 */
  int writeFile (int fd, byte[] buf);

  /*
  	@roseuid 3FCAE0E60179 */
  int closeFile (int fd);

  /*
  	@roseuid 3FD43F9D029A */
  int commit (byte[] InMsg, block_tHolder OutMsg);
} // interface EasyAppIDLOperations
