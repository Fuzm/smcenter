package com.irdstudio.smcenter.core.assembly.socket.client.corba;

public class CEasyData {

 private String key;
 private EDBITypeDesc data;
 private String opt;
 private String ns;
 private int len;
 private int type;
 private boolean copy;

   protected static final int EASY_TYPE_SHORT = 1;
   protected static final int EASY_TYPE_USHORT = 2;
   protected static final int EASY_TYPE_INT = 3;
   protected static final int EASY_TYPE_UINT = 4;
   protected static final int EASY_TYPE_LONG = 5;
   protected static final int EASY_TYPE_ULONG = 6;
   protected static final int EASY_TYPE_FLOAT = 7;
   protected static final int EASY_TYPE_DOUBLE = 8;
   protected static final int EASY_TYPE_STRING = 9;
   protected static final int EASY_TYPE_STRUCT = 10;
   protected static final int EASY_TYPE_BIN = 11;
   protected static final int EASY_TYPE_USER = 12;

  public CEasyData() {

    key = null;
    data = null;
    opt = null;
    ns = null;
    setNS("DEFAULT");
    len = 0;
    type = 0;
    copy = true;
  }

  public CEasyData(String key){
    this();
    setKey(key);
  }

  public void setData(EDBITypeDesc data,int len,boolean copy){

      this.data = data;
      this.copy = copy;
      this.len = len;
  }

  public void setData(EDBITypeDesc data){
      this.data = data;
  }

  public EDBITypeDesc getData(){

    EDBIStructTypeDesc ps;

    if ( type != EASY_TYPE_STRUCT )
      return data;
    else
    {
      ps = ( EDBIStructTypeDesc )data;
      return ps;
    }

  }


 public void setKey( String key ){
    this.key = key;
  }

 public String getKey(){
   return this.key;
 }

 public void setType( int type ){
   this.type = type;
 }

 public int getType(){
   return this.type;
 }

 public void setOpt(String opt){
    this.opt = opt;
 }
 public String getOpt(){
   return opt;
 }

 public int getLength(){
   return this.len;
 }

 public void setNS( String ns )
 {
    this.ns = ns;
 }
 public String getNS(){
    return this.ns;
 }
}