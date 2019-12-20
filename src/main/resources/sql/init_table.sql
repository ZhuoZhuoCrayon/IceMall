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


CREATE TABLE CusLevel
(
    cusLevelId    integer auto_increment,
    levelName     varchar(100),
    levelDiscount float,
    PRIMARY KEY (cusLevelId)
) ENGINE = InnoDB
  CHARSET = utf8;


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

#------------------------------------------------------------------------

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


# 产品描述
CREATE TABLE ProDescribe
(
    proId integer,
    desId integer,
    PRIMARY KEY (proId, desId)
) ENGINE = InnoDB
  CHARSET = utf8;

CREATE TABLE PreferentialCondition
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
    PRIMARY KEY (proId),
    FOREIGN KEY (preferentialConditionId) REFERENCES PreferentialCondition (preConId)
) ENGINE = InnoDB
  CHARSET = utf8;


CREATE TABLE ProEvaluation
(
    proEvaId integer auto_increment,
    userId   integer,
    proId    integer,
    level    integer,  # 1-5
    evaDate  date,
    desId    integer,
    PRIMARY KEY (proEvaId),
    FOREIGN KEY (proId) REFERENCES Product (proId),
    FOREIGN KEY (userId) REFERENCES Customer (userId),
    foreign key (desId)  references Description(desId)
) ENGINE = InnoDB
  CHARSET = utf8;


CREATE TABLE CusFavorite
(
    userId integer,
    proId    integer,
    PRIMARY KEY (userId, proId)
) ENGINE = InnoDB
  CHARSET = utf8;

# 商品列表
CREATE TABLE ProductList
(
    proListId    integer auto_increment,
    proId        integer,
    purQuantity  integer,
    proTotPrice  float,
    proUnitPrice float,
    favorableTotPrice float,    # 优惠后总价：单品优惠+用户折扣
    proReduce   float,          # 商品优惠金额
    userReduce  float,          # 用户折扣
    PRIMARY KEY (proListId),
    FOREIGN KEY (proId) REFERENCES Product (proId)
) ENGINE = InnoDB
  CHARSET = utf8;


CREATE TABLE ShoppingCart
(
    cartId      integer auto_increment,
    userId      integer,
    proListId   integer,
    primary key (cartId),
    foreign key (userId) references Customer(userId),
    foreign key (proListId) references ProductList(proListId)
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
    transId         integer,
    ordTotPrice     float,
    resEId          integer,
    PRIMARY KEY (orderId),
    FOREIGN KEY (userId) REFERENCES Customer (userId),
    FOREIGN KEY (transId) REFERENCES Transportation (transId),
    FOREIGN KEY (resEId) REFERENCES Employee (userId)
) ENGINE = InnoDB
  CHARSET = utf8;

create table OrderProductList
(
    ordProId    integer auto_increment,
    proListId   integer,
    orderId     integer,
    primary key (ordProId),
    foreign key (proListId) references ProductList(proListId),
    foreign key (orderId) references Orders(orderId)
) ENGINE = InnoDB
 CHARSET = utf8;


# --------------------- insert---------------------------------------------------------------

#----------------------insert sys----------------------------------------------------------

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

insert into CusLevel(levelName, levelDiscount) VALUES
('Non',1),
('VIP',0.9),
('SVIP',0.85);

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

#----------------------insert sys----------------------------------------------------------


# 评价
insert into description(type, category, desHead, desBody)
VALUES
('TEXT','EVALUATE',null,'很好，相当精美，包装特别，很好喝'),
('TEXT','EVALUATE',null,'很棒的一款酒，很甜很好喝。价格也很实惠。度数也不是很高。'),
('TEXT','EVALUATE',null,'总体而言这次体验还是非常的不错，色泽味道我都很满意。'),
('TEXT','EVALUATE',null,'收到货，喝了一瓶才来评论，酒的味道很清醇，把红酒倒入醒酒器中摇匀，等半小时后，让红酒与空气接触， 让酒的香气充分发挥，并让酒里的沉淀物隔开。沿着葡萄酒杯，一股红酒缓缓流入， 在酒杯中旋转不停，浓重香味怡人芬芳，还没品尝已是醉了，是正品，服务很满意！好评！'),
('TEXT','EVALUATE',null,'还不错，双十一买的，价格便宜，味道不错，甜甜的，满口都是香味。已经喝了一瓶了，还有一瓶没有喝，不错也不错了，价格物美价廉。最近喜欢喝洋酒了，葡萄酒更容易下咽，味道也没有那么重，不会有很多的酒臭味。非常不错。'),
('TEXT','EVALUATE',null,'觉得很好喝，几乎没有酒精味，但是有后劲。很喜欢。甜甜的，冰镇之后更好喝。很适合女生。准备入手那一瓶黑色的。我一口气喝完的，晕了一下午。'),
('TEXT','EVALUATE',null,'很不错，口感也好，价格也很便宜。家人都很喜欢'),
('TEXT','EVALUATE',null,'味道很好的淡淡的果味，淡淡的酒味，度数也不算太高，喝起来味道很好，很满意'),
('TEXT','EVALUATE',null,'相当精美，给人一种高档次的感觉。里面三种不同度数的酒，摆放在一起很精美很好'),
('TEXT','EVALUATE',null,'口感非常丰富，入口醇香，平滑柔美，单宁细腻，余味悠长'),
('TEXT','EVALUATE',null,'这个酒很好喝啊，特意冰镇了才喝的'),
('TEXT','EVALUATE',null,'气泡特别的细腻，冰镇了一会喝甜甜的口感非常好喝，各种花果的香气也很丰富'),
('TEXT','EVALUATE',null,'酒水很好 云岭的冰酒是很有名的 快递很赞 服务到位 很满意'),
('TEXT','EVALUATE',null,'看着不错，物流很快，很满意，要是口感好的话会回购的'),
('TEXT','EVALUATE',null,'很好，口感偏甜，大家还挺喜欢的'),
('TEXT','EVALUATE',null,'入口感觉很好，一定品尝，不然几口就没了，东西还不错'),
('TEXT','EVALUATE',null,'很好很不错 不是第一次购买了 快递速度快'),
('TEXT','EVALUATE',null,'挺不错的 包装很好 发货次日达 第二次购买了'),
('TEXT','EVALUATE',null,'包装很正 送货很快 等喝了再追评'),
('TEXT','EVALUATE',null,'很好，口感偏甜，大家还挺喜欢的 '),
('TEXT','EVALUATE',null,'很好，相当精美，包装特别，很好喝'),
('TEXT','EVALUATE',null,'很棒的一款酒，很甜很好喝。价格也很实惠。度数也不是很高。'),
('TEXT','EVALUATE',null,'总体而言这次体验还是非常的不错，色泽味道我都很满意。'),
('TEXT','EVALUATE',null,'收到货，喝了一瓶才来评论，酒的味道很清醇，把红酒倒入醒酒器中摇匀，等半小时后，让红酒与空气接触， 让酒的香气充分发挥，并让酒里的沉淀物隔开。沿着葡萄酒杯，一股红酒缓缓流入， 在酒杯中旋转不停，浓重香味怡人芬芳，还没品尝已是醉了，是正品，服务很满意！好评！'),
('TEXT','EVALUATE',null,'还不错，双十一买的，价格便宜，味道不错，甜甜的，满口都是香味。已经喝了一瓶了，还有一瓶没有喝，不错也不错了，价格物美价廉。最近喜欢喝洋酒了，葡萄酒更容易下咽，味道也没有那么重，不会有很多的酒臭味。非常不错。'),
('TEXT','EVALUATE',null,'觉得很好喝，几乎没有酒精味，但是有后劲。很喜欢。甜甜的，冰镇之后更好喝。很适合女生。准备入手那一瓶黑色的。我一口气喝完的，晕了一下午。'),
('TEXT','EVALUATE',null,'很不错，口感也好，价格也很便宜。家人都很喜欢'),
('TEXT','EVALUATE',null,'味道很好的淡淡的果味，淡淡的酒味，度数也不算太高，喝起来味道很好，很满意'),
('TEXT','EVALUATE',null,'相当精美，给人一种高档次的感觉。里面三种不同度数的酒，摆放在一起很精美很好'),
('TEXT','EVALUATE',null,'口感非常丰富，入口醇香，平滑柔美，单宁细腻，余味悠长'),
('TEXT','EVALUATE',null,'这个酒很好喝啊，特意冰镇了才喝的'),
('TEXT','EVALUATE',null,'气泡特别的细腻，冰镇了一会喝甜甜的口感非常好喝，各种花果的香气也很丰富'),
('TEXT','EVALUATE',null,'酒水很好 云岭的冰酒是很有名的 快递很赞 服务到位 很满意'),
('TEXT','EVALUATE',null,'看着不错，物流很快，很满意，要是口感好的话会回购的'),
('TEXT','EVALUATE',null,'一款非常好喝的白冰，这个品牌的红冰也是非常好的?'),
('TEXT','EVALUATE',null,'很好，口感偏甜，大家还挺喜欢的'),
('TEXT','EVALUATE',null,'入口感觉很好，一定品尝，不然几口就没了，东西还不错'),
('TEXT','EVALUATE',null,'很好很不错 不是第一次购买了 快递速度快'),
('TEXT','EVALUATE',null,'挺不错的 包装很好 发货次日达 第二次购买了'),

# 描述
('TEXT','DESCRIBE',null,'特性:精品葡萄酒 甜度:甜型 保质期10年 酒精度:6 存储方法:常温避光 产品重量:1.7kg 容量750ml'),
('TEXT','DESCRIBE',null,'特性:精品葡萄酒 甜度:甜型 口感:香甜 保质期:10年 酒精度:3 存储方法:避光恒温 产品重量:1.5kg 容量375ml'),
('TEXT','DESCRIBE',null,'特性:精品葡萄酒 甜度:甜型 葡萄品种:长相思（Sauvignon Blanc）保质期:10年 酒精度:11%vol 存储方法:避光恒温 产品重量:1.7kg 容量500ml'),
('TEXT','DESCRIBE',null,'特性:精品葡萄酒 甜度:甜型 保质期:10年 酒精度:11%vol 存储方法:避光恒温 产品重量:1.7kg 容量500ml'),


#预览图
('IMG','PREVIEW',null,'/static/img/preview/p1.jpg'),
('IMG','PREVIEW',null,'/static/img/preview/p2.jpg'),
('IMG','PREVIEW',null,'/static/img/preview/p3.jpg'),
('IMG','PREVIEW',null,'/static/img/preview/p4.jpg'),
('IMG','PREVIEW',null,'/static/img/preview/p5.jpg'),
('IMG','PREVIEW',null,'/static/img/preview/p6.jpg'),
('IMG','PREVIEW',null,'/static/img/preview/p7.jpg'),
('IMG','PREVIEW',null,'/static/img/preview/p8.jpg'),
('IMG','PREVIEW',null,'/static/img/preview/p9.jpg'),
('IMG','PREVIEW',null,'/static/img/preview/p10.jpg'),
('IMG','PREVIEW',null,'/static/img/preview/p11.jpg'),
('IMG','PREVIEW',null,'/static/img/preview/p12.jpg'),
('IMG','PREVIEW',null,'/static/img/preview/p13.jpg'),
('IMG','PREVIEW',null,'/static/img/preview/p14.jpg'),
('IMG','PREVIEW',null,'/static/img/preview/p15.jpg'),
('IMG','PREVIEW',null,'/static/img/preview/p16.jpg'),
('IMG','PREVIEW',null,'/static/img/preview/p17.jpg'),
('IMG','PREVIEW',null,'/static/img/preview/p18.jpg'),
('IMG','PREVIEW',null,'/static/img/preview/p19.jpg'),
('IMG','PREVIEW',null,'/static/img/preview/p20.jpg'),
('IMG','PREVIEW',null,'/static/img/preview/p21.jpg'),
('IMG','PREVIEW',null,'/static/img/preview/p22.jpg'),
('IMG','PREVIEW',null,'/static/img/preview/p23.jpg'),
('IMG','PREVIEW',null,'/static/img/preview/p24.jpg'),
('IMG','PREVIEW',null,'/static/img/preview/p25.jpg'),
('IMG','PREVIEW',null,'/static/img/preview/p26.jpg'),
('IMG','PREVIEW',null,'/static/img/preview/p27.jpg'),
('IMG','PREVIEW',null,'/static/img/preview/p28.jpg'),
('IMG','PREVIEW',null,'/static/img/preview/p29.jpg'),
('IMG','PREVIEW',null,'/static/img/preview/p30.jpg'),
('IMG','PREVIEW',null,'/static/img/preview/p31.jpg'),
('IMG','PREVIEW',null,'/static/img/preview/p32.jpg'),
('IMG','PREVIEW',null,'/static/img/preview/p33.jpg'),
('IMG','PREVIEW',null,'/static/img/preview/p34.jpg'),
('IMG','PREVIEW',null,'/static/img/preview/p35.jpg'),
('IMG','PREVIEW',null,'/static/img/preview/p36.jpg'),
('IMG','PREVIEW',null,'/static/img/preview/p37.jpg'),
('IMG','PREVIEW',null,'/static/img/preview/p38.jpg'),
('IMG','PREVIEW',null,'/static/img/preview/p39.jpg'),
('IMG','PREVIEW',null,'/static/img/preview/p40.jpg'),
('IMG','PREVIEW',null,'/static/img/preview/p41.jpg'),
('IMG','PREVIEW',null,'/static/img/preview/p42.jpg'),
('IMG','PREVIEW',null,'/static/img/preview/p43.jpg'),
('IMG','PREVIEW',null,'/static/img/preview/p44.jpg'),
('IMG','PREVIEW',null,'/static/img/preview/p45.jpg'),
('IMG','PREVIEW',null,'/static/img/preview/p46.jpg'),
('IMG','PREVIEW',null,'/static/img/preview/p47.jpg'),
('IMG','PREVIEW',null,'/static/img/preview/p48.jpg'),
('IMG','PREVIEW',null,'/static/img/preview/p49.jpg'),
('IMG','PREVIEW',null,'/static/img/preview/p50.jpg'),

('IMG','DETAIL_DESCRIBE',null,'/static/img/detail/1.png'),
('IMG','DETAIL_DESCRIBE',null,'/static/img/detail/2.png'),
('IMG','DETAIL_DESCRIBE',null,'/static/img/detail/3.png'),
('IMG','DETAIL_DESCRIBE',null,'/static/img/detail/4.png'),
('IMG','DETAIL_DESCRIBE',null,'/static/img/detail/5.png'),
('IMG','DETAIL_DESCRIBE',null,'/static/img/detail/6.png'),
('IMG','DETAIL_DESCRIBE',null,'/static/img/detail/7.png'),
('IMG','DETAIL_DESCRIBE',null,'/static/img/detail/8.png'),
('IMG','DETAIL_DESCRIBE',null,'/static/img/detail/9.png'),
('IMG','DETAIL_DESCRIBE',null,'/static/img/detail/10.jpg'),
('IMG','DETAIL_DESCRIBE',null,'/static/img/detail/11.jpg'),
('IMG','DETAIL_DESCRIBE',null,'/static/img/detail/12.jpg'),
('IMG','DETAIL_DESCRIBE',null,'/static/img/detail/13.jpg'),
('IMG','DETAIL_DESCRIBE',null,'/static/img/detail/14.jpg'),
('IMG','DETAIL_DESCRIBE',null,'/static/img/detail/15.png'),
('IMG','DETAIL_DESCRIBE',null,'/static/img/detail/16.png'),
('IMG','DETAIL_DESCRIBE',null,'/static/img/detail/17.png'),
('IMG','DETAIL_DESCRIBE',null,'/static/img/detail/18.png'),
('IMG','DETAIL_DESCRIBE',null,'/static/img/detail/19.png'),
('IMG','DETAIL_DESCRIBE',null,'/static/img/detail/20.png'),
('IMG','DETAIL_DESCRIBE',null,'/static/img/detail/21.jpg'),
('IMG','DETAIL_DESCRIBE',null,'/static/img/detail/22.jpg'),
('IMG','DETAIL_DESCRIBE',null,'/static/img/detail/23.jpg'),
('IMG','DETAIL_DESCRIBE',null,'/static/img/detail/24.jpg'),
('IMG','DETAIL_DESCRIBE',null,'/static/img/detail/25.png'),
('IMG','DETAIL_DESCRIBE',null,'/static/img/detail/26.png'),
('IMG','DETAIL_DESCRIBE',null,'/static/img/detail/27.png'),
('IMG','DETAIL_DESCRIBE',null,'/static/img/detail/28.png'),
('IMG','DETAIL_DESCRIBE',null,'/static/img/detail/29.png'),
('IMG','DETAIL_DESCRIBE',null,'/static/img/detail/30.png'),
('IMG','DETAIL_DESCRIBE',null,'/static/img/detail/31.png'),
('IMG','DETAIL_DESCRIBE',null,'/static/img/detail/32.png'),
('IMG','DETAIL_DESCRIBE',null,'/static/img/detail/33.png'),
('IMG','DETAIL_DESCRIBE',null,'/static/img/detail/34.png'),
('IMG','DETAIL_DESCRIBE',null,'/static/img/detail/35.png'),
('IMG','DETAIL_DESCRIBE',null,'/static/img/detail/36.png'),
('IMG','DETAIL_DESCRIBE',null,'/static/img/detail/37.png'),
('IMG','DETAIL_DESCRIBE',null,'/static/img/detail/38.png'),
('IMG','DETAIL_DESCRIBE',null,'/static/img/detail/39.png'),
('IMG','DETAIL_DESCRIBE',null,'/static/img/detail/40.png'),
('IMG','DETAIL_DESCRIBE',null,'/static/img/detail/41.png'),
('IMG','DETAIL_DESCRIBE',null,'/static/img/detail/42.png'),
('IMG','DETAIL_DESCRIBE',null,'/static/img/detail/43.png'),
('IMG','DETAIL_DESCRIBE',null,'/static/img/detail/44.png'),
('IMG','DETAIL_DESCRIBE',null,'/static/img/detail/45.png'),
('IMG','DETAIL_DESCRIBE',null,'/static/img/detail/46.png'),
('IMG','DETAIL_DESCRIBE',null,'/static/img/detail/47.png'),
('IMG','DETAIL_DESCRIBE',null,'/static/img/detail/48.png'),
('IMG','DETAIL_DESCRIBE',null,'/static/img/detail/49.png'),
('IMG','DETAIL_DESCRIBE',null,'/static/img/detail/50.png'),
('IMG','DETAIL_DESCRIBE',null,'/static/img/detail/51.png'),
('IMG','DETAIL_DESCRIBE',null,'/static/img/detail/52.png'),
('IMG','DETAIL_DESCRIBE',null,'/static/img/detail/53.jpg'),
('IMG','DETAIL_DESCRIBE',null,'/static/img/detail/54.png'),
('IMG','DETAIL_DESCRIBE',null,'/static/img/detail/55.png'),
('IMG','DETAIL_DESCRIBE',null,'/static/img/detail/56.png'),
('IMG','DETAIL_DESCRIBE',null,'/static/img/detail/57.png'),
('IMG','DETAIL_DESCRIBE',null,'/static/img/detail/58.png'),
('IMG','DETAIL_DESCRIBE',null,'/static/img/detail/59.png'),
('IMG','DETAIL_DESCRIBE',null,'/static/img/detail/60.png'),
('IMG','DETAIL_DESCRIBE',null,'/static/img/detail/61.png'),
('IMG','DETAIL_DESCRIBE',null,'/static/img/detail/62.png'),
('IMG','DETAIL_DESCRIBE',null,'/static/img/detail/63.png'),
('IMG','DETAIL_DESCRIBE',null,'/static/img/detail/64.jpg'),
('IMG','DETAIL_DESCRIBE',null,'/static/img/detail/65.png'),
('IMG','DETAIL_DESCRIBE',null,'/static/img/detail/66.png'),
('IMG','DETAIL_DESCRIBE',null,'/static/img/detail/67.jpg'),

# 描述
('TEXT','DESCRIBE','特性','精品葡萄酒'),
('TEXT','DESCRIBE','甜度','甜型'),
('TEXT','DESCRIBE','果种','丹麦进口'),
('TEXT','DESCRIBE','存储方法','低于常温'),
('TEXT','DESCRIBE','产品重量','1.7kg'),
('TEXT','DESCRIBE','酒精度','12%vol'),
('TEXT','DESCRIBE','容量','750ml'),

('TEXT','DESCRIBE','特性','特级葡萄酒'),
('TEXT','DESCRIBE','甜度','微甜型'),
('TEXT','DESCRIBE','果种','源自中国云南'),
('TEXT','DESCRIBE','存储方法','常温避光'),
('TEXT','DESCRIBE','产品重量','1.1kg'),
('TEXT','DESCRIBE','酒精度','13%vol'),
('TEXT','DESCRIBE','容量','500ml'),

('TEXT','DESCRIBE','特性','中级葡萄酒'),
('TEXT','DESCRIBE','甜度','回甘型'),
('TEXT','DESCRIBE','果种','荷兰进口'),
('TEXT','DESCRIBE','存储方法','冷藏'),
('TEXT','DESCRIBE','产品重量','1.0kg'),
('TEXT','DESCRIBE','酒精度','20%vol'),
('TEXT','DESCRIBE','容量','700ml'),

('TEXT','DESCRIBE','特性','高级葡萄酒'),
('TEXT','DESCRIBE','甜度','超甜型'),
('TEXT','DESCRIBE','果种','英产巨峰'),
('TEXT','DESCRIBE','存储方法','常温避光'),
('TEXT','DESCRIBE','产品重量','1.1kg'),
('TEXT','DESCRIBE','酒精度','100%vol'),
('TEXT','DESCRIBE','容量','1500ml'),

('TEXT','DESCRIBE','特性','中端葡萄酒'),
('TEXT','DESCRIBE','甜度','巨甜型'),
('TEXT','DESCRIBE','果种','优质平原苹果'),
('TEXT','DESCRIBE','存储方法','常温避光'),
('TEXT','DESCRIBE','产品重量','1.5kg'),
('TEXT','DESCRIBE','酒精度','11%vol'),
('TEXT','DESCRIBE','容量','999ml'),


# 浏览页描述
('TEXT','PREVIEW_DESCRIBE',null,'澳洲原瓶进口红酒奔富洛神山庄赤霞珠'),
('TEXT','PREVIEW_DESCRIBE',null,'奔富蔻/寇兰山设拉子赤霞珠干红葡萄酒'),
('TEXT','PREVIEW_DESCRIBE',null,'圣芝红酒法国波尔多AOC银奖干红原瓶'),
('TEXT','PREVIEW_DESCRIBE',null,'Lafite拉菲传奇波尔多冰酒'),
('TEXT','PREVIEW_DESCRIBE',null,'杰卡斯经典梅洛西拉冰酒'),
('TEXT','PREVIEW_DESCRIBE',null,'法国原瓶原装进口冰酒2支装 法尔干红'),
('TEXT','PREVIEW_DESCRIBE',null,'新地达 威龙有机干冰酒 优级酒田'),
('TEXT','PREVIEW_DESCRIBE',null,'智利进口冰酒酒'),
('TEXT','PREVIEW_DESCRIBE',null,'火烈鸟原瓶原装干红冰酒'),
('TEXT','PREVIEW_DESCRIBE',null,'张裕官方黄金冰谷冰酒酒庄金钻威代尔'),
('TEXT','PREVIEW_DESCRIBE',null,'慕拉冰酒2支装起泡酒甜酒水果酒冰白'),
('TEXT','PREVIEW_DESCRIBE',null,'莫高冰酒冰葡萄酒甜红酒正品荣远冰红'),
('TEXT','PREVIEW_DESCRIBE',null,'吉林通化葡萄酒冰酒集安北冰红冰葡萄'),
('TEXT','PREVIEW_DESCRIBE',null,'慕拉起泡酒甜酒白葡萄酒少女甜型气泡'),
('TEXT','PREVIEW_DESCRIBE',null,'辉煌Gloria加拿大冰酒庄园进口晚收红'),
('TEXT','PREVIEW_DESCRIBE',null,'云惜加拿大冰酒icewine原瓶进口晚收甜'),
('TEXT','PREVIEW_DESCRIBE',null,'红酒冰山葡萄酒晚收甜酒甜'),
('TEXT','PREVIEW_DESCRIBE',null,'维科尼娅冰酒贵腐约会红酒甜型葡萄酒'),
('TEXT','PREVIEW_DESCRIBE',null,'富隆红酒进口冰酒德国维特思蓝冰王甜'),
('TEXT','PREVIEW_DESCRIBE',null,'德国莱茵黑森产区冰酒Icewine Eiswein'),
('TEXT','PREVIEW_DESCRIBE',null,'华龙北冰红山葡萄酒原汁小支红酒通化'),
('TEXT','PREVIEW_DESCRIBE',null,'Dynasty王朝 5度纪念日低醇白起泡葡'),
('TEXT','PREVIEW_DESCRIBE',null,'集安北冰红冰葡萄酒甜红酒果酒低度女'),
('TEXT','PREVIEW_DESCRIBE',null,'原瓶原装进口 雪诗尼 加拿大威代尔冰'),
('TEXT','PREVIEW_DESCRIBE',null,'通化葡萄酒 菀妍冰酒(冰白）11.5度 37'),
('TEXT','PREVIEW_DESCRIBE',null,'德国进口冰酒 钻石冰白/冰红/金冰白 甜'),
('TEXT','PREVIEW_DESCRIBE',null,'卡诗图冰酒6支装冰白葡萄酒甜型红酒'),
('TEXT','PREVIEW_DESCRIBE',null,'6支礼盒装 冰酒甜白女士香槟起泡葡萄'),
('TEXT','PREVIEW_DESCRIBE',null,'莫高红酒长相守冰酒冰红冰白冰葡萄酒'),
('TEXT','PREVIEW_DESCRIBE',null,'凯特岭加拿大冰酒原瓶进口白兰地利口');


# 添加商品描述
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (1,161);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (1,162);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (1,163);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (1,164);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (1,165);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (1,166);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (1,167);

INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (2,168);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (2,169);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (2,170);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (2,171);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (2,172);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (2,173);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (2,174);

INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (3,175);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (3,176);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (3,177);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (3,178);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (3,179);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (3,180);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (3,181);

INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (4,182);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (4,183);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (4,184);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (4,185);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (4,186);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (4,187);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (4,188);

INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (5,189);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (5,190);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (5,191);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (5,192);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (5,193);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (5,194);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (5,195);

INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (6,161);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (6,162);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (6,163);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (6,164);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (6,165);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (6,166);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (6,167);

INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (7,168);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (7,169);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (7,170);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (7,171);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (7,172);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (7,173);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (7,174);

INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (8,175);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (8,176);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (8,177);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (8,178);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (8,179);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (8,180);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (8,181);

INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (9,182);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (9,183);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (9,184);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (9,185);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (9,186);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (9,187);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (9,188);

INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (10,189);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (10,190);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (10,191);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (10,192);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (10,193);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (10,194);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (10,195);

INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (11,161);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (11,162);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (11,163);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (11,164);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (11,165);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (11,166);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (11,167);

INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (12,168);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (12,169);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (12,170);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (12,171);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (12,172);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (12,173);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (12,174);

INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (13,175);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (13,176);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (13,177);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (13,178);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (13,179);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (13,180);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (13,181);

INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (14,182);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (14,183);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (14,184);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (14,185);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (14,186);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (14,187);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (14,188);

INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (15,189);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (15,190);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (15,191);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (15,192);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (15,193);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (15,194);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (15,195);

INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (16,161);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (16,162);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (16,163);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (16,164);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (16,165);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (16,166);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (16,167);

INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (17,168);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (17,169);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (17,170);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (17,171);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (17,172);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (17,173);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (17,174);

INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (18,175);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (18,176);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (18,177);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (18,178);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (18,179);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (18,180);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (18,181);

INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (19,182);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (19,183);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (19,184);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (19,185);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (19,186);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (19,187);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (19,188);

INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (20,189);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (20,190);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (20,191);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (20,192);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (20,193);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (20,194);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (20,195);

INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (21,161);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (21,162);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (21,163);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (21,164);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (21,165);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (21,166);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (21,167);

INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (22,168);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (22,169);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (22,170);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (22,171);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (22,172);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (22,173);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (22,174);

INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (23,175);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (23,176);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (23,177);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (23,178);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (23,179);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (23,180);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (23,181);

INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (24,182);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (24,183);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (24,184);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (24,185);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (24,186);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (24,187);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (24,188);

INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (25,189);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (25,190);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (25,191);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (25,192);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (25,193);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (25,194);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (25,195);

INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (26,161);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (26,162);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (26,163);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (26,164);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (26,165);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (26,166);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (26,167);

INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (27,168);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (27,169);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (27,170);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (27,171);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (27,172);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (27,173);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (27,174);

INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (28,175);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (28,176);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (28,177);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (28,178);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (28,179);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (28,180);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (28,181);

INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (29,182);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (29,183);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (29,184);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (29,185);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (29,186);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (29,187);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (29,188);

INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (30,189);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (30,190);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (30,191);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (30,192);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (30,193);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (30,194);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (30,195);

# 插入预览图片
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (1,44);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (2,45);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (3,46);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (4,47);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (5,48);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (6,49);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (7,50);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (8,51);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (9,52);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (10,53);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (11,54);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (12,55);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (13,56);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (14,57);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (15,58);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (16,59);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (17,60);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (18,61);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (19,62);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (20,63);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (21,64);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (22,65);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (23,66);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (24,67);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (25,68);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (26,69);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (27,70);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (28,71);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (29,72);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (30,73);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (31,84);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (32,85);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (33,86);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (34,87);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (35,88);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (36,89);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (37,90);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (38,91);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (39,92);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (40,93);

# 插入描述图片
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (1,94);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (1,95);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (1,96);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (2,97);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (2,98);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (2,99);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (3,100);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (3,101);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (3,102);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (4,103);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (4,104);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (4,105);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (5,106);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (5,107);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (5,108);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (6,109);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (6,110);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (6,111);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (7,112);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (7,113);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (7,114);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (8,115);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (8,116);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (8,144);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (9,117);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (9,118);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (9,119);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (10,120);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (10,121);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (10,122);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (11,123);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (11,124);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (11,125);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (12,126);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (12,127);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (12,128);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (13,129);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (13,130);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (13,131);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (14,132);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (14,133);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (14,134);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (15,135);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (15,136);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (15,137);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (16,138);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (16,139);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (16,140);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (17,141);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (17,142);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (17,143);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (18,144);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (18,145);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (18,146);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (19,147);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (19,148);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (19,149);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (20,120);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (20,121);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (20,122);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (21,94);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (21,95);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (21,96);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (22,97);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (22,98);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (22,99);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (23,100);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (23,101);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (23,102);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (24,103);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (24,104);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (24,121);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (25,141);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (25,107);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (25,140);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (26,109);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (26,110);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (26,111);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (27,112);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (27,150);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (27,151);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (28,152);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (28,153);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (28,154);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (29,155);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (29,156);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (29,157);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (30,158);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (30,159);
INSERT INTO `prodescribe`(`proId`, `desId`) VALUES (30,160);


# 1满减 2优惠折扣 3 满额折扣
insert into PreferentialCondition(preCondition, preCDescribe, preDiscount, preCLimit, preCReduceAmount)
values
(1,'满100减10',null,100,10),
(1,'满1000减500',null,1000,500),
(2,'8折',0.8,null,null),
(3,'满1000打8折',0.8,1000,null);





insert into Product(productionBatch, proName, proPrice, proQuantity,
                    proCreationTime, productionTime, preservePeriod,
                    proPortionalTaxRate, preferentialConditionId, proStatus)
VALUES
('191206','卡摩莫斯卡托低醇冰酒',148,999,'2019-12-1','2019-12-6','2019-12-6',0.03,1,'上架'),
('191206','黄金冰谷金钻级冰酒',199,399,'2019-12-1','2019-12-6','2019-12-6',0.03,2,'上架'),
('191206','长相思冰酒',248,599,'2019-12-1','2019-12-6','2019-12-6',0.03,1,'上架'),
('191206','维代尔VQA晚收甜白葡萄酒单支礼品盒装',399,699,'2019-12-1','2019-12-6','2019-12-6',0.03,1,'上架'),
('191206','朵雅（DOYA）冰酒 艾薇白葡萄酒甜酒',458,799,'2019-12-1','2019-12-6','2019-12-6',0.03,1,'上架'),
('191206','长相守冰酒',299,599,'2019-12-1','2019-12-6','2019-12-6',0.03,1,'上架'),
('191206','慕拉(MOULA)冰酒',169,599,'2019-12-1','2019-12-6','2019-12-6',0.03,1,'上架'),
('191206','晚收甜葡萄酒冰酒（甜蜜时光）',348,699,'2019-12-1','2019-12-6','2019-12-6',0.03,3,'上架'),
('191206','长白山葡萄酒 冰酒 红酒 冰山葡萄酒',398,377,'2019-12-1','2019-12-6','2019-12-6',0.03,1,'上架'),
('191206','张裕（CHANGYU）酿酒师珍藏冰酒',458,298,'2019-12-1','2019-12-6','2019-12-6',0.05,1,'上架'),
('191206','莫高冰酒 长相守冰白葡萄酒红酒甜酒',398,456,'2019-12-1','2019-12-6','2019-12-6',0.03,1,'上架'),
('191206','MOGAO冰酒 滴晶冰葡萄酒红酒甜酒',599,436,'2019-12-1','2019-12-6','2019-12-6',0.03,2,'上架'),
('191206','网红冰酒女士甜白冰酒',298,369,'2019-12-1','2019-12-6','2019-12-6',0.03,1,'上架'),
('191206','VQA级云岭lnniskillin 冰白葡萄酒ICEWINE 女士甜酒',398,196,'2019-12-1','2019-12-6','2019-12-6',0.03,1,'上架'),
('191206','德国进口 圣母之泉（Blaue Quelle）冰酒',288,369,'2019-12-1','2019-12-6','2019-12-6',0.05,3,'上架'),
('191206','德国进口 凯斯勒（Kessler-Zink）酒庄莉贝冰酒',148,745,'2019-12-1','2019-12-6','2019-12-6',0.03,1,'上架'),
('191206','柏兰图怡萱冰酒冰白甜型葡萄酒冰酒',348,991,'2019-12-1','2019-12-6','2019-12-6',0.03,1,'上架'),
('191206','法国进口红酒 卡伯纳小红鸟系列',248,199,'2019-12-1','2019-12-6','2019-12-6',0.03,2,'上架'),
('191206','钻石Diamond雷司令QMP晚收冰甜葡萄酒',288,599,'2019-12-1','2019-12-6','2019-12-6',0.03,2,'上架'),
('191206','德国冰酒庄园原瓶原装进口冰酒',699,999,'2019-12-1','2019-12-6','2019-12-6',0.03,2,'上架'),
('191206','五女山 窖藏红冰葡萄酒 甜型冰酒',298,999,'2019-12-1','2019-12-6','2019-12-6',0.03,1,'上架'),
('191206','凯特岭Kittling Ridge冰酒',548,389,'2019-12-1','2019-12-6','2019-12-6',0.03,1,'上架'),
('191206','长相思冰酒',148,999,'2019-12-1','2019-12-6','2019-12-6',0.03,1,'上架'),
('191206','加拿大原瓶原装进口维代尔冰酒',298,999,'2019-12-1','2019-12-6','2019-12-6',0.03,3,'上架'),
('191206','朵雅（DOYA）冰酒 艾薇白葡萄酒甜酒',148,369,'2019-12-1','2019-12-6','2019-12-6',0.03,1,'上架'),
('191206','长相守冰酒',148,999,'2019-12-1','2019-12-6','2019-12-6',0.05,3,'上架'),
('191206','慕拉(MOULA)冰酒',148,999,'2019-12-1','2019-12-6','2019-12-6',0.03,3,'上架'),
('191206','晚收甜葡萄酒冰酒（甜蜜时光）',148,999,'2019-12-1','2019-12-6','2019-12-6',0.03,3,'上架'),
('191206','长白山葡萄酒 冰酒 红酒 冰山葡萄酒',148,599,'2019-12-1','2019-12-6','2019-12-6',0.03,1,'上架'),
('191206','张裕（CHANGYU）酿酒师珍藏冰酒',148,999,'2019-12-1','2019-12-6','2019-12-6',0.03,1,'上架');


insert into proevaluation(userId, proId, level, evaDate, desId) VALUES
(2,1,5,now(),1),
(2,2,4,now(),2),
(2,3,3,now(),3),
(2,4,2,now(),4),
(2,5,1,now(),5),
(2,6,0,now(),6),
(2,7,5,now(),7),
(6,1,4,now(),8),
(6,2,3,now(),9),
(6,3,2,now(),10),
(6,4,1,now(),11),
(7,1,0,now(),12),
(7,2,5,now(),13),
(7,3,4,now(),14),
(7,4,3,now(),15),
(7,5,2,now(),16),
(7,6,1,now(),17),
(8,1,0,now(),18),
(8,2,5,now(),19),
(8,3,4,now(),20),
(8,4,3,now(),21),
(8,5,2,now(),22),
(8,6,1,now(),23),
(9,1,0,now(),24),
(9,2,5,now(),25),
(9,3,4,now(),26),
(9,4,3,now(),27),
(9,5,2,now(),28),
(9,6,1,now(),29),
(9,7,0,now(),30),
(9,8,5,now(),31);

insert into CusFavorite(userId, proId) values
(2,1),(2,2),(2,3),
(6,1),(6,3),(6,5),(6,11),
(7,11),(7,15),(7,17),(7,19),
(8,2),(8,15),(8,13),(8,14),(9,22),(9,11),(9,15);


insert into ProductList(proId, purQuantity,
                        proTotPrice, proUnitPrice, favorableTotPrice,
                        proReduce, userReduce) VALUES
(1,2,296,148,296,0,0),
(2,2,398,199,398,0,0),
(4,2,798,399,798,0,0),
(5,1,458,458,458,0,0),
(6,2,598,299,598,0,0),
(7,3,507,169,507,0,0),
(9,2,796,398,796,0,0),
(10,2,916,458,916,0,0),
(11,1,398,398,398,0,0),
(12,2,1198,599,1198,0,0),
(13,2,596,298,596,0,0),
(14,2,796,398,796,0,0),
(16,2,296,148,296,0,0),
(19,1,288,288,288,0,0),
(21,1,298,298,298,0,0),
(22,2,1096,548,1096,0,0),
(25,1,148,148,148,0,0),
(26,2,296,148,296,0,0),
(27,2,296,148,296,0,0),
(30,2,296,148,296,0,0);


insert into shoppingcart(userId, proListId) VALUES
(2,1),
(2,2),
(2,3),
(6,4),
(6,5),
(6,6),
(7,7),
(7,8),
(7,9);


insert into Orders(ordStatus, userId, ordCreationTime, transId, ordTotPrice, resEId) VALUES
(4,2,null,null,2192,3),
(4,6,null,null,1290,3),
(5,7,null,null,298,3),
(1,8,null,null,1440,3);


insert into OrderProductList(proListId, orderId) VALUES
(9,1),
(10,1),
(11,1),
(12,2),
(13,2),
(14,2),
(15,3),
(16,4),
(17,4),
(18,4);