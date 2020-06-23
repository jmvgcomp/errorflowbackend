CREATE TABLE level(
    id INT(11) PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO level (name) VALUES ('Error');
INSERT INTO level (name) VALUES ('Warning');
INSERT INTO level (name) VALUES ('Info');