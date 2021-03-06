package jsmprovider;

import core.utils.GsonWrapper;
import mdb.utils.JMSSessionFactory;

import javax.jms.*;
import java.util.Random;
import java.util.concurrent.TimeoutException;

/**
 * @author Nichlas Bjørndal
 * A wrapper for sending messages over JMS.
 */
public class JmsProvider {

    public static final String TIMEOUT_ERROR = "JMS communication failed, a timeout limit was reached.";
    public static final String JMS_ERROR = "JMS communication failed.";

    private Session session;

    public JmsProvider() {
        session = JMSSessionFactory.createJmsSession();
    }

    /**
     * @param queueName Name of the JMS queue.
     * @param msg A message in JSON format to be sent over JMS.
     * @param timeout The timout limit in milliseconds.
     * @return The reply from the consumer in JSON format.
     * @throws Exception Thrown if the timeout limit is exceeded.
     */
    public String sendMessage(String queueName, String msg, int timeout) throws TimeoutException,JMSException {
        Destination dest = session.createQueue(queueName);

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

        Message receive = responseConsumer.receive(timeout);

        if (receive == null)
            throw new TimeoutException();

        return getTextFromReceivedMessage(receive);
    }

    /**
     * Sends a message with a default timeout of 5000 milliseconds.
     * @param queueName Name of the JMS queue.
     * @param msg A message in JSON format to be sent over JMS.
     * @return The reply from the consumer in JSON format.
     * @throws Exception Thrown if the timeout limit is exceeded.
     */
    public String sendMessage(String queueName, String msg) throws TimeoutException,JMSException {
        return sendMessage(queueName,msg,5000);
    }

    /**
     * Sends a message containing an object in json format.
     * @param queueName Name of the JMS queue.
     * @param msgObj An object to be converted to JSON and sent.
     * @return The reply from the consumer in JSON format.
     * @throws Exception Thrown if the timeout limit is exceeded.
     */
    public String sendMessage(String queueName, Object msgObj) throws TimeoutException,JMSException {
        String jsonString = GsonWrapper.toJson(msgObj);
        return sendMessage(queueName,jsonString,5000);
    }

    /**
     * Extracts text as a String from a received message
     * @param response The message to be used
     * @return a String with the text contained in the passed message
     */
    private String getTextFromReceivedMessage(Message response) {
        String messageText = null;
        if(response instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) response;

            try {
                messageText = textMessage.getText();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
        return messageText;
    }

    /**
     * Used to generate a random string to be used as an identifier for a temporary queue,
     * when getting a response from a bean
     * @return the randomly generated string
     */
    private String createRandomString() {
        Random random = new Random(System.currentTimeMillis());
        long randomLong = random.nextLong();
        return Long.toHexString(randomLong);
    }
}
