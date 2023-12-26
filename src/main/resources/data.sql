insert into member (ID, USERNAME, PASSWORD,CREATE_DATE,MODIFY_DATE,EMAIL,NICKNAME)
values (1, 'admin', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi',localtime(),localtime(),'wooju@naver.com','admin');
insert into member (ID, USERNAME, PASSWORD,CREATE_DATE,MODIFY_DATE,EMAIL,NICKNAME)
values (2, 'cc', '$2a$10$Jl3fWLavOUul7iypj1k2qu4PDknkMmUuXXS99dmkhJxAFga4jF6LK',localtime(),localtime(),'wooju@naver.com','cc');
insert into AUTHORITY (AUTHORITY_NAME) values ('ROLE_USER');
insert into AUTHORITY (AUTHORITY_NAME) values ('ROLE_ADMIN');
