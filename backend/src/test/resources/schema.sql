CREATE TABLE IF NOT EXISTS mq_messages (
    id SERIAL PRIMARY KEY,
    message_id VARCHAR(100),
    message_content TEXT,
    correlation_id VARCHAR(100),
    received_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

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