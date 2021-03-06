package com.irdstudio.smcenter.core.assembly.socket.client.corba;

/**
* CallBackPOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from EasyEai.idl
* Monday, March 15, 2004 8:32:43 PM CST
*/

public abstract class CallBackPOA extends org.omg.PortableServer.Servant
 implements CallBackOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("put", new java.lang.Integer (0));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {
       case 0:  // CallBack/put
       {
         byte msg[] = block_tHelper.read (in);
         int $result = (int)0;
         $result = this.put (msg);
         out = $rh.createReply();
         out.write_long ($result);
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:CallBack:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public CallBack _this()
  {
    return CallBackHelper.narrow(
    super._this_object());
  }

  public CallBack _this(org.omg.CORBA.ORB orb)
  {
    return CallBackHelper.narrow(
    super._this_object(orb));
  }


} // class CallBackPOA
