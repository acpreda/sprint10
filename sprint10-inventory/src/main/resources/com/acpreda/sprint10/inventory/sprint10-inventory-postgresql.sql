/*
drop table entry;
drop table tx;
drop table account;
*/

create table account (
    account varchar(127) not null,
    unit varchar(7) not null,
    memo boolean not null,
    summary boolean not null,
    balance numeric(12,6) not null,
    name varchar(255) not null,
    constraint pk_account primary key (account)
);

create table tx (
    tx bigint not null,
    when_booked timestamptz not null,
    description varchar(255),
    constraint pk_tx primary key (tx)
);

create index idx_tx_when_booked on tx(when_booked);

create table entry (
    tx bigint not null,
    account varchar(127) not null,
    when_charged timestamptz not null,
    amount numeric(12,6) not null,
    constraint fk_entry_tx foreign key (tx) references tx(tx),
    constraint fk_entry_account foreign key (account) references account(account)
);

create index idx_entry_tx on entry(tx);
create index idx_entry_account on entry(account);
create index idx_entry_when_charged on entry(when_charged);

