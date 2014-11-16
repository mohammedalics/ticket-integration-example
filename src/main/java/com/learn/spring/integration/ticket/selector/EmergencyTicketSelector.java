package com.learn.spring.integration.ticket.selector;

import org.springframework.integration.Message;
import org.springframework.integration.core.MessageSelector;
import org.springframework.stereotype.Component;

import com.learn.spring.integration.ticket.entity.Ticket;
import com.learn.spring.integration.ticket.entity.Ticket.Priority;

@Component
public class EmergencyTicketSelector implements MessageSelector {
  @Override
  public boolean accept(Message<?> message) {
    return ((Ticket) message.getPayload()).getPriority() != Priority.emergency;
  }
}
