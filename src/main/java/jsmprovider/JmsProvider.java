package jsmprovider;

import javax.annotation.Resource;
import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public abstract class JmsProvider {
    @Resource(lookup = "java:/ConnectionFactory")
    protected ConnectionFactory cf;

    public JmsProvider(){

        InitialContext jndiContext = null;
        try {
            jndiContext = new InitialContext();
            cf = (ConnectionFactory) jndiContext.lookup("java:/ConnectionFactory");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    private Session GetSession(){
        Connection connection = null;
        Session session = null;
        try {
            connection = cf.createConnection();
            session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return session;
    }

    public void SendMessage(Destination dest, String msg) throws JMSException {
        Session session = GetSession();

        Message message = session.createTextMessage(msg);
        session.createProducer(dest).send(message);
    }



}
