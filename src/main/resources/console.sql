CREATE TABLE role (
                      id SERIAL NOT NULL,
                      name VARCHAR(25) NOT NULL,
                      CONSTRAINT role_pk PRIMARY KEY (id)
);

CREATE TABLE call_type (
                           code INTEGER NOT NULL,
                           name VARCHAR(15) NOT NULL,
                           CONSTRAINT call_type_pk PRIMARY KEY (code)
);

CREATE TABLE tariff_type (
                             id SERIAL NOT NULL,
                             code varchar(25) not null,
                             name VARCHAR(25) NOT NULL,
                             minute_price INTEGER NOT NULL,
                             free_minutes INTEGER NOT NULL,
                             fixed_price INTEGER NOT NULL,
                             CONSTRAINT tariff_type_pk PRIMARY KEY (id)
);



CREATE TABLE abonent (
                         id SERIAL NOT NULL,
                         first_name VARCHAR(25) NOT NULL,
                         last_name VARCHAR(25) NOT NULL,
                         role_id INTEGER NOT NULL,
                         CONSTRAINT abonent_pk PRIMARY KEY (id)
);
CREATE TABLE phone_number (
                              id SERIAL NOT NULL,
                              phone_number VARCHAR(25) NOT NULL,
                              balance NUMERIC(6,2) NOT NULL,
                              abonent_id INTEGER NOT NULL,
                              tariff_type_id INTEGER NOT NULL,
                              CONSTRAINT phone_number_pk PRIMARY KEY (id),
                              CONSTRAINT phone_number_abonent_fk FOREIGN KEY (abonent_id)
                                  REFERENCES abonent (id),
                              CONSTRAINT phone_number_tariff_type_fk FOREIGN KEY (tariff_type_id)
                                  REFERENCES tariff_type (id)
);

CREATE TABLE call_record (
                             id SERIAL NOT NULL,
                             call_start TIMESTAMP NOT NULL,
                             call_end TIMESTAMP NOT NULL,
                             call_type_code INTEGER NOT NULL,
                             phone_number_id INTEGER NOT NULL,
                             CONSTRAINT call_record_pk PRIMARY KEY (id),
                             CONSTRAINT call_record_call_type_fk FOREIGN KEY (call_type_code)
                                 REFERENCES call_type (code),
                             CONSTRAINT call_record_phone_number_fk FOREIGN KEY (phone_number_id)
                                 REFERENCES phone_number (id)
);


CREATE TABLE phone_number_balance_history (
                                              id SERIAL NOT NULL,
                                              old_balance NUMERIC(6,2) NOT NULL,
                                              new_balance NUMERIC(6,2) NOT NULL,
                                              change_date TIMESTAMP NOT NULL,
                                              phone_number_id INTEGER NOT NULL,
                                              CONSTRAINT phone_number_balance_history_pk PRIMARY KEY (id),
                                              CONSTRAINT phone_number_balance_history_phone_number_fk FOREIGN KEY (phone_number_id)
                                                  REFERENCES phone_number (id)
);


ALTER TABLE abonent
    ADD CONSTRAINT abonent_role_fk FOREIGN KEY (role_id)
        REFERENCES role (id);

ALTER TABLE phone_number_balance_history
    ALTER COLUMN change_date SET DEFAULT NOW();

ALTER TABLE tariff_type ADD CONSTRAINT tariff_type_name_unique UNIQUE (name);

ALTER TABLE role ADD CONSTRAINT role_name_unique UNIQUE (name);

ALTER TABLE phone_number ADD CONSTRAINT phone_number_unique UNIQUE (phone_number);

ALTER TABLE call_type ADD CONSTRAINT call_type_name_unique UNIQUE (name);


INSERT INTO tariff_type (code, name, minute_price, free_minutes, fixed_price)
VALUES ('06', 'Безлимит 300', 1, 300, 100);

INSERT INTO tariff_type (code, name, minute_price, free_minutes, fixed_price)
VALUES ('03', 'Поминутный', 1.5, 0, 0);

INSERT INTO tariff_type (code, name, minute_price, free_minutes, fixed_price)
VALUES ('11', 'Обычный', 0.5, 100, 0);

INSERT INTO role (name) VALUES ('USER');
INSERT INTO role (name) VALUES ('ADMIN');

INSERT INTO call_type (name, code)
VALUES ('Исходящий', '01'), ('Входящий', '02');


CREATE OR REPLACE FUNCTION update_phone_number_balance_history()
    RETURNS TRIGGER AS $$
BEGIN
    IF OLD.balance <> NEW.balance THEN
        INSERT INTO phone_number_balance_history(phone_number_id, old_balance, new_balance, change_date)
        VALUES(OLD.id, OLD.balance, NEW.balance, NOW());
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER tr_update_phone_number_balance_history
    AFTER UPDATE OF balance ON phone_number
    FOR EACH ROW
EXECUTE FUNCTION update_phone_number_balance_history();


