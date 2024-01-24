INSERT INTO user_role (user_id, role_id) VALUES (
    (SELECT id FROM _user WHERE first_name = 'admin'),
    (SELECT id FROM role WHERE name = 'ROLE_ADMIN')
                                                );

INSERT INTO user_role (user_id, role_id) VALUES (
    (SELECT id FROM _user WHERE first_name = 'admin'),
    (SELECT id FROM role WHERE name = 'ROLE_USER')
                                                );

INSERT INTO user_role (user_id, role_id) VALUES (
    (SELECT id FROM _user WHERE first_name = 'user'),
    (SELECT id FROM role WHERE name = 'ROLE_USER')
                                                );
