package com.rros.core;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import com.rabbitmq.client.QueueingConsumer;

public class User {
	private Connector connector;
	private String exchangeName;
	private String queueName; 
	private QueueingConsumer consumer;
	private int userID;
	
	public User() throws KeyManagementException, NoSuchAlgorithmException, URISyntaxException, IOException {
		connector = new Connector.Builder().build();
		userID = this.hashCode();
		connector.connect();
		connectToExchange("chatroom");
		declareQueue();
		bindQueue();
		consumer = new QueueingConsumer(connector.getChannel());
		connector.getChannel().basicConsume(queueName, true, consumer);
		new receiverThread(consumer,userID).start();
	}
	
	public void connectToExchange(String exchangeName){
		this.exchangeName = exchangeName;
	}
	
	public void declareQueue() throws IOException{
		queueName = connector.getChannel().queueDeclare().getQueue();
	}
	public void bindQueue() throws IOException{
		connector.getChannel().queueBind(queueName, exchangeName, "");
	}
	public void publish(String msg) throws IOException{
		msg = String.valueOf(userID)+":"+msg;
		connector.getChannel().basicPublish(exchangeName, "", null, msg.getBytes());
	}
}
