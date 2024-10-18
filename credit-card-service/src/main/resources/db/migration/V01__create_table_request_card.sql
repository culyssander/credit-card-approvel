CREATE TABLE request_card(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    account_numbers VARCHAR(10),
    client_cpf VARCHAR(11) NOT NULL,
    has_job TINYINT,
    own_house TINYINT,
    score VARCHAR(10),
    result VARCHAR(20),
    reason VARCHAR(100),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100)
);