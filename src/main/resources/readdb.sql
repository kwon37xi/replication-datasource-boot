CREATE TABLE users (
  id INTEGER AUTO_INCREMENT,
  name varchar(25) NOT NULL,
  PRIMARY KEY(id)
);

insert into users (id, name) values (1, 'read_1'), (2, 'read_2'), (3, 'read_3'), (4, 'read_4');