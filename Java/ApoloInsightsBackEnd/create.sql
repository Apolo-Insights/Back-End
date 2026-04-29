
    create table agendamentos (
        data date,
        hora time(6),
        id bigint not null auto_increment,
        servico_id bigint,
        usuario_id bigint,
        forma_pagamento enum ('LOCAL','PIX'),
        primary key (id)
    ) engine=InnoDB;

    create table categorias (
        id bigint not null auto_increment,
        foto varchar(255),
        nome varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table horarios_disponiveis (
        bloqueado bit not null,
        dia_semana tinyint,
        hora_fim time(6),
        hora_inicio time(6),
        categoria_id bigint,
        id bigint not null auto_increment,
        primary key (id)
    ) engine=InnoDB;

    create table produtos (
        estoque integer,
        preco float(53),
        id bigint not null auto_increment,
        descricao varchar(255),
        foto varchar(255),
        nome varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table servicos (
        duracao decimal(21,0),
        preco float(53),
        categoria_id bigint,
        id bigint not null auto_increment,
        descricao varchar(255),
        foto varchar(255),
        nome varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table usuarios (
        data_nascimento date,
        role tinyint,
        id bigint not null auto_increment,
        cpf varchar(255),
        email varchar(255),
        nome varchar(255),
        senha varchar(255),
        telefone varchar(255),
        genero enum ('FEMININO','MASCULINO','OUTROS'),
        primary key (id)
    ) engine=InnoDB;

    alter table agendamentos 
       add constraint FKcgtkuidmxhrkhgt0m742k35wd 
       foreign key (servico_id) 
       references servicos (id);

    alter table agendamentos 
       add constraint FKlvnidyf53gkjeqkwwv6sxynpf 
       foreign key (usuario_id) 
       references usuarios (id);

    alter table horarios_disponiveis 
       add constraint FKo21385nprtt8on9v8lnpk5sgn 
       foreign key (categoria_id) 
       references categorias (id);

    alter table servicos 
       add constraint FKjekep8dy296dto42jr0cj5am1 
       foreign key (categoria_id) 
       references categorias (id);

    create table agendamentos (
        data date,
        hora time(6),
        id bigint not null auto_increment,
        servico_id bigint,
        usuario_id bigint,
        forma_pagamento enum ('LOCAL','PIX'),
        primary key (id)
    ) engine=InnoDB;

    create table categorias (
        id bigint not null auto_increment,
        foto varchar(255),
        nome varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table horarios_disponiveis (
        bloqueado bit not null,
        dia_semana tinyint,
        hora_fim time(6),
        hora_inicio time(6),
        categoria_id bigint,
        id bigint not null auto_increment,
        primary key (id)
    ) engine=InnoDB;

    create table produtos (
        estoque integer,
        preco float(53),
        id bigint not null auto_increment,
        descricao varchar(255),
        foto varchar(255),
        nome varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table servicos (
        duracao decimal(21,0),
        preco float(53),
        categoria_id bigint,
        id bigint not null auto_increment,
        descricao varchar(255),
        foto varchar(255),
        nome varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table usuarios (
        data_nascimento date,
        role tinyint,
        id bigint not null auto_increment,
        cpf varchar(255),
        email varchar(255),
        nome varchar(255),
        senha varchar(255),
        telefone varchar(255),
        genero enum ('FEMININO','MASCULINO','OUTROS'),
        primary key (id)
    ) engine=InnoDB;

    alter table agendamentos 
       add constraint FKcgtkuidmxhrkhgt0m742k35wd 
       foreign key (servico_id) 
       references servicos (id);

    alter table agendamentos 
       add constraint FKlvnidyf53gkjeqkwwv6sxynpf 
       foreign key (usuario_id) 
       references usuarios (id);

    alter table horarios_disponiveis 
       add constraint FKo21385nprtt8on9v8lnpk5sgn 
       foreign key (categoria_id) 
       references categorias (id);

    alter table servicos 
       add constraint FKjekep8dy296dto42jr0cj5am1 
       foreign key (categoria_id) 
       references categorias (id);
