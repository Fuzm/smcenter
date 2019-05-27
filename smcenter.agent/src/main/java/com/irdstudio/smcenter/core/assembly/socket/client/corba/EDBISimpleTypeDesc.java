package com.irdstudio.smcenter.core.assembly.socket.client.corba;

import java.io.File;

public class EDBISimpleTypeDesc extends EDBITypeDesc{

  public int offset;
  public int scale;

  public EDBISimpleTypeDesc(int type) {

    super(type);
    scale = 0;
    offset = 0;
  }

public void setValue( String ptr ){
  data = ptr;
}

public String getValue (){
  return data;
}



public void print( File fp ){
//  if ( length > 0 && scale > 0 )
//          fprintf( fp, "%c[%d,%d]", edbi_type_char[type], length, scale );
//  else if ( length > 0 )
//          fprintf( fp, "%c[%d]", edbi_type_char[type], length );
//  else
//          fprintf( fp, "%c", edbi_type_char[type] );
}

public void print() {
}

}