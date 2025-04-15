package com.hnn.msg.routing.repository;

import com.hnn.msg.routing.model.MqMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import java.time.LocalDateTime;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class MqMessageRepositoryTest {

    @Autowired
    private MqMessageRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void findByContentContainingIgnoreCase_shouldReturnMatchingMessages() {
        // Arrange
        MqMessage message1 = new MqMessage();
        message1.setContent("Hello World");
        entityManager.persist(message1);

        MqMessage message2 = new MqMessage();
        message2.setContent("Goodbye World");
        entityManager.persist(message2);

        entityManager.flush();

        // Act
        List<MqMessage> found = repository.findByContentContainingIgnoreCase("hello");

        // Assert
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getContent()).isEqualTo("Hello World");
    }

    @Test
    void findByCorrelationId_shouldReturnMatchingMessages() {
        // Arrange
        String correlationId = "123-456";
        MqMessage message = new MqMessage();
        message.setCorrelationId(correlationId);
        entityManager.persist(message);
        entityManager.persist(new MqMessage()); // Another message with null correlationId
        entityManager.flush();

        // Act
        List<MqMessage> found = repository.findByCorrelationId(correlationId);

        // Assert
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getCorrelationId()).isEqualTo(correlationId);
    }

    @Test
    void findByReceivedTimestampBetween_shouldReturnMessagesInDateRange() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start = now.minusHours(1);
        LocalDateTime end = now.plusHours(1);

        MqMessage message1 = new MqMessage();
        entityManager.persist(message1);

        MqMessage message2 = new MqMessage();
        entityManager.persist(message2);

        entityManager.flush();

        // Act
        List<MqMessage> found = repository.findByReceivedTimestampBetween(start, end);

        // Assert
        assertThat(found).hasSize(2);
    }

    @Test
    void findByContentContainingIgnoreCase_shouldBeCaseInsensitive() {
        // Arrange
        MqMessage message = new MqMessage();
        message.setContent("Case Sensitive Test");
        entityManager.persist(message);
        entityManager.flush();

        // Act
        List<MqMessage> foundLower = repository.findByContentContainingIgnoreCase("case");
        List<MqMessage> foundUpper = repository.findByContentContainingIgnoreCase("TEST");

        // Assert
        assertThat(foundLower).hasSize(1);
        assertThat(foundUpper).hasSize(1);
    }

    @Test
    void findByReceivedTimestampBetween_shouldHandleEmptyResults() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        entityManager.persist(new MqMessage()); // Message with null timestamp
        entityManager.flush();

        // Act
        List<MqMessage> found = repository.findByReceivedTimestampBetween(now.minusHours(5), now.minusHours(4));

        // Assert
        assertThat(found).isEmpty();
    }
}
