CREATE TABLE users (
  id BIGINT NOT NULL AUTO_INCREMENT,
  name varchar(100),
  password varchar(100) NOT NULL,
  email varchar(100) DEFAULT NULL,
  avatar_url varchar,
  PRIMARY KEY (id),
  UNIQUE KEY UK_email (email)
);

CREATE TABLE users_role (
    user_id BIGINT NOT NULL,
    user_role varchar(100) NOT NULL
);

CREATE TABLE users_refresh_token (
    user_id BIGINT NOT NULL,
    auth_token varchar(300) NOT NULL,
    refresh_token varchar(300) NOT NULL,
    created_date TIMESTAMP NOT NULL,
    PRIMARY KEY (auth_token)
);

CREATE TABLE dishes (
    id BIGINT NOT NULL AUTO_INCREMENT,
    owner_user_id BIGINT,
    name varchar(200) NOT NULL,
    description varchar,
    created_date TIMESTAMP NOT NULL,
    PRIMARY KEY (id)
);

CREATE INDEX dishes_user_id ON dishes(owner_user_id);