package com.irdstudio.smcenter.core.assembly.socket.client.corba;

/**
* CallBackHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from EasyEai.idl
* Monday, March 15, 2004 8:32:43 PM CST
*/

public final class CallBackHolder implements org.omg.CORBA.portable.Streamable
{
  public CallBack value = null;

  public CallBackHolder ()
  {
  }

  public CallBackHolder (CallBack initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = CallBackHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    CallBackHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return CallBackHelper.type ();
  }

}