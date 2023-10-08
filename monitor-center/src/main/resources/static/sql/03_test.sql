-- Add test data to process_monitoring_rule table
INSERT INTO monitoring_rule (id, name, type, description, enabled, threshold, interval, notification_recipients, process_name_regex, log_file_path, log_patterns, created_by, updated_by, create_date, update_date)
VALUES
    (1, 'Test Process Rule 1', 'PROCESS', 'Description for Test Process Rule 1', true, 5, 5000, ARRAY['test1@example.com', 'test2@example.com'], '.*notepad.*', null, null, 'admin', 'admin', current_timestamp, current_timestamp);

INSERT INTO monitoring_rule (id, name, type, description, enabled, threshold, interval, notification_recipients, process_name_regex, log_file_path, log_patterns, created_by, updated_by, create_date, update_date)
VALUES
    (2, 'Test Log  Rule 1', 'LOG', 'Description for Test Process Rule 1', true, 5, 5000, ARRAY['test1@example.com', 'test2@example.com'], null, 'test.log', ARRAY['.*ERROR.*'], 'admin', 'admin', current_timestamp, current_timestamp);

-- Add test data to server table
INSERT INTO server (id, server_name, hostname, description, created_by, updated_by, create_date, update_date)
VALUES
    (1, 'My Server 1', 'Coffee', 'Kevin Server 1', 'admin', 'admin', current_timestamp, current_timestamp);

INSERT INTO server (id, server_name, hostname, description, created_by, updated_by, create_date, update_date)
VALUES
    (2, 'My Server 2', 'Linux Server 1', 'Linux Server 1', 'admin', 'admin', current_timestamp, current_timestamp);

-- Associate process_monitoring_rule records with server records
INSERT INTO server_monitoring_rule (server_id, monitoring_rule_id, create_date, update_date)
VALUES
    (1, 1, current_timestamp, current_timestamp);

INSERT INTO server_monitoring_rule (server_id, monitoring_rule_id, create_date, update_date)
VALUES
    (1, 2, current_timestamp, current_timestamp);
