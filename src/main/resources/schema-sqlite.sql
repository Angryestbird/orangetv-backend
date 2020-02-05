drop table if exists user;
drop table if exists authority;
drop table if exists role;
drop table if exists movie;
drop table if exists movie_route;
drop table if exists route;

create table user(
    id integer primary key,
    email varchar(100),
    password varchar(100),
    motto varchar(300),
    name varchar(100),
    enabled integer
);

create unique index index_user
on user (email);

create table authority(
    user_id integer,
    role_id integer
);

create table role(
    id integer primary key,
    role_name varchar(100)
);

create index index_authority
on authority (user_id,role_id);

create table movie(
    id integer primary key,
    title text unique,
    poster_url varchar(100),
    video_url varchar(100),
    description text
);

create table movie_route(
    id integer primary key,
    movie_id integer,
    route_id integer
);

create table route(
    id integer primary key,
    host varchar(100),
    sub_path varchar(200),
    port integer
);
