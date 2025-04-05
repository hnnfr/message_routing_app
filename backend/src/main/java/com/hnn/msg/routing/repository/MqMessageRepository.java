package com.hnn.msg.routing.repository;

import com.hnn.msg.routing.model.MqMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MqMessageRepository extends JpaRepository<MqMessage, Long> {
    List<MqMessage> findByContentContainingIgnoreCase(String content);
    List<MqMessage> findByCorrelationId(String correlationId);
    List<MqMessage> findByReceivedTimestampBetween(LocalDateTime start, LocalDateTime end);
}
