package jsmprovider;

import mdb.JMSSessionFactory;

import javax.annotation.Resource;
import javax.jms.*;
import javax.xml.soap.Text;
import java.util.Random;
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
        MessageConsumer responseConsumer = session.createConsumer(tempDest);

        String correlationID = createRandomString();

        TextMessage message = session.createTextMessage();
        message.setText(msg);
        message.setJMSReplyTo(tempDest);
        message.setJMSCorrelationID(correlationID);

        MessageProducer msgProducer = session.createProducer(dest);
        msgProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        msgProducer.send(message);

        Message receive = responseConsumer.receive();
        return handleResponse(receive);
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

    private String createRandomString() {
        Random random = new Random(System.currentTimeMillis());
        long randomLong = random.nextLong();
        return Long.toHexString(randomLong);
    }
}
