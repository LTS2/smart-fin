-- -- ROLE_TYPE 테이블에 기본 데이터 삽입
-- INSERT INTO ROLE_TYPE(code, name, created_by, updated_by) VALUES
-- ('ADMIN', '어드민 권한', 'system', 'system'),
-- ('USER', '유저 권한', 'system', 'system');

INSERT INTO ROLE_TYPE(code, name) VALUES
('ADMIN', '어드민 권한'),
('USER', '유저 권한');

-- USER 테이블에 데이터 삽입
INSERT INTO USER (id, name, email, company_name, created_by, updated_by) VALUES
(1, '이우진', 'leewoojin98@naver.com', '와이에스메타', 'system', 'system');

-- USER_ROLE 테이블에 데이터 삽입
INSERT INTO USER_ROLE (user_id, code, created_by, updated_by) VALUES
(1, 'USER', 'system', 'system');
