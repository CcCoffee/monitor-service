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


CREATE TRIGGER update_monitoring_rule_update_date_trigger
BEFORE UPDATE ON monitoring_rule
FOR EACH ROW
EXECUTE FUNCTION modify_update_date();

CREATE TRIGGER update_server_monitoring_rule_update_date_trigger
BEFORE UPDATE ON server_monitoring_rule
FOR EACH ROW
EXECUTE FUNCTION modify_update_date();