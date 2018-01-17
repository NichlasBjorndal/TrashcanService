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

import mdb.utils.OnMessageUtil;

import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * This class contains the base implementation for our MessageDrivenBeanClasses.
 * BaseMDB takes care of receiving, processing and responding to JMS messages.
 */
public abstract class BaseMDB implements MessageListener {

    /**
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message rcvMessage) {
        String receivedText = OnMessageUtil.getTextFromMessage(rcvMessage);
        String responseMessageText = null;
        responseMessageText = processMessage(receivedText);
        OnMessageUtil.Reply(rcvMessage, responseMessageText);
    }


    /**
     * This method must be overloaded for every implementation of MBD classes.
     * This method is where a messages consumed from the JMS queue is processed before sending a response.
     * @param receivedText The received JMS message in JSON format
     * @return The reply to the received JMS message in JSON format.
     */
    protected abstract String processMessage(String receivedText);
}