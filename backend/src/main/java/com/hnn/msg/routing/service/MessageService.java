package com.hnn.msg.routing.service;

import com.hnn.msg.routing.dto.MessageDto;
import com.hnn.msg.routing.dto.MessageListDto;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface MessageService {
    MessageListDto getAllMessages(Pageable pageable);
    MessageDto getMessageById(Long id);
    List<MessageDto> searchByContent(String content);
    List<MessageDto> getByCorrelationId(String correlationId);
    List<MessageDto> getByDateRange(LocalDateTime start, LocalDateTime end);
    long getMessagesCount();
}