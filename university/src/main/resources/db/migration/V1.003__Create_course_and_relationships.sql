create table course (id int4 not null, name varchar(255), primary key (id));
create table course_students (courses_id int4 not null, students_id int4 not null, primary key (courses_id, students_id));
create table course_teachers (courses_id int4 not null, teachers_id int4 not null, primary key (courses_id, teachers_id));
alter table if exists course_students add constraint FK_course_students_to_student foreign key (students_id) references student;
alter table if exists course_students add constraint FK_course_students_to_course foreign key (courses_id) references course;
alter table if exists course_teachers add constraint FK_course_teachers_to_teacher foreign key (teachers_id) references teacher;
alter table if exists course_teachers add constraint FK_course_teachers_to_course foreign key (courses_id) references course;
