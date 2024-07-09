CREATE SEQUENCE IF NOT EXISTS clients_client_id_seq INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE;

CREATE TABLE IF NOT EXISTS clients (
    client_id integer DEFAULT nextval('clients_client_id_seq'),
    code character varying(5),
    status_id integer,
    CONSTRAINT clients_pkey PRIMARY KEY (client_id)
);


CREATE TABLE IF NOT EXISTS account_statuses (
    status_code character varying(3) NOT NULL,
    name character varying(20),
    CONSTRAINT account_statuses_pkey PRIMARY KEY (status_code)
);

CREATE SEQUENCE IF NOT EXISTS account_id_seq INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE;

CREATE TABLE IF NOT EXISTS accounts (
    account_id integer DEFAULT nextval('account_id_seq'),
    client_id integer,
    account_number character varying(25) NOT NULL,
    name character varying(50),
    currency_code character varying(3),
    previous_day_balance money,
    created_at timestamp without time zone,
    account_type_code character varying(3),
    account_status_code character varying(3),
    CONSTRAINT accounts_pkey PRIMARY KEY (account_id),
    CONSTRAINT account_status_code FOREIGN KEY (account_status_code) REFERENCES account_statuses(status_code) NOT VALID,
    CONSTRAINT accounts_client_id_fkey FOREIGN KEY (client_id) REFERENCES clients(client_id)
);

CREATE TABLE IF NOT EXISTS account_operations (
    operation_id integer,
    account_id integer
);

CREATE TABLE IF NOT EXISTS account_types (
    name character varying(20),
    description character varying(100),
    type_code character varying(3) NOT NULL
);


CREATE TABLE IF NOT EXISTS client_statuses (
    status_id integer,
    name character varying(20),
    description character varying(50)
);


CREATE SEQUENCE IF NOT EXISTS account_statuses_history_id_seq INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START 1 CACHE 1 NO CYCLE;

CREATE TABLE IF NOT EXISTS account_statuses_history (
	account_id integer NOT NULL UNIQUE,
	previous_status_code varchar NOT NULL,
	date_time_of_change timestamp NULL,
	new_status_code varchar NOT NULL,
	id integer DEFAULT nextval('account_statuses_history_id_seq'),
	CONSTRAINT account_statuses_history_pkey PRIMARY KEY (id),
	CONSTRAINT "account_id-fk" FOREIGN KEY (account_id) REFERENCES accounts(account_id),
	CONSTRAINT new_account_status_fk FOREIGN KEY (new_status_code) REFERENCES account_statuses(status_code),
	CONSTRAINT previous_account_status_fk FOREIGN KEY (previous_status_code) REFERENCES account_statuses(status_code)
);
