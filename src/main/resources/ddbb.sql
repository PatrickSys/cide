create table department(id int primary key auto_increment, name varchar(25) unique );
create table person(id int primary key auto_increment, nif varchar(11) unique, name varchar(20),
                    first_name varchar(20), last_name varchar(20), birth_date Date, gender varchar(10), address varchar(100), phone_number int);
create table professor(id int primary key auto_increment, person_id int, department_id int,
                       foreign key (department_id) references department(id), foreign key (person_id) references person(id) )
