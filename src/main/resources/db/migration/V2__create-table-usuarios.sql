create table usuarios(
    id bigint not null auto_increment,
    email varchar(100) not null unique,
    contraseña varchar(100) not null,

        primary key(id)
);