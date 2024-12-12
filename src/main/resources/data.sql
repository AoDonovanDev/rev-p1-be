drop table if exists post;
drop table if exists account;
drop table if exists comment;
drop table if exists postlike;
drop table if exists follow;

create table account (
    accountId int primary key auto_increment,
    username varchar(255) not null unique,
    password varchar(255)
);
create table post (
    postId int primary key auto_increment,
    postedBy int,
    postText varchar(255),
    timePostedEpoch bigint,
    foreign key (postedBy) references account(accountId)
);

create table comment (
    commentId int primary key auto_increment,
    postId int,
    commentedBy int,    
    commentText varchar(255),
    foreign key (commentedBy) references account(accountId),
    foreign key (postId) references post(postId)
);

create table postlike (
    likeId int primary key auto_increment,
    accountId int,
    postId int,
    foreign key (accountId) references account(accountId),
    foreign key (postId) references post(postId)
);

create table follow (
    followId int primary key auto_increment,
    followingAccountId int,
    followedAccountId int,
    foreign key (followingAccountId) references account(accountId),
    foreign key (followedAccountId) references account(accountId)
);

-- Starting test values with ids of 9999 to avoid test issues
insert into account values (9999, 'testuser1', 'password');
insert into account values (9998, 'testuser2', 'password');
insert into account values (9997, 'testuser3', 'password');
insert into account values (9996, 'testuser4', 'password');

insert into post values (9999, 9999,'test message 1',1669947792);
insert into post values (9997, 9997,'test message 2',1669947792);
insert into post values (9996, 9996,'test message 3',1669947792);

