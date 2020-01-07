drop table if exists movie;
create table movie(
    id integer primary key,
    title text unique,
    poster_url varchar(100),
    video_url varchar(100),
    description text
);
