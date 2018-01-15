package mdb.utils;

import javax.jms.*;

public class OnMessageUtil {

    /**
     * @param message Java Messaging Service message.
     * @return Message converted into TextMessage.
     */
    public static String getTextFromMessage(Message message){
        TextMessage msg = (TextMessage) message;
        String rcvMsgText = null;

        try {
            rcvMsgText = msg.getText();
        } catch (JMSException e) {
            e.printStackTrace();
        }

        return rcvMsgText;
    }

    /**
     * @param receivedMessage Received message.
     * @param responseMessageText The text message to be send back to the original sender.
     */
    public static void Reply(Message receivedMessage, String responseMessageText){
        Session jmsSession = JMSSessionFactory.createJmsSession();
        try {
            TextMessage response = jmsSession.createTextMessage();
            response.setText(responseMessageText);
            response.setJMSCorrelationID(receivedMessage.getJMSCorrelationID());

            MessageProducer producer = jmsSession.createProducer(null);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            producer.send(receivedMessage.getJMSReplyTo(),response);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}