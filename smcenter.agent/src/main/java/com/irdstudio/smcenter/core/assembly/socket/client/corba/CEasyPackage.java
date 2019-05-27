package com.irdstudio.smcenter.core.assembly.socket.client.corba;

import java.util.*;

public class CEasyPackage {

  protected static final int EASY_PACK_HEADER_LEN = 10;
  protected String  value = null;
  protected HashMap data, mns  ;
  protected int zlevel;

  public static final int FIELDINDEX_NS = 0;
  public static final int FIELDINDEX_KEY = 1;

  public CEasyPackage() {

    data = new HashMap();
    mns = new HashMap();
    String s = "DEFAULT";
    mns.put(s,s);
    zlevel = 0;
}

public Set getKey(){
   if(data == null) return null;
   return data.keySet();
}

 /**
  * <p>得到包中所有的KEY信息的集合，其中集合中每个元素为一个字符串数组</p>
  * <p>数组的第0{@link FIELDINDEX_NS}位为 NS, 第1{@link FIELDINDEX_KEY}位为 KEY</p>
  * @return Collection 包中所有KEY信息的集合
  */
 public Collection getFields(){
  Collection co_return = null;
  if( this.getKey() != null ){
    co_return = new ArrayList();

    for(Iterator itr= this.getKey().iterator(); itr.hasNext();){

        String st_v = (String)itr.next();
        int index = st_v.indexOf(":");
        String st_ns = st_v.substring(0,index);
        String st_key =  st_v.substring(index + 1);

        co_return.add(new String[]{st_ns,st_key});
    }
  }
  return co_return;
}

/**
  * 判断给定关键字的Item是否存在
  */
public boolean exists( String key, String NS ){

  CEasyData d = getItem( key,NS );
  if( d == null)
    return false;
  return true;
}

/**
  * 设置压缩比
  */
public void setZipLevel( short l ){

  if ( l < 0 || l > 9 )
    return ;
  zlevel = l;

}
/**
  * 实现了NS重新命名
  */
public void setNS( String key, String NS, String newNS ){

  CEasyData d = getItem( key, NS );
  if ( d == null) return;
  if( newNS == null )
    d.setNS("DEFAULT");
  else
    d.setNS(newNS);
  mns.put(newNS,newNS);
}


//取出结构:
//====================================================================================
  public String getStruct( String key, int len,String NS ){

  EDBIStructTypeDesc ps;
  EDBISimpleTypeDesc t;
  CEasyData d;
  String p = "";

  d = getItem( key, NS );
  if ( d == null )
    return null;
  if ( d.getType() != CEasyData.EASY_TYPE_STRUCT )
    return null;

  ps = (EDBIStructTypeDesc)d.getData();
  Vector v = ps.types;
      for( int i = 0;i < v.size() ;i ++){
            t = (EDBISimpleTypeDesc)v.elementAt(i);
            p = t.getValue();
      }
//  if ( ps->at(0)->getValue( p, len ) == false )
//    return EASY_ERROR;
  return p;
}
//==========================================================================================
public void setOpt( String key, String opt,String NS ){

  CEasyData d = getItem( key, NS );
  if ( d == null )
    return;
  d.setOpt(opt);
}

 public String genKey(String key,String NS){
   if (NS == null) NS = "DEFAULT";
   return  NS + ":" + key;
 }

 public void  addShort ( String key, int val, String NS ){

   EDBISimpleTypeDesc desc = new EDBISimpleTypeDesc(CEasyData.EASY_TYPE_SHORT);
   desc.setValue(String.valueOf(val));
   setItem( key, CEasyData.EASY_TYPE_SHORT, desc,4/*sizeof(short)*/, null, NS );
 }

 public void addInt ( String key, int val, String NS ){

   EDBISimpleTypeDesc desc = new EDBISimpleTypeDesc(CEasyData.EASY_TYPE_INT);
   desc.setValue(String.valueOf(val));
   setItem( key, CEasyData.EASY_TYPE_INT, desc,
                    4/*sizeof(short)*/, null, NS );
 }

 public void addLong( String key, long val, String NS )
 {
   EDBISimpleTypeDesc desc = new EDBISimpleTypeDesc(CEasyData.EASY_TYPE_LONG);
   desc.setValue(String.valueOf(val));
   setItem( key, CEasyData.EASY_TYPE_LONG, desc,
                    4/*sizeof(short)*/, null, NS );
 }
 public void addFloat( String key, float val, String NS )
{
   EDBISimpleTypeDesc desc = new EDBISimpleTypeDesc(CEasyData.EASY_TYPE_FLOAT);
   desc.setValue(String.valueOf(val));
   setItem( key, CEasyData.EASY_TYPE_FLOAT, desc,
                    4/*sizeof(short)*/, null, NS );
}
 public void addDouble( String key, double val, String NS )
{
   EDBISimpleTypeDesc desc = new EDBISimpleTypeDesc(CEasyData.EASY_TYPE_FLOAT);
   desc.setValue(String.valueOf(val));
   setItem( key, CEasyData.EASY_TYPE_DOUBLE, desc,
                    8/*sizeof(short)*/, null, NS );
}

/**
  * 参数 fmt: 输入格式字符串;格式为:
  * 格式字符串：第一个字符定义如下：i,d,c,S 等表示类型
  */
public void addStruct( String key, String fmt, String p, int len, String NS )
{
/**
  * 执行之后己将结构的类型放入到EDBIStructTypeDesc类的type向量中，
  * 而结构的值在下面放进
  */
  EDBIStructTypeDesc stc = new EDBIStructTypeDesc(fmt);
  EDBISimpleTypeDesc ptc;
  EDBIStructTypeDesc tc = null;
  String ptr;
  int i;


  for ( i=0; i<stc.getSize(); i++ )
    {

      ptc = (EDBISimpleTypeDesc)stc.at(i);
      ptc.setValue(p);

    }
    setItem( key, CEasyData.EASY_TYPE_STRUCT, stc, stc.getSize(),null, NS );

}


public void setItem(String key,int type,EDBITypeDesc val,int len,
                      String opt,String NS){

   CEasyData d = new CEasyData();
   String str;

   if ( key == null || val == null || len == 0 )
     return ;

    str = genKey( key, NS );
    d.setKey(key);
    d.setType(type);
    d.setData( val, len, true );

    if ( opt != null) d.setOpt(opt);

    if ( NS != null){
       d.setNS( NS );
       mns.put(NS,NS);
  }
    data.put(str,d);
}

public CEasyData getItem( String key, String NS){
  return (CEasyData)data.get(genKey(key,NS));
}

public Vector getNS(){
  Vector ee = new Vector();
   for ( Iterator iter = mns.keySet().iterator(); iter.hasNext(); )
     ee.add(iter.next());
  return ee;
}
public void addFilename( String filename ){
   addString(filename, filename, "EASY_PACK_FILENAME_NS" )/*( "EASY_PACK_FILENAME_NS") */;
}
public Vector getFilenames(){

  Vector ee = new Vector();
 String ptr = null;
  ptr = "EASY_PACK_FILENAME_NS" ;
  if ( ptr == null )
    ptr = "EASY_PACK_FILENAME_NS";
   for ( Iterator it =data.values().iterator(); it.hasNext();)
     {

              CEasyData tmp = (CEasyData)it.next();

/**
                   if( ptr.equals(tmp.getNS()))
                            ee.add(tmp.getKey());
*/
              if( tmp.getNS().indexOf(ptr) == 0)
                            ee.add(tmp.getKey());
    }

        return ee;
}
//根据命名空间找ITEM
public Vector getItemsByNS( String NS){

  Vector ee = new Vector();
  String ptr;
  if( NS == null )  ptr = "DEFAULT";
  else ptr = NS;

for( Iterator iter = data.values().iterator(); iter.hasNext(); ){

    CEasyData temp = (CEasyData)iter.next();
    String s = temp.getNS();

    if(ptr.equals(s)){
       ee.add(temp);
    }
  }
  return ee;
}
/** 打包方法 */
public String pack(){

  Vector nee = new Vector();
  Vector iee = new Vector();

  CEasyData d = new CEasyData();
  String line,p,tmp = "",buf;

  byte[] zbuf;
  long zlen;
  buf = "";

 nee = getNS();

  for(Iterator iter = nee.iterator();iter.hasNext(); ){
    String val = (String)iter.next();
  //取出命名空间，其中一个是默认的

    buf = buf + "%%";
    buf = buf + val;
    buf = buf + "\n";

    iee = getItemsByNS(val);


      for( Iterator it = iee.iterator(); it.hasNext(); ){
        line = "";
        d = (CEasyData)it.next();
             line = line + d.getKey();
        line = line + "@";
        tmp = d.getType() + "@";
        line = line + tmp;

        if ( d.getType() != CEasyData.EASY_TYPE_STRUCT )
           tmp = d.getLength() + "@";

          else
            {
            EDBIStructTypeDesc ps = (EDBIStructTypeDesc)d.getData();
            tmp = d.getLength() + "@";
            //tmp = ps.getFormat();
            //tmp.charAt(tmp.indexOf(tmp.length() -1));
	    }
        line = line + tmp;
//        tmp = d.getLength() + "@";
//        line = line + tmp;
        if( d.getOpt() != null )
          tmp = d.getOpt() + "@";
        else
          tmp = "@";
        line = line + tmp;
        line = genItemLine(line,d.getType(),d);

        buf = buf + line;
        buf = buf + "\n";
      }
      //构造包头为10个字节
  }
  String t1  = String.valueOf(buf.getBytes().length);
  String t2 = "";
  for ( int i = 0,len = 9 - t1.length(); i< len ; i++){
    t2 = t2 + "0";
    tmp = "0" + t2 + t1;
  }
  buf = tmp + buf;
  return buf;
}

//解包
public void unpack ( String s){

  String m = "DEFAULT";
  int rtn, pos, zl;
  String header , s1;
  byte[] zbuf, zbufs;
  int len;

  if ( s.length() < EASY_PACK_HEADER_LEN )
    return;

  mns.put(m,m);
  header = s.substring(0,EASY_PACK_HEADER_LEN);
  zl = header.charAt(0) - '0';
  s1 = s.substring(1,EASY_PACK_HEADER_LEN);
  len = Integer.valueOf(s1).intValue();


  pos = EASY_PACK_HEADER_LEN;
  if ( zl == 0 )
    {
    rtn = processUnpack( s, s.length(), pos );
    }
    else
      {
     //  zbufs = new byte[len];
     //  zbufs = s.substring(EASY_PACK_HEADER_LEN, len).getBytes();
     //  len = len * 8;
     //  zbuf = new byte[len];
     //  pos = 0;
        //rtn = processUnpack( (const char *)zbuf, (int)len, pos );
      }
    zl = zlevel;


}

public void skipWS( String buf, int len, int pos )
{

  while ( pos < len )
     {
       if ( buf.charAt(pos) == ' ' || buf.charAt(pos) == '\t' || buf.charAt(pos) == '\n' )
         pos++;
       else
         break;
     }
  return ;
}

public int processNSItem( String ns, String buf, int len, int pos){
//addString( String key, String val, String NS )

  String s;
  int count = 0;

  while( pos < len -1 )
    {
      //skipWS( buf, len ,pos );
     CEasyData d = new CEasyData();

      if ( buf.charAt(pos) == '%' && pos < len-1 && buf.charAt(pos + 1) == '%' )
        break;

      d.setNS( ns );
      pos = processNSItemKey( buf, len , pos, d );

      pos = processNSItemType( buf, len, pos, d );

      pos = processNSItemLength( buf, len, pos, d );
      pos = processNSItemOpt( buf, len, pos, d );
      pos = processNSItemValue( buf, len ,pos, d );
      String str = genKey(d.getKey().trim(), ns.trim() );
      data.put(str,d);
      count++;
      skipWS( buf, len, pos );


  }
    return count;
}

public int processNSItemKey( String buf, int len, int pos,CEasyData d ){

  char key[] = new char[128];
  int i, j;
  i = 0;
  skipWS( buf, len ,pos );
  j = pos;
  while( pos < len )
    {
    if ( buf.charAt(pos) == '@' )
        {
          pos ++;

          break;
        }
      key[i++] = buf.charAt(pos++);

    }
  d.setKey( String.valueOf(key).trim());
  return pos;
}

public int processNSItemType( String buf, int len ,int pos,CEasyData d ){

  char st[] = new char[16];
  int i, j;
  i = 0;
  skipWS( buf, len, pos );

  while( pos < len )
    {

      if ( buf.charAt(pos) == '@' )
        {
          pos++;
          break;
        }

      st[i++] = buf.charAt(pos ++);
    }
  d.setType( Integer.parseInt(String.valueOf(st).trim()) );
return pos;
}

public int processNSItemLength( String buf,int len,int pos,CEasyData d ){

  char sl[] = new char[128];
  int i, j;
  i = 0;
  skipWS( buf, len, pos );

  while( pos < len )
    {
      if ( buf.charAt(pos) == '@' )
        {
          pos++;
          break;
        }
      sl[i++] = buf.charAt(pos ++);
    }

  if ( d.getType() == CEasyData.EASY_TYPE_STRUCT )
    {
    EDBIStructTypeDesc ps = new EDBIStructTypeDesc();
      d.setData(ps,0,false);
      ps.parse();
    //EDBITypeCharParser *ps;

      //ps = new EDBITypeCharParser;
      //d->setData( (const char *)ps, sizeof(EDBITypeCharParser*), false );

      //ps->parse( sl );
    }
    return pos;
}

public int processNSItemOpt( String buf, int len ,int pos, CEasyData d ){

  char opt[] = new char[256];
  int i, j;

  i = 0;

  while( pos < len )
    {
      if ( buf.charAt(pos) == '@' )
        {
          pos++;
          break;
        }
      opt[i++] = buf.charAt(pos ++);
    }
    d.setOpt(String.valueOf(opt));
return pos;
}

public int processUnpack( String buf, int len ,int pos ){

  int i,j, count;
  count = 0;

  while ( pos < len )//pos从第10个字节的位置开始算起，，位置小于包长度
    {
     char ns[] = new char[128];
     skipWS( buf, len, pos );

     if ( buf.charAt(pos) == '%' && pos < len - 1 && buf.charAt(pos + 1) == '%' )
        {
          pos+=2;
          i = 0;

          while( pos < len && i< ns.length && buf.charAt(pos) != '\n' ){
            ns[i++] = buf.charAt(pos ++);

         }
          if ( pos == len )
            break;
          pos++;

          mns.put(String.valueOf(ns),String.valueOf(ns));
          count += processNSItem(  String.valueOf(ns), buf, len, pos );
        }
     else
       pos++;
    }

  return count;
}

public String  genItemLine( String line, int type, CEasyData d ){
    String buf = "";
    EDBIStructTypeDesc stc;
    EDBISimpleTypeDesc ptc;
    EDBISimpleTypeDesc ed;

    switch( type )
     {

      case CEasyData.EASY_TYPE_SHORT:

       ed = (EDBISimpleTypeDesc)d.getData();
       buf = "<!--" + ed.getValue() + "-->";
            break;
      case CEasyData.EASY_TYPE_USHORT:

       ed = (EDBISimpleTypeDesc)d.getData();
       buf = "<!--" + ed.getValue() + "-->";
            break;
      case CEasyData.EASY_TYPE_INT:

       ed = (EDBISimpleTypeDesc)d.getData();
       buf = "<!--" + ed.getValue() + "-->";
            break;
      case CEasyData.EASY_TYPE_UINT:

       ed = (EDBISimpleTypeDesc)d.getData();
       buf = "<!--" + ed.getValue() + "-->";
            break;
      case CEasyData.EASY_TYPE_LONG:

       ed = (EDBISimpleTypeDesc)d.getData();
       buf = "<!--" + ed.getValue() + "-->";
            break;
      case CEasyData.EASY_TYPE_ULONG:

       ed = (EDBISimpleTypeDesc)d.getData();
       buf = "<!--" + ed.getValue() + "-->";
            break;
      case CEasyData.EASY_TYPE_FLOAT:

       ed = (EDBISimpleTypeDesc)d.getData();
       buf = "<!--" + ed.getValue() + "-->";
            break;
      case CEasyData.EASY_TYPE_DOUBLE:

       ed = (EDBISimpleTypeDesc)d.getData();
       buf = "<!--" + ed.getValue() + "-->";
            break;
      case CEasyData.EASY_TYPE_STRING:
        ed = (EDBISimpleTypeDesc)d.getData();
        buf = "<!--" + ed.getValue() + "-->";
             break;
     case CEasyData.EASY_TYPE_BIN:
     case CEasyData.EASY_TYPE_STRUCT:

     stc = ( EDBIStructTypeDesc )d.getData();

        for ( int i = 0,len = stc.types.size(); i < len; i++){
           String vline = "";
           ptc = (EDBISimpleTypeDesc)stc.types.elementAt(i);
           vline = "<!--" + ptc.getValue() + "-->";
          //  CEasyData dd = new CEasyData();
          //  dd.setData(ptc);
           //System.out.println("sdfadf");
           // vline = vline + genItemLine( vline, dd.getType(), dd );
           line = line + vline;
        }
       break;
     }
   line = line + buf;
   return line;
}

 public int  processNSItemSingleValue( String buf, int len, int pos,
                                               int blen)
{
  int i, j;
  String p;
  String value = "";
  skipWS( buf, len ,pos );

  if ( blen < 100 )

    while( pos < len )
    {
      if ( buf.charAt( pos ) == '<' && pos < len - 4 && buf.charAt( pos + 1 ) =='!' && buf.charAt( pos + 2) =='-' &&
           buf.charAt( pos + 3 ) =='-' )
        {
          pos += 4;

          while( pos < len )
            {
              if ( buf.charAt( pos ) =='-' && pos < len-3 && buf.charAt( pos + 1 ) =='-' && buf.charAt( pos + 2) =='>' )
                {
                  pos += 3;
                  break;
                }
              value = value + buf.charAt( pos++);
            }

          break;
        }
      else
        pos++;
    }
  else//
    {
     p = buf.substring(buf.indexOf("<!--"));
     if ( p == null )
        return 0;
      i = buf.length() - p.length();
      pos = i + 4;

      p = buf.substring(buf.indexOf("-->"));
      if ( p == null )
        return 0;
      j = buf.length() - p.length();
      value = buf.substring(i,j);
      pos = j + 3;
    }

 this.value = value;
  return pos;
}

public int processNSItemValue( String buf, int len, int pos,
                                        CEasyData d )
{
  //String value = null;

  if ( d.getType() != CEasyData.EASY_TYPE_STRUCT )
  {
    pos = processNSItemSingleValue( buf, len, pos, d.getLength());

    EDBISimpleTypeDesc ed;

    switch( d.getType() )
      {
      case CEasyData.EASY_TYPE_SHORT:
        ed = new EDBISimpleTypeDesc(CEasyData.EASY_TYPE_SHORT);
        ed.setValue(value);
        d.setData(ed);
        break;
      case CEasyData.EASY_TYPE_USHORT:
        ed = new EDBISimpleTypeDesc(CEasyData.EASY_TYPE_USHORT);
        ed.setValue(value);
        d.setData(ed);
        break;
      case CEasyData.EASY_TYPE_INT:
        ed = new EDBISimpleTypeDesc(CEasyData.EASY_TYPE_INT);
        ed.setValue(value);
        d.setData(ed);
        break;
      case CEasyData.EASY_TYPE_UINT:
        ed = new EDBISimpleTypeDesc(CEasyData.EASY_TYPE_UINT);
        ed.setValue(value);
        d.setData(ed);
        break;
      case CEasyData.EASY_TYPE_LONG:
        ed = new EDBISimpleTypeDesc(CEasyData.EASY_TYPE_LONG);
        ed.setValue(value);
        d.setData(ed);
        break;
      case CEasyData.EASY_TYPE_ULONG:
        ed = new EDBISimpleTypeDesc(CEasyData.EASY_TYPE_ULONG);
        ed.setValue(value);
        d.setData(ed);
        break;
      case CEasyData.EASY_TYPE_FLOAT:
        ed = new EDBISimpleTypeDesc(CEasyData.EASY_TYPE_FLOAT);
        ed.setValue(value);
        d.setData(ed);
        break;
      case CEasyData.EASY_TYPE_DOUBLE:
        ed = new EDBISimpleTypeDesc(CEasyData.EASY_TYPE_DOUBLE);
        ed.setValue(value);
        d.setData(ed);
        break;
      case CEasyData.EASY_TYPE_STRING:
        ed = new EDBISimpleTypeDesc(CEasyData.EASY_TYPE_STRING);
        ed.setValue(value);
        d.setData(ed);
        break;

      case CEasyData.EASY_TYPE_BIN:

        /*for ( i=0, j=0; i<value.length(); )
          {
            if ( value[i] == '\n' || value[i] == ' ' || value[i] == '\t' )
              {
                i++;
                continue;
              }
            by[0] = value[i++];
            by[1] = value[i++];
            by[2] = 0;
            p[j++] = (char)atoi(by);
          }*/

 //       d->setData( p, len );
 //       delete[] p;
        break;
}
}

return pos;
}

public  void addString( String key, String val, String NS ){

     EDBISimpleTypeDesc desc = new EDBISimpleTypeDesc(CEasyData.EASY_TYPE_STRING);
     desc.setValue(String.valueOf(val));
     setItem( key, CEasyData.EASY_TYPE_STRING, desc, val.getBytes().length, null, NS );
}

public long getLong( String key, String NS )
{
  CEasyData  d;
  d = getItem( key, NS );

  String s =d.getData().data;
  return Long.valueOf(s).longValue();
}

public String getString( String key, String NS )
{

  CEasyData  d;
  d = getItem( key, NS );
  String s = d.getData().data;
  return s;
}

}
