package com.rros.core;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rros.resources.Util;

public class Connector {
	private Connection connection;
	private Channel channel;
	private ConnectionFactory factory;

	public static class Builder {
		private ConnectionFactory factory;
		private static final String RABBIT_SERVER_URI = Util.getURI();

		public Builder() throws KeyManagementException,
				NoSuchAlgorithmException, URISyntaxException {
			factory = new ConnectionFactory();
			factory.setUsername("test");
            factory.setPassword("test");
            factory.setHost("54.174.129.185");
            factory.setPort(5672);
            factory.setVirtualHost("/test");
		}
        public Builder setUsername(String username){
            factory.setUsername(username);
            return this;
        }

		public Connector build() {
			return new Connector(this);
		}

	}

	private Connector(Builder builder) {
		this.factory = builder.factory;
	}

	public Connection getConnection() {
		return connection;
	}

	public Channel getChannel() {
		return channel;
	}

	public void connect() throws IOException {
		connection = factory.newConnection();
		channel = connection.createChannel();
	}
	
	public void declareExchange(String exchangeName,String type) throws IOException{
		channel.exchangeDeclare(exchangeName, type);
	}

	public void closeChannel() throws IOException {
		this.channel.close();
	}

	public void closeConnection() throws IOException {
		this.connection.close();
	}

}
