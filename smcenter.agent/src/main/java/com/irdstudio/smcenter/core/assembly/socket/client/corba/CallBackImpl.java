package com.irdstudio.smcenter.core.assembly.socket.client.corba;

import java.util.Vector;
import org.omg.CORBA.*;

class CallBackImpl extends CallBackPOA {
  public	CEasyClient worker;

  public CallBackImpl() {
  }

  public int put(byte msg[]) {
		try
		{
			int ret;
			String s=new String(msg);

      System.out.println("get reply message from server:\n" + s);
			worker.DownPkg.unpack(s);

			Vector itor;
			itor = worker.DownPkg.getFilenames();

			for (int i = 0 ; i<itor.size(); i++)
			{
				if ( (ret = worker.recvFile((String)itor.elementAt(i))) != 0)
            return ret;
			}

			synchronized(worker)
			{
				worker.CbCtrl = 1;
				worker.notify();
			}
			return 0;
  	}
		catch (Exception ex)
		{
			ex.printStackTrace();
			ex.getMessage();
			return 0;
		}
	}
}

