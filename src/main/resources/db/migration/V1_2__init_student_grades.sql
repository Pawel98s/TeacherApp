CREATE TABLE student_grades (
    student_id INT REFERENCES student(student_id) ON DELETE CASCADE,
    grade INT
);