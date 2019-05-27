package com.irdstudio.smcenter.core.assembly.socket.client.corba;

/**
* _EasyIDLStub.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from EasyEai.idl
* Monday, March 15, 2004 8:32:43 PM CST
*/

public class _EasyIDLStub extends org.omg.CORBA.portable.ObjectImpl implements EasyIDL
{

  public int commit (byte[] msg, CallBack cb)
  {
    org.omg.CORBA.portable.InputStream $in = null;
    try {
       org.omg.CORBA.portable.OutputStream $out = _request ("commit", true);
       block_tHelper.write ($out, msg);
       CallBackHelper.write ($out, cb);
       $in = _invoke ($out);
       int $result = $in.read_long ();
       return $result;
    } catch (org.omg.CORBA.portable.ApplicationException $ex) {
       $in = $ex.getInputStream ();
       String _id = $ex.getId ();
       throw new org.omg.CORBA.MARSHAL (_id);
    } catch (org.omg.CORBA.portable.RemarshalException $rm) {
       return commit (msg, cb);
    } finally {
        _releaseReply ($in);
    }
  } // commit

  public int openFile (String filename, int mode)
  {
    org.omg.CORBA.portable.InputStream $in = null;
    try {
       org.omg.CORBA.portable.OutputStream $out = _request ("openFile", true);
       $out.write_string (filename);
       $out.write_long (mode);
       $in = _invoke ($out);
       int $result = $in.read_long ();
       return $result;
    } catch (org.omg.CORBA.portable.ApplicationException $ex) {
       $in = $ex.getInputStream ();
       String _id = $ex.getId ();
       throw new org.omg.CORBA.MARSHAL (_id);
    } catch (org.omg.CORBA.portable.RemarshalException $rm) {
       return openFile (filename, mode);
    } finally {
        _releaseReply ($in);
    }
  } // openFile

  public int readFile (int fd, block_tHolder buf)
  {
    org.omg.CORBA.portable.InputStream $in = null;
    try {
       org.omg.CORBA.portable.OutputStream $out = _request ("readFile", true);
       $out.write_long (fd);
       $in = _invoke ($out);
       int $result = $in.read_long ();
       buf.value = block_tHelper.read ($in);
       return $result;
    } catch (org.omg.CORBA.portable.ApplicationException $ex) {
       $in = $ex.getInputStream ();
       String _id = $ex.getId ();
       throw new org.omg.CORBA.MARSHAL (_id);
    } catch (org.omg.CORBA.portable.RemarshalException $rm) {
       return readFile (fd, buf);
    } finally {
        _releaseReply ($in);
    }
  } // readFile

  public int writeFile (int fd, byte[] buf)
  {
    org.omg.CORBA.portable.InputStream $in = null;
    try {
       org.omg.CORBA.portable.OutputStream $out = _request ("writeFile", true);
       $out.write_long (fd);
       block_tHelper.write ($out, buf);
       $in = _invoke ($out);
       int $result = $in.read_long ();
       return $result;
    } catch (org.omg.CORBA.portable.ApplicationException $ex) {
       $in = $ex.getInputStream ();
       String _id = $ex.getId ();
       throw new org.omg.CORBA.MARSHAL (_id);
    } catch (org.omg.CORBA.portable.RemarshalException $rm) {
       return writeFile (fd, buf);
    } finally {
        _releaseReply ($in);
    }
  } // writeFile

  public int closeFile (int fd)
  {
    org.omg.CORBA.portable.InputStream $in = null;
    try {
       org.omg.CORBA.portable.OutputStream $out = _request ("closeFile", true);
       $out.write_long (fd);
       $in = _invoke ($out);
       int $result = $in.read_long ();
       return $result;
    } catch (org.omg.CORBA.portable.ApplicationException $ex) {
       $in = $ex.getInputStream ();
       String _id = $ex.getId ();
       throw new org.omg.CORBA.MARSHAL (_id);
    } catch (org.omg.CORBA.portable.RemarshalException $rm) {
       return closeFile (fd);
    } finally {
        _releaseReply ($in);
    }
  } // closeFile

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:EasyIDL:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }

  private void readObject (java.io.ObjectInputStream s) throws java.io.IOException
  {
     String str = s.readUTF ();
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.Object obj = org.omg.CORBA.ORB.init (args, props).string_to_object (str);
     org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl) obj)._get_delegate ();
     _set_delegate (delegate);
  }

  private void writeObject (java.io.ObjectOutputStream s) throws java.io.IOException
  {
     String[] args = null;
     java.util.Properties props = null;
     String str = org.omg.CORBA.ORB.init (args, props).object_to_string (this);
     s.writeUTF (str);
  }
} // class _EasyIDLStub