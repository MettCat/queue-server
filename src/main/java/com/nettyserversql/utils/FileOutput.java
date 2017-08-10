package com.nettyserversql.utils;

/**获取文件中的字符串，并进行分割
 * Created by Administrator on 2017/6/22.
 */
public class FileOutput {


    /**
     * 文件读取
     * 读取记录sql语句信息的文件
     * 返回字符串数组
     *
     * @param
     */
    public String sqlIo(String str) {


        int index;

        //读取文件内容


        String sql= null;
        //得到数据


        //对数据进行处理
        //判断是否是sql insert
        if ((index = str.indexOf( "insert" )) != -1 && clearEeore( str, index )) {
            sql = str.substring( str.indexOf("insert" ));
            return sql;
        } else if ((index = str.indexOf( "delete" )) != -1 && clearEeore( str, index )) {//判断是否是sql delete
            sql =  str.substring( str.indexOf("delete" ));
            return sql;
        } else if ((index = str.indexOf( "update" )) != -1 && clearEeore( str, index )) {//判断是否是sql update
            sql = str.substring( str.indexOf("update" ));
            return sql;
        } else if ((index = str.indexOf( "call" )) != -1 && clearEeore( str, index )) {
            String str2 = call( str );
            sql = str2.substring( str.indexOf("call" ));
            return sql;
        } else {//当语句为空或语句为sql select 时候 将语句进行丢弃，不做处理
            return "";
        }


    }



    /**
     * 判断是否是有异常的sql信息
     * @param str
     * @param index
     * @return
     */
    public  boolean clearEeore(String str,int index){
        str = str.substring(0,index);
        if (str.length()>22){
            return false;
        }else {
            return true;
        }
    }
        /**
         * 对存储结构进行处理
         */
    public String call(String str){

        str = str.substring(str.indexOf("call"));
        str = str.replaceAll("call","exec");
        str = str.replaceAll("\\("," ");
        str = str.replaceAll("\\)"," ");
        str = str.replaceAll("}","");
        System.out.println("------------>"+str);
        return str;
        //str = str.replaceAll("call","exec");
        //System.out.println(str);
    }

    /**
     * 将其中的关键字进行大小写转换
     * @param str
     * @return
     */

    public  String toggleCase(String str){

        if (str.indexOf("INSERT") !=-1){
            str = str.replaceAll("INSERT","insert");
            return str;
        }else if (str.indexOf("UPDATE") !=-1){
            str = str.replaceAll("UPDATE","update");
            return str;
        }else if (str.indexOf("DELETE") !=-1){
            str = str.replaceAll("DELETE","delete");
            return str;
        }else if (str.indexOf("CALL") !=-1){
            str = str.replaceAll("CALL","call");
            return str;
        }else{
            return str;
        }

    }


}
