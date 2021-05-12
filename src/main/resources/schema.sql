CREATE TABLE countries
(
    code varchar(3) primary key,
    name VARCHAR(50) NOT NULL
);


CREATE TABLE states
(
    id integer AUTO_INCREMENT primary key,
    country_code varchar(3),
    name varchar(50) NOT NULL,
    constraint  fk_state_country foreign key (country_code) references countries(code)
);