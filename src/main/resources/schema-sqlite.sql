drop table if exists movie;
drop table if exists movie_route;
drop table if exists route;

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
