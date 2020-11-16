CREATE TABLE IF NOT EXISTS groups(
    group_id SERIAL,
    group_name text NOT NULL,
    PRIMARY KEY(group_id)
);

CREATE TABLE IF NOT EXISTS students(
	student_id SERIAL,
	group_id INT NOT NULL,
	first_name text NOT NULL,
	last_name text NOT NULL,
	PRIMARY KEY(student_id)
);

CREATE TABLE IF NOT EXISTS courses(
	course_id SERIAL,
	course_name text NOT NULL,
	course_description text NOT NULL,
	PRIMARY KEY (course_id)
);

CREATE TABLE IF NOT EXISTS students_courses(
    soc_id SERIAL,
    student_id integer,
    course_id integer,
    PRIMARY KEY (soc_id)
);
