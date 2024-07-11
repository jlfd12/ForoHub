create table topicos(
    id bigint not null auto_increment,
    titulo varchar(100) not null,
    mensaje varchar(100) not null,
    fecha_creacion datetime not null,
    id_usuarios bigint,
    curso varchar(100) not null,

    primary key(id),
    foreign key (id_usuarios) references usuarios(id)
);