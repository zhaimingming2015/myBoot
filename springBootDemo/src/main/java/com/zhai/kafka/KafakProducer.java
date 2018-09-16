package com.zhai.kafka;

import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;

public class KafakProducer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		createTopic();
	}
	
	public static void createTopic(){
		
		
		//创建topic
	    Properties props = new Properties();
	    props.put("bootstrap.servers", "114.67.237.73:9092");
	    AdminClient adminClient = AdminClient.create(props);
	    ArrayList<NewTopic> topics = new ArrayList<NewTopic>();
	    NewTopic newTopic = new NewTopic("topic-test", 1, (short) 1);
	    topics.add(newTopic);
	    CreateTopicsResult result = adminClient.createTopics(topics);
	    try {
	        result.all().get();
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    } catch (ExecutionException e) {
	        e.printStackTrace();
	    }
	    
	    System.out.println("----------succeed!!");
	}

}
