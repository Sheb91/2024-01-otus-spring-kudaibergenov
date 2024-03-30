create table authors(
    id bigserial,
    name varchar(128),
    primary key (id)
);

create table if not exists genres(
    id bigserial,
    name varchar(255),
    primary key (id)
);

create table if not exists books(
    id bigserial,
    name varchar(255),
    author_id bigint references authors(id),
    genre_id bigint references genres(id),
    primary key (id)
)