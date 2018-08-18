package com.zhai.common.security.dh;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Objects;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;

import org.apache.tomcat.util.codec.binary.Base64;

public class SecurityDH {
	
	private static String src="imoocisstartzhaimmmmcccdeeesdifdfdfdfdfdfdfdfererer2545dfdfdfdf4555555555555555555555555555577777777777788888888888";

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		jdkDH();
	}

	public static void jdkDH(){
		
		try {
			//1初始化发送方密钥     实例化密钥对生成器
			KeyPairGenerator senderkeyPairGenerator = KeyPairGenerator.getInstance("DH");
			senderkeyPairGenerator.initialize(512);
			//生成密钥对
			KeyPair senderKeyPair = senderkeyPairGenerator.generateKeyPair();
			//发送方公钥载体
			byte[] senderPublicKeyEnc = senderKeyPair.getPublic().getEncoded();//发送方公钥，发送给接收方（网络、文件。。。）
			
			
			//2初始化接收方密钥
			KeyFactory receiverKeyFactory = KeyFactory.getInstance("DH");
			//使用发送方传递的 公钥
			X509EncodedKeySpec x509EncodedKeySpec=new X509EncodedKeySpec(senderPublicKeyEnc);
			DHPublicKey senderDHPublicKey = (DHPublicKey) receiverKeyFactory.generatePublic(x509EncodedKeySpec);//生成接收方公钥
			//DHPublicKey dhPublicKey=(DHPublicKey) receiverPublicKey;
			//从发送方公钥 中解析出参数
			DHParameterSpec dhParameterSpec = senderDHPublicKey.getParams();
			KeyPairGenerator receiverKeyPairGenerator = KeyPairGenerator.getInstance("DH");
			receiverKeyPairGenerator.initialize(dhParameterSpec);
			KeyPair receiverKeyPair = receiverKeyPairGenerator.generateKeyPair();
			PrivateKey receiverPrivateKey = receiverKeyPair.getPrivate();//接收方私钥
			byte[] receiverPublicKeyEnc = receiverKeyPair.getPublic().getEncoded();////接收方公钥
			
			
			//3密钥构建
			KeyAgreement receiverKeyAgreement = KeyAgreement.getInstance("DH");
			receiverKeyAgreement.init(receiverPrivateKey);
			receiverKeyAgreement.doPhase(senderDHPublicKey, true);//?
			//生成接收方的本地密钥
			SecretKey receiverDESKey = receiverKeyAgreement.generateSecret("DES");
			
			
			KeyFactory senderKeyFactory = KeyFactory.getInstance("DH");
			x509EncodedKeySpec=new X509EncodedKeySpec(receiverPublicKeyEnc);
			PublicKey receiverPublicKey = senderKeyFactory.generatePublic(x509EncodedKeySpec);//获取接收方公钥
			KeyAgreement senderKeyAgreement = KeyAgreement.getInstance("DH");
			senderKeyAgreement.init(senderKeyPair.getPrivate());
			senderKeyAgreement.doPhase(receiverPublicKey, true);
			//生成发送的本地密钥
			SecretKey senderDESKey = senderKeyAgreement.generateSecret("DES");
			
			if(Objects.equals(receiverDESKey, senderDESKey)){
				System.out.println("all is same");
			}
			//双方本地密钥构建完成   ----end
	
			//4加密
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, senderDESKey);
			byte[] result = cipher.doFinal(src.getBytes());
			System.out.println("jdk ENCRYPT:"+Base64.encodeBase64String(result));
			
			
			//5解密
			//Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, receiverDESKey);
			result = cipher.doFinal(result);
			System.out.println("jdk DECRYPT:"+new String(result));
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
