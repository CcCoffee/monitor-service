-- Add test data to log_monitoring_rule table
INSERT INTO log_monitoring_rule (id, name, description, enabled, threshold, interval, notification_recipients, log_file_path, log_patterns, created_by, updated_by, create_date, update_date)
VALUES
    (1, 'Test Log Rule 1', 'Description for Test Log Rule 1', true, 5, 5000, ARRAY['test1@example.com', 'test2@example.com'], 'test.log', ARRAY['.*ERROR.*'], 'admin', 'admin', current_timestamp, current_timestamp);

-- Add test data to process_monitoring_rule table
INSERT INTO process_monitoring_rule (id, name, description, enabled, threshold, interval, notification_recipients, process_name_regex, created_by, updated_by, create_date, update_date)
VALUES
    (1, 'Test Process Rule 1', 'Description for Test Process Rule 1', true, 5, 5000, ARRAY['test1@example.com', 'test2@example.com'], '.*notepad.*', 'admin', 'admin', current_timestamp, current_timestamp);

-- Add test data to server table
INSERT INTO server (id, server_name, hostname, description, created_by, updated_by, create_date, update_date)
VALUES
    (1, 'My Server 1', 'Coffee', 'Kevin Server 1', 'admin', 'admin', current_timestamp, current_timestamp);
	
-- Associate log_monitoring_rule records with server records
INSERT INTO server_log_monitoring_rule (server_id, log_monitoring_rule_id, create_date, update_date)
VALUES
    (1, 1, current_timestamp, current_timestamp);

-- Associate process_monitoring_rule records with server records
INSERT INTO server_process_monitoring_rule (server_id, process_monitoring_rule_id, create_date, update_date)
VALUES
    (1, 1, current_timestamp, current_timestamp);
