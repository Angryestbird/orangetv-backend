insert into user (id, email, password, motto, name, enabled) values (
    0,
    "user@example.com",
    "password",
    "hello",
    "user",
    1
);
insert into user (id ,email, password, motto, name, enabled) values (
    1,
    "admin@example.com",
    "password",
    "hello",
    "admin",
    1
);

insert into role (id, role_name) values (0, "ROLE_USER");
insert into role (id, role_name) values (1, "ROLE_ADMIN");

insert into authority (user_id, role_id) values (0, 0);
insert into authority (user_id, role_id) values (1, 0);
insert into authority (user_id, role_id) values (1, 1);

insert into movie (title, poster_url, video_url) values (
    "变形金刚BD国英双语双字",
    "poster/变形金刚1.png",
    "变形金刚BD国英双语双字.mkv"
);
insert into movie (title, poster_url, video_url) values (
    "变形金刚2BD双语双字",
    "poster/变形金刚2.jpeg",
    "变形金刚2BD双语双字.mkv"
);
insert into movie (title, poster_url, video_url) values (
    "变形金刚3BD双语双字",
    "poster/变形金刚3.jpg",
    "变形金刚3BD双语双字.mkv"
);
insert into movie (title, poster_url, video_url) values (
    "变形金刚4：绝迹重生BD国英双语中英双字",
    "poster/变形金刚4.jpg",
    "变形金刚4：绝迹重生BD国英双语中英双字.mkv"
);
insert into movie (title, poster_url, video_url) values (
    "变形金刚5：最后的骑士BD中英双字",
    "poster/变形金刚5.jpg",
    "变形金刚5：最后的骑士BD中英双字.mp4"
);
insert into movie (title, poster_url, video_url) values (
    "古墓丽影：源起之战HD中英双字修正",
    "poster/古墓丽影.jpeg",
    "古墓丽影：源起之战HD中英双字修正.mp4"
);
