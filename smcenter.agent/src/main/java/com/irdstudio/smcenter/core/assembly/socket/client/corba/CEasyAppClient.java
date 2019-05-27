package com.irdstudio.smcenter.core.assembly.socket.client.corba;

import java.io.*;
import java.util.Vector;
import org.omg.CosNaming.*;
import org.omg.CORBA.*;
import org.omg.CosNaming.NamingContext.*;


public final class CEasyAppClient
{
   public 	CEasyPackage DownPkg ;
   public 	CEasyPackage UpPkg;
   private 	EasyAppIDL Server;
   final 	int MAXPKG = 32768;

   public CEasyAppClient()
   {
   }

   /**
    * @param seconds
    * @return int
    * @roseuid 3FD1CD2F03CB
    */
   public int callTrade()
   {
        try
        {
                byte[] UpBuf = new byte[MAXPKG];
                byte[] DownBuf = new byte[MAXPKG];
                block_tHolder DownBlock = new block_tHolder(DownBuf);

                int ret;
                Vector itor;

                // up load all files to server first

                itor = UpPkg.getFilenames();

                for (int i = 0; i< itor.size(); i++ )
                {
                ret = sendFile((String)itor.elementAt(i));
                }
                itor.clear();

                // Then send buffer up and get the reply buffer
System.err.println("UpPkg.pack(): " + UpPkg.pack());
                UpBuf = UpPkg.pack().getBytes();
		if ( (ret = Server.commit(UpBuf, DownBlock)) != 0 )
                    return (ret);

         //now unpack the down buf
         String s = new String(DownBlock.value);

         DownPkg.unpack(s);
         itor = DownPkg.getFilenames();

        // Down load all files at last

		for (int i = 0; i< itor.size(); i++ )
         {
           ret = recvFile((String)itor.elementAt(i));
         }

         itor.clear();
         itor = null;

         return 0;
    	}
    catch (Exception ex)
    {
      ex.printStackTrace() ;
      ex.getMessage();

      return 0;
    }
    finally{
      Server._release(); //lm add
      orb.destroy();     //lm add
    }
  }


     private ORB orb  = null;

   /**
    * @param args
    * @return int
    * @roseuid 3FD1CD1C01B1
    */
   public int connServer(String ServerName, String Location)
   {
     String[] ns = {new String("-ORBInitRef"), new String("NameService=corbaloc:iiop:1.2@" + Location + "/NameService")};
      orb  = ORB.init(ns,null);
  try
  {

//for(int n=0; n<ns.length; n++){
//  System.err.println(">>>>" + ns[n]);
//}
	  
	  
    org.omg.CORBA.Object namingContextObj = orb.resolve_initial_references("NameService");
    NamingContext namingContext = NamingContextHelper.narrow(namingContextObj);
    NameComponent[] path = { new NameComponent("AppPlug", "PlugType"), new NameComponent(ServerName, "PlugID")};
    org.omg.CORBA.Object Obj = namingContext.resolve(path);
    Server = EasyAppIDLHelper.narrow(Obj);

    //orb.destroy();////lm add 070703
    return 0;
  }
  catch (Exception ex)
  {
    orb.destroy();        //lm add
    ex.printStackTrace() ;
    ex.getMessage();
    return 1;
  }


   }

   public int sendFile(String FileName)
   {
  try
  {
          byte[] buf = new byte[MAXPKG];
          String path;
        path = System.getProperty( "FILEDIR" ) ;
          if (path == null)
             path = "./" + FileName;
            else
			 path = path + FileName;

          FileInputStream file = new FileInputStream(path);

          int fd = Server.openFile(FileName, 1);

          if (fd<3){
            file.close();
            return (2020003);
          }

        int len;
        while ( (len = file.read(buf)) != -1)
        {
        	if (len < MAXPKG)
        	{
				byte[] w_buf = new byte[len];
				System.arraycopy(buf,0,w_buf,0,len);
				Server.writeFile(fd, w_buf);
        	}
        	else
				Server.writeFile(fd, buf);

        }
        Server.closeFile(fd);
        file.close();
        return (0);
  }
  catch (Exception ex)
  {
        ex.printStackTrace() ;
        ex.getMessage();
        return (1);
  }

   }

   public int recvFile(String FileName)
   {
  try
  {
        byte[] buf = new byte[MAXPKG];
        String path;

        path = System.getProperty( "FILEDIR" ) ;
         if (path == null)
           path = "./" + FileName;
         else
           path = path + FileName;

        FileOutputStream file = new FileOutputStream(path);
        int fd = Server.openFile(FileName, 0);

        if (fd<3){
          file.close();//lm add
          return (2020003);
        }

        block_tHolder block = new block_tHolder(buf);

        while (Server.readFile(fd, block) != 0){
				file.write(block.value);
		}
        Server.closeFile(fd);
        file.close();
        return (0);
  }
  catch (Exception ex)
  {
        ex.printStackTrace() ;
        ex.getMessage();
        return (1);
  }
}

   public void clearData(){
     if(this.DownPkg.data != null){
         this.DownPkg.data.clear();
         this.DownPkg.data = null;
     }
     if(this.DownPkg.mns != null){
         this.DownPkg.mns.clear();
         this.DownPkg.mns = null;
     }

     if(this.UpPkg.data != null){
         this.UpPkg.data.clear();
         this.UpPkg.data = null;
     }
     if(this.UpPkg.mns != null){
         this.UpPkg.mns.clear();
         this.UpPkg.mns = null;
     }
   }
////TEST////TEST////TEST////TEST////TEST////TEST////TEST////TEST////TEST////TEST////TEST////TEST////
    public static void main(String[] args) {
      long fm = Runtime.getRuntime().freeMemory();
      long mm = Runtime.getRuntime().maxMemory();
      long tm = Runtime.getRuntime().totalMemory();
      long um = tm - fm;
      
      
      System.err.println("free memory["+fm+"]");
      System.err.println("max memory["+mm+"]");
      System.err.println("total memory["+tm+"]");
      System.err.println("use memory[" + um + "]");


      System.setProperty("FILEDIR", "f:\\temp\\0166\\");
      //for (int n = 0; n < 1000; n++) {
        //String SvrName = "gyyw";
        //String SvrName = "bbyw";
        String SvrName = "yygl";
        String Location = "192.168.20.144:9001";
        CEasyAppClient client = new CEasyAppClient();

        if (client.connServer(SvrName, Location) == 1){
          System.err.println("connServer错误 ");
          //continue;
        };

        client.UpPkg = new CEasyPackage();
        client.DownPkg = new CEasyPackage();

        client.UpPkg.addFilename("easymisup.txt");
        //client.UpPkg.addString("I080"," ","DEFAULT");
        //client.UpPkg.addString("JDRQ"," ","DEFAULT");
        //client.UpPkg.addString("S100"," ","DEFAULT");
        //client.UpPkg.addString("SQGY"," ","DEFAULT");
        //client.UpPkg.addString("JYRQ","18991231","DEFAULT");
        client.UpPkg.addString("TradeName", SvrName, "DEFAULT");
        client.UpPkg.addString("JYM", "70045", "DEFAULT");
        //client.UpPkg.addString("_SQM"," ","DEFAULT");
        client.UpPkg.addString("_gyh", "016616", "DEFAULT");
        client.UpPkg.addString("_hostseqno", " ", "DEFAULT");
        client.UpPkg.addString("_jgm", "0166", "DEFAULT");
        client.UpPkg.addString("_jym", "70045", "DEFAULT");
        client.UpPkg.addString("DKQX", "3", "DEFAULT");
        client.UpPkg.addString("JGM", "0113", "DEFAULT");
        client.UpPkg.addString("CUSTNO", "1000209342", "DEFAULT");
        client.UpPkg.addString("Operation", "DEFAULT", "DEFAULT");
        client.UpPkg.addString("_jgm", "0113", "DEFAULT");
        client.UpPkg.addString("_gyh", "0124", "DEFAULT");
        client.UpPkg.addString("DBFS", "1", "DEFAULT");
        client.UpPkg.addString("YWPZ", "010001", "DEFAULT");
        //client.UpPkg.addString("UPFILE","0166/TEST060301.dat","DEFAULT");
        //client.UpPkg.addFilename("0166/TEST060301.dat");

        client.callTrade();

        //String st_hostcode =  client.DownPkg.getString("_hostcode", "DEFAULT");
        //String st_errcode = client.DownPkg.getString("_errmsg", "DEFAULT");

        //String st_operlevel = client.DownPkg.getString("I020", "DEFAULT");
        //System.err.println("st_hostcode: " + st_hostcode);
        //System.err.println("st_errcode: " + st_errcode);


        //if(client.orb != null)
         //client.orb.destroy();
         client.DownPkg.data.clear();
         client.UpPkg.data.clear();
         client.DownPkg.mns.clear();
         client.UpPkg.mns.clear();
         client = null;
     // }
      try {
        Thread.currentThread().sleep(1000);
      }
      catch (InterruptedException ex) {
        ex.printStackTrace();
      }

      System.gc();
      try {
        Thread.currentThread().sleep(1000);
      }
      catch (InterruptedException ex) {
        ex.printStackTrace();
      }
      fm = Runtime.getRuntime().freeMemory();
      mm = Runtime.getRuntime().maxMemory();
      tm = Runtime.getRuntime().totalMemory();
      um = tm - fm;
      System.err.println("----------------------------\n");
      System.err.println("free memory["+fm+"]");
      System.err.println("max memory["+mm+"]");
      System.err.println("total memory["+tm+"]");
      System.err.println("use memory[" + um + "]");

    }
}
