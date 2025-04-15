package com.hnn.msg.routing.service;

import com.hnn.msg.routing.dto.MessageDto;
import com.hnn.msg.routing.dto.MessageListDto;
import com.hnn.msg.routing.mapper.MessageMapper;
import com.hnn.msg.routing.model.MqMessage;
import com.hnn.msg.routing.repository.MqMessageRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MessageServiceImplTest {

    @Mock
    private MqMessageRepository messageRepository;

    @Mock
    private MessageMapper messageMapper;

    @InjectMocks
    private MessageServiceImpl messageService;

    @Test
    void getAllMessages_ShouldReturnMessageListDto() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        MqMessage message = new MqMessage();
        MessageDto messageDto = new MessageDto();
        Page<MqMessage> page = new PageImpl<>(Collections.singletonList(message));

        when(messageRepository.findAll(pageable)).thenReturn(page);
        when(messageMapper.messageToDto(message)).thenReturn(messageDto);

        // Act
        MessageListDto result = messageService.getAllMessages(pageable);

        // Assert
        assertEquals(1, result.getContent().size());
        assertEquals(messageDto, result.getContent().get(0));
        assertEquals(1, result.getTotalPages());
        assertEquals(1, result.getTotalElements());
        verify(messageRepository).findAll(pageable);
        verify(messageMapper).messageToDto(message);
    }

    @Test
    void getMessageById_WhenExists_ShouldReturnMessageDto() {
        // Arrange
        Long id = 1L;
        MqMessage message = new MqMessage();
        MessageDto messageDto = new MessageDto();

        when(messageRepository.findById(id)).thenReturn(Optional.of(message));
        when(messageMapper.messageToDto(message)).thenReturn(messageDto);

        // Act
        MessageDto result = messageService.getMessageById(id);

        // Assert
        assertEquals(messageDto, result);
        verify(messageRepository).findById(id);
        verify(messageMapper).messageToDto(message);
    }

    @Test
    void getMessageById_WhenNotExists_ShouldThrowException() {
        // Arrange
        Long id = 1L;
        when(messageRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> messageService.getMessageById(id));
        verify(messageRepository).findById(id);
        verifyNoInteractions(messageMapper);
    }

    @Test
    void searchByContent_ShouldReturnListOfMessageDto() {
        // Arrange
        String content = "test";
        MqMessage message = new MqMessage();
        MessageDto messageDto = new MessageDto();

        when(messageRepository.findByContentContainingIgnoreCase(content))
                .thenReturn(Collections.singletonList(message));
        when(messageMapper.messageToDto(message)).thenReturn(messageDto);

        // Act
        List<MessageDto> result = messageService.searchByContent(content);

        // Assert
        assertEquals(1, result.size());
        assertEquals(messageDto, result.get(0));
        verify(messageRepository).findByContentContainingIgnoreCase(content);
        verify(messageMapper).messageToDto(message);
    }

    @Test
    void getByCorrelationId_ShouldReturnListOfMessageDto() {
        // Arrange
        String correlationId = "123";
        MqMessage message = new MqMessage();
        MessageDto messageDto = new MessageDto();

        when(messageRepository.findByCorrelationId(correlationId))
                .thenReturn(Collections.singletonList(message));
        when(messageMapper.messageToDto(message)).thenReturn(messageDto);

        // Act
        List<MessageDto> result = messageService.getByCorrelationId(correlationId);

        // Assert
        assertEquals(1, result.size());
        assertEquals(messageDto, result.get(0));
        verify(messageRepository).findByCorrelationId(correlationId);
        verify(messageMapper).messageToDto(message);
    }

    @Test
    void getByDateRange_ShouldReturnListOfMessageDto() {
        // Arrange
        LocalDateTime start = LocalDateTime.now().minusDays(1);
        LocalDateTime end = LocalDateTime.now();
        MqMessage message = new MqMessage();
        MessageDto messageDto = new MessageDto();

        when(messageRepository.findByReceivedTimestampBetween(start, end))
                .thenReturn(Collections.singletonList(message));
        when(messageMapper.messageToDto(message)).thenReturn(messageDto);

        // Act
        List<MessageDto> result = messageService.getByDateRange(start, end);

        // Assert
        assertEquals(1, result.size());
        assertEquals(messageDto, result.get(0));
        verify(messageRepository).findByReceivedTimestampBetween(start, end);
        verify(messageMapper).messageToDto(message);
    }

    @Test
    void getMessagesCount_ShouldReturnCount() {
        // Arrange
        long expectedCount = 5L;
        when(messageRepository.count()).thenReturn(expectedCount);

        // Act
        long result = messageService.getMessagesCount();

        // Assert
        assertEquals(expectedCount, result);
        verify(messageRepository).count();
    }
}
