package com.rros.core;

import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

public class receiverThread extends Thread {
	private QueueingConsumer consumer;

	public receiverThread(QueueingConsumer consumer) {
		super();
		this.consumer = consumer;
	}

	public void run() {
		QueueingConsumer.Delivery delivery;
		try {
			while (this.isAlive()) {
				delivery = consumer.nextDelivery();
				String message = new String(delivery.getBody());

				System.out.println(" [x] Received '" + message + "'");
			}
		} catch (ShutdownSignalException | ConsumerCancelledException
				| InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
