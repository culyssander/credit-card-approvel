CREATE TABLE users(
    id BIGINT       PRIMARY KEY AUTO_INCREMENT,
    name            VARCHAR(100) NOT NULL,
    email           VARCHAR(150) NOT NULL UNIQUE,
    password        VARCHAR(150) NOT NULL,
    status          TINYINT DEFAULT 0,
    role            VARCHAR(10) NOT NULL,
    created_at      DATETIME DEFAULT CURRENT_TIMESTAMP,
    last_updated    DATETIME,
    last_login      DATETIME
);