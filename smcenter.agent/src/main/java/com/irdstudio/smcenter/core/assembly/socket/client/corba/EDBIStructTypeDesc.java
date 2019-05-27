package com.irdstudio.smcenter.core.assembly.socket.client.corba;

import java.util.*;
import java.io.File;

public class EDBIStructTypeDesc extends EDBITypeDesc{

  protected Vector items;
  protected int pos;
  protected String error;
  protected String format;
  protected Vector types = new Vector();
  EDBISimpleTypeDesc	desc = null;

  public EDBIStructTypeDesc() {
    type = EDBI_TYPE_STRU;

    error = "";
    format = null;
    pos = 0;
  }
  public EDBIStructTypeDesc(String fmt) {
    type = EDBI_TYPE_STRU;
    error = "";
    format = fmt;
    pos = 0;
    parse();
  }
  public int getSize(){
  // EDBIStructTypeDesc st = (EDBIStructTypeDesc)at(0);
   return types.size();
  }


  public void add( EDBISimpleTypeDesc desc ){
    items.add( desc );
  }

 public EDBITypeDesc at ( int pos ){
   if ( pos >= types.size() )
     return null;
   return (EDBITypeDesc)types.elementAt(pos);
 }


  public void print( File fp ){
    int	i;

    //fprintf( fp, "S[" );
    //for ( i=0; i<items.size(); i++ )
    //       items[i]->print( fp );
    //fprintf( fp, "]" );

  }
  public String getValue(){

    String pData = "";
    for ( int i = 0,len = types.size(); i< len; i++ ) {

      EDBISimpleTypeDesc tc = (EDBISimpleTypeDesc)types.elementAt(i);
      String p = tc.getValue();
      pData = pData + p;
    }
    return pData;
  }

  public String getFormat(){
    return format;
  }

  public int getTypeDesc(){

    char tc[] = new char[16];
    int len = format.length();
    int p = pos,i,j;
    int result;

    EDBITypeDesc ds = null;
    EDBIStructTypeDesc	sds = null;

System.out.println("format.chaAt[p] = " + format.charAt(p));
    switch( format.charAt(p) ) {
      case 'E':
        return 0;
      case 'I':
        desc = new EDBISimpleTypeDesc( EDBI_TYPE_INT );
        break;
      case 'l':
      case 'L':
        desc = new EDBISimpleTypeDesc( EDBI_TYPE_LONG );
        break;
      case 'i':
        desc = new EDBISimpleTypeDesc( EDBI_TYPE_SHORT );
        break;
      case 'd':
        desc = new EDBISimpleTypeDesc( EDBI_TYPE_DBL );
        break;
      case 'f':
        desc = new EDBISimpleTypeDesc( EDBI_TYPE_FLT );
        break;
      case 'c':
      case 'v':
             if ( format.charAt(p) == 'c' )
                 desc = new EDBISimpleTypeDesc( EDBI_TYPE_CHAR );
             else
                 desc = new EDBISimpleTypeDesc( EDBI_TYPE_VCHAR );

             p++;
             if( format.charAt(p) == '[' ) {
                   i = 0;
                   p++;

                   while(( p< len)  && ( format.charAt(p) != ']' ))
                   tc[i++] = format.charAt(p++);
                   desc.length = Integer.parseInt(String.valueOf(tc));
                }
               else {
                   p--;
                   desc.length = 1;
               }
               break;

       case 'S':
             p++;

             if ( format.charAt(p) != '[' ) {
               System.out.println("格式错误!");
               return -1;
             }
             p++;

             sds = new EDBIStructTypeDesc();
             pos = p;

             while( true ) {
                        result = getTypeDesc();

                        if ( result == 0 || result < 0 ) {
                                return -1;
                        }
                        sds.add((EDBISimpleTypeDesc)desc);

                        if ( result == ']' ) {
                                p = pos;
                                break;
                        }
                }

             desc = (EDBISimpleTypeDesc)ds;
             break;
      case 'n':
      case 'r':
      case 'B':
      case 'C':
      case 'R':
      case 'T':
      case 'M':
      default:
        desc = null;
        return -1;
    }
    p++;
    pos = p;
    return p;
  }

  //解析方法
public boolean parse(){

  EDBIStructTypeDesc  tc = null;
  int	offset = 0, i, ll, lr;
  int result;
  this.format = format + "E";
  pos = 0;

  while( true) {

    result = this.getTypeDesc();
          if ( result == 0 )
                  break;
          if ( result < 0 )
                  return false;
          types.add(desc);
  }
          return true;
}

  public void print(){
  }
}