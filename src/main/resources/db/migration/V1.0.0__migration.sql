CREATE TABLE if not exists statuses
(
    id uuid not null,
    name_status text not null,
    user_id text not null,
    order_num integer not null,
    CONSTRAINT pk_status primary key(id)

);

CREATE TABLE if not exists vacancies
(
    id uuid not null,
    user_id text not null,
    name_vacancy text not null,
    status_id uuid,
    company text,
    salary integer,
    notes text not null,
    primary key(id),
    CONSTRAINT fk_status_name FOREIGN KEY(status_id)  REFERENCES statuses(id)
);


CREATE TABLE if not exists events
(
    id uuid not null,
    user_id text not null,
    begin_date timestamptz,
    vacancy_id uuid,
    is_completed boolean default false,
    notes  text not null,
    primary key(id),
    CONSTRAINT fk_vacancy FOREIGN KEY(vacancy_id)  REFERENCES vacancies(id)
);

CREATE TABLE if not exists contacts
(
    id uuid not null,
    user_id text not null,
    name text not null,
    company text,
    mail text,
    telephone text,
    notes text,
    primary key(id)
);

CREATE TABLE if not exists vacancies_contacts
(
    vacancy_id uuid not null,
    contact_id uuid not null,
    CONSTRAINT pk_v_c primary key(vacancy_id,contact_id),
    CONSTRAINT fk_vacancy FOREIGN KEY(vacancy_id)  REFERENCES vacancies(id),
    CONSTRAINT fk_contact FOREIGN KEY(contact_id)  REFERENCES contacts(id)
);