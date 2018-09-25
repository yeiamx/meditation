create table notification(
    id int primary key,
    title varchar(30),
    content varchar(30),
	img_url varchar(80)
);

insert into notification(id,title,content) value (0,"Notification","Boy you need meditation");

alter table notification modify column id varchar(30);