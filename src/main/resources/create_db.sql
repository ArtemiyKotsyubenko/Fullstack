CREATE TABLE users
(
    login    text primary key,
    password text,
    token text
);

CREATE TABLE bookings
(
    login text references users (login),
    time  text primary key,
)