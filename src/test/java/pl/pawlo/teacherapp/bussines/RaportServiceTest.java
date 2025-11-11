package pl.pawlo.teacherapp.bussines;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pawlo.teacherapp.business.LessonService;
import pl.pawlo.teacherapp.business.RaportService;
import pl.pawlo.teacherapp.business.StudentService;
import pl.pawlo.teacherapp.domain.Lesson;
import pl.pawlo.teacherapp.domain.LessonStatus;
import pl.pawlo.teacherapp.domain.SchoolClass;
import pl.pawlo.teacherapp.domain.Student;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class RaportServiceTest {

    @Mock
    private StudentService studentService;

    @Mock
    private LessonService lessonService;

    @InjectMocks
    private RaportService raportService;

    private Student student;

    private Lesson lesson;

    @BeforeEach
    void setUp() {

        student= Student.builder()
                .studentId(1)
                .name("Marek")
                .surname("Kowalski")
                .studentClass(SchoolClass.CLASS_4_PODSTAWOWKA)
                .phone("123456789")
                .notes("Good student")
                .build();

        lesson = Lesson.builder()
                .lessonId(1)
                .date(LocalDate.of(2020, 1, 1))
                .startLesson(LocalTime.of(12, 30))
                .endLesson(LocalTime.of(13,30))
                .price(BigDecimal.valueOf(50))
                .location("Home")
                .description("Good lesson")
                .student(student)
                .status(LessonStatus.ZAKONCZONA)
                .build();
    }


    @Test
    void shouldCountStudents(){
        Mockito.when(studentService.countStudents()).thenReturn(1L);
        long countStudents = raportService.countStudents();
        Assertions.assertThat(countStudents).isEqualTo(1);
        Mockito.verify(studentService).countStudents();
    }

    @Test
    void shouldCountLessons(){
        Mockito.when(lessonService.findAll()).thenReturn(List.of(lesson));
        long countLessons = raportService.countLessons();
        Assertions.assertThat(countLessons).isEqualTo(1);

    }

    @Test
    void shouldCountProfits(){
        Mockito.when(lessonService.findAll()).thenReturn(List.of(lesson));
        Integer countedProfits = raportService.countProfits();
        Assertions.assertThat(countedProfits).isEqualTo(50);

    }

    @Test
    void shouldFindStudentsWithIndividualProfit(){
        Student student1 = Student.builder()
                .studentId(1)
                .name("Adam")
                .surname("Robak")
                .build();

        Student student2 = Student.builder()
                .studentId(2)
                .name("Maria")
                .surname("Wosk")
                .build();

        Lesson lesson1 = Lesson.builder()
                .lessonId(1)
                .student(student1)
                .status(LessonStatus.ZAKONCZONA)
                .price(BigDecimal.valueOf(100))
                .build();

        Lesson lesson2 = Lesson.builder()
                .lessonId(2)
                .student(student2)
                .status(LessonStatus.W_REALIZACJI)
                .price(BigDecimal.valueOf(50))
                .build();

        Lesson lesson3 = Lesson.builder()
                .lessonId(3)
                .student(student1)
                .status(LessonStatus.ZAKONCZONA)
                .price(BigDecimal.valueOf(150))
                .build();

        Mockito.when(studentService.findAll()).thenReturn(List.of(student1, student2));
        Mockito.when(lessonService.findAll()).thenReturn(List.of(lesson1, lesson2, lesson3));

        Map<Student, BigDecimal> individualProfit = raportService.findStudentsWithIndividualProfit();

        Assertions.assertThat(individualProfit).containsEntry(student1, BigDecimal.valueOf(250));
        Assertions.assertThat(individualProfit).hasSize(1);
        Assertions.assertThat(individualProfit).doesNotContainKey(student2);

    }

    @Test
    void shouldFindStudentsAbandonedLessons(){
        Student student1 = Student.builder()
                .studentId(1)
                .name("Jan")
                .surname("Kowal")
                .build();

        Lesson lesson1 = Lesson.builder()
                .lessonId(1)
                .student(student1)
                .status(LessonStatus.ODWOŁANA)
                .build();

        Mockito.when(lessonService.findAll()).thenReturn(List.of(lesson1));
        List<Student> studentsAbandonedLessons = raportService.findStudentsAbandonedLessons();
        Assertions.assertThat(studentsAbandonedLessons).containsExactly(student1);
    }

    @Test
    void shouldFindStudentsPercentageOfPresence(){

        Student student1 = Student.builder()
                .studentId(1)
                .name("Adam")
                .surname("Robak")
                .build();

        Student student2 = Student.builder()
                .studentId(2)
                .name("Maria")
                .surname("Wosk")
                .build();

        Lesson lesson1 = Lesson.builder()
                .lessonId(1)
                .student(student1)
                .status(LessonStatus.ZAKONCZONA)
                .build();

        Lesson lesson2 = Lesson.builder()
                .lessonId(2)
                .student(student1)
                .status(LessonStatus.ODWOŁANA)
                .build();

        Lesson lesson3 = Lesson.builder()
                .lessonId(3)
                .student(student2)
                .status(LessonStatus.ANULOWANA)
                .build();

        Lesson lesson4 = Lesson.builder()
                .lessonId(4)
                .student(student2)
                .status(LessonStatus.ZAKONCZONA)
                .build();

        Mockito.when(studentService.findAll()).thenReturn(List.of(student1, student2));
        Mockito.when(lessonService.findAll()).thenReturn(List.of(lesson1, lesson2, lesson3, lesson4));

        Map<Student, Double> result = raportService.findStudentsPercentageOfPresence();

        Assertions.assertThat(result.get(student1)).isEqualTo(50.0);
        Assertions.assertThat(result.get(student2)).isEqualTo(50.0);
        Assertions.assertThat(result).hasSize(2);

        Mockito.verify(studentService).findAll();
        Mockito.verify(lessonService).findAll();

    }

    @Test
    void shouldFindStudentsWithCountOfLessonsCancelled(){

        Student student1 = Student.builder()
                .studentId(1)
                .name("Adam")
                .surname("Robak")
                .build();

        Student student2 = Student.builder()
                .studentId(2)
                .name("Maria")
                .surname("Wosk")
                .build();

        Lesson lesson1 = Lesson.builder()
                .lessonId(1)
                .student(student1)
                .status(LessonStatus.ODWOŁANA)
                .build();

        Lesson lesson2 = Lesson.builder()
                .lessonId(2)
                .student(student1)
                .status(LessonStatus.ZAKONCZONA)
                .build();

        Lesson lesson3 = Lesson.builder()
                .lessonId(3)
                .student(student2)
                .status(LessonStatus.ANULOWANA)
                .build();

        Lesson lesson4 = Lesson.builder()
                .lessonId(4)
                .student(student2)
                .status(LessonStatus.ODWOŁANA_USPRAWIEDLIWIONA)
                .build();

        Lesson lesson5 = Lesson.builder()
                .lessonId(5)
                .student(student2)
                .status(LessonStatus.W_REALIZACJI)
                .build();

        Mockito.when(studentService.findAll()).thenReturn(List.of(student1, student2));
        Mockito.when(lessonService.findAll()).thenReturn(List.of(lesson1, lesson2, lesson3, lesson4, lesson5));

        List<Map.Entry<Student, Integer>> result = raportService.findStudentsWithCountOfLessonsCancelled();

        Assertions.assertThat(result).hasSize(2);
        Assertions.assertThat(result.get(0).getKey()).isEqualTo(student2);
        Assertions.assertThat(result.get(0).getValue()).isEqualTo(2);
        Assertions.assertThat(result.get(1).getKey()).isEqualTo(student1);
        Assertions.assertThat(result.get(1).getValue()).isEqualTo(1);

        Mockito.verify(studentService).findAll();
        Mockito.verify(lessonService).findAll();

    }



}
