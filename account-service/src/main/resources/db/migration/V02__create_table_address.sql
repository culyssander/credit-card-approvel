CREATE TABLE address (
    id                  INT PRIMARY KEY AUTO_INCREMENT,
    cep                 VARCHAR(9),
    address             VARCHAR(100),
    numbers             VARCHAR(11),
    complement          VARCHAR(20),
    account_number      VARCHAR(10) NOT NULL,
    created_at          DATETIME DEFAULT CURRENT_TIMESTAMP
);