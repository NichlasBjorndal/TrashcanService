/*
 * JBoss, Home of Professional Open Source
 * Copyright 2015, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package mdb;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.*;

@MessageDriven(name = "UserQueueMDB", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "queue/CreateUserQueue"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")})

public class UserQueueMDB implements MessageListener {

    @Resource(lookup = "java:/ConnectionFactory")
    protected ConnectionFactory cf;


    /**
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message rcvMessage) {
        TextMessage msg;

        try {
            System.out.println("Received message: " + ((TextMessage)rcvMessage).getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }

        Session jmsSession = JMSSessionFactory.createJmsSession();
        try {
            TextMessage response = jmsSession.createTextMessage();

            msg = (TextMessage) rcvMessage;
            String rcvMsgText = msg.getText();
            //TODO: Process received text

            response.setText("Read you loud and clear, over.");
            response.setJMSCorrelationID(rcvMessage.getJMSCorrelationID());


            MessageProducer producer = jmsSession.createProducer(null);
            producer.send(rcvMessage.getJMSReplyTo(),response);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}