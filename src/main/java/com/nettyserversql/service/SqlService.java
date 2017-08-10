package com.nettyserversql.service;

import com.nettyserversql.dao.SqlMapper;
import com.nettyserversql.entity.SqlVo;
import com.nettyserversql.entity.TestMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/8/8.
 */
@Service
public class SqlService {

    @Autowired
    private SqlMapper sqlMapper;

    public int save(TestMode testMode){
       int t=  sqlMapper.save( testMode );
       return t;
    }

    public Boolean appliquerSql(String str){
        //sql语句执行的状态进行判断
        SqlVo sqlVo = new SqlVo();
        sqlVo.setSql( str );
        try {
            sqlMapper.appliquerSql( sqlVo );
            return true;
        }catch (Exception e){
            //e.printStackTrace();
            return false;
        }
    }

}
