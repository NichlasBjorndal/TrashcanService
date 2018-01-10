package jsmprovider;

import javax.annotation.Resource;
import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.UUID;

public class JmsProvider {
    @Resource(lookup = "java:/ConnectionFactory")
    protected ConnectionFactory cf;

    @Resource(lookup = "java:/queue/CreateUserQueue")
    private Queue createQueue;

    public JmsProvider(){

        InitialContext jndiContext = null;
        try {
            jndiContext = new InitialContext();
            cf = (ConnectionFactory) jndiContext.lookup("java:/ConnectionFactory");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    private Session getSession() {
        Connection connection;
        Session session = null;
        try {
            connection = cf.createConnection();
            session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return session;
    }

    private String handleResponse(Message response) {
        String messageText = null;
        if(response instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) response;
            try {
                messageText = textMessage.getText();
                System.out.println("Received message from temp queue: " + messageText);
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
        return messageText;
    }

    public String sendMessage(String qName, String msg) throws JMSException {
        Session session = getSession();
        Destination dest = session.createQueue(qName);

        Destination tempDest = session.createTemporaryQueue();
        MessageConsumer reponseConsumer = session.createConsumer(tempDest);

        Message message = session.createTextMessage(msg);
        message.setJMSReplyTo(tempDest);
        message.setJMSCorrelationID(UUID.randomUUID().toString());
        session.createProducer(dest).send(message);

        Message receive = reponseConsumer.receive();

        return handleResponse(receive);
    }
}