package com.pe.demneru.client.jms.provider;public class JmsProvider{    private String              jndiQueueFactory;  private String              jndiQueue;  private String              provider;  private static final String contexto = "weblogic.jndi.WLInitialContextFactory";  private String              tipoProg;    public JmsProvider(){}    public String getJndiQueueFactory(){    return jndiQueueFactory;  }    public String getJndiQueue(){    return jndiQueue;  }    public String getUrl(){    return getProvider();  }    public static String getContexto(){    return contexto;  }    public String getTipoProg(){    return tipoProg;  }    public void setTipoProg( String tipoProg ){    this.tipoProg = tipoProg;  }    public void setJndiQueueFactory( String jndiQueueFactory ){    this.jndiQueueFactory = jndiQueueFactory;  }    public void setJndiQueue( String jndiQueue ){    this.jndiQueue = jndiQueue;  }    public String getProvider(){    return provider;  }    public void setProvider( String provider ){    this.provider = provider;  }}