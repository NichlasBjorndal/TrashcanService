package jsmprovider;

import mdb.JMSSessionFactory;

import javax.annotation.Resource;
import javax.jms.*;
import java.util.UUID;

public class JmsProvider {
    @Resource(lookup = "java:/ConnectionFactory")
    protected ConnectionFactory cf;

    @Resource(lookup = "java:/queue/CreateUserQueue")
    private Queue createQueue;

    public String sendMessage(String qName, String msg) throws JMSException {
        Session session = JMSSessionFactory.createJmsSession();
        Destination dest = session.createQueue(qName);

        Destination tempDest = session.createTemporaryQueue();
        MessageConsumer reponseConsumer = session.createConsumer(tempDest);

        Message message = session.createTextMessage(msg);
        message.setJMSReplyTo(tempDest);
        message.setJMSCorrelationID(UUID.randomUUID().toString());
        session.createProducer(dest).send(message);

        Message receive = reponseConsumer.receive();
        return handleResponse(receive);
        //return "";
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
}
