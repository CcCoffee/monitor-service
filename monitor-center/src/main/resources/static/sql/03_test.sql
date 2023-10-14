-- Add test data to process_monitoring_rule table
INSERT INTO monitoring_rule (id, name, type, application, description, enabled, threshold, interval, notification_recipients, process_name_regex, log_file_path, log_patterns, created_by, updated_by, create_date, update_date)
VALUES
    (1, 'Test Process Rule 1', 'PROCESS', null, 'Description for Test Process Rule 1', true, 5, 5000, ARRAY['test1@example.com', 'test2@example.com'], '.*notepad.*', null, null, 'admin', 'admin', current_timestamp, current_timestamp);

INSERT INTO monitoring_rule (id, name, type, application, description, enabled, threshold, interval, notification_recipients, process_name_regex, log_file_path, log_patterns, created_by, updated_by, create_date, update_date)
VALUES
    (2, 'Test Log  Rule 1', 'LOG', 'CMS', 'Description for Test Process Rule 1', true, 5, 5000, ARRAY['test1@example.com', 'test2@example.com'], null, 'test.log', ARRAY['.*ERROR.*'], 'admin', 'admin', current_timestamp, current_timestamp);

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

INSERT INTO alert_channel (name, type, description, configuration, created_by, updated_by)
VALUES ('Email Channel 1', 'EMAIL', 'test', '{"emailAddress": ["example1@example.com","example2@example.com"], "emailTemplate": "template1"}', 'admin', 'admin');

INSERT INTO alert_channel (name, type, description, configuration, created_by, updated_by)
VALUES ('Email Channel 2', 'EMAIL', 'test','{"emailAddress": ["example1@example.com","example2@example.com"], "emailTemplate": "template1"}', 'admin', 'admin');

INSERT INTO alert_channel (name, type, description, configuration, created_by, updated_by)
VALUES ('Email Channel 3', 'EMAIL', 'test','{"emailAddress": ["example1@example.com","example2@example.com"], "emailTemplate": "template1"}', 'admin', 'admin');

INSERT INTO alert (name, description, severity, type, source, status, repeat_count, start_time, end_time, acknowledged_by, rule_id, action)
VALUES ('Alert 1', 'Description of Alert 1', 'High', 'LOG', 'Source A', 'Open', 0, '2023-10-13 10:00:00', NULL, NULL, 1, 'Action A');

INSERT INTO alert (name, description, severity, type, source, status, repeat_count, start_time, end_time, acknowledged_by, rule_id, action)
VALUES ('Alert 2', 'Description of Alert 2', 'Medium', 'PROCESS', 'Source B', 'Closed', 1, '2023-10-13 11:00:00', '2023-10-13 12:00:00', 'John Doe', 2, 'Action B');

INSERT INTO alert (name, description, severity, type, source, status, repeat_count, start_time, end_time, acknowledged_by, rule_id, action)
VALUES ('Alert 3', 'Description of Alert 3', 'High', 'LOG', 'Source A', 'Acknowledged', 0, '2023-10-13 10:00:00', NULL, 'admin', 1, 'Action A');
