INSERT INTO role (id, name, state, creation_date, update_date) VALUES (1, 'client', 'active', NOW(), NOW());
INSERT INTO role (id, name, state, creation_date, update_date) VALUES (2, 'owner', 'active', NOW(), NOW());
INSERT INTO role (id, name, state, creation_date, update_date) VALUES (3, 'admin', 'active', NOW(), NOW());
INSERT INTO role (id, name, state, creation_date, update_date) VALUES (4, 'ghost', 'active', NOW(), NOW());

-- Usuario ghost: permite el flujo de compra sin login real (ver EASY_STORE_GHOST_USER/PASSWORD).
INSERT INTO user (id, username, name, last_name, password, state, role_id, creation_date, update_date) VALUES (1, 'ghost@gmail.com', 'Ghost', 'User', '$2y$05$Dlzv2svl1.js4jfR3l4LgukGpkWl9DvGZJ7qrjZgB72974F6CLmgS', 'active', 4, NOW(), NOW());
INSERT INTO account (id, name, description, state, creation_date, update_date) VALUES (1, 'Cuenta ghost', 'Cuenta por defecto para compras sin registro', 'active', NOW(), NOW());
INSERT INTO account_has_user (account_id, user_id, state, creation_date, update_date) VALUES (1, 1, 'active', NOW(), NOW());
