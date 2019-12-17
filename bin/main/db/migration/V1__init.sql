CREATE TABLE users (
  id BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(100),
  password VARCHAR(100) NOT NULL,
  email VARCHAR(100) UNIQUE DEFAULT NULL,
  avatar_url TEXT,
  PRIMARY KEY (id),
  UNIQUE KEY UK_email (email)
);

CREATE TABLE users_role (
    user_id BIGINT NOT NULL,
    user_role VARCHAR(100) NOT NULL
);

CREATE TABLE users_refresh_token (
    user_id BIGINT NOT NULL,
    auth_token VARCHAR(300) NOT NULL,
    refresh_token VARCHAR(300) NOT NULL,
    created_date TIMESTAMP NOT NULL,
    PRIMARY KEY (auth_token)
);

CREATE TABLE dishes (
    id BIGINT NOT NULL AUTO_INCREMENT,
    owner_user_id BIGINT,
    name VARCHAR(200) NOT NULL,
    description TEXT,
    created_date TIMESTAMP NOT NULL,
    PRIMARY KEY (id)
);

CREATE INDEX dishes_user_id ON dishes(owner_user_id);
