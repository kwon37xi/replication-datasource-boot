CREATE TABLE users (
  id INTEGER AUTO_INCREMENT,
  name varchar(25) NOT NULL,
  PRIMARY KEY(id)
);

insert into users (id, name) values (1, 'write_1'), (2, 'write_2'), (3, 'write_3'), (4, 'write_4');