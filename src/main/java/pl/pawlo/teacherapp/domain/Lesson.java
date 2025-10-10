package pl.pawlo.teacherapp.domain;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.pawlo.teacherapp.database.entity.StudentEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Data
@EqualsAndHashCode(of = "lessonId")
public class Lesson {

    Integer lessonId;
    LocalDate date;
    LocalTime startLesson;
    LocalTime endLesson;
    BigDecimal price;
    String location;
    String description;
    Student student;
    LessonStatus status;


}
