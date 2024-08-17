-- 테이블 삭제
DROP TABLE IF EXISTS PASSWORD;
DROP TABLE IF EXISTS USER;
DROP TABLE IF EXISTS LOAN_BROKER;
DROP TABLE IF EXISTS TOKEN;
DROP TABLE IF EXISTS AUTH;

-- 테이블 생성
CREATE TABLE USER (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '사용자 고유 ID',
    email VARCHAR(30) NOT NULL COMMENT '이메일 주소 (고유값)',
    name VARCHAR(20) NOT NULL COMMENT '사용자 이름',
    company_name VARCHAR(100) NOT NULL COMMENT '업체 이름',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성 일시',
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '수정 일시',
    deleted_at TIMESTAMP NULL COMMENT '삭제 일시 (논리 삭제 시 사용)',
    created_by VARCHAR(255) NULL COMMENT '생성한 사람',
    updated_by VARCHAR(255) NULL COMMENT '수정한 사람',
    UNIQUE (email)
);

CREATE TABLE PASSWORD (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '비밀번호 고유 ID',
    user_id BIGINT UNIQUE NOT NULL COMMENT '사용자 ID (외래키)',
    hashed_password VARCHAR(255) NOT NULL COMMENT '암호화된 비밀번호 (SHA-256)',
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
    expires_at TIMESTAMP NOT NULL,  -- 만료 시간 (expiresAt)
    revoked TINYINT(1) NOT NULL,  -- 철회 상태 (0: 유효한 토큰, 1: 유효하지 않은 토큰) 유효하지 않은 토큰으로 엑세스 토큰 재발급 불가능하다.
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
