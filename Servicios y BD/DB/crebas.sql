/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     25/5/2022 11:19:50                           */
/*==============================================================*/


drop table if exists CUPON;

drop table if exists DIRECCION;

drop table if exists REGISTROCUPON;

drop table if exists RESTAURANTE;

drop table if exists TIPOCUPON;

drop table if exists USUARIO;

/*==============================================================*/
/* Table: CUPON                                                 */
/*==============================================================*/
create table CUPON
(
   ID_CUPON             int not null auto_increment,
   ID_RESTAURANTE       int not null,
   ID_TIPO              int not null,
   CODIGO_CUPON         varchar(5) not null,
   NOMBRE_CUPON         varchar(35) not null,
   DESCRIPCION_CUPON    varchar(50) not null,
   HORARIO_CUPON        varchar(70) not null,
   STOCK_CUPON          int,
   HABILITADO_CUPON     int,
   primary key (ID_CUPON)
);

/*==============================================================*/
/* Table: DIRECCION                                             */
/*==============================================================*/
create table DIRECCION
(
   ID_DIRECCION         int not null auto_increment,
   ID_RESTAURANTE       int not null,
   MUNICIPIO            varchar(40) not null,
   CALLE                varchar(40) not null,
   NUMERO_LOCAL         varchar(40) not null,
   primary key (ID_DIRECCION)
);

/*==============================================================*/
/* Table: REGISTROCUPON                                         */
/*==============================================================*/
create table REGISTROCUPON
(
   ID_REGISTRO          int not null auto_increment,
   ID_CUPON_REGISTRO    int not null,
   ID_USUARIO_REGISTRO  int not null,
   ID_CUPON             int not null,
   ID_USUARIO           int not null,
   FECHA_REGISTRO       date not null,
   HORA_REGISTRO        time not null,
   primary key (ID_REGISTRO, ID_CUPON_REGISTRO, ID_USUARIO_REGISTRO)
);

/*==============================================================*/
/* Table: RESTAURANTE                                           */
/*==============================================================*/
create table RESTAURANTE
(
   ID_RESTAURANTE       int not null auto_increment,
   NOMBRE_RESTAURANTE   varchar(30) not null,
   primary key (ID_RESTAURANTE)
);

/*==============================================================*/
/* Table: TIPOCUPON                                             */
/*==============================================================*/
create table TIPOCUPON
(
   ID_TIPO              int not null auto_increment,
   NOMBRE_TIPO          varchar(30) not null,
   primary key (ID_TIPO)
);

/*==============================================================*/
/* Table: USUARIO                                               */
/*==============================================================*/
create table USUARIO
(
   ID_USUARIO           int not null auto_increment,
   USERNAME             varchar(40) not null,
   PASSWORD             varchar(40) not null,
   EMAIL                varchar(100) not null,
   NOMBRE               varchar(40) not null,
   APELLIDO             varchar(40) not null,
   primary key (ID_USUARIO)
);

alter table CUPON add constraint FK_CREAR foreign key (ID_RESTAURANTE)
      references RESTAURANTE (ID_RESTAURANTE) on delete restrict on update restrict;

alter table CUPON add constraint FK_PERTENECE foreign key (ID_TIPO)
      references TIPOCUPON (ID_TIPO) on delete restrict on update restrict;

alter table DIRECCION add constraint FK_SITUA foreign key (ID_RESTAURANTE)
      references RESTAURANTE (ID_RESTAURANTE) on delete restrict on update restrict;

alter table REGISTROCUPON add constraint FK_CANJEA foreign key (ID_USUARIO)
      references USUARIO (ID_USUARIO) on delete restrict on update restrict;

alter table REGISTROCUPON add constraint FK_SE_REGISTRA foreign key (ID_CUPON)
      references CUPON (ID_CUPON) on delete restrict on update restrict;

