CREATE TABLE t_user (
   id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
   username VARCHAR(100) NOT NULL,
   password VARCHAR(100) NOT NULL,
   name VARCHAR(255) NOT NULL,
   email VARCHAR(255) NOT NULL,
   active BOOLEAN NOT NULL,
   created_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE UNIQUE INDEX t_user_name_uk ON t_user (username);
CREATE UNIQUE INDEX t_user_email_uk ON t_user (email);

CREATE TABLE t_role (
   id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
   name VARCHAR(50) NOT NULL,
   description VARCHAR(255),
   active BOOLEAN NOT NULL,
   created_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE UNIQUE INDEX t_role_name_uk ON t_role (name);

CREATE TABLE t_user_roles (
   user_id INT NOT NULL,
   role_id INT NOT NULL
);

ALTER TABLE t_user_roles ADD CONSTRAINT t_user_roles_pk PRIMARY KEY(user_id, role_id);
ALTER TABLE t_user_roles ADD FOREIGN KEY (user_id) REFERENCES t_user(id);
ALTER TABLE t_user_roles ADD FOREIGN KEY (role_id) REFERENCES t_role(id);
