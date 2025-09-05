CREATE TABLE student (
        student_id SERIAL PRIMARY KEY,
        name VARCHAR(255) NOT NULL,
        surname VARCHAR(255) NOT NULL,
        student_class VARCHAR(255) NOT NULL,
        phone VARCHAR(255),
        notes VARCHAR(255)
);