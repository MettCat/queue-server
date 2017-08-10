package com.nettyserversql;

import com.nettyserversql.entity.SqlVo;
import com.nettyserversql.entity.TestMode;
import com.nettyserversql.service.SqlService;
import com.nettyserversql.utils.FileOutput;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NettyserversqlApplicationTests {

	@Autowired
	private SqlService testService;

	FileOutput fileOutput = new FileOutput();

	@Test
	public void contextLoads() {
		TestMode testMode = new TestMode();
		testMode.setCityname("'成都'");
		testMode.setPollutantcode( "'co'" );
		testMode.setPollutantnum( 52 );
		testMode.setStationnum( 58 );
		testService.save( testMode );
	}

	@Test
	public void test1(){
		String str = "insert into test values( '成都1',58, 'co',52)";
		SqlVo sqlVo = new SqlVo();
		sqlVo.setSql(str);
		testService.appliquerSql( str );
	}

	@Test
	public void test2(){
		String str = fileOutput.sqlIo( "2017-07-17 14:42:13 update tbRole set IsVisible=0 where RoleID = 93 " );
		System.out.println(str);
	}

}
