package com.nettyserversql.dao;

import com.nettyserversql.entity.SqlVo;
import com.nettyserversql.entity.TestMode;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2017/8/8.
 */

@Mapper
public interface SqlMapper {


    @Insert( "insert into test values( ${cityname},${stationnum}, ${pollutantcode},${pollutantnum})" )
    public int save(TestMode testMode);

    @Select("${sql}")
    public void appliquerSql(SqlVo sql);


}
