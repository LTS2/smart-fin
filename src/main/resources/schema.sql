-- 테이블 삭제
DROP TABLE IF EXISTS PASSWORD;
DROP TABLE IF EXISTS USER;
DROP TABLE IF EXISTS LOAN_BROKER;
DROP TABLE IF EXISTS TOKEN;
DROP TABLE IF EXISTS AUTH;

-- 테이블 생성
CREATE TABLE USER (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '사용자 고유 ID',
    name VARCHAR(50) NULL COMMENT '사용자 이름',
    rrn VARBINARY(30) NULL COMMENT '주민등록번호 (암호화됨) Resident Registration Number(RRN)',
    phone_number VARCHAR(20) NULL COMMENT '전화번호',
    address VARCHAR(500) NULL COMMENT '주소',
    email VARCHAR(30) NULL COMMENT '이메일 주소 (고유값)',
    token VARCHAR(512) NULL COMMENT '사용자 인증 토큰',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성 일시',
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '수정 일시',
    deleted_at TIMESTAMP NULL COMMENT '삭제 일시 (논리 삭제 시 사용)',
    created_by VARCHAR(255) NULL COMMENT '생성한 사람',
    updated_by VARCHAR(255) NULL COMMENT '수정한 사람',
    UNIQUE (email)
);

CREATE TABLE LOAN_BROKER (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '대부중개 고유 ID',
    broker_type VARCHAR(11) NOT NULL COMMENT '중개 유형 (Loan_Broker, Corporation, Individual)',
    company_name VARCHAR(255) NOT NULL COMMENT '회사 이름',
    owner_name VARCHAR(30) NOT NULL COMMENT '대표자 이름',
    company_address VARCHAR(255) NOT NULL COMMENT '회사 주소',
    tax_id VARCHAR(30) NOT NULL COMMENT '세금 ID',
    loan_broker_registration_number VARCHAR(255) NOT NULL COMMENT '대부 중개 등록 번호',
    association_registration_number VARCHAR(255) NOT NULL COMMENT '협회 등록 번호',
    association_registration_date DATE NOT NULL COMMENT '협회 등록 날짜',
    phone_number VARCHAR(255) NOT NULL COMMENT '회사 전화번호',
    contact_person_name VARCHAR(30) NOT NULL COMMENT '담당자 이름',
    contact_person_phone VARCHAR(30) NOT NULL COMMENT '담당자 전화번호',
    contact_person_email VARCHAR(255) NOT NULL COMMENT '담당자 이메일',
    tax_id_certificate VARCHAR(255) NOT NULL COMMENT '세금 ID 증명서',
    loan_broker_certificate VARCHAR(255) NOT NULL COMMENT '대부 중개 증명서',
    association_training_certificate VARCHAR(255) NOT NULL COMMENT '협회 교육 수료 증명서',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성 일시',
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '수정 일시',
    deleted_at TIMESTAMP NULL COMMENT '삭제 일시 (논리 삭제 시 사용)',
    created_by VARCHAR(255) NULL COMMENT '생성한 사람',
    updated_by VARCHAR(255) NULL COMMENT '수정한 사람'
);

CREATE TABLE PASSWORD (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '비밀번호 고유 ID',
    user_id BIGINT UNIQUE NOT NULL COMMENT '사용자 ID (외래키)',
    hashed_password VARCHAR(255) NOT NULL COMMENT '암호화된 비밀번호 (SHA-256)',
    salt VARCHAR(255) NULL COMMENT '비밀번호 암호화에 사용된 솔트값',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성 일시',
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '수정 일시',
    deleted_at TIMESTAMP NULL COMMENT '삭제 일시 (논리 삭제 시 사용)',
    created_by VARCHAR(255) NULL COMMENT '생성한 사람',
    updated_by VARCHAR(255) NULL COMMENT '수정한 사람'
    -- FOREIGN KEY (user_id) REFERENCES user(id)
);

CREATE TABLE TOKEN (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '토큰 고유 ID',
    user_id BIGINT NOT NULL COMMENT '사용자 ID (외래키)',
    refresh_token VARCHAR(512) NOT NULL COMMENT 'JWT 리프레시 토큰',
    device_id VARCHAR(255) DEFAULT NULL COMMENT '장치 ID (선택적)',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성 일시',
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '수정 일시',
    deleted_at TIMESTAMP NULL COMMENT '삭제 일시 (논리 삭제 시 사용)',
    created_by VARCHAR(255) NULL COMMENT '생성한 사람',
    updated_by VARCHAR(255) NULL COMMENT '수정한 사람'
    -- FOREIGN KEY (user_id) REFERENCES user(id)
);

CREATE TABLE AUTH (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '권한 고유 ID',
    user_id BIGINT NOT NULL DEFAULT 0 COMMENT '사용자 ID (외래키)',
    is_admin TINYINT(1) NOT NULL DEFAULT 0 COMMENT '관리자 권한 (0: 아니오, 1: 예)',
    is_manager TINYINT(1) NOT NULL DEFAULT 0 COMMENT '매니저 권한 (0: 아니오, 1: 예)',
    is_user TINYINT(1) NOT NULL DEFAULT 0 COMMENT '일반 사용자 권한 (0: 아니오, 1: 예)',
    approval_status VARCHAR(10) DEFAULT 'WAIT' NOT NULL COMMENT '회원가입 승인 상태 (WAIT: 대기중, APPROVED: 승인됨, REJECTED: 거부됨)',
    created_at TIMESTAMP NOT NULL COMMENT '생성 일시',
    updated_at TIMESTAMP NOT NULL COMMENT '수정 일시',
    deleted_at TIMESTAMP NULL COMMENT '삭제 일시 (논리 삭제 시 사용)',
    created_by VARCHAR(255) NULL COMMENT '생성한 사람',
    updated_by VARCHAR(255) NULL COMMENT '수정한 사람'
    -- CONSTRAINT fk_auth_user FOREIGN KEY (user_id) REFERENCES USER(id)
);
