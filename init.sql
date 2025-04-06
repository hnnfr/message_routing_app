-- Database initialization script for PostgreSQL
-- Mq_messages table creation
CREATE TABLE IF NOT EXISTS mq_messages (
    id SERIAL PRIMARY KEY,
    message_id VARCHAR(100),
    message_content TEXT,
    correlation_id VARCHAR(100),
    received_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_message_id ON mq_messages(message_id);
CREATE INDEX idx_correlation_id ON mq_messages(correlation_id);

COMMENT ON TABLE mq_messages IS 'Stores MQ messages';

COMMENT ON COLUMN mq_messages.message_id IS 'Unique message identifier from the queueing system (e.g., JMSMessageID, AMQP Message ID)';
COMMENT ON COLUMN mq_messages.message_content IS 'Complete message payload stored as text (supports structured data formats)';
COMMENT ON COLUMN mq_messages.correlation_id IS 'Message correlation identifier for tracking message relationships across systems';
COMMENT ON COLUMN mq_messages.received_timestamp IS 'Timestamp of message receipt and storage in database';

-- Partner table creation
-- Create ENUM types first (PostgreSQL specific)
CREATE TYPE direction_type AS ENUM ('INBOUND', 'OUTBOUND');
CREATE TYPE flow_type AS ENUM ('MESSAGE', 'ALERTING', 'NOTIFICATION');

-- Create the partners table
CREATE TABLE IF NOT EXISTS partners (
    id BIGSERIAL PRIMARY KEY,
    alias VARCHAR(50) NOT NULL,
    type VARCHAR(50) NOT NULL,
    direction direction_type NOT NULL,
    application VARCHAR(100),
    processed_flow_type flow_type,
    description VARCHAR(500) NOT NULL,
    CONSTRAINT uk_partners_alias UNIQUE (alias)
);

CREATE INDEX idx_partners_alias ON partners (alias);
CREATE INDEX idx_partners_direction ON partners (direction);
CREATE INDEX idx_partners_flow_type ON partners (processed_flow_type);

COMMENT ON TABLE partners IS 'Stores MQ partner configuration information';

COMMENT ON COLUMN partners.id IS 'Primary key, auto-incrementing ID';
COMMENT ON COLUMN partners.alias IS 'Unique identifier for the partner (required)';
COMMENT ON COLUMN partners.type IS 'Type of partner (required)';
COMMENT ON COLUMN partners.direction IS 'Direction of communication: INBOUND or OUTBOUND (required)';
COMMENT ON COLUMN partners.application IS 'Associated application name (optional)';
COMMENT ON COLUMN partners.processed_flow_type IS 'Type of flow being processed: MESSAGE, ALERTING, or NOTIFICATION';
COMMENT ON COLUMN partners.description IS 'Description of the partner (required)';
