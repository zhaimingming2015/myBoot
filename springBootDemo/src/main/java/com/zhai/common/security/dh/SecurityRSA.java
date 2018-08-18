package com.zhai.common.security.dh;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.apache.tomcat.util.codec.binary.Base64;

public class SecurityRSA {
	
	public static void main(String[] args) {
		jdkRSA();
	}
	
	
	public static void jdkRSA(){
		//RSA的实现比DH简单
		
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			keyPairGenerator.initialize(512);
			
			KeyPair keyPair = keyPairGenerator.generateKeyPair();
			
			RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
			RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
			
			System.out.println("publicKey:" + Base64.encodeBase64String(rsaPublicKey.getEncoded()));
			System.out.println("PrivateKey:" + Base64.encodeBase64String(rsaPrivateKey.getEncoded()));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
