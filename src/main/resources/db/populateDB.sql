DELETE FROM TEST.PUBLIC.VOTES;
DELETE FROM TEST.PUBLIC.MEALS;
DELETE FROM TEST.PUBLIC.RESTAURANTS;
DELETE FROM TEST.PUBLIC.USERS_ROLES;
DELETE FROM TEST.PUBLIC.USERS;
DELETE FROM TEST.PUBLIC.ROLES_AUTHORITIES;
DELETE FROM TEST.PUBLIC.ROLES;
DELETE FROM TEST.PUBLIC.AUTHORITIES;

ALTER SEQUENCE global_seq RESTART WITH 1000;

INSERT INTO PUBLIC.USERS (EMAIL, ENCRYPTED_PASSWORD, FIRST_NAME, LAST_NAME, USER_ID) VALUES
('test@test.com', '$2a$10$A2CgSZVPOn03bs.AerYRFuwz39fkRQ2jIkHkUryv0PPmBgpcrwRCC', 'UserName', 'UserLastName', 'dbkpmrTiPq1MbmFaK4LD'),
('admin@test.com', '$2a$10$3Yovv5H2pewgZibULOBnqub2s8YyyKECbx2.VBopC647JO/yVXyg2', 'Admin', 'Adminlastname', 'PH26ZZ7S1YJon7no997oTIUEr2hptK');

INSERT INTO PUBLIC.ROLES (NAME) VALUES
('ROLE_USER'),
('ROLE_ADMIN');

INSERT INTO PUBLIC.AUTHORITIES (NAME) VALUES
('READ_AUTHORITY'),
('WRITE_AUTHORITY'),
('DELETE_AUTHORITY');

INSERT INTO PUBLIC.RESTAURANTS (ADDRESS, NAME) VALUES
('Москва, проспект Маяковского 9', 'Белая устрица'),
('Москва, маршалла Жукова 12', 'Тбилиси'),
('Красная Пресня 44', 'Napoleon');

INSERT INTO PUBLIC.MEALS (DATE, DESCRIPTION, PRICE, RESTAURANT_ID) VALUES
('2020-04-28', 'Бараньи ребрышки', 16, 1007),
('2020-04-28', 'Макароны по флотски', 10, 1007),
('2020-04-29', 'Борщ по украински', 17, 1007),
('2020-04-29', 'Семга запеченная', 25, 1007),
('2020-04-30', 'Чайник чая', 10, 1008),
('2020-04-30', 'Узбекский Плов', 15, 1008),
(CURRENT_DATE, 'Лагман', 20, 1007),
(CURRENT_DATE, 'Блинчики с мясом', 25, 1007),
(CURRENT_DATE, 'Бастурма', 5, 1008),
(CURRENT_DATE, 'Шашлык', 50, 1009);

INSERT INTO PUBLIC.ROLES_AUTHORITIES (ROLES_ID, AUTHORITIES_ID) VALUES
(1002, 1004),
(1002, 1005),
(1003, 1004),
(1003, 1005),
(1003, 1006);

INSERT INTO PUBLIC.USERS (EMAIL, ENCRYPTED_PASSWORD, FIRST_NAME, LAST_NAME, USER_ID) VALUES
('user3@test.com', '$2a$10$IIramrophsPTsmUXA.hI3e6juoG6Sbrif1E/WwqJZQ.P0Iu2/fC3y', 'User3', 'TestUser', 'tMa4UtLClcoGUmozrDEt'),
('user4@test.com', '$2a$10$WtvdIZTXBJ.FIvpd2moTIueSfJdOdoSji2gOOHHVB8.ZykxGWlUu.', 'User4', 'TestUser', 'bJ3tnk2VANHwkPzXAUul'),
('swagger@test.com', '$2a$10$UNrUz1STmqeXd/QHI8tZEuCiDl7YzgkOO6Bwf53e4iIMARcJDQfR6', 'Swagger', 'SwaggerLN', 'uUXz4nF5SadFDYi7RuQ4');

INSERT INTO PUBLIC.VOTES (CREATED, DATE, RESTAURANT_ID, USER_ID) VALUES
('2020-04-28 10:15:00.000000', '2020-04-28', 1007, '1000'),
('2020-04-29 18:22:06.164000', '2020-04-29', 1007, '1000'),
('2020-04-30 10:15:00.000000', '2020-04-30', 1008, '1000'),
('2020-04-30 11:00:00.000000', '2020-04-30', 1007, '1001'),
(CURRENT_TIMESTAMP, CURRENT_DATE, 1007, '1000'),
(CURRENT_TIMESTAMP, CURRENT_DATE, 1007, '1001');
;

INSERT INTO PUBLIC.USERS_ROLES (USERS_ID, ROLES_ID) VALUES
(1000, 1002),
(1001, 1003),
(1020, 1002),
(1021, 1002),
(1022, 1002);
