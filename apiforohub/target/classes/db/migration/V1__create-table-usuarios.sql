create table usuarios(
    id bigint not null auto_increment,
    email varchar(100) not null,
    contrasena varchar(250) not null,

    primary key(id)
);