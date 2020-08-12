INSERT INTO t_user (id, username, password, name, email, active)
 VALUES (1, 'doctor', '$2a$10$32UY5gfEN.197om2Y6cqfevmfeGtByEkIOzxKyc07.R6PO96Mjzhy', 'Doctor', 'doctor@fake-email.es', true);

INSERT INTO t_role (id, name, description, active)
 VALUES (1, 'ROLE_DP3T_ACCESS_CODE', 'Can create access codes for uploading exposed keys', true);

INSERT INTO t_user_roles (user_id, role_id) VALUES (1, 1);
