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
