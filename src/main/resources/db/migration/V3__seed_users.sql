-- Seed test users: 1 ADMIN, 2 EDITOR, 22 USER — 25 total.
-- All passwords are BCrypt-encoded "Test1234!" (strength 10).
-- Each user is paired with a shopping_cart and an ACTIVE subscription.
-- Subscription mix: DEFAULT (users 01-10), STANDARD (11-18), PREMIUM (19-22).

SET @pw = '$2y$10$16C6U.XYt61UIrlo/g4icudDFGUktMgpo7Zcen/26l6Cv3.MTGn0m';

-- ── ADMIN ────────────────────────────────────────────────────────────────────────

SET @c_admin = UUID_TO_BIN(UUID()); INSERT INTO shopping_cart (id, total_price) VALUES (@c_admin, 0.00);
SET @u_admin = UUID_TO_BIN(UUID()); INSERT INTO user (id, email, username, first_name, last_name, password, role, shopping_cart_id) VALUES (@u_admin, 'admin@techtome.com', 'techtome_admin', 'Admin', 'User', @pw, 'ADMIN', @c_admin);
INSERT INTO subscription (id, type, status, price, created_on, completed_on, owner_id) VALUES (UUID_TO_BIN(UUID()), 'PREMIUM', 'ACTIVE', 0.00, NOW(), DATE_ADD(NOW(), INTERVAL 1 MONTH), @u_admin);

-- ── EDITORS ──────────────────────────────────────────────────────────────────────

SET @c_ed1 = UUID_TO_BIN(UUID()); INSERT INTO shopping_cart (id, total_price) VALUES (@c_ed1, 0.00);
SET @u_ed1 = UUID_TO_BIN(UUID()); INSERT INTO user (id, email, username, first_name, last_name, password, role, shopping_cart_id) VALUES (@u_ed1, 'editor1@techtome.com', 'editor_alex', 'Alex', 'Morgan', @pw, 'EDITOR', @c_ed1);
INSERT INTO subscription (id, type, status, price, created_on, completed_on, owner_id) VALUES (UUID_TO_BIN(UUID()), 'STANDARD', 'ACTIVE', 0.00, NOW(), DATE_ADD(NOW(), INTERVAL 1 MONTH), @u_ed1);

SET @c_ed2 = UUID_TO_BIN(UUID()); INSERT INTO shopping_cart (id, total_price) VALUES (@c_ed2, 0.00);
SET @u_ed2 = UUID_TO_BIN(UUID()); INSERT INTO user (id, email, username, first_name, last_name, password, role, shopping_cart_id) VALUES (@u_ed2, 'editor2@techtome.com', 'editor_blake', 'Blake', 'Rivera', @pw, 'EDITOR', @c_ed2);
INSERT INTO subscription (id, type, status, price, created_on, completed_on, owner_id) VALUES (UUID_TO_BIN(UUID()), 'STANDARD', 'ACTIVE', 0.00, NOW(), DATE_ADD(NOW(), INTERVAL 1 MONTH), @u_ed2);

-- ── USERS 01–10 (DEFAULT subscription) ──────────────────────────────────────────

SET @c01 = UUID_TO_BIN(UUID()); INSERT INTO shopping_cart (id, total_price) VALUES (@c01, 0.00);
SET @u01 = UUID_TO_BIN(UUID()); INSERT INTO user (id, email, username, first_name, last_name, password, role, shopping_cart_id) VALUES (@u01, 'user01@techtome.com', 'user_casey', 'Casey', 'Jordan', @pw, 'USER', @c01);
INSERT INTO subscription (id, type, status, price, created_on, completed_on, owner_id) VALUES (UUID_TO_BIN(UUID()), 'DEFAULT', 'ACTIVE', 0.00, NOW(), DATE_ADD(NOW(), INTERVAL 1 MONTH), @u01);

SET @c02 = UUID_TO_BIN(UUID()); INSERT INTO shopping_cart (id, total_price) VALUES (@c02, 0.00);
SET @u02 = UUID_TO_BIN(UUID()); INSERT INTO user (id, email, username, first_name, last_name, password, role, shopping_cart_id) VALUES (@u02, 'user02@techtome.com', 'user_dana', 'Dana', 'Kim', @pw, 'USER', @c02);
INSERT INTO subscription (id, type, status, price, created_on, completed_on, owner_id) VALUES (UUID_TO_BIN(UUID()), 'DEFAULT', 'ACTIVE', 0.00, NOW(), DATE_ADD(NOW(), INTERVAL 1 MONTH), @u02);

SET @c03 = UUID_TO_BIN(UUID()); INSERT INTO shopping_cart (id, total_price) VALUES (@c03, 0.00);
SET @u03 = UUID_TO_BIN(UUID()); INSERT INTO user (id, email, username, first_name, last_name, password, role, shopping_cart_id) VALUES (@u03, 'user03@techtome.com', 'user_eli', 'Eli', 'Chen', @pw, 'USER', @c03);
INSERT INTO subscription (id, type, status, price, created_on, completed_on, owner_id) VALUES (UUID_TO_BIN(UUID()), 'DEFAULT', 'ACTIVE', 0.00, NOW(), DATE_ADD(NOW(), INTERVAL 1 MONTH), @u03);

SET @c04 = UUID_TO_BIN(UUID()); INSERT INTO shopping_cart (id, total_price) VALUES (@c04, 0.00);
SET @u04 = UUID_TO_BIN(UUID()); INSERT INTO user (id, email, username, first_name, last_name, password, role, shopping_cart_id) VALUES (@u04, 'user04@techtome.com', 'user_finley', 'Finley', 'Patel', @pw, 'USER', @c04);
INSERT INTO subscription (id, type, status, price, created_on, completed_on, owner_id) VALUES (UUID_TO_BIN(UUID()), 'DEFAULT', 'ACTIVE', 0.00, NOW(), DATE_ADD(NOW(), INTERVAL 1 MONTH), @u04);

SET @c05 = UUID_TO_BIN(UUID()); INSERT INTO shopping_cart (id, total_price) VALUES (@c05, 0.00);
SET @u05 = UUID_TO_BIN(UUID()); INSERT INTO user (id, email, username, first_name, last_name, password, role, shopping_cart_id) VALUES (@u05, 'user05@techtome.com', 'user_gray', 'Gray', 'Okafor', @pw, 'USER', @c05);
INSERT INTO subscription (id, type, status, price, created_on, completed_on, owner_id) VALUES (UUID_TO_BIN(UUID()), 'DEFAULT', 'ACTIVE', 0.00, NOW(), DATE_ADD(NOW(), INTERVAL 1 MONTH), @u05);

SET @c06 = UUID_TO_BIN(UUID()); INSERT INTO shopping_cart (id, total_price) VALUES (@c06, 0.00);
SET @u06 = UUID_TO_BIN(UUID()); INSERT INTO user (id, email, username, first_name, last_name, password, role, shopping_cart_id) VALUES (@u06, 'user06@techtome.com', 'user_harper', 'Harper', 'Nguyen', @pw, 'USER', @c06);
INSERT INTO subscription (id, type, status, price, created_on, completed_on, owner_id) VALUES (UUID_TO_BIN(UUID()), 'DEFAULT', 'ACTIVE', 0.00, NOW(), DATE_ADD(NOW(), INTERVAL 1 MONTH), @u06);

SET @c07 = UUID_TO_BIN(UUID()); INSERT INTO shopping_cart (id, total_price) VALUES (@c07, 0.00);
SET @u07 = UUID_TO_BIN(UUID()); INSERT INTO user (id, email, username, first_name, last_name, password, role, shopping_cart_id) VALUES (@u07, 'user07@techtome.com', 'user_indigo', 'Indigo', 'Santos', @pw, 'USER', @c07);
INSERT INTO subscription (id, type, status, price, created_on, completed_on, owner_id) VALUES (UUID_TO_BIN(UUID()), 'DEFAULT', 'ACTIVE', 0.00, NOW(), DATE_ADD(NOW(), INTERVAL 1 MONTH), @u07);

SET @c08 = UUID_TO_BIN(UUID()); INSERT INTO shopping_cart (id, total_price) VALUES (@c08, 0.00);
SET @u08 = UUID_TO_BIN(UUID()); INSERT INTO user (id, email, username, first_name, last_name, password, role, shopping_cart_id) VALUES (@u08, 'user08@techtome.com', 'user_jamie', 'Jamie', 'Lee', @pw, 'USER', @c08);
INSERT INTO subscription (id, type, status, price, created_on, completed_on, owner_id) VALUES (UUID_TO_BIN(UUID()), 'DEFAULT', 'ACTIVE', 0.00, NOW(), DATE_ADD(NOW(), INTERVAL 1 MONTH), @u08);

SET @c09 = UUID_TO_BIN(UUID()); INSERT INTO shopping_cart (id, total_price) VALUES (@c09, 0.00);
SET @u09 = UUID_TO_BIN(UUID()); INSERT INTO user (id, email, username, first_name, last_name, password, role, shopping_cart_id) VALUES (@u09, 'user09@techtome.com', 'user_kai', 'Kai', 'Osei', @pw, 'USER', @c09);
INSERT INTO subscription (id, type, status, price, created_on, completed_on, owner_id) VALUES (UUID_TO_BIN(UUID()), 'DEFAULT', 'ACTIVE', 0.00, NOW(), DATE_ADD(NOW(), INTERVAL 1 MONTH), @u09);

SET @c10 = UUID_TO_BIN(UUID()); INSERT INTO shopping_cart (id, total_price) VALUES (@c10, 0.00);
SET @u10 = UUID_TO_BIN(UUID()); INSERT INTO user (id, email, username, first_name, last_name, password, role, shopping_cart_id) VALUES (@u10, 'user10@techtome.com', 'user_lane', 'Lane', 'Kowalski', @pw, 'USER', @c10);
INSERT INTO subscription (id, type, status, price, created_on, completed_on, owner_id) VALUES (UUID_TO_BIN(UUID()), 'DEFAULT', 'ACTIVE', 0.00, NOW(), DATE_ADD(NOW(), INTERVAL 1 MONTH), @u10);

-- ── USERS 11–18 (STANDARD subscription) ─────────────────────────────────────────

SET @c11 = UUID_TO_BIN(UUID()); INSERT INTO shopping_cart (id, total_price) VALUES (@c11, 0.00);
SET @u11 = UUID_TO_BIN(UUID()); INSERT INTO user (id, email, username, first_name, last_name, password, role, shopping_cart_id) VALUES (@u11, 'user11@techtome.com', 'user_morgan', 'Morgan', 'Adeyemi', @pw, 'USER', @c11);
INSERT INTO subscription (id, type, status, price, created_on, completed_on, owner_id) VALUES (UUID_TO_BIN(UUID()), 'STANDARD', 'ACTIVE', 0.00, NOW(), DATE_ADD(NOW(), INTERVAL 1 MONTH), @u11);

SET @c12 = UUID_TO_BIN(UUID()); INSERT INTO shopping_cart (id, total_price) VALUES (@c12, 0.00);
SET @u12 = UUID_TO_BIN(UUID()); INSERT INTO user (id, email, username, first_name, last_name, password, role, shopping_cart_id) VALUES (@u12, 'user12@techtome.com', 'user_noel', 'Noel', 'Johansson', @pw, 'USER', @c12);
INSERT INTO subscription (id, type, status, price, created_on, completed_on, owner_id) VALUES (UUID_TO_BIN(UUID()), 'STANDARD', 'ACTIVE', 0.00, NOW(), DATE_ADD(NOW(), INTERVAL 1 MONTH), @u12);

SET @c13 = UUID_TO_BIN(UUID()); INSERT INTO shopping_cart (id, total_price) VALUES (@c13, 0.00);
SET @u13 = UUID_TO_BIN(UUID()); INSERT INTO user (id, email, username, first_name, last_name, password, role, shopping_cart_id) VALUES (@u13, 'user13@techtome.com', 'user_ocean', 'Ocean', 'Fischer', @pw, 'USER', @c13);
INSERT INTO subscription (id, type, status, price, created_on, completed_on, owner_id) VALUES (UUID_TO_BIN(UUID()), 'STANDARD', 'ACTIVE', 0.00, NOW(), DATE_ADD(NOW(), INTERVAL 1 MONTH), @u13);

SET @c14 = UUID_TO_BIN(UUID()); INSERT INTO shopping_cart (id, total_price) VALUES (@c14, 0.00);
SET @u14 = UUID_TO_BIN(UUID()); INSERT INTO user (id, email, username, first_name, last_name, password, role, shopping_cart_id) VALUES (@u14, 'user14@techtome.com', 'user_pat', 'Pat', 'Yamamoto', @pw, 'USER', @c14);
INSERT INTO subscription (id, type, status, price, created_on, completed_on, owner_id) VALUES (UUID_TO_BIN(UUID()), 'STANDARD', 'ACTIVE', 0.00, NOW(), DATE_ADD(NOW(), INTERVAL 1 MONTH), @u14);

SET @c15 = UUID_TO_BIN(UUID()); INSERT INTO shopping_cart (id, total_price) VALUES (@c15, 0.00);
SET @u15 = UUID_TO_BIN(UUID()); INSERT INTO user (id, email, username, first_name, last_name, password, role, shopping_cart_id) VALUES (@u15, 'user15@techtome.com', 'user_quinn', 'Quinn', 'Mensah', @pw, 'USER', @c15);
INSERT INTO subscription (id, type, status, price, created_on, completed_on, owner_id) VALUES (UUID_TO_BIN(UUID()), 'STANDARD', 'ACTIVE', 0.00, NOW(), DATE_ADD(NOW(), INTERVAL 1 MONTH), @u15);

SET @c16 = UUID_TO_BIN(UUID()); INSERT INTO shopping_cart (id, total_price) VALUES (@c16, 0.00);
SET @u16 = UUID_TO_BIN(UUID()); INSERT INTO user (id, email, username, first_name, last_name, password, role, shopping_cart_id) VALUES (@u16, 'user16@techtome.com', 'user_reese', 'Reese', 'Ivanov', @pw, 'USER', @c16);
INSERT INTO subscription (id, type, status, price, created_on, completed_on, owner_id) VALUES (UUID_TO_BIN(UUID()), 'STANDARD', 'ACTIVE', 0.00, NOW(), DATE_ADD(NOW(), INTERVAL 1 MONTH), @u16);

SET @c17 = UUID_TO_BIN(UUID()); INSERT INTO shopping_cart (id, total_price) VALUES (@c17, 0.00);
SET @u17 = UUID_TO_BIN(UUID()); INSERT INTO user (id, email, username, first_name, last_name, password, role, shopping_cart_id) VALUES (@u17, 'user17@techtome.com', 'user_sage', 'Sage', 'Nakamura', @pw, 'USER', @c17);
INSERT INTO subscription (id, type, status, price, created_on, completed_on, owner_id) VALUES (UUID_TO_BIN(UUID()), 'STANDARD', 'ACTIVE', 0.00, NOW(), DATE_ADD(NOW(), INTERVAL 1 MONTH), @u17);

SET @c18 = UUID_TO_BIN(UUID()); INSERT INTO shopping_cart (id, total_price) VALUES (@c18, 0.00);
SET @u18 = UUID_TO_BIN(UUID()); INSERT INTO user (id, email, username, first_name, last_name, password, role, shopping_cart_id) VALUES (@u18, 'user18@techtome.com', 'user_terry', 'Terry', 'Okonkwo', @pw, 'USER', @c18);
INSERT INTO subscription (id, type, status, price, created_on, completed_on, owner_id) VALUES (UUID_TO_BIN(UUID()), 'STANDARD', 'ACTIVE', 0.00, NOW(), DATE_ADD(NOW(), INTERVAL 1 MONTH), @u18);

-- ── USERS 19–22 (PREMIUM subscription) ──────────────────────────────────────────

SET @c19 = UUID_TO_BIN(UUID()); INSERT INTO shopping_cart (id, total_price) VALUES (@c19, 0.00);
SET @u19 = UUID_TO_BIN(UUID()); INSERT INTO user (id, email, username, first_name, last_name, password, role, shopping_cart_id) VALUES (@u19, 'user19@techtome.com', 'user_urban', 'Urban', 'Petrov', @pw, 'USER', @c19);
INSERT INTO subscription (id, type, status, price, created_on, completed_on, owner_id) VALUES (UUID_TO_BIN(UUID()), 'PREMIUM', 'ACTIVE', 0.00, NOW(), DATE_ADD(NOW(), INTERVAL 1 MONTH), @u19);

SET @c20 = UUID_TO_BIN(UUID()); INSERT INTO shopping_cart (id, total_price) VALUES (@c20, 0.00);
SET @u20 = UUID_TO_BIN(UUID()); INSERT INTO user (id, email, username, first_name, last_name, password, role, shopping_cart_id) VALUES (@u20, 'user20@techtome.com', 'user_val', 'Val', 'Andersen', @pw, 'USER', @c20);
INSERT INTO subscription (id, type, status, price, created_on, completed_on, owner_id) VALUES (UUID_TO_BIN(UUID()), 'PREMIUM', 'ACTIVE', 0.00, NOW(), DATE_ADD(NOW(), INTERVAL 1 MONTH), @u20);

SET @c21 = UUID_TO_BIN(UUID()); INSERT INTO shopping_cart (id, total_price) VALUES (@c21, 0.00);
SET @u21 = UUID_TO_BIN(UUID()); INSERT INTO user (id, email, username, first_name, last_name, password, role, shopping_cart_id) VALUES (@u21, 'user21@techtome.com', 'user_wren', 'Wren', 'Castillo', @pw, 'USER', @c21);
INSERT INTO subscription (id, type, status, price, created_on, completed_on, owner_id) VALUES (UUID_TO_BIN(UUID()), 'PREMIUM', 'ACTIVE', 0.00, NOW(), DATE_ADD(NOW(), INTERVAL 1 MONTH), @u21);

SET @c22 = UUID_TO_BIN(UUID()); INSERT INTO shopping_cart (id, total_price) VALUES (@c22, 0.00);
SET @u22 = UUID_TO_BIN(UUID()); INSERT INTO user (id, email, username, first_name, last_name, password, role, shopping_cart_id) VALUES (@u22, 'user22@techtome.com', 'user_xen', 'Xen', 'Bakker', @pw, 'USER', @c22);
INSERT INTO subscription (id, type, status, price, created_on, completed_on, owner_id) VALUES (UUID_TO_BIN(UUID()), 'PREMIUM', 'ACTIVE', 0.00, NOW(), DATE_ADD(NOW(), INTERVAL 1 MONTH), @u22);
