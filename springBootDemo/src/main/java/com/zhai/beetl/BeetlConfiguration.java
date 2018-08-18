package com.zhai.beetl;

import org.beetl.ext.spring.BeetlGroupUtilConfiguration;

public class BeetlConfiguration extends BeetlGroupUtilConfiguration {

	@Override
	protected void initOther() {
		System.out.println("BeetlConfiguration====initOther---");
		groupTemplate.registerFunctionPackage("shiro", new ShiroExt());
	}

	
}
