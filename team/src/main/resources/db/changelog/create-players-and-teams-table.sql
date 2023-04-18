create table teams
(
    id   bigint auto_increment
        primary key,
    name varchar(255) null
);

create table players
(
    id            bigint auto_increment
        primary key,
    date_of_birth date         null,
    name          varchar(255) null,
    position      varchar(255) null,
    team_id       bigint       null,
    constraint FK5nglidr00c4dyybl171v6kask
        foreign key (team_id) references teams (id)
);

