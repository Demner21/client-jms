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
    String message = "<DataTransaccionCBIO><idTransaccion>202098112525</idTransaccion><nombreAplicacion>SICAR</nombreAplicacion><usuarioAplicacion>C18655</usuarioAplicacion>\r"
        + "  <tipoOperacion>C19</tipoOperacion>\r" + "  <idNegocio>21633488</idNegocio>\r"
        + "  <idNegocioAux>987969796</idNegocioAux>\r" + "  <xmlDatos><![CDATA[<CallbackCambioSimCBIOMDB>\r"
        + "  <coIdPub>MIG000006401567</coIdPub>\r" + "  <codigoBloqueo/>\r" + "  <csIdPub>7648799</csIdPub>\r"
        + "  <iccidActual>8951100320141820073</iccidActual>\r" + "  <iccidNuevo>8951101639454956433</iccidNuevo>\r"
        + "  <imsiActual>716100314182007</imsiActual>\r" + "  <imsiNuevo>716101645495643</imsiNuevo>\r"
        + "  <linea>987969796</linea>\r" + "  <motivoReposicion>Da&amp;#241;o</motivoReposicion>\r"
        + "  <numeroDocumento>09309768</numeroDocumento>\r" + "  <tipoDocumento>01</tipoDocumento>\r"
        + "</CallbackCambioSimCBIOMDB>]]></xmlDatos>\r" + "</DataTransaccionCBIO>\r";
    logger.debug( message );
    logger.debug( message.replaceAll( "\n|\r", "" ) );
    return message.replaceAll( "\n|\r", "" );
  }
}
