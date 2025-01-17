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

CREATE TABLE genres (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(255) NOT NULL UNIQUE,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
                        user_id INT,
                        CONSTRAINT fk_genre_user FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE movies (
                                      id INT AUTO_INCREMENT PRIMARY KEY,
                                      title VARCHAR(255) NOT NULL,
                                      director VARCHAR(255),
                                      release_year DATE,
                                      imdb VARCHAR(255),
                                      user_id INT,
                                      FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

-- Create movie_genre relationship table
CREATE TABLE movie_genre (
                             movie_id INT NOT NULL,
                             genre_id INT NOT NULL,
                             PRIMARY KEY (movie_id, genre_id),
                             FOREIGN KEY (movie_id) REFERENCES movies (id),
                             FOREIGN KEY (genre_id) REFERENCES genres (id)
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

INSERT INTO genres (name, user_id) VALUES ('Action', 1),
                                          ('Comedy', 1),
                                          ('Drama', 2);

INSERT INTO movies (title, director, release_year, IMDb, user_id)
VALUES ('Inception', 'Christopher Nolan', '2010-07-16', '8.8', 1),
('The Dark Knight', 'Christopher Nolan', '2008-07-18', '9.0', 1),
('Forrest Gump', 'Robert Zemeckis', '1994-07-06', '8.8', 2);

INSERT INTO movie_genre (movie_id, genre_id) VALUES
                                                 (1, 1),
(2, 1),-- The Dark Knight, Action
(3, 3); -- Forrest Gump, Drama