package com.irdstudio.smcenter.core.assembly.socket.client.corba;

/**
* block_tHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from EasyEai.idl
* Monday, March 15, 2004 8:32:43 PM CST
*/

public final class block_tHolder implements org.omg.CORBA.portable.Streamable
{
  public byte value[] = null;

  public block_tHolder ()
  {
  }

  public block_tHolder (byte[] initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = block_tHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    block_tHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return block_tHelper.type ();
  }

}
