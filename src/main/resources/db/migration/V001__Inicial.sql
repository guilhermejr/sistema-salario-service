create table if not exists tipo_folhas
(
    id bigserial not null,
    descricao varchar(255) not null,
    ativo bool not null,
    criado timestamp without time zone,
    atualizado timestamp without time zone,
    usuario uuid not null,
    primary key (id)
);

create table if not exists tipo_itens
(
    id bigserial not null,
    descricao varchar(255) not null,
    tipo char(1) not null,
    ativo bool not null,
    criado timestamp without time zone,
    atualizado timestamp without time zone,
    usuario uuid not null,
    primary key (id)
);

create table if not exists folhas
(
    id bigserial not null,
    data_pagamento date not null,
    total_proventos numeric(19,2) not null,
    total_descontos numeric(19,2) not null,
    total_liquido numeric(19,2) not null,
    tipo_folha_id bigint not null,
    usuario uuid not null,
    primary key (id)
);

alter table if exists folhas
    add constraint fk_tipo_folhas
    foreign key (tipo_folha_id)
    references tipo_folhas;

create table if not exists itens
(
    id bigserial not null,
    valor numeric(19,2) not null,
    criado timestamp without time zone,
    atualizado timestamp without time zone,
    folha_id bigint not null,
    tipo_item_id bigint not null,
    usuario uuid not null,
    primary key (id)
);

alter table if exists itens
    add constraint fk_folhas
    foreign key (folha_id)
    references folhas;

alter table if exists itens
    add constraint fk_tipo_itens
    foreign key (tipo_item_id)
    references tipo_itens;