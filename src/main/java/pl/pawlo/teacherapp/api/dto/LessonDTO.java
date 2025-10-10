package pl.pawlo.teacherapp.api.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.pawlo.teacherapp.domain.LessonStatus;
import pl.pawlo.teacherapp.domain.Student;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LessonDTO {

    Integer lessonId;
    LocalDate date;
    LocalTime startLesson;
    LocalTime endLesson;
    BigDecimal price;
    String location;
    String description;
    Integer studentId;
    DayOfWeek dayOfWeek;
    LocalDate startDate;
    LocalDate endDate;
    LessonStatus status;
}
