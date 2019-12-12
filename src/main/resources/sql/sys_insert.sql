insert into users values(1,'admin','3bb0b0e9f079312bb881bce0d0346e52','315a6b25cc2d8a5af98f5768e2945e7a',null,null,null,null);
insert into users values(2,'user','fa2a26aa8be21a5ec4fae415be6ac657','8fb10b18001d1e492130bbdc2285a032',null,null,null,null);

insert into employee values(1,1.5,'admin');
insert into customer values(2,null,3,2);

insert into roles values(1,'admin', '管理员');
insert into roles values(2,'user','普通用户');


insert into users_roles values(1,1, 1);
insert into users_roles values(2,2, 2);

insert into permissions values(1, '/customers/detail.do','perms[/customers/detail.do]','获取客户信息');

insert into roles_permissions values (1,1,1);
insert into roles_permissions values (2,2,1);