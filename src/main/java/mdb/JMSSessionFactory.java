package mdb;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Factory for creating sessions to be used when communicating over JMS.
 */
public class JMSSessionFactory {

    /**
     * @return A Session object (@see javax.jms.Session) to be used when sending messages.
     */
    public static Session createJmsSession() {
        ConnectionFactory cf = null;
        InitialContext jndiContext;
        try {
            jndiContext = new InitialContext();
            cf = (ConnectionFactory) jndiContext.lookup("java:/ConnectionFactory");
        } catch (NamingException e) {
            e.printStackTrace();
        }

        Connection connection;
        Session session = null;
        try {
            connection = cf.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        } catch (JMSException e) {
            e.printStackTrace();
        }

        return session;
    }
}
