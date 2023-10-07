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
    create_date TIMESTAMP NOT NULL,
    update_date TIMESTAMP NOT NULL
);

CREATE TABLE server (
    id SERIAL PRIMARY KEY,
    server_name VARCHAR(255) NOT NULL,
    hostname VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    created_by VARCHAR(255) NOT NULL,
    updated_by VARCHAR(255) NOT NULL,
    create_date TIMESTAMP NOT NULL,
    update_date TIMESTAMP NOT NULL
);

CREATE TABLE server_monitoring_rule (
    server_id INTEGER,
    monitoring_rule_id INTEGER,
    create_date TIMESTAMP NOT NULL,
    update_date TIMESTAMP NOT NULL,
    PRIMARY KEY (server_id, monitoring_rule_id),
    FOREIGN KEY (server_id) REFERENCES server (id),
    FOREIGN KEY (monitoring_rule_id) REFERENCES monitoring_rule (id)
);
