package com.hnn.msg.routing.service;

import com.hnn.msg.routing.dto.MessageDto;
import com.hnn.msg.routing.dto.MessageListDto;
import com.hnn.msg.routing.exception.ResourceNotFoundException;
import com.hnn.msg.routing.mapper.MessageMapper;
import com.hnn.msg.routing.model.MqMessage;
import com.hnn.msg.routing.repository.MqMessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class MessageServiceImpl implements MessageService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final MqMessageRepository messageRepository;
    private final MessageMapper messageMapper;

    public MessageServiceImpl(MqMessageRepository messageRepository,
                              MessageMapper messageMapper) {
        this.messageRepository = messageRepository;
        this.messageMapper = messageMapper;
    }

    @Override
    public MessageListDto getAllMessages(Pageable pageable) {
        log.debug("Request to get all messages");
        Page<MqMessage> messagePage = messageRepository.findAll(pageable);
        Page<MessageDto> dtoPage = messagePage.map(messageMapper::messageToDto);
        return MessageListDto.fromPage(dtoPage);
    }

    @Override
    public MessageDto getMessageById(Long id) {
        log.debug("Request to get message by id: {}", id);
        return messageRepository.findById(id)
                .map(messageMapper::messageToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Message not found with id: " + id));
    }

    @Override
    public List<MessageDto> searchByContent(String content) {
        log.debug("Request to search messages by content: {}", content);
        return messageRepository.findByContentContainingIgnoreCase(content)
                .stream()
                .map(messageMapper::messageToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<MessageDto> getByCorrelationId(String correlationId) {
        log.debug("Request to get messages by correlationId: {}", correlationId);
        return messageRepository.findByCorrelationId(correlationId)
                .stream()
                .map(messageMapper::messageToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<MessageDto> getByDateRange(LocalDateTime start, LocalDateTime end) {
        log.debug("Request to get messages by date range from {} to {}", start, end);
        return messageRepository.findByReceivedTimestampBetween(start, end)
                .stream()
                .map(messageMapper::messageToDto)
                .collect(Collectors.toList());
    }

    @Override
    public long getMessagesCount() {
        log.debug("Request to get messages count");
        return messageRepository.count();
    }
}