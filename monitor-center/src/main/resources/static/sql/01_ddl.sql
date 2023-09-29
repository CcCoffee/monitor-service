CREATE TABLE log_monitoring_rule (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    enabled BOOLEAN NOT NULL,
    threshold INTEGER,
    interval INTEGER,
    notification_recipients TEXT[],
    log_file_path VARCHAR(255) NOT NULL,
    log_patterns TEXT[] NOT NULL,
    created_by VARCHAR(255) NOT NULL,
    updated_by VARCHAR(255) NOT NULL,
    create_date TIMESTAMP NOT NULL,
    update_date TIMESTAMP NOT NULL
);

CREATE TABLE process_monitoring_rule (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    enabled BOOLEAN NOT NULL,
    threshold INTEGER,
    interval INTEGER,
    notification_recipients TEXT[] NOT NULL,
    process_name_regex VARCHAR(255) NOT NULL,
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

CREATE TABLE server_log_monitoring_rule (
    server_id INTEGER,
    log_monitoring_rule_id INTEGER,
    create_date TIMESTAMP NOT NULL,
    update_date TIMESTAMP NOT NULL,
    PRIMARY KEY (server_id, log_monitoring_rule_id),
    FOREIGN KEY (server_id) REFERENCES server (id),
    FOREIGN KEY (log_monitoring_rule_id) REFERENCES log_monitoring_rule (id)
);

CREATE TABLE server_process_monitoring_rule (
    server_id INTEGER,
    process_monitoring_rule_id INTEGER,
    create_date TIMESTAMP NOT NULL,
    update_date TIMESTAMP NOT NULL,
    PRIMARY KEY (server_id, process_monitoring_rule_id),
    FOREIGN KEY (server_id) REFERENCES server (id),
    FOREIGN KEY (process_monitoring_rule_id) REFERENCES process_monitoring_rule (id)
);
