create table if not exists ano
(
    id bigserial not null,
    ano int not null,
    criado timestamp without time zone,
    atualizado timestamp without time zone,
    usuario uuid not null,
    primary key (id)
);
