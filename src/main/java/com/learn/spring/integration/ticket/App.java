package com.learn.spring.integration.ticket;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.learn.spring.integration.ticket.channel.EmergencyTicketReceiver;
import com.learn.spring.integration.ticket.channel.ProblemReporter;
import com.learn.spring.integration.ticket.channel.TicketReceiver;
import com.learn.spring.integration.ticket.entity.Ticket;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        String contextName = "queue-channel.xml";

        ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext(contextName);
        applicationContext.start();

        ProblemReporter problemReporter =
                applicationContext.getBean(ProblemReporter.class);
        EmergencyTicketReceiver ticketReceiver =
                applicationContext.getBean("emergencyTicketReceiver", EmergencyTicketReceiver.class);
        TicketGenerator ticketGenerator =
                applicationContext.getBean(TicketGenerator.class);
        List<Ticket> tickets = ticketGenerator.createTickets();
        for (Ticket ticket : tickets) {
            problemReporter.openTicket(ticket);
        }

        Thread consumerThread = new Thread(ticketReceiver);
        consumerThread.start();
    }
}
