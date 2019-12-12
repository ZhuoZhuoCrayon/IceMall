CREATE TABLE Description
(
    desId    integer auto_increment,
    type     varchar(100),
    category varchar(100),
    desHead  text,
    desBody  text,
    PRIMARY KEY (desId)
) ENGINE = InnoDB
  CHARSET = utf8;

# 产品评价描述
CREATE TABLE ProEvaDescribe
(
    proEvaId integer,
    desId    integer,
    PRIMARY KEY (proEvaId, desId)
) ENGINE = InnoDB
  CHARSET = utf8;

# 产品描述
CREATE TABLE proDescribe
(
    proId integer,
    desId integer,
    PRIMARY KEY (proId, desId)
) ENGINE = InnoDB
  CHARSET = utf8;

CREATE TABLE PreferentialConditions
(
    preConId     integer auto_increment,
    preCondition text,
    preDiscount float,
    PRIMARY KEY (preConId)
) ENGINE = InnoDB
  CHARSET = utf8;


CREATE TABLE Product
(
    proId                   integer auto_increment,
    productionBatch         VARCHAR(255),
    proName                 text,
    proPrice                float,
    proQuantity             integer,
    proCreationTime         date,
    productionTime          date,
    preservePeriod          date,
    proPortionalTaxRate     float,
    preferentialConditionId integer,
    proStatus               text,
    PRIMARY KEY (proId, productionBatch),
    FOREIGN KEY (preferentialConditionId) REFERENCES PreferentialConditions (preConId)
) ENGINE = InnoDB
  CHARSET = utf8;


CREATE TABLE CusLevel
(
    cusLevelId    integer auto_increment,
    levelName     text,
    levelDiscount float,
    PRIMARY KEY (cusLevelId)
) ENGINE = InnoDB
  CHARSET = utf8;


CREATE TABLE CusFavorite
(
    userId integer,
    proId    integer,
    PRIMARY KEY (userId, proId)
) ENGINE = InnoDB
  CHARSET = utf8;

#--------------用户权限控制--------------------------------------

CREATE TABLE Users
(
    userId       integer auto_increment,
    userName     varchar(100),
    passward     varchar(100),
    salt         varchar(200),
    email        varchar(100),
    phoneNumber  varchar(100),
    registerDate date,
    birthday     date,
    PRIMARY KEY (userId)
) ENGINE = InnoDB
  CHARSET = utf8;
create unique index idx_users_username on Users(username);;


create table Roles
(
    id          integer auto_increment comment '角色编号',
    role        varchar(100) comment '角色名称',
    description varchar(100) comment '角色描述',
    constraint pk_sys_roles primary key (id)
) charset = utf8
  ENGINE = InnoDB;
create unique index idx_roles_role on Roles (role);


create table Permissions
(
    id          integer auto_increment comment '编号',
    permission  varchar(100) comment '权限url',
    perms       varchar(100) comment '权限域',
    description varchar(100) comment '权限描述',
    constraint pk_permissions primary key (id)
) charset = utf8
  ENGINE = InnoDB;
create unique index idx_permissions_permission on Permissions (permission);


create table users_roles
(
    id      integer auto_increment comment '编号',
    user_id integer comment '用户编号',
    role_id integer comment '角色编号',
    constraint pk_users_roles primary key (id)
) charset = utf8
  ENGINE = InnoDB;


create table roles_permissions
(
    id            integer auto_increment comment '编号',
    role_id       integer comment '角色编号',
    permission_id integer comment '权限编号',
    constraint pk_roles_permissions primary key (id)
) charset = utf8
  ENGINE = InnoDB;
#------------------------------------------------------------------------


CREATE TABLE Customer
(
    userId     integer,
    cusStatus  text,
    cusLevelId integer,
    cusFavId   integer,
    FOREIGN KEY (cusLevelId) REFERENCES CusLevel (cusLevelId),
    FOREIGN KEY (cusFavId) REFERENCES CusFavorite (cusFavId),
    FOREIGN KEY (userId) REFERENCES Users (userId),
    PRIMARY KEY (userId)
) ENGINE = InnoDB
  CHARSET = utf8;


CREATE TABLE Employee
(
    userId        integer,
    empWorkload   float,
    empOccupation text,
    FOREIGN KEY (userId) REFERENCES Users (userId),
    PRIMARY KEY (userId)
) ENGINE = InnoDB
  CHARSET = utf8;


CREATE TABLE OrderProductList
(
    ordProId     integer auto_increment,
    proId        integer,
    purQuantity  integer,
    proTotPrice  float,
    proUnitPrice float,
    PRIMARY KEY (ordProId),
    FOREIGN KEY (proId) REFERENCES Product (proId)
) ENGINE = InnoDB
  CHARSET = utf8;


CREATE TABLE ProEvaluation
(
    proEvaId integer auto_increment,
    userId   integer,
    proId    integer,
    level    text,
    PRIMARY KEY (proEvaId),
    FOREIGN KEY (proId) REFERENCES Product (proId),
    FOREIGN KEY (userId) REFERENCES Customer (userId)
) ENGINE = InnoDB
  CHARSET = utf8;


CREATE TABLE Transportation
(
    transId           integer auto_increment,
    transMethod       text,
    orderId           integer,
    origin            text,
    destination       text,
    postage           float,
    transCreationTime date,
    PRIMARY KEY (transId)
) ENGINE = InnoDB
  CHARSET = utf8;


CREATE TABLE Orders
(
    orderId         integer auto_increment,
    ordStatus       text,
    userId          integer,
    ordCreationTime date,
    ordProId        integer,
    transId         integer,
    ordTotPrice     float,
    resEId          integer,
    PRIMARY KEY (orderId),
    FOREIGN KEY (userId) REFERENCES Customer (userId),
    FOREIGN KEY (ordProId) REFERENCES OrderProductList (ordProId),
    FOREIGN KEY (transId) REFERENCES Transportation (transId),
    FOREIGN KEY (resEId) REFERENCES Employee (userId)
) ENGINE = InnoDB
  CHARSET = utf8;
