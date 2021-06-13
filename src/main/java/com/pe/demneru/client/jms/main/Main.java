package com.pe.demneru.client.jms.main;

import javax.jms.JMSException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.pe.demneru.client.jms.UtilJMS;
import com.pe.demneru.client.jms.provider.JmsProvider;

public class Main{
  
  private static final Logger logger = LogManager.getLogger( Main.class );
  
  public static void main( String[] args ){
    logger.trace( "Entering application." );
    JmsProvider provider = createProvider();
    UtilJMS clientJms = new UtilJMS( provider );
    logger.debug( clientJms.toString() );
    try{
      clientJms.enviarMensajeJMSPuntoAPunto( getTextMEssage(), "TEXT_MESSAGE" );
    }
    catch( JMSException e ){
      logger.error( e );
    }
    logger.debug( "se ha terminado de enviar el mensaje" );
  }
  
  private static JmsProvider createProvider(){
    JmsProvider provider = new JmsProvider();
    provider.setJndiQueue( "pe.com.jndi.queue" );
    provider.setJndiQueueFactory( "pe.com.services.cf" );
    provider.setProvider( "t3://localhost:7001" );
    return provider;
  }
  
  private static String getTextMEssage(){
    String message = "messageToSend";
    logger.debug( message );
    logger.debug( message.replaceAll( "\n|\r", "" ) );
    return message.replaceAll( "\n|\r", "" );
  }
}
