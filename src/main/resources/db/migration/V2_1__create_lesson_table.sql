CREATE TABLE lesson (
                        lesson_id      SERIAL PRIMARY KEY,
                        date           DATE NOT NULL,
                        start_lesson   TIME NOT NULL,
                        end_lesson     TIME NOT NULL,
                        price          NUMERIC(10,2),
                        location       VARCHAR(255),
                        description    TEXT,
                        student_id     INT NOT NULL,

                        CONSTRAINT fk_student FOREIGN KEY (student_id) REFERENCES student(student_id) ON DELETE CASCADE
);