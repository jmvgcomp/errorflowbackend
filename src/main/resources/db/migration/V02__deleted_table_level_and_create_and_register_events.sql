DROP TABLE level;

CREATE TABLE event(
    id BIGINT(20) AUTO_INCREMENT,
    level VARCHAR(15) NOT NULL,
    description VARCHAR(50) NOT NULL,
    origin VARCHAR(50) NOT NULL,
    log VARCHAR(5000) NOT NULL,
    quantity INT(11) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    last_modified_at TIMESTAMP NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET = utf8mb4;

INSERT INTO event(level, description, origin, log, quantity, created_at, last_modified_at) VALUES ('ERROR', 'error test', 'test environment', 'not found', 1, now(), now())