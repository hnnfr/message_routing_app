package com.hnn.msg.routing.controller;

import com.hnn.msg.routing.dto.MessageDto;
import com.hnn.msg.routing.dto.MessageListDto;
import com.hnn.msg.routing.service.MessageService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class MessageControllerTest {

    @Mock
    private MessageService messageService;

    @InjectMocks
    private MessageController messageController;

    private MockMvc mockMvc;

    private void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(messageController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    void getAllMessages_ShouldReturnMessageListDto() throws Exception {
        setup();
        MessageListDto expected = MessageListDto.getInstance(Collections.emptyList(), 0, 0);
        when(messageService.getAllMessages(any(Pageable.class))).thenReturn(expected);

        mockMvc.perform(get("/messages"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.totalPages").value(0))
                .andExpect(jsonPath("$.totalElements").value(0));

        verify(messageService).getAllMessages(PageRequest.of(0, 20, Sort.by(Sort.Direction.ASC, "receivedTimestamp")));
    }

    @Test
    void getMessageById_WhenExists_ShouldReturnMessageDto() throws Exception {
        setup();
        MessageDto expected = new MessageDto();
        when(messageService.getMessageById(1L)).thenReturn(expected);

        mockMvc.perform(get("/messages/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(messageService).getMessageById(1L);
    }

    @Test
    void searchByContent_ShouldReturnMessages() throws Exception {
        setup();
        List<MessageDto> expected = Collections.singletonList(new MessageDto());
        when(messageService.searchByContent(anyString())).thenReturn(expected);

        mockMvc.perform(get("/messages/search")
                        .param("content", "test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1));

        verify(messageService).searchByContent("test");
    }

    @Test
    void getByCorrelationId_ShouldReturnMessages() throws Exception {
        setup();
        List<MessageDto> expected = Collections.singletonList(new MessageDto());
        when(messageService.getByCorrelationId(anyString())).thenReturn(expected);

        mockMvc.perform(get("/messages/correlation/123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1));

        verify(messageService).getByCorrelationId("123");
    }

    @Test
    void getByDateRange_ShouldReturnMessages() throws Exception {
        setup();
        LocalDateTime start = LocalDateTime.now().minusDays(1);
        LocalDateTime end = LocalDateTime.now();
        List<MessageDto> expected = Collections.singletonList(new MessageDto());

        when(messageService.getByDateRange(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(expected);

        mockMvc.perform(get("/messages/date-range")
                        .param("start", start.toString())
                        .param("end", end.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1));

        verify(messageService).getByDateRange(start, end);
    }

    @Test
    void getMessagesCount_ShouldReturnCount() throws Exception {
        setup();
        when(messageService.getMessagesCount()).thenReturn(5L);

        mockMvc.perform(get("/messages/count"))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));

        verify(messageService).getMessagesCount();
    }

    @Test
    void getByDateRange_WithInvalidDates_ShouldReturnBadRequest() throws Exception {
        setup();

        mockMvc.perform(get("/messages/date-range")
                        .param("start", "invalid-date")
                        .param("end", "invalid-date"))
                .andExpect(status().isBadRequest());
    }
}
