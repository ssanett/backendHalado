create table schooladministration.schools
(
    id           bigint auto_increment
        primary key,
    city         varchar(255) null,
    house_number int          null,
    postal_code  varchar(255) null,
    street       varchar(255) null,
    school_name  varchar(255) null
);

create table schooladministration.students
(
    id                bigint auto_increment
        primary key,
    date_of_birth     date         null,
    school_age_status varchar(255) null,
    school_id         bigint       null,
    constraint FKdojmg8v3rw2ow4dev2b8q5oqq
        foreign key (school_id) references schooladministration.schools (id)
);

