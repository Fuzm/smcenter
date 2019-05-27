package com.irdstudio.smcenter.core.assembly.socket.client.corba;

import java.io.*;
import java.util.Vector;
import org.omg.CosNaming.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;

public final class CEasyClient
{
	public	ORB orb;
	public 	CEasyPackage DownPkg;
	public 	CEasyPackage UpPkg;
	public 	int	CbCtrl = 0;
	private EasyIDL Server;
	private	CallBack CbServant;
	final 	int MAXPKG = 32768;

  public CEasyClient()
  {
  }

	public int callServer(long nsec)
	{
		try
		{
			byte[] 	UpBuf;
			byte[] 	DownBuf = new byte[MAXPKG];
			block_tHolder DownBlock = new block_tHolder(DownBuf);

			int ret;
			Vector itor;

			// up load all files to server first

			itor = UpPkg.getFilenames();

			for (int i = 0; i< itor.size(); i++ )
			{
				ret = sendFile((String)itor.elementAt(i));
			}

			// Then send buffer up and get the reply buffer

			CbCtrl = 0;
			UpBuf = UpPkg.pack().getBytes();
			if ( (ret = Server.commit(UpBuf, CbServant)) != 0 )
      	return (ret);

			//now wait the respond
			synchronized(this)
			{
				if (CbCtrl == 0)
					try
					{
						wait(nsec * 1000);
					}
          catch (InterruptedException e)
          {
            CbCtrl = 0;
          }
			}

			if (CbCtrl == 1)
				return 0;
			else
				return 2020002;
  	}
		catch (Exception ex)
		{
			ex.printStackTrace() ;
			ex.getMessage();
			return 0;
		}
	}

  // ServerName is the objname, Location is like "xxx.xxx.xxx.xxx:port"

	public int connServer(String ServerName, String Location)
	{
		try
		{
			String[] ns = {new String("-ORBInitRef"), new String("NameService=corbaloc:iiop:1.2@" + Location + "/NameService")};

			orb = ORB.init(ns,null);
			org.omg.CORBA.Object namingContextObj = orb.resolve_initial_references("NameService");
			NamingContext namingContext = NamingContextHelper.narrow(namingContextObj);
			NameComponent[] path = { new NameComponent("InterPlug", "PlugType"), new NameComponent(ServerName, "PlugID")};
			org.omg.CORBA.Object Obj = namingContext.resolve(path);
			Server = EasyIDLHelper.narrow(Obj);

			POA RootPOA = (POA)orb.resolve_initial_references("RootPOA");
      RootPOA.the_POAManager().activate();

			CallBackImpl cb_servant = new CallBackImpl();
			cb_servant.worker = this;
      org.omg.CORBA.Object ref = RootPOA.servant_to_reference(cb_servant);
      CbServant = CallBackHelper.narrow(ref);
			return 0;
		}
		catch (Exception ex)
		{
			ex.printStackTrace() ;
			ex.getMessage();
			return 2010011;
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

          if (fd<3)
                  return (2020003);

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

        return (0);
  }
  catch (Exception ex)
  {
        ex.printStackTrace() ;
        ex.getMessage();
        return (-1);
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

        if (fd<3)
                return (2020003);

        block_tHolder block = new block_tHolder(buf);

        while (Server.readFile(fd, block) != 0){
          file.write(block.value);
        }
        Server.closeFile(fd);
        return (0);
  }
  catch (Exception ex)
  {
        ex.printStackTrace() ;
        ex.getMessage();
        return (1);
  }

   }

}
