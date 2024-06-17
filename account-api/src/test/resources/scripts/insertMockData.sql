
--/* client_statuses */
INSERT INTO client_statuses VALUES('1', 'UNVERIFIED', 'Pending client approval status') ON CONFLICT DO NOTHING;
INSERT INTO client_statuses VALUES('2', 'ACTIVE', 'Client active status') ON CONFLICT DO NOTHING;
INSERT INTO client_statuses VALUES('3', 'BLOCKED', 'The client was blocked') ON CONFLICT DO NOTHING;
INSERT INTO client_statuses VALUES('4', 'INACTIVE', 'When a client stops servicing the bank') ON CONFLICT DO NOTHING;

--/* account_statuses */
INSERT INTO account_statuses(status_code, name) VALUES ('NEW', 'New') ON CONFLICT DO NOTHING;
INSERT INTO account_statuses(status_code, name) VALUES ('ACT', 'Active') ON CONFLICT DO NOTHING;
INSERT INTO account_statuses(status_code, name) VALUES ('CLS', 'Closed') ON CONFLICT DO NOTHING;
INSERT INTO account_statuses(status_code, name) VALUES ('BLC', 'Blocked') ON CONFLICT DO NOTHING;

INSERT INTO account_types (name, description, type_code)
VALUES('Payment account', 'It is a type of account that allows to store money and make financial transactions', 'PMT') ON CONFLICT DO NOTHING;