CREATE TABLE monitoring_rule (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    type VARCHAR(20) NOT NULL,
    description VARCHAR(255) NOT NULL,
    enabled BOOLEAN NOT NULL,
    threshold INTEGER,
    interval INTEGER,
    notification_recipients TEXT[] NOT NULL,
    process_name_regex VARCHAR(255),
    log_file_path VARCHAR(255),
    log_patterns TEXT[],
    created_by VARCHAR(255) NOT NULL,
    updated_by VARCHAR(255) NOT NULL,
    create_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE server (
    id SERIAL PRIMARY KEY,
    server_name VARCHAR(255) NOT NULL,
    hostname VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    created_by VARCHAR(255) NOT NULL,
    updated_by VARCHAR(255) NOT NULL,
    create_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE server_monitoring_rule (
    server_id INTEGER,
    monitoring_rule_id INTEGER,
    create_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
    PRIMARY KEY (server_id, monitoring_rule_id),
    FOREIGN KEY (server_id) REFERENCES server (id),
    FOREIGN KEY (monitoring_rule_id) REFERENCES monitoring_rule (id)
);

--| id | name         | type         | configuration                              | enabled | created_at           | updated_at           |
--|----|--------------|--------------|--------------------------------------------|---------|----------------------|----------------------|
--| 1  | Email Alert  | Email        | {"email": "alerts@example.com"}             | true    | 2023-10-10 09:00:00  | 2023-10-10 09:00:00  |
--| 2  | SMS Alert    | SMS          | {"phone_number": "+1234567890"}             | true    | 2023-10-10 10:30:00  | 2023-10-10 10:30:00  |
CREATE TABLE channel (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    type VARCHAR(50) NOT NULL, -- EMAIL,SYMPHONY,XMATTERS
    description VARCHAR(255) NOT NULL,
    configuration JSONB,
    enabled BOOLEAN NOT NULL DEFAULT true,
    created_by VARCHAR(255) NOT NULL,
    updated_by VARCHAR(255) NOT NULL,
    create_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE alert_channel (
    alert_id INT REFERENCES alert (id),
    channel_id INT REFERENCES channel (id),
    PRIMARY KEY (alert_id, channel_id)
);

CREATE TABLE monitoring_rule_channel (
    monitoring_rule_id INT REFERENCES monitoring_rule(id),
    channel_id INT REFERENCES channel (id),
    PRIMARY KEY (monitoring_rule_id, channel_id),
    create_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 如何确定两条Alert相同，可以给repeat_count + 1
-- 1. status 为 OPEN
-- 2. 相同 source
-- 3. 相同的 rule_id
CREATE TABLE alert (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    severity VARCHAR(20) NOT NULL,
    type VARCHAR(50) NOT NULL,
    source VARCHAR(100),
    status VARCHAR(20) NOT NULL,
    repeat_count INT DEFAULT 0,
    start_time TIMESTAMP,
    end_time TIMESTAMP,
    acknowledged_by VARCHAR(100),
    rule_id INT,
    channel_id INT[] NOT NULL,
    action VARCHAR(100),
    create_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (rule_id) REFERENCES monitoring_rule (id)
);

-- 与 alert 表多对一，一条alert可以产生多条notification
--| id | alert_id | channel_id | notification_time       | status  | response_code | response_message |
--|----|----------|------------|-------------------------|---------|---------------|------------------|
--| 1  | 101      | 201        | 2023-10-10 10:30:00 UTC | Success | 200           | Notification sent successfully.      |
--| 2  | 102      | 202        | 2023-10-10 12:45:00 UTC | Failure | 500           | Internal server error. Please try again. |
--| 3  | 103      | 201        | 2023-10-11 09:15:00 UTC | Success | 200           | Notification sent successfully.      |
CREATE TABLE alert_notification_history (
    id SERIAL PRIMARY KEY,
    alert_id INT NOT NULL,
    channel_messages JSONB, --[{channel_id: xx, message: xx}]
    notification_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(50) NOT NULL,
    response_code INT,
    response_message TEXT,
    create_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (alert_id) REFERENCES alert (id)
);

CREATE OR REPLACE FUNCTION update_repeat_count()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.status = 'Open' AND OLD.status = 'Open' THEN
        NEW.repeat_count = OLD.repeat_count + 1;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER alert_update_repeat_count
BEFORE UPDATE ON alert
FOR EACH ROW
EXECUTE FUNCTION update_repeat_count();



