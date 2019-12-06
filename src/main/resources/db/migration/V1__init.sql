CREATE TABLE users (
  id INT NOT NULL AUTO_INCREMENT,
  name varchar(100),
  password varchar(100) NOT NULL,
  email varchar(100) DEFAULT NULL,
  avatar_url varchar,
  PRIMARY KEY (id),
  UNIQUE KEY UK_email (email)
);

CREATE TABLE users_role (
    user_id INT NOT NULL,
    user_role varchar(100) NOT NULL
);

CREATE TABLE users_refresh_token (
    user_id INT NOT NULL,
    auth_token varchar(300) NOT NULL,
    refresh_token varchar(300) NOT NULL,
    created_date TIMESTAMP NOT NULL,
    PRIMARY KEY (auth_token)
);