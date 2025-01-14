-- Create authorities table
CREATE TABLE authorities (
    name VARCHAR(50) PRIMARY KEY
);

-- Create users table
CREATE TABLE users (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(50) NOT NULL,
                       password VARCHAR(255) NOT NULL
);

-- Create user_authorities table
CREATE TABLE user_authorities (
                                  user_id INT,
                                  authority_name VARCHAR(50),
                                  FOREIGN KEY (user_id) REFERENCES users(id),
                                  FOREIGN KEY (authority_name) REFERENCES authorities(name),
                                  PRIMARY KEY (user_id, authority_name)
);

CREATE TABLE movies (
                                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                      title VARCHAR(255) NOT NULL,
                                      director VARCHAR(255),
                                      release_year DATE,
                                      genre VARCHAR(255),
                                      imdb VARCHAR(255),
                                      user_id BIGINT,
                                      FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

insert into authorities(name)
values ('USER');

insert into users(username, password)
values ('user1', '$2a$12$uNjYXhfahdmASvxUIDx0ruuTU4Nj66U0ry2NOkm6SLSprrF1ZJhnq'), -- password: 1234
('user2', '$2a$12$uNjYXhfahdmASvxUIDx0ruuTU4Nj66U0ry2NOkm6SLSprrF1ZJhnq'), -- password: 1234
('user3', '$2a$12$uNjYXhfahdmASvxUIDx0ruuTU4Nj66U0ry2NOkm6SLSprrF1ZJhnq'); -- password: 1234

insert into user_authorities(user_id, authority_name)
values (1, 'USER'),
(2, 'USER'),
(3, 'USER');

INSERT INTO movies (title, director, release_year, genre, imdb, user_id) VALUES
('Inception', 'Christopher Nolan', '2010-07-16', 'Science Fiction', '8.8', 1),
('Lucifer', 'Francis Ford Coppola', '1972-03-24', 'Crime', '9.2', 2),
('Pulp Fiction', 'Quentin Tarantino', '1994-10-14', 'Crime', '8.9', 1),
('The Dark Knight', 'Christopher Nolan', '2008-07-18', 'Action', '9.0', 2),
('IT', 'Steven King', '2017-07-18', 'Action', '9.0', 3);