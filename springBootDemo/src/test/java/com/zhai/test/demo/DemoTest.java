package com.zhai.test.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.zhai.common.utils.RedisOperator;


@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoTest {
	
	//@Autowired
	//private StringRedisTemplate stringRedisTemplate;
	
	@Autowired
	private RedisOperator redisOperator;
	
	
	@Test
	public void testDemo(){
		//stringRedisTemplate.opsForValue().set("uuid", "immoc20180519");
		String str = redisOperator.getValueByKey("uuid");
		//System.out.println(str);
		long timeTolive = redisOperator.timeTolive("uuid");
		
		System.out.println(timeTolive/3600);
	}
	
	
	@Test
	public void testPath(){
		String path = System.getProperty("java.io.tmpdir");
		
		System.out.println("java.io.tmpdir:"+path);
		//path=C:\Users\ZHAIMI~1\AppData\Local\Temp\
	}

}
