create table role
(
    id bigserial constraint role_pk primary key,
    name varchar(25) not null constraint role_name_unique unique
);
INSERT INTO role (name) VALUES ('ROLE_USER'), ('ROLE_ADMIN');


create table call_type
(
    code integer constraint call_type_pk primary key,
    name varchar(15) not null constraint call_type_name_unique unique
);
INSERT INTO call_type (name, code)
VALUES ('Исходящий', '01'), ('Входящий', '02');


create table tariff_type
(
    id bigserial constraint tariff_type_pk primary key,
    code         varchar(25) not null constraint tariff_type_code_unique unique,
    name         varchar(25) not null constraint tariff_type_name_unique unique,
    minute_price integer     not null,
    free_minutes integer     not null,
    fixed_price  integer     not null
);
INSERT INTO tariff_type (code, name, minute_price, free_minutes, fixed_price)
VALUES ('06', 'Безлимит 300', 1, 300, 100), ('03', 'Поминутный', 1.5, 0, 0), ('11', 'Обычный', 0.5, 100, 0);


create table abonent
(
    id bigserial constraint abonent_pk primary key,
    first_name varchar(255) not null ,
    last_name  varchar(255) not null ,
    username   varchar(255) not null constraint abonent_username_unique unique,
    password   varchar(255) not null
);


create table phone_number
(
    id bigserial not null constraint phone_number_pk primary key,
    balance        double precision not null ,
    free_minute    integer not null ,
    phone_number   varchar(255) not null ,
    tariff_type_id bigint not null constraint phone_number_tariff_type_fk references tariff_type(id),
    abonent_id     bigint constraint phone_number_abonent_fk references abonent(id)
);


create table call_record
(
    id bigserial not null constraint call_record_pk primary key,
    call_end        timestamp not null ,
    call_start      timestamp not null ,
    call_type_code  integer not null constraint call_record_call_type_fk references call_type (code),
    phone_number_id bigint not null constraint call_record_phone_number_fk references phone_number(id)
);


create table phone_number_balance_history
(
    id  serial not null constraint phone_number_balance_history_pk primary key,
    change_data     timestamp default now(),
    new_balance     double precision not null ,
    old_balance     double precision not null ,
    phone_number_id bigint constraint phone_number_balance_history_phone_number_fk references phone_number(id)
);

create table user_roles
(
    abonent_id bigint not null constraint user_roles_abonent_fk references abonent,
    role_id    bigint not null constraint user_roles_role_fk references role,
    primary key (abonent_id, role_id)
);

CREATE OR REPLACE FUNCTION update_phone_number_balance_history()
    RETURNS TRIGGER AS $$
BEGIN
    IF OLD.balance <> NEW.balance THEN
        INSERT INTO phone_number_balance_history(phone_number_id, old_balance, new_balance)
        VALUES(OLD.id, OLD.balance, NEW.balance);
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER tr_update_phone_number_balance_history
    AFTER UPDATE OF balance ON phone_number
    FOR EACH ROW
EXECUTE FUNCTION update_phone_number_balance_history();

INSERT INTO abonent (id, first_name, last_name, username, password)
VALUES (1, 'Abonent','Test','user','$2a$10$xKgEuO4TcHw6ctRoWj/2E.ruaTXFcm7gFaAJeNdVwkLhPkbD8NAQ2'),
    (2, 'Manager','Test','admin','$2a$10$3MTpcAY4PG9DXdrOZmQU5uH07ohO7hypdjWiDE4e9zLC0RcRiVBi6');

INSERT INTO user_roles (abonent_id, role_id) VALUES (1,1), (2,2);


