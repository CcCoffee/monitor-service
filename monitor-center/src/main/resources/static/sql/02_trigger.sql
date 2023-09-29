CREATE OR REPLACE FUNCTION modify_update_date()
RETURNS TRIGGER AS $$
BEGIN
    NEW.update_date = NOW();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_server_update_date_trigger
BEFORE UPDATE ON server
FOR EACH ROW
EXECUTE FUNCTION modify_update_date();

CREATE TRIGGER update_log_monitoring_rule_update_date_trigger
BEFORE UPDATE ON log_monitoring_rule
FOR EACH ROW
EXECUTE FUNCTION modify_update_date();

CREATE TRIGGER update_process_monitoring_rule_update_date_trigger
BEFORE UPDATE ON process_monitoring_rule
FOR EACH ROW
EXECUTE FUNCTION modify_update_date();

CREATE TRIGGER update_server_log_monitoring_rule_update_date_trigger
BEFORE UPDATE ON server_log_monitoring_rule
FOR EACH ROW
EXECUTE FUNCTION modify_update_date();

CREATE TRIGGER update_server_process_monitoring_rule_update_date_trigger
BEFORE UPDATE ON server_process_monitoring_rule
FOR EACH ROW
EXECUTE FUNCTION modify_update_date();