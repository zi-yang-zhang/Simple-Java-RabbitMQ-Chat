package com.rros.core;

import java.util.Calendar;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

public class receiverThread extends Thread {
	private QueueingConsumer consumer;
	private int userID;

	public receiverThread(QueueingConsumer consumer, int userID) {
		super();
		this.consumer = consumer;
		this.userID = userID;

	}

	public void run() {
		QueueingConsumer.Delivery delivery;
		try {
			while (this.isAlive()) {
				delivery = consumer.nextDelivery();
				String message = new String(delivery.getBody());
				if (stringParser(message)) {
					System.out.println(consumer.getConsumerTag() + ":'"
							+ message + "'");
					System.out.println(new java.sql.Timestamp(Calendar
							.getInstance().getTime().getTime()));
				} else {
					continue;
				}
			}
		} catch (ShutdownSignalException | ConsumerCancelledException
				| InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean stringParser(String msg) {
		String userID = msg.split(":")[0];
		if (userID.equalsIgnoreCase(String.valueOf(this.userID))) {
			return false;
		} else {
			return true;
		}
	}

}
