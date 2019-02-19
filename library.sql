Drop Database if exists librarysystem;
Create Database if not exists librarysystem;
USE librarysystem;

create table if not exists users (
id int primary key auto_increment,
username varchar(20) not null unique,
pass varchar(100) not null,
enabled boolean not null
);

create table if not exists librarian {

};

create table if not exists book {

};