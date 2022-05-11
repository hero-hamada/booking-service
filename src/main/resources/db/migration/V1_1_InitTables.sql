alter table account
    drop constraint account_user_id_fk;

alter table ticket
    drop constraint ticket_event_fk;

alter table ticket
    drop constraint ticket_user_fk;

drop table if exists account cascade;

drop table if exists event cascade;

drop table if exists ticket cascade;

drop table if exists "user" cascade;

create table account (
                         id  bigserial not null,
                         money numeric(19, 2) not null,
                         user_id int8 not null,
                         primary key (id)
);

create table event (
                       id  bigserial not null,
                       date date not null,
                       ticket_price numeric(19, 2) not null,
                       title varchar(255) not null,
                       primary key (id)
);

create table ticket (
                        id  bigserial not null,
                        category varchar(255) not null,
                        event_id int8 not null,
                        place int4 not null,
                        user_id int8 not null,
                        primary key (id)
);

create table "user" (
                        id  bigserial not null,
                        email varchar(255) not null,
                        is_account_non_locked boolean not null,
                        is_enabled boolean not null,
                        username varchar(255) not null,
                        password varchar(255) not null,
                        register_date date not null,
                        role varchar(255) not null,
                        primary key (id)
);

alter table account
    add constraint account_user_id_fk
        foreign key (user_id)
            references "user";

alter table ticket
    add constraint ticket_event_fk
        foreign key (event_id)
            references event;

alter table ticket
    add constraint ticket_user_fk
        foreign key (user_id)
            references "user";