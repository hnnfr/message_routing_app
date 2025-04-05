CREATE TABLE IF NOT EXISTS mq_messages (
    id SERIAL PRIMARY KEY,
    message_id VARCHAR(100),
    message_content TEXT,
    correlation_id VARCHAR(100),
    received_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_message_id ON mq_messages(message_id);
CREATE INDEX idx_correlation_id ON mq_messages(correlation_id);