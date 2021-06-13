package com.pe.demneru.client.jms;

import java.io.Serializable;
import java.util.Hashtable;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.pe.demneru.client.jms.provider.JmsProvider;

public class UtilJMS{
  
  private Context                objContexto      = null;
  private QueueConnectionFactory objQueueFactory  = null;
  private QueueConnection        objQueueConexion = null;
  private QueueSession           objQueueSesion   = null;
  private Queue                  objQueue         = null;
  private QueueSender            objQueueSender   = null;
  private QueueReceiver          objQueueReciver  = null;
  private ObjectMessage          objMensaje       = null;
  private final Logger           logger           = LogManager.getLogger( UtilJMS.class );
  
  public UtilJMS( JmsProvider provider ){
    this.jndiQueueFactory = provider.getJndiQueueFactory();
    this.jndiQueue = provider.getJndiQueue();
    this.url = provider.getUrl();
  }
  
  private String              jndiQueueFactory;
  private String              jndiQueue;
  private static final String CONTEXTO = "weblogic.jndi.WLInitialContextFactory";
  private String              url;
  private TextMessage         textMensaje;
  
  public void enviarMensajeJMSPuntoAPunto( Serializable textMessage, String typeMessage ) throws JMSException{
    logger.info( "******** INICIO DE: [enviarMensajeJMSPuntoAPunto] ********" );
    try{
      if( ( this.jndiQueueFactory != null ) && ( this.jndiQueue != null ) ){
        // ---------------- CONFIGURACION [INICIO] ----------------//
        Hashtable<String, String> objProperties = new Hashtable<>();
        objProperties.put( Context.INITIAL_CONTEXT_FACTORY, CONTEXTO );
        objProperties.put( Context.PROVIDER_URL, url );
        // ---------------- CONFIGURACION [FINAL] -----------------//
        this.objContexto = new InitialContext( objProperties );
        this.objQueueFactory = (QueueConnectionFactory)this.objContexto.lookup( this.jndiQueueFactory );
        this.objQueueConexion = objQueueFactory.createQueueConnection();
        this.objQueueSesion = this.objQueueConexion.createQueueSession( false, Session.AUTO_ACKNOWLEDGE );
        this.objQueue = (Queue)this.objContexto.lookup( this.jndiQueue );
        this.objQueueSender = this.objQueueSesion.createSender( this.objQueue );
        if( typeMessage.equals( "TEXT_MESSAGE" ) ){
          this.textMensaje = this.objQueueSesion.createTextMessage( (String)textMessage );
          this.objQueueSender.send( this.textMensaje );
        }
        else{
          this.objMensaje = this.objQueueSesion.createObjectMessage();
          this.objMensaje.setObject( textMessage );
          this.objQueueSender.send( this.objMensaje );
        }
        // ENVIANDO MENSAJE:
        logger.info( "- MENSAJE ENVIADO ...!!!" );
        // LIMPIANDO & CERRAR CONEXIONES:
        this.limpiarCerrarConexiones();
      }
    }
    catch( JMSException e ){
      logger.error( e );
      throw new JMSException( "Ha ocurrido un error" );
    }
    catch( Exception e ){
      logger.error( e );
      throw new JMSException( "Ha ocurrido un error" );
    }
    finally{
      logger.info( "******** FIN DE: [enviarMensajeJMSPuntoAPunto] ********" );
    }
  }
  
  public void limpiarCerrarConexiones(){
    try{
      if( this.objQueueSender != null ){
        this.objQueueSender.close();
      }
      if( this.objQueueReciver != null ){
        this.objQueueReciver.close();
      }
      if( this.objQueueSesion != null ){
        this.objQueueSesion.close();
      }
      if( this.objQueueConexion != null ){
        this.objQueueConexion.close();
      }
      this.objMensaje = null;
      this.objQueueSender = null;
      this.objQueueReciver = null;
      this.objQueue = null;
      this.objQueueSesion = null;
      this.objQueueConexion = null;
      this.objQueueFactory = null;
      this.objContexto = null;
    }
    catch( JMSException e ){
      logger.error( "Ha ocurrido un JMSException ", e );
    }
    catch( Exception e ){
      logger.error( "Ha ocurrido un problema ", e );
    }
  }
  
  @Override
  public String toString(){
    return "UtilJMS [objContexto=" + objContexto + ", objQueueFactory=" + objQueueFactory + ", objQueueConexion="
        + objQueueConexion + ", objQueueSesion=" + objQueueSesion + ", objQueue=" + objQueue + ", objQueueSender="
        + objQueueSender + ", objQueueReciver=" + objQueueReciver + ", objMensaje=" + objMensaje + ", jndiQueueFactory="
        + jndiQueueFactory + ", jndiQueue=" + jndiQueue + ", url=" + url + "]";
  }
}
