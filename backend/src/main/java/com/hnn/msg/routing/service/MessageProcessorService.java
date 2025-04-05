package com.hnn.msg.routing.service;

import com.hnn.msg.routing.model.MqMessage;
import com.hnn.msg.routing.repository.MqMessageRepository;
import jakarta.jms.JMSException;
import jakarta.jms.TextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
public class MessageProcessorService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final MqMessageRepository repository;

    @Autowired
    public MessageProcessorService(MqMessageRepository repository) {
        this.repository = repository;
    }

    @JmsListener(destination = "${ibm.mq.queue}")
    public void processMessage(TextMessage message) throws JMSException {
        try {
            MqMessage mqMessage = new MqMessage();
            mqMessage.setMessageId(message.getJMSMessageID());
            mqMessage.setContent(message.getText());
            mqMessage.setCorrelationId(message.getJMSCorrelationID());

            repository.save(mqMessage);

            log.info("Successfully processed message ID: {}", mqMessage.getMessageId());
        } catch (Exception e) {
            log.error("Error processing message: {}", e.getMessage(), e);
            throw e; // Will trigger retry if configured
        }
    }
}