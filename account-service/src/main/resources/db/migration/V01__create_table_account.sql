CREATE TABLE account (
    account_number      VARCHAR(10) PRIMARY KEY,
    client_name         VARCHAR(100) NOT NULL,
    client_cpf          VARCHAR(11) NOT NULL UNIQUE,
    client_birth        DATE NOT NULL,
    type_account        VARCHAR(15) NOT NULL,
    has_job             TINYINT NOT NULL,
    own_house           TINYINT NOT NULL,
    score               VARCHAR(10) NOT NULL,
    created_at          DATETIME DEFAULT CURRENT_TIMESTAMP
);