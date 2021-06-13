package com.pe.demneru.client.jms.producer;

import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Producer {

    private static final Logger logger = LogManager.getLogger( Producer.class );

    @Resource(lookup = "jms/ConnectionFactory")
    private static ConnectionFactory connectionFactory;

    @Resource(lookup = "jms/Queue")
    private static Queue queue;

    @Resource(lookup = "jms/Topic")
    private static Queue topic;

    public void sendMessage( String destType, String messageToSend){
        logger.debug("Destination type is " + destType);

        Destination dest = null;
        try { 
            if (destType.equals("queue")) { 
                dest = (Destination) queue; 
            } else { 
                dest = (Destination) topic; 
            }
        }catch (Exception e) {
            logger.error("Error setting destination: " +e.toString(), e);
            System.exit(1);
        }
        Connection connection=null;
        try {
                connection = connectionFactory.createConnection();
                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                MessageProducer producer = session.createProducer(dest);
                TextMessage message = session.createTextMessage();
                message.setText("This is message from producer: " +  messageToSend); 
                logger.debug("Sending message: " + message.getText()); 
                producer.send(message);
        } catch (Exception e) {
            logger.error(e);
        }finally { 
            if (connection != null) { 
                try { connection.close(); } 
                catch (JMSException e) { } 
            }
        }
    }

    public static void main(String[] args) {
        Producer producer = new Producer();
        producer.sendMessage("queue", "Hellor there");
    }
}
