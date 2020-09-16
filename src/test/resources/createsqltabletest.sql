CREATE TABLE IF NOT EXISTS groups (
    group_id INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY ,
    group_name VARCHAR(50)
);

INSERT INTO groups (group_name) VALUES ('a');
INSERT INTO groups (group_name) VALUES ('b');
INSERT INTO groups (group_name) VALUES ('c');
INSERT INTO groups (group_name) VALUES ('d');
INSERT INTO groups (group_name) VALUES ('e');

CREATE TABLE IF NOT EXISTS students(
	student_id INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
	group_id INT NOT NULL,
	first_name VARCHAR(50) NOT NULL,
	last_name VARCHAR(50) NOT NULL
);

INSERT INTO students (group_id, first_name, last_name) VALUES (1, 'John', 'Smith');
INSERT INTO students (group_id, first_name, last_name) VALUES (2, 'Adam', 'Sendler');
INSERT INTO students (group_id, first_name, last_name) VALUES (3, 'Jack', 'Richer');
INSERT INTO students (group_id, first_name, last_name) VALUES (4, 'Tedd', 'Suiny');
INSERT INTO students (group_id, first_name, last_name) VALUES (5, 'Tom', 'Cruz');
INSERT INTO students (group_id, first_name, last_name) VALUES (5, 'Edward', 'Norton');

CREATE TABLE IF NOT EXISTS courses(
	course_id INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
	course_name VARCHAR(50) NOT NULL,
	course_description VARCHAR(50) NOT NULL
);

INSERT INTO courses (course_name, course_description) VALUES ('Computer science', 'Course of Computer science');
INSERT INTO courses (course_name, course_description) VALUES ('Ecology', 'Course of Ecology');
INSERT INTO courses (course_name, course_description) VALUES ('Economics', 'Course of Economics');
INSERT INTO courses (course_name, course_description) VALUES ('Geography', 'Course of Geography');
INSERT INTO courses (course_name, course_description) VALUES ('History', 'Course of History');
INSERT INTO courses (course_name, course_description) VALUES ('Literature', 'Course of Literature');
INSERT INTO courses (course_name, course_description) VALUES ('Music', 'Course of Course of Music');
INSERT INTO courses (course_name, course_description) VALUES ('Natural history', 'Course of Natural history');
INSERT INTO courses (course_name, course_description) VALUES ('Philosophy', 'Course of Philosophy');

CREATE TABLE IF NOT EXISTS students_courses(
    soc_id INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
    student_id INTEGER,
    course_id INTEGER
);

