INSERT INTO roles (role) VALUES ('ADMIN') ON DUPLICATE KEY UPDATE role = role;
INSERT INTO roles (role) VALUES ('USER')  ON DUPLICATE KEY UPDATE role = role;

-- создание админа, пароль - "admin"
INSERT INTO users (username, email, password)
    VALUES ('admin','admin@example.com','$2a$12$m9S5TBWV4Y/22UyP./aM0ewxlIDozE.PePAiVcwv2JiQCbLWJMTg2')
    ON DUPLICATE KEY UPDATE username = username;

-- выдача роли ADMIN админу
INSERT INTO users_roles (user_id, role_id)
    SELECT u.id, r.id FROM users u JOIN roles r
    ON r.role IN ('ADMIN', 'USER') WHERE u.username = 'admin';
