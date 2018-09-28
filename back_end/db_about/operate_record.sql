/*create table notification(
    id varchar(30) primary key,
    title varchar(30),
    content varchar(30),
	img_url varchar(80)
);

insert into notification(id,title,content) value (0,"Notification","Boy you need meditation");*/
create table user(
	user_id varchar(40) primary key,
	user_name varchar(40),
	password varchar(20)
);

