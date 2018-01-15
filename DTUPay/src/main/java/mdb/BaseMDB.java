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

import dtu.ws.fastmoney.BankServiceException_Exception;
import mdb.utils.OnMessageUtil;
import org.junit.rules.ExternalResource;

import javax.jms.*;

public abstract class BaseMDB implements MessageListener {

    /**
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message rcvMessage) {
        String receivedText = OnMessageUtil.getTextFromMessage(rcvMessage);
        String responseMessageText = null;
        try {
            responseMessageText = processMessage(receivedText);
        } catch (BankServiceException_Exception e) {
            e.printStackTrace();
        }
        OnMessageUtil.Reply(rcvMessage, responseMessageText);
    }

    protected abstract String processMessage(String receivedText) throws BankServiceException_Exception;
}