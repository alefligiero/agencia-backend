INSERT INTO _user (id, first_name, last_name, email, password, created_at, updated_at) VALUES (gen_random_uuid(),
                                                                                               'admin',
                                                                                               'admin',
                                                                                               'admin@admin.com',
                                                                                               '$2a$12$bLviwqgr7cM7m49CD3xI3.sZ1osmkHSDVHuyT4kL8IMRJ.CA5wV8u',
                                                                                               now(),
                                                                                               now());

INSERT INTO _user (id, first_name, last_name, email, password, created_at, updated_at) VALUES (gen_random_uuid(),
                                                                                               'user',
                                                                                               'user',
                                                                                               'user@user.com',
                                                                                               '$2a$12$fQK8b6TLRjKb2zDzbnprOe5ldcTCWtuHGrNuR1WbTOvh.cJlCBpOa',
                                                                                               now(),
                                                                                               now());