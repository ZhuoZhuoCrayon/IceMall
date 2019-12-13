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
    preCondition integer,   #e:1-满额立减或 2-每满100-balabala
    preCDescribe varchar(255),  # 优惠描述
    preDiscount float,  # 折扣
    preCLimit float,    # 减免阈值
    preCReduceAmount float, # 减免金额
    PRIMARY KEY (preConId)
) ENGINE = InnoDB
  CHARSET = utf8;


CREATE TABLE Product
(
    proId                   integer auto_increment,
    productionBatch         VARCHAR(255),
    proName                 varchar(255),
    proPrice                float,
    proQuantity             integer,
    proCreationTime         date,
    productionTime          date,
    preservePeriod          date,
    proPortionalTaxRate     float,
    preferentialConditionId integer,
    proStatus               varchar(255),
    PRIMARY KEY (proId, productionBatch),
    FOREIGN KEY (preferentialConditionId) REFERENCES PreferentialConditions (preConId)
) ENGINE = InnoDB
  CHARSET = utf8;


CREATE TABLE CusLevel
(
    cusLevelId    integer auto_increment,
    levelName     varchar(100),
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
    password     varchar(100),
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
    cusStatus  varchar(100),
    cusLevelId integer,
    FOREIGN KEY (cusLevelId) REFERENCES CusLevel (cusLevelId),
    FOREIGN KEY (userId) REFERENCES Users (userId),
    PRIMARY KEY (userId)
) ENGINE = InnoDB
  CHARSET = utf8;


CREATE TABLE Employee
(
    userId        integer,
    empWorkload   float,
    empOccupation varchar(100),
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
    level    integer,  # 1-5
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
    ordStatus       integer,    # e:1送达 2：取消...
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


# --------------------- insert---------------------------------------------------------------
# 评价
INSERT INTO `description`(`desId`, `type`, `category`, `desHead`, `desBody`) VALUES (1,'文本','评价',null,'很好，相当精美，包装特别，很好喝');
INSERT INTO `description`(`desId`, `type`, `category`, `desHead`, `desBody`) VALUES (2,'文本','评价',null,'很棒的一款酒，很甜很好喝。价格也很实惠。度数也不是很高。');
INSERT INTO `description`(`desId`, `type`, `category`, `desHead`, `desBody`) VALUES (3,'文本','评价',null,'总体而言这次体验还是非常的不错，色泽味道我都很满意。');
INSERT INTO `description`(`desId`, `type`, `category`, `desHead`, `desBody`) VALUES (4,'文本','评价',null,'收到货，喝了一瓶才来评论，酒的味道很清醇，把红酒倒入醒酒器中摇匀，等半小时后，让红酒与空气接触， 让酒的香气充分发挥，并让酒里的沉淀物隔开。沿着葡萄酒杯，一股红酒缓缓流入， 在酒杯中旋转不停，浓重香味怡人芬芳，还没品尝已是醉了，是正品，服务很满意！好评！');
INSERT INTO `description`(`desId`, `type`, `category`, `desHead`, `desBody`) VALUES (5,'文本','评价',null,'还不错，双十一买的，价格便宜，味道不错，甜甜的，满口都是香味。已经喝了一瓶了，还有一瓶没有喝，不错也不错了，价格物美价廉。最近喜欢喝洋酒了，葡萄酒更容易下咽，味道也没有那么重，不会有很多的酒臭味。非常不错。');
INSERT INTO `description`(`desId`, `type`, `category`, `desHead`, `desBody`) VALUES (6,'文本','评价',null,'觉得很好喝，几乎没有酒精味，但是有后劲。很喜欢。甜甜的，冰镇之后更好喝。很适合女生。准备入手那一瓶黑色的。我一口气喝完的，晕了一下午。');
INSERT INTO `description`(`desId`, `type`, `category`, `desHead`, `desBody`) VALUES (7,'文本','评价',null,'很不错，口感也好，价格也很便宜。家人都很喜欢');
INSERT INTO `description`(`desId`, `type`, `category`, `desHead`, `desBody`) VALUES (8,'文本','评价',null,'味道很好的淡淡的果味，淡淡的酒味，度数也不算太高，喝起来味道很好，很满意');
INSERT INTO `description`(`desId`, `type`, `category`, `desHead`, `desBody`) VALUES (9,'文本','评价',null,'相当精美，给人一种高档次的感觉。里面三种不同度数的酒，摆放在一起很精美很好');
INSERT INTO `description`(`desId`, `type`, `category`, `desHead`, `desBody`) VALUES (10,'文本','评价',null,'口感非常丰富，入口醇香，平滑柔美，单宁细腻，余味悠长');
INSERT INTO `description`(`desId`, `type`, `category`, `desHead`, `desBody`) VALUES (11,'文本','评价',null,'这个酒很好喝啊，特意冰镇了才喝的');
INSERT INTO `description`(`desId`, `type`, `category`, `desHead`, `desBody`) VALUES (12,'文本','评价',null,'气泡特别的细腻，冰镇了一会喝甜甜的口感非常好喝，各种花果的香气也很丰富');
INSERT INTO `description`(`desId`, `type`, `category`, `desHead`, `desBody`) VALUES (13,'文本','评价',null,'酒水很好 云岭的冰酒是很有名的 快递很赞 服务到位 很满意');
INSERT INTO `description`(`desId`, `type`, `category`, `desHead`, `desBody`) VALUES (14,'文本','评价',null,'看着不错，物流很快，很满意，要是口感好的话会回购的');
INSERT INTO `description`(`desId`, `type`, `category`, `desHead`, `desBody`) VALUES (15,'文本','评价',null,'一款非常好喝的白冰，这个品牌的红冰也是非常好的?');
INSERT INTO `description`(`desId`, `type`, `category`, `desHead`, `desBody`) VALUES (16,'文本','评价',null,'很好，口感偏甜，大家还挺喜欢的');
INSERT INTO `description`(`desId`, `type`, `category`, `desHead`, `desBody`) VALUES (17,'文本','评价',null,'入口感觉很好，一定品尝，不然几口就没了，东西还不错');
INSERT INTO `description`(`desId`, `type`, `category`, `desHead`, `desBody`) VALUES (18,'文本','评价',null,'很好很不错 不是第一次购买了 快递速度快');
INSERT INTO `description`(`desId`, `type`, `category`, `desHead`, `desBody`) VALUES (19,'文本','评价',null,'挺不错的 包装很好 发货次日达 第二次购买了');
INSERT INTO `description`(`desId`, `type`, `category`, `desHead`, `desBody`) VALUES (20,'文本','评价',null,'包装很正 送货很快 等喝了再追评');
INSERT INTO `description`(`desId`, `type`, `category`, `desHead`, `desBody`) VALUES (21,'文本','评价',null,'很好，口感偏甜，大家还挺喜欢的 ');
INSERT INTO `description`(`desId`, `type`, `category`, `desHead`, `desBody`) VALUES (22,'文本','评价',null,'很好，相当精美，包装特别，很好喝');
INSERT INTO `description`(`desId`, `type`, `category`, `desHead`, `desBody`) VALUES (23,'文本','评价',null,'很棒的一款酒，很甜很好喝。价格也很实惠。度数也不是很高。');
INSERT INTO `description`(`desId`, `type`, `category`, `desHead`, `desBody`) VALUES (24,'文本','评价',null,'总体而言这次体验还是非常的不错，色泽味道我都很满意。');
INSERT INTO `description`(`desId`, `type`, `category`, `desHead`, `desBody`) VALUES (25,'文本','评价',null,'收到货，喝了一瓶才来评论，酒的味道很清醇，把红酒倒入醒酒器中摇匀，等半小时后，让红酒与空气接触， 让酒的香气充分发挥，并让酒里的沉淀物隔开。沿着葡萄酒杯，一股红酒缓缓流入， 在酒杯中旋转不停，浓重香味怡人芬芳，还没品尝已是醉了，是正品，服务很满意！好评！');
INSERT INTO `description`(`desId`, `type`, `category`, `desHead`, `desBody`) VALUES (26,'文本','评价',null,'还不错，双十一买的，价格便宜，味道不错，甜甜的，满口都是香味。已经喝了一瓶了，还有一瓶没有喝，不错也不错了，价格物美价廉。最近喜欢喝洋酒了，葡萄酒更容易下咽，味道也没有那么重，不会有很多的酒臭味。非常不错。');
INSERT INTO `description`(`desId`, `type`, `category`, `desHead`, `desBody`) VALUES (27,'文本','评价',null,'觉得很好喝，几乎没有酒精味，但是有后劲。很喜欢。甜甜的，冰镇之后更好喝。很适合女生。准备入手那一瓶黑色的。我一口气喝完的，晕了一下午。');
INSERT INTO `description`(`desId`, `type`, `category`, `desHead`, `desBody`) VALUES (28,'文本','评价',null,'很不错，口感也好，价格也很便宜。家人都很喜欢');
INSERT INTO `description`(`desId`, `type`, `category`, `desHead`, `desBody`) VALUES (29,'文本','评价',null,'味道很好的淡淡的果味，淡淡的酒味，度数也不算太高，喝起来味道很好，很满意');
INSERT INTO `description`(`desId`, `type`, `category`, `desHead`, `desBody`) VALUES (30,'文本','评价',null,'相当精美，给人一种高档次的感觉。里面三种不同度数的酒，摆放在一起很精美很好');
INSERT INTO `description`(`desId`, `type`, `category`, `desHead`, `desBody`) VALUES (31,'文本','评价',null,'口感非常丰富，入口醇香，平滑柔美，单宁细腻，余味悠长');
INSERT INTO `description`(`desId`, `type`, `category`, `desHead`, `desBody`) VALUES (32,'文本','评价',null,'这个酒很好喝啊，特意冰镇了才喝的');
INSERT INTO `description`(`desId`, `type`, `category`, `desHead`, `desBody`) VALUES (33,'文本','评价',null,'气泡特别的细腻，冰镇了一会喝甜甜的口感非常好喝，各种花果的香气也很丰富');
INSERT INTO `description`(`desId`, `type`, `category`, `desHead`, `desBody`) VALUES (34,'文本','评价',null,'酒水很好 云岭的冰酒是很有名的 快递很赞 服务到位 很满意');
INSERT INTO `description`(`desId`, `type`, `category`, `desHead`, `desBody`) VALUES (35,'文本','评价',null,'看着不错，物流很快，很满意，要是口感好的话会回购的');
INSERT INTO `description`(`desId`, `type`, `category`, `desHead`, `desBody`) VALUES (36,'文本','评价',null,'一款非常好喝的白冰，这个品牌的红冰也是非常好的?');
INSERT INTO `description`(`desId`, `type`, `category`, `desHead`, `desBody`) VALUES (37,'文本','评价',null,'很好，口感偏甜，大家还挺喜欢的');
INSERT INTO `description`(`desId`, `type`, `category`, `desHead`, `desBody`) VALUES (38,'文本','评价',null,'入口感觉很好，一定品尝，不然几口就没了，东西还不错');
INSERT INTO `description`(`desId`, `type`, `category`, `desHead`, `desBody`) VALUES (39,'文本','评价',null,'很好很不错 不是第一次购买了 快递速度快');
INSERT INTO `description`(`desId`, `type`, `category`, `desHead`, `desBody`) VALUES (40,'文本','评价',null,'挺不错的 包装很好 发货次日达 第二次购买了');

# 描述
INSERT INTO `description`(`desId`, `type`, `category`, `desHead`, `desBody`) VALUES (41,'文本','评价',null,'特性:精品葡萄酒 甜度:甜型 保质期10年 酒精度:6 存储方法:常温避光 产品重量:1.7kg 容量750ml');
INSERT INTO `description`(`desId`, `type`, `category`, `desHead`, `desBody`) VALUES (42,'文本','评价',null,'特性:精品葡萄酒 甜度:甜型 口感:香甜 保质期:10年 酒精度:3 存储方法:避光恒温 产品重量:1.5kg 容量375ml');
INSERT INTO `description`(`desId`, `type`, `category`, `desHead`, `desBody`) VALUES (43,'文本','评价',null,'特性:精品葡萄酒 甜度:甜型 葡萄品种:长相思（Sauvignon Blanc）保质期:10年 酒精度:11%vol 存储方法:避光恒温 产品重量:1.7kg 容量500ml');
INSERT INTO `description`(`desId`, `type`, `category`, `desHead`, `desBody`) VALUES (44,'文本','评价',null,'特性:精品葡萄酒 甜度:甜型 保质期:10年 酒精度:11%vol 存储方法:避光恒温 产品重量:1.7kg 容量500ml');




INSERT INTO `proevadescribe`(`ProEvaId`, `desId`) VALUES (1,1);
INSERT INTO `proevadescribe`(`ProEvaId`, `desId`) VALUES (2,2);
INSERT INTO `proevadescribe`(`ProEvaId`, `desId`) VALUES (3,3);
INSERT INTO `proevadescribe`(`ProEvaId`, `desId`) VALUES (4,4);
INSERT INTO `proevadescribe`(`ProEvaId`, `desId`) VALUES (5,5);
INSERT INTO `proevadescribe`(`ProEvaId`, `desId`) VALUES (6,6);
INSERT INTO `proevadescribe`(`ProEvaId`, `desId`) VALUES (7,7);
INSERT INTO `proevadescribe`(`ProEvaId`, `desId`) VALUES (8,8);
INSERT INTO `proevadescribe`(`ProEvaId`, `desId`) VALUES (9,9);
INSERT INTO `proevadescribe`(`ProEvaId`, `desId`) VALUES (10,10);
INSERT INTO `proevadescribe`(`ProEvaId`, `desId`) VALUES (11,11);
INSERT INTO `proevadescribe`(`ProEvaId`, `desId`) VALUES (12,12);
INSERT INTO `proevadescribe`(`ProEvaId`, `desId`) VALUES (13,13);
INSERT INTO `proevadescribe`(`ProEvaId`, `desId`) VALUES (14,14);
INSERT INTO `proevadescribe`(`ProEvaId`, `desId`) VALUES (15,15);
INSERT INTO `proevadescribe`(`ProEvaId`, `desId`) VALUES (16,16);
INSERT INTO `proevadescribe`(`ProEvaId`, `desId`) VALUES (17,17);
INSERT INTO `proevadescribe`(`ProEvaId`, `desId`) VALUES (18,18);
INSERT INTO `proevadescribe`(`ProEvaId`, `desId`) VALUES (19,19);
INSERT INTO `proevadescribe`(`ProEvaId`, `desId`) VALUES (20,20);
INSERT INTO `proevadescribe`(`ProEvaId`, `desId`) VALUES (21,21);
INSERT INTO `proevadescribe`(`ProEvaId`, `desId`) VALUES (22,22);
INSERT INTO `proevadescribe`(`ProEvaId`, `desId`) VALUES (23,23);
INSERT INTO `proevadescribe`(`ProEvaId`, `desId`) VALUES (24,24);
INSERT INTO `proevadescribe`(`ProEvaId`, `desId`) VALUES (25,25);
INSERT INTO `proevadescribe`(`ProEvaId`, `desId`) VALUES (26,26);
INSERT INTO `proevadescribe`(`ProEvaId`, `desId`) VALUES (27,27);
INSERT INTO `proevadescribe`(`ProEvaId`, `desId`) VALUES (28,28);
INSERT INTO `proevadescribe`(`ProEvaId`, `desId`) VALUES (29,29);
INSERT INTO `proevadescribe`(`ProEvaId`, `desId`) VALUES (30,30);
INSERT INTO `proevadescribe`(`ProEvaId`, `desId`) VALUES (31,31);
INSERT INTO `proevadescribe`(`ProEvaId`, `desId`) VALUES (32,32);
INSERT INTO `proevadescribe`(`ProEvaId`, `desId`) VALUES (33,33);
INSERT INTO `proevadescribe`(`ProEvaId`, `desId`) VALUES (34,34);
INSERT INTO `proevadescribe`(`ProEvaId`, `desId`) VALUES (35,35);
INSERT INTO `proevadescribe`(`ProEvaId`, `desId`) VALUES (36,36);
INSERT INTO `proevadescribe`(`ProEvaId`, `desId`) VALUES (37,37);
INSERT INTO `proevadescribe`(`ProEvaId`, `desId`) VALUES (38,38);
INSERT INTO `proevadescribe`(`ProEvaId`, `desId`) VALUES (39,39);
INSERT INTO `proevadescribe`(`ProEvaId`, `desId`) VALUES (40,40);




INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (1,41);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (2,41);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (3,43);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (4,42);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (5,42);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (6,44);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (7,44);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (8,41);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (9,44);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (10,42);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (11,44);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (12,41);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (13,41);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (14,41);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (15,42);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (16,43);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (17,41);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (18,42);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (19,41);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (20,43);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (21,42);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (22,43);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (23,41);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (24,43);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (25,41);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (26,44);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (27,44);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (28,41);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (29,42);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (30,41);

# 1满减 2优惠折扣 3 满额折扣
INSERT INTO preferentialconditions VALUES (1,1,'满100减10',null,100,10);
INSERT INTO preferentialconditions VALUES (2,1,'满1000减500',null,1000,500);
INSERT INTO preferentialconditions VALUES (3,2,'8折',0.8,null,null);
INSERT INTO preferentialconditions VALUES (4,3,'满1000打8折',0.8,1000,null);





INSERT INTO `product`(`proId`, `productionBatch`, `proName`, `proPrice`, `proQuantity`, `proCreationTime`, `productionTime`, `preservePeriod`, `proPortionalTaxRate`, `preferentialConditionId`, `proStatus`) VALUES (1,'191206','卡摩莫斯卡托低醇冰酒',148,999,'2019-12-1','2019-12-6','2019-12-6',0.03,1,'上架');
INSERT INTO `product`(`proId`, `productionBatch`, `proName`, `proPrice`, `proQuantity`, `proCreationTime`, `productionTime`, `preservePeriod`, `proPortionalTaxRate`, `preferentialConditionId`, `proStatus`) VALUES (2,'191206','黄金冰谷金钻级冰酒',199,399,'2019-12-1','2019-12-6','2019-12-6',0.03,2,'上架');
INSERT INTO `product`(`proId`, `productionBatch`, `proName`, `proPrice`, `proQuantity`, `proCreationTime`, `productionTime`, `preservePeriod`, `proPortionalTaxRate`, `preferentialConditionId`, `proStatus`) VALUES (3,'191206','长相思冰酒',248,599,'2019-12-1','2019-12-6','2019-12-6',0.03,1,'上架');
INSERT INTO `product`(`proId`, `productionBatch`, `proName`, `proPrice`, `proQuantity`, `proCreationTime`, `productionTime`, `preservePeriod`, `proPortionalTaxRate`, `preferentialConditionId`, `proStatus`) VALUES (4,'191206','维代尔VQA晚收甜白葡萄酒单支礼品盒装',399,699,'2019-12-1','2019-12-6','2019-12-6',0.03,1,'上架');
INSERT INTO `product`(`proId`, `productionBatch`, `proName`, `proPrice`, `proQuantity`, `proCreationTime`, `productionTime`, `preservePeriod`, `proPortionalTaxRate`, `preferentialConditionId`, `proStatus`) VALUES (5,'191206','朵雅（DOYA）冰酒 艾薇白葡萄酒甜酒',458,799,'2019-12-1','2019-12-6','2019-12-6',0.03,1,'上架');
INSERT INTO `product`(`proId`, `productionBatch`, `proName`, `proPrice`, `proQuantity`, `proCreationTime`, `productionTime`, `preservePeriod`, `proPortionalTaxRate`, `preferentialConditionId`, `proStatus`) VALUES (6,'191206','长相守冰酒',299,599,'2019-12-1','2019-12-6','2019-12-6',0.03,1,'上架');
INSERT INTO `product`(`proId`, `productionBatch`, `proName`, `proPrice`, `proQuantity`, `proCreationTime`, `productionTime`, `preservePeriod`, `proPortionalTaxRate`, `preferentialConditionId`, `proStatus`) VALUES (7,'191206','慕拉(MOULA)冰酒',169,599,'2019-12-1','2019-12-6','2019-12-6',0.03,1,'上架');
INSERT INTO `product`(`proId`, `productionBatch`, `proName`, `proPrice`, `proQuantity`, `proCreationTime`, `productionTime`, `preservePeriod`, `proPortionalTaxRate`, `preferentialConditionId`, `proStatus`) VALUES (8,'191206','晚收甜葡萄酒冰酒（甜蜜时光）',348,699,'2019-12-1','2019-12-6','2019-12-6',0.03,3,'上架');
INSERT INTO `product`(`proId`, `productionBatch`, `proName`, `proPrice`, `proQuantity`, `proCreationTime`, `productionTime`, `preservePeriod`, `proPortionalTaxRate`, `preferentialConditionId`, `proStatus`) VALUES (9,'191206','长白山葡萄酒 冰酒 红酒 冰山葡萄酒',398,377,'2019-12-1','2019-12-6','2019-12-6',0.03,1,'上架');
INSERT INTO `product`(`proId`, `productionBatch`, `proName`, `proPrice`, `proQuantity`, `proCreationTime`, `productionTime`, `preservePeriod`, `proPortionalTaxRate`, `preferentialConditionId`, `proStatus`) VALUES (10,'191206','张裕（CHANGYU）酿酒师珍藏冰酒',458,298,'2019-12-1','2019-12-6','2019-12-6',0.05,1,'上架');
INSERT INTO `product`(`proId`, `productionBatch`, `proName`, `proPrice`, `proQuantity`, `proCreationTime`, `productionTime`, `preservePeriod`, `proPortionalTaxRate`, `preferentialConditionId`, `proStatus`) VALUES (11,'191206','莫高冰酒 长相守冰白葡萄酒红酒甜酒',398,456,'2019-12-1','2019-12-6','2019-12-6',0.03,1,'上架');
INSERT INTO `product`(`proId`, `productionBatch`, `proName`, `proPrice`, `proQuantity`, `proCreationTime`, `productionTime`, `preservePeriod`, `proPortionalTaxRate`, `preferentialConditionId`, `proStatus`) VALUES (12,'191206','MOGAO冰酒 滴晶冰葡萄酒红酒甜酒',599,436,'2019-12-1','2019-12-6','2019-12-6',0.03,2,'上架');
INSERT INTO `product`(`proId`, `productionBatch`, `proName`, `proPrice`, `proQuantity`, `proCreationTime`, `productionTime`, `preservePeriod`, `proPortionalTaxRate`, `preferentialConditionId`, `proStatus`) VALUES (13,'191206','网红冰酒女士甜白冰酒',298,369,'2019-12-1','2019-12-6','2019-12-6',0.03,1,'上架');
INSERT INTO `product`(`proId`, `productionBatch`, `proName`, `proPrice`, `proQuantity`, `proCreationTime`, `productionTime`, `preservePeriod`, `proPortionalTaxRate`, `preferentialConditionId`, `proStatus`) VALUES (14,'191206','VQA级云岭lnniskillin 冰白葡萄酒ICEWINE 女士甜酒',398,196,'2019-12-1','2019-12-6','2019-12-6',0.03,1,'上架');
INSERT INTO `product`(`proId`, `productionBatch`, `proName`, `proPrice`, `proQuantity`, `proCreationTime`, `productionTime`, `preservePeriod`, `proPortionalTaxRate`, `preferentialConditionId`, `proStatus`) VALUES (15,'191206','德国进口 圣母之泉（Blaue Quelle）冰酒',288,369,'2019-12-1','2019-12-6','2019-12-6',0.05,3,'上架');
INSERT INTO `product`(`proId`, `productionBatch`, `proName`, `proPrice`, `proQuantity`, `proCreationTime`, `productionTime`, `preservePeriod`, `proPortionalTaxRate`, `preferentialConditionId`, `proStatus`) VALUES (16,'191206','德国进口 凯斯勒（Kessler-Zink）酒庄莉贝冰酒',148,745,'2019-12-1','2019-12-6','2019-12-6',0.03,1,'上架');
INSERT INTO `product`(`proId`, `productionBatch`, `proName`, `proPrice`, `proQuantity`, `proCreationTime`, `productionTime`, `preservePeriod`, `proPortionalTaxRate`, `preferentialConditionId`, `proStatus`) VALUES (17,'191206','柏兰图怡萱冰酒冰白甜型葡萄酒冰酒',348,991,'2019-12-1','2019-12-6','2019-12-6',0.03,1,'上架');
INSERT INTO `product`(`proId`, `productionBatch`, `proName`, `proPrice`, `proQuantity`, `proCreationTime`, `productionTime`, `preservePeriod`, `proPortionalTaxRate`, `preferentialConditionId`, `proStatus`) VALUES (18,'191206','法国进口红酒 卡伯纳小红鸟系列',248,199,'2019-12-1','2019-12-6','2019-12-6',0.03,2,'上架');
INSERT INTO `product`(`proId`, `productionBatch`, `proName`, `proPrice`, `proQuantity`, `proCreationTime`, `productionTime`, `preservePeriod`, `proPortionalTaxRate`, `preferentialConditionId`, `proStatus`) VALUES (19,'191206','钻石Diamond雷司令QMP晚收冰甜葡萄酒',288,599,'2019-12-1','2019-12-6','2019-12-6',0.03,2,'上架');
INSERT INTO `product`(`proId`, `productionBatch`, `proName`, `proPrice`, `proQuantity`, `proCreationTime`, `productionTime`, `preservePeriod`, `proPortionalTaxRate`, `preferentialConditionId`, `proStatus`) VALUES (20,'191206','德国冰酒庄园原瓶原装进口冰酒',699,999,'2019-12-1','2019-12-6','2019-12-6',0.03,2,'上架');
INSERT INTO `product`(`proId`, `productionBatch`, `proName`, `proPrice`, `proQuantity`, `proCreationTime`, `productionTime`, `preservePeriod`, `proPortionalTaxRate`, `preferentialConditionId`, `proStatus`) VALUES (21,'191206','五女山 窖藏红冰葡萄酒 甜型冰酒',298,999,'2019-12-1','2019-12-6','2019-12-6',0.03,1,'上架');
INSERT INTO `product`(`proId`, `productionBatch`, `proName`, `proPrice`, `proQuantity`, `proCreationTime`, `productionTime`, `preservePeriod`, `proPortionalTaxRate`, `preferentialConditionId`, `proStatus`) VALUES (22,'191206','凯特岭Kittling Ridge冰酒',548,389,'2019-12-1','2019-12-6','2019-12-6',0.03,1,'上架');
INSERT INTO `product`(`proId`, `productionBatch`, `proName`, `proPrice`, `proQuantity`, `proCreationTime`, `productionTime`, `preservePeriod`, `proPortionalTaxRate`, `preferentialConditionId`, `proStatus`) VALUES (23,'191206','长相思冰酒',148,999,'2019-12-1','2019-12-6','2019-12-6',0.03,1,'上架');
INSERT INTO `product`(`proId`, `productionBatch`, `proName`, `proPrice`, `proQuantity`, `proCreationTime`, `productionTime`, `preservePeriod`, `proPortionalTaxRate`, `preferentialConditionId`, `proStatus`) VALUES (24,'191206','加拿大原瓶原装进口维代尔冰酒',298,999,'2019-12-1','2019-12-6','2019-12-6',0.03,3,'上架');
INSERT INTO `product`(`proId`, `productionBatch`, `proName`, `proPrice`, `proQuantity`, `proCreationTime`, `productionTime`, `preservePeriod`, `proPortionalTaxRate`, `preferentialConditionId`, `proStatus`) VALUES (25,'191206','朵雅（DOYA）冰酒 艾薇白葡萄酒甜酒',148,369,'2019-12-1','2019-12-6','2019-12-6',0.03,1,'上架');
INSERT INTO `product`(`proId`, `productionBatch`, `proName`, `proPrice`, `proQuantity`, `proCreationTime`, `productionTime`, `preservePeriod`, `proPortionalTaxRate`, `preferentialConditionId`, `proStatus`) VALUES (26,'191206','长相守冰酒',148,999,'2019-12-1','2019-12-6','2019-12-6',0.05,3,'上架');
INSERT INTO `product`(`proId`, `productionBatch`, `proName`, `proPrice`, `proQuantity`, `proCreationTime`, `productionTime`, `preservePeriod`, `proPortionalTaxRate`, `preferentialConditionId`, `proStatus`) VALUES (27,'191206','慕拉(MOULA)冰酒',148,999,'2019-12-1','2019-12-6','2019-12-6',0.03,3,'上架');
INSERT INTO `product`(`proId`, `productionBatch`, `proName`, `proPrice`, `proQuantity`, `proCreationTime`, `productionTime`, `preservePeriod`, `proPortionalTaxRate`, `preferentialConditionId`, `proStatus`) VALUES (28,'191206','晚收甜葡萄酒冰酒（甜蜜时光）',148,999,'2019-12-1','2019-12-6','2019-12-6',0.03,3,'上架');
INSERT INTO `product`(`proId`, `productionBatch`, `proName`, `proPrice`, `proQuantity`, `proCreationTime`, `productionTime`, `preservePeriod`, `proPortionalTaxRate`, `preferentialConditionId`, `proStatus`) VALUES (29,'191206','长白山葡萄酒 冰酒 红酒 冰山葡萄酒',148,599,'2019-12-1','2019-12-6','2019-12-6',0.03,1,'上架');
INSERT INTO `product`(`proId`, `productionBatch`, `proName`, `proPrice`, `proQuantity`, `proCreationTime`, `productionTime`, `preservePeriod`, `proPortionalTaxRate`, `preferentialConditionId`, `proStatus`) VALUES (30,'191206','张裕（CHANGYU）酿酒师珍藏冰酒',148,999,'2019-12-1','2019-12-6','2019-12-6',0.03,1,'上架');




INSERT INTO `cuslevel`(`cusLevelId`, `levelName`, `levelDiscount`) VALUES (1,'Non',1);
INSERT INTO `cuslevel`(`cusLevelId`, `levelName`, `levelDiscount`) VALUES (2,'VIP',0.9);
INSERT INTO `cuslevel`(`cusLevelId`, `levelName`, `levelDiscount`) VALUES (3,'SVIP',0.85);



INSERT INTO cusfavorite VALUES (2,1);
INSERT INTO cusfavorite VALUES (2,2);
INSERT INTO cusfavorite VALUES (2,3);
INSERT INTO cusfavorite VALUES (6,1);
INSERT INTO cusfavorite VALUES (6,3);
INSERT INTO cusfavorite VALUES (6,5);
INSERT INTO cusfavorite VALUES (6,11);
INSERT INTO cusfavorite VALUES (7,11);
INSERT INTO cusfavorite VALUES (7,15);
INSERT INTO cusfavorite VALUES (7,17);
INSERT INTO cusfavorite VALUES (7,19);
INSERT INTO cusfavorite VALUES (8,2);
INSERT INTO cusfavorite VALUES (8,15);
INSERT INTO cusfavorite VALUES (8,13);
INSERT INTO cusfavorite VALUES (8,14);
INSERT INTO cusfavorite VALUES (9,22);
INSERT INTO cusfavorite VALUES (9,11);
INSERT INTO cusfavorite VALUES (9,15);




INSERT INTO `orderproductlist`(`ordProId`, `proId`, `purQuantity`, `proTotPrice`, `proUnitPrice`) VALUES (1,1,2,296,148);
INSERT INTO `orderproductlist`(`ordProId`, `proId`, `purQuantity`, `proTotPrice`, `proUnitPrice`) VALUES (2,2,2,398,199);
INSERT INTO `orderproductlist`(`ordProId`, `proId`, `purQuantity`, `proTotPrice`, `proUnitPrice`) VALUES (3,4,2,798,399);
INSERT INTO `orderproductlist`(`ordProId`, `proId`, `purQuantity`, `proTotPrice`, `proUnitPrice`) VALUES (4,5,1,458,458);
INSERT INTO `orderproductlist`(`ordProId`, `proId`, `purQuantity`, `proTotPrice`, `proUnitPrice`) VALUES (5,6,2,598,299);
INSERT INTO `orderproductlist`(`ordProId`, `proId`, `purQuantity`, `proTotPrice`, `proUnitPrice`) VALUES (6,7,3,507,169);
INSERT INTO `orderproductlist`(`ordProId`, `proId`, `purQuantity`, `proTotPrice`, `proUnitPrice`) VALUES (7,9,2,796,398);
INSERT INTO `orderproductlist`(`ordProId`, `proId`, `purQuantity`, `proTotPrice`, `proUnitPrice`) VALUES (8,10,2,916,458);
INSERT INTO `orderproductlist`(`ordProId`, `proId`, `purQuantity`, `proTotPrice`, `proUnitPrice`) VALUES (9,11,1,398,398);
INSERT INTO `orderproductlist`(`ordProId`, `proId`, `purQuantity`, `proTotPrice`, `proUnitPrice`) VALUES (10,12,2,1198,599);
INSERT INTO `orderproductlist`(`ordProId`, `proId`, `purQuantity`, `proTotPrice`, `proUnitPrice`) VALUES (11,13,2,596,298);
INSERT INTO `orderproductlist`(`ordProId`, `proId`, `purQuantity`, `proTotPrice`, `proUnitPrice`) VALUES (12,14,2,796,398);
INSERT INTO `orderproductlist`(`ordProId`, `proId`, `purQuantity`, `proTotPrice`, `proUnitPrice`) VALUES (13,16,2,296,148);
INSERT INTO `orderproductlist`(`ordProId`, `proId`, `purQuantity`, `proTotPrice`, `proUnitPrice`) VALUES (14,19,1,288,288);
INSERT INTO `orderproductlist`(`ordProId`, `proId`, `purQuantity`, `proTotPrice`, `proUnitPrice`) VALUES (15,21,1,298,298);
INSERT INTO `orderproductlist`(`ordProId`, `proId`, `purQuantity`, `proTotPrice`, `proUnitPrice`) VALUES (16,22,2,1096,548);
INSERT INTO `orderproductlist`(`ordProId`, `proId`, `purQuantity`, `proTotPrice`, `proUnitPrice`) VALUES (17,25,1,148,148);
INSERT INTO `orderproductlist`(`ordProId`, `proId`, `purQuantity`, `proTotPrice`, `proUnitPrice`) VALUES (18,26,2,296,148);
INSERT INTO `orderproductlist`(`ordProId`, `proId`, `purQuantity`, `proTotPrice`, `proUnitPrice`) VALUES (19,27,2,296,148);
INSERT INTO `orderproductlist`(`ordProId`, `proId`, `purQuantity`, `proTotPrice`, `proUnitPrice`) VALUES (20,30,2,296,148);







#----------------------insert sys---------------------------

insert into users values(1,'admin','3bb0b0e9f079312bb881bce0d0346e52','315a6b25cc2d8a5af98f5768e2945e7a',null,null,null,null);
insert into users values(2,'user','fa2a26aa8be21a5ec4fae415be6ac657','8fb10b18001d1e492130bbdc2285a032',null,null,null,null);
insert into users values(3,'cxx','f0bdca517db36b55feafcf3d129b9799','0d926376758d195b1c048d5a0b785c68',null,null,null,null);
insert into users values(4,'cph','5b62be119205476e49c9d53f06d7c37b','43b6440bf86dea40174632b6872a4a98',null,null,null,null);
insert into users values(5,'cwq','15e00bd805df4e5db38416c4d476401d','554ce5874a155121c2eb363d24247659',null,null,null,null);
insert into users values(6,'user1','500e287d63055f58e2d285dbefa48b9e','d888df2e2aec3afdd9552cb51d42b8a0',null,null,null,null);
insert into users values(7,'user2','d328a848e7fe79b5ed64b6603293902e','aa27c9bdf76d2422ea7dcded65725fde',null,null,null,null);
insert into users values(8,'user3','2d97066b5a5bcda4eba9f5f1e37765c4','3f695411a5bd555009096627476001d5',null,null,null,null);
insert into users values(9,'user4','5cebac81a8380e2ecbf07e9f7893e3df','59f7214e75042dbd4bf60557a7c0a2b5',null,null,null,null);

insert into employee values(1,0,'admin');
insert into employee values(3,9999.99,'admin');
insert into employee values(4,23.33,'admin');
insert into employee values(5,888,'admin');

insert into customer values(2,'活跃',1);
insert into customer values(6,'活跃',2);
insert into customer values(7,'活跃',3);
insert into customer values(8,'摸鱼',1);
insert into customer values(9,'摸鱼',2);


insert into roles values(1,'customer', '普通用户');
insert into roles values(2,'productAdmin','产品管理员');
insert into roles values(3,'customerAdmin', '客户管理员');
insert into roles values(4,'employeeAdmin','员工管理员');
insert into roles values(5,'logisticsAdmin', '物流管理员');
insert into roles values(6,'SalesAdmin','销售管理员');
insert into roles values(7,'superAdmin', '超级管理员');

insert into users_roles values(1,1,2);
insert into users_roles values(3,3,7);
insert into users_roles values(4,4,7);
insert into users_roles values(5,5,4);

insert into users_roles values(2,2,1);
insert into users_roles values(6,6,1);
insert into users_roles values(7,7,1);
insert into users_roles values(8,8,1);
insert into users_roles values(9,9,1);

insert into permissions values(1, '/customers/detail.do','perms[/customers/detail.do]','获取客户信息');

insert into roles_permissions values (1,1,1);
insert into roles_permissions values (2,2,1);