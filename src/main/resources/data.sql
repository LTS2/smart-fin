INSERT INTO USER (id, name, email, company_name, created_by, updated_by) VALUES
(1, 'John Doe', 'john.doe@example.com','와이에스메타', 'system', 'system');

INSERT INTO ROLE_TYPE(id, code, name, created_by, updated_by) VALUES (1, 'ADMIN', '어드민 권한', 'system', 'system');
INSERT INTO ROLE_TYPE(id, code, name, created_by, updated_by) VALUES (2, 'USER', '유저 권한', 'system', 'system');