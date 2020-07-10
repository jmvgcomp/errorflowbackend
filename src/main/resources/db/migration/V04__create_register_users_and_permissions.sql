CREATE TABLE user(
    id BIGINT(11) AUTO_INCREMENT,
    name VARCHAR(15) NOT NULL,
    email VARCHAR(50) NOT NULL,
    password VARCHAR(150) NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET = utf8mb4;

CREATE TABLE permission(
    id BIGINT(11) AUTO_INCREMENT,
    role VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET = utf8mb4;

CREATE TABLE user_permission(
    user_id BIGINT(11) NOT NULL ,
    permission_id BIGINT(11) NOT NULL,
    PRIMARY KEY (user_id, permission_id),
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (permission_id) REFERENCES permission(id)
) ENGINE=InnoDB DEFAULT CHARSET = utf8mb4;

INSERT INTO user(id, name, email, password) VALUES (1, 'admin', 'admin@errorflow.com', '$2a$10$adAh3tlS3a0uk7.r1oStB.4evz14Q67USFrP52iDHFbdTA2dMmI/O');
INSERT INTO user(id, name, email, password) VALUES (2, 'manager', 'manager@errorflow.com', '$2a$10$iYWfq/gS0bbfEwKbKzwsfORlNLe1nVpPJthoqPwFqQKjet5HAbrYS');
INSERT INTO user(id, name, email, password) VALUES (3, 'assistant', 'assistant@errorflow.com', '$2a$10$QD.BsiyWdJOVRl.BrXlbDO0Mgzl2NqUtkwrrE1x.P1TezQoR3sF82');

INSERT INTO permission(id, role) VALUES (1, 'ROLE_REGISTER_EVENT');
INSERT INTO permission(id, role) VALUES (2, 'ROLE_LIST_EVENT');
INSERT INTO permission(id, role) VALUES (3, 'ROLE_SEARCH_EVENT');
INSERT INTO permission(id, role) VALUES (4, 'ROLE_DELETE_EVENT');

INSERT INTO user_permission(user_id, permission_id) VALUES (1,1);
INSERT INTO user_permission(user_id, permission_id) VALUES (1,2);
INSERT INTO user_permission(user_id, permission_id) VALUES (1,3);
INSERT INTO user_permission(user_id, permission_id) VALUES (1,4);

INSERT INTO user_permission(user_id, permission_id) VALUES (2,1);
INSERT INTO user_permission(user_id, permission_id) VALUES (2,2);
INSERT INTO user_permission(user_id, permission_id) VALUES (2,3);

INSERT INTO user_permission(user_id, permission_id) VALUES (3,2);
INSERT INTO user_permission(user_id, permission_id) VALUES (3,3);