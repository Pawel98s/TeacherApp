package pl.pawlo.teacherapp.database.entity;

import jakarta.persistence.*;
import lombok.*;
import pl.pawlo.teacherapp.domain.LessonStatus;
import pl.pawlo.teacherapp.domain.Student;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@EqualsAndHashCode(of = "lessonId")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "lesson")
public class LessonEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lesson_id")
    Integer lessonId;

    @Column(name = "date")
    LocalDate date;

    @Column(name = "start_lesson")
    LocalTime startLesson;

    @Column(name = "end_lesson")
    LocalTime endLesson;

    @Column(name = "price")
    BigDecimal price;

    @Column(name = "location")
    String location;

    @Column(name = "description")
    String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    LessonStatus status = LessonStatus.W_REALIZACJI;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    StudentEntity student;
}
