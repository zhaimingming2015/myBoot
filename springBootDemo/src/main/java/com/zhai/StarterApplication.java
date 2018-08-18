package com.zhai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import tk.mybatis.spring.annotation.MapperScan;

/**
 * 
 * git提交代码地址：
 * 本项目地址：https://github.com/zhaimingming2015/myBoot.git
 * 
 * 举例：
 * https://github.com/zhaimingming2015/third.git
 * @author ZHAIMINGMING
 *
 */

@SpringBootApplication
//扫描 mybatis mapper 包路径
@MapperScan(basePackages="com.zhai.mapper")
//扫描 所有需要的包, 包含一些自用的工具类包 所在的路径
@ComponentScan(basePackages={"com.zhai"})
public class StarterApplication {

	public static void main(String[] args) {
		SpringApplication.run(StarterApplication.class, args);
	}

}
