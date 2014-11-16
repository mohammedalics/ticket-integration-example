package com.learn.spring.integration.ticket.channel;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

import com.learn.spring.integration.ticket.entity.Ticket;

@Component
public class ProblemReporter {

  private QueueChannel channel;

  @Value("#{ticketChannel}")
  public void setChannel(QueueChannel channel) {
    this.channel = channel;
  }

  public void openTicket(Ticket ticket) {
    channel.send(MessageBuilder.withPayload( ticket).build() );
    System.out.println("Ticket Sent - " + ticket.toString());
  }
}