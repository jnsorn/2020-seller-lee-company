create table if not exists organization
(
    group_id bigint auto_increment primary key,
    name     varchar(255) not null,
    code     varchar(6)   not null
);
