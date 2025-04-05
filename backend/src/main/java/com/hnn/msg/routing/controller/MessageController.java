package com.hnn.msg.routing.controller;

import com.hnn.msg.routing.dto.MessageDto;
import com.hnn.msg.routing.dto.MessageListDto;
import com.hnn.msg.routing.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    public ResponseEntity<MessageListDto> getAllMessages(
            @PageableDefault(size = 20, sort = "receivedTimestamp") Pageable pageable) {
        log.info("getAllMessages");
        return ResponseEntity.ok(messageService.getAllMessages(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageDto> getMessageById(@PathVariable("id") Long id) {
        log.info("Request to get Message by Id: {}", id);
        return ResponseEntity.ok(messageService.getMessageById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<MessageDto>> searchByContent(
            @RequestParam("content") String content) {
        log.info("searchByContent {}", content);
        return ResponseEntity.ok(messageService.searchByContent(content));
    }

    @GetMapping("/correlation/{correlationId}")
    public ResponseEntity<List<MessageDto>> getByCorrelationId(
            @PathVariable("correlationId") String correlationId) {
        log.info("getByCorrelationId {}", correlationId);
        return ResponseEntity.ok(messageService.getByCorrelationId(correlationId));
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<MessageDto>> getByDateRange(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        log.info("getByDateRange from {} to {}", start, end);
        return ResponseEntity.ok(messageService.getByDateRange(start, end));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getMessagesCount() {
        log.info("getMessagesCount");
        return ResponseEntity.ok(messageService.getMessagesCount());
    }
}