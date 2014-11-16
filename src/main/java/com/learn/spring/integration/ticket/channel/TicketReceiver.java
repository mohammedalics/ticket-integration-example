package com.learn.spring.integration.ticket.channel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.Message;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.core.MessageSelector;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.learn.spring.integration.ticket.entity.Ticket;

@Component
public class TicketReceiver implements Runnable {

	final static int RECEIVE_TIMEOUT = 1000;

	protected QueueChannel channel;

	@Value("#{ticketChannel}")
	public void setChannel(QueueChannel channel) {
		this.channel = channel;
	}

	public void handleTicketMessage() {
		Message<?> ticketMessage;

		while (true) {
			ticketMessage = channel.receive(RECEIVE_TIMEOUT);
			if (ticketMessage != null) {
				handleTicket((Ticket) ticketMessage.getPayload());
			} else {
				try {
					/** Handle some other tasks **/
					Thread.sleep(1000);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	void handleTicket(Ticket ticket) {
		System.out.println("Received ticket - " + ticket.toString());
		
	}
	

	@Override
	public void run() {
		handleTicketMessage();
	}
}