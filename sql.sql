create database StudentInfoManagement;
use StudentInfoManagement;

#院系表
create table Department(
Dno char(12),
Dname char(12),
constraint primary key PK_Department (Dno)
);

#班级表
create table Class(
Clno char(12),
Clname char(12),
Dno char(12),
constraint primary key PK_Class (Clno),
constraint foreign key FK_Class_Department (Dno) references Department(Dno)
);

#学生表
create table Student(
Sno char(12),
Sname char(8),
Ssex char(2) check(Ssex in ('男','女')),
Sage smallint check(Sage > 0),
Clno char(12),
constraint primary key PK_Student (Sno),
constraint foreign key FK_Student_Class (Clno) references Class(Clno)
);

#课程表
create table Course(
Cno char(12),
Cname char(12),
Cteacher char(8),
Ccredit smallint check(Ccredit > 0),
constraint primary key PK_Course (Cno)
);

#选课表
create table SC(
Sno char(12),
Cno char(12),
Grade smallint check(Grade < 100 and Grade > 0),
constraint foreign key FK_SC_Student (Sno) references Student(Sno),
constraint foreign key FK_SC_Course (Cno) references Course(Cno),
constraint primary key PK_SC (Sno,Cno)
);

#用户表
create table User(
username char(12),
password char(12) not null,
level char(6) check(level in ('用户','管理员')),
constraint primary key PK_User (username)
);

#添加数据
insert into Department
values('201501','软件学院');
insert into Department
values('201502','经管学院');
insert into Department
values('201503','人文学院');
insert into Department
values('201504','国际学院');
insert into Department
values('201505','理学院');

insert into Class
values('20150103','2015-3班','201501');#软件学院
insert into Class
values('20150104','2015-4班','201501');#软件学院
insert into Class
values('20150201','2015-1班','201502');#经管学院
insert into Class
values('20150202','2015-2班','201502');#经管学院
insert into Class
values('20150301','2015-1班','201503');#人文学院
insert into Class
values('20150302','2015-2班','201503');#人文学院

insert into Student
values('2015010312','张明','男',20,'20150103');
insert into Student
values('2015010313','秦羽','女',19,'20150103');
insert into Student
values('2015010314','刘翔','男',19,'20150103');

insert into Course
values('1','高等数学','张三',4);
insert into Course
values('2','数据库原理设计','李四',3);
insert into Course
values('3','Java程序设计','王五',4);

insert into SC
values('2015010312','1',50);
insert into SC
values('2015010312','2',80);
insert into SC
values('2015010312','3',70);
insert into SC
values('2015010313','1',90);
insert into SC
values('2015010313','2',89);
insert into SC
values('2015010313','3',59);
insert into SC
values('2015010314','1',85);
insert into SC
values('2015010314','2',75);
insert into SC
values('2015010314','3',95);

insert into User
values('admin','666','管理员');
insert into user
values('user','123','用户');
