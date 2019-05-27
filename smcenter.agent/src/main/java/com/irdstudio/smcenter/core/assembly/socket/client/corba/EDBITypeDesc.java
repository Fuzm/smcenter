package com.irdstudio.smcenter.core.assembly.socket.client.corba;

import java.io.File;


public abstract class EDBITypeDesc {

  protected static final int EDBI_TYPE_UNDEF = 1;
  protected static final int EDBI_TYPE_NUM = 2;
  protected static final int EDBI_TYPE_INT = 3;
  protected static final int EDBI_TYPE_SHORT = 4;
  protected static final int EDBI_TYPE_CHAR = 5;
  protected static final int EDBI_TYPE_VCHAR = 6;
  protected static final int EDBI_TYPE_LONG = 7;
  protected static final int EDBI_TYPE_DBL = 8;
  protected static final int EDBI_TYPE_FLT = 9;
  protected static final int EDBI_TYPE_REL = 10;
  protected static final int EDBI_TYPE_BLOB = 11;
  protected static final int EDBI_TYPE_CLOB = 12;
  protected static final int EDBI_TYPE_DATE = 13;
  protected static final int EDBI_TYPE_TIME = 14;
  protected static final int EDBI_TYPE_TS = 15;
  protected static final int EDBI_TYPE_STRU = 16;
  protected char edbi_type_char[] = {
                  '\0',	/* Nothing */
                  'U',	/* Undefined type */
                  'n',	/* Numeric */
                  'I',	/* Integer */
                  'i',	/* Small integer */
                  'c',	/* Char */
                  'v',	/* Var char */
                  'l',	/* Decimal */
                  'd',	/* Double */
                  'f',	/* Floate */
                  'r',	/* Real */
                  'B',	/* Blob */
                  'C',	/* Clob */
                  'R',	/* Date */
                  'T',	/* Time */
                  'M',	/* Timestamp */
                  'S',	/* Struct */
                  'E'		/* End */
	};
  protected int type;
  protected int length;
  protected String data;

 public EDBITypeDesc(){

   type = EDBI_TYPE_UNDEF;
   data = null;
   length = 0;

 }
public EDBITypeDesc( int type ){
  this.type = type;
  data = null;
  length = 0;
}
abstract void	print();
abstract void	print( File fp);

public void setValue( String data, int len ){
}
public void getValue( String data, int len ){
}

}