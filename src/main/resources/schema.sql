-- 테이블 삭제
DROP TABLE IF EXISTS password;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS loan_broker;
DROP TABLE IF EXISTS token;

-- 테이블 생성
CREATE TABLE user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NULL,
    rrn VARBINARY(30) NULL COMMENT 'Resident Registration Number(RRN)',
    phone_number VARCHAR(20) NULL,
    address VARCHAR(500) NULL,
    email VARCHAR(30) NULL ,
    token VARCHAR(512) NULL,
--     created_by VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
--     updated_by VARCHAR(255) NULL,
--     updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
--     updated_at TIMESTAMP NULL,
    deleted_at TIMESTAMP NULL,
    UNIQUE (email)
);

CREATE TABLE loan_broker (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    broker_type VARCHAR(11) NOT NULL COMMENT '대부중개(Loan_Broker), 법인(Corporation), 개인(Individual)',
    company_name VARCHAR(255) NOT NULL,
    owner_name VARCHAR(30) NOT NULL,
    company_address VARCHAR(255) NOT NULL,
    tax_id VARCHAR(30) NOT NULL,
    loan_broker_registration_number VARCHAR(255) NOT NULL,
    association_registration_number VARCHAR(255) NOT NULL,
    association_registration_date DATE NOT NULL,
    phone_number VARCHAR(255) NOT NULL,
    contact_person_name VARCHAR(30) NOT NULL,
    contact_person_phone VARCHAR(30) NOT NULL,
    contact_person_email VARCHAR(255) NOT NULL,
    tax_id_certificate VARCHAR(255) NOT NULL,
    loan_broker_certificate VARCHAR(255) NOT NULL,
    association_training_certificate VARCHAR(255) NOT NULL,
--     created_by VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
--     updated_by VARCHAR(255) NULL,
--     updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
--     updated_at TIMESTAMP NULL,
    deleted_at TIMESTAMP NULL
);

CREATE TABLE password (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    hashed_password VARCHAR(255) NOT NULL COMMENT 'SHA-256 암호화',
    salt VARCHAR(255) NULL COMMENT '솔트값',
--     created_by VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
--     updated_by VARCHAR(255) NULL,
--     updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
--     updated_at TIMESTAMP NULL,
    deleted_at TIMESTAMP NULL
--     FOREIGN KEY (user_id) REFERENCES user(id)
);

CREATE TABLE token (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    refresh_token VARCHAR(512) NOT NULL,
    device_id VARCHAR(255) DEFAULT NULL COMMENT '(Optional) 추후 제거 가능',
--     created_by VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
--     updated_by VARCHAR(255) NULL,
--     updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,    deleted_at TIMESTAMP NULL
--     FOREIGN KEY (user_id) REFERENCES user(id)
);
