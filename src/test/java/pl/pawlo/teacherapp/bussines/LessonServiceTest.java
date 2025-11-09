package pl.pawlo.teacherapp.bussines;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pawlo.teacherapp.api.dto.LessonDTO;
import pl.pawlo.teacherapp.business.LessonService;
import pl.pawlo.teacherapp.business.StudentService;
import pl.pawlo.teacherapp.business.dao.LessonDAO;
import pl.pawlo.teacherapp.domain.Lesson;
import pl.pawlo.teacherapp.domain.LessonStatus;
import pl.pawlo.teacherapp.domain.SchoolClass;
import pl.pawlo.teacherapp.domain.Student;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class LessonServiceTest {

    @Mock
    private LessonDAO lessonDAO;

    @Mock
    private StudentService studentService;

    @InjectMocks
    private LessonService lessonService;

    private Lesson lesson;

    private Student student;

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
                .status(LessonStatus.W_REALIZACJI)
                .build();
    }


    @Test
    void shouldSaveLesson() {
        lessonService.save(lesson);
        Mockito.verify(lessonDAO,Mockito.times(1)).save(lesson);
    }

    @Test
    void shouldFindAllLessons() {
        Mockito.when(lessonDAO.findAll()).thenReturn(List.of(lesson));
        List<Lesson> lessons = lessonService.findAll();
        Assertions.assertThat(lessons).hasSize(1);
    }

    @Test
    void shouldDeleteLessonById(){
        lessonService.deleteById(1);
        Mockito.verify(lessonDAO,Mockito.times(1)).deleteById(1);
    }

    @Test
    void shouldFindLessonById() {
        Mockito.when(lessonDAO.findById(1)).thenReturn(Optional.of(lesson));
        Lesson foundLesson = lessonService.findById(1);
        Assertions.assertThat(foundLesson).isEqualTo(lesson);
    }

    @Test
    void shouldThrowExceptionIfLessonNotFound() {
        Mockito.when(lessonDAO.findById(1)).thenReturn(Optional.empty());
        Assertions.assertThatThrownBy(()->lessonService.findById(1))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Lesson not found");
    }

    @Test
    void shouldUpdateLesson() {
        LessonDTO lessonUpdate= LessonDTO.builder()
                .startLesson(LocalTime.of(13,00))
                .endLesson(LocalTime.of(14,00))
                .location("Online")
                .build();

        Mockito.when(lessonDAO.findById(1)).thenReturn(Optional.of(lesson));

        lessonService.updateLesson(1,lessonUpdate);

        Mockito.verify(lessonDAO,Mockito.times(1)).save(lesson);
        Assertions.assertThat(lesson.getLocation()).isEqualTo("Online");
    }

    @Test
    void shouldSaveScheduledLessons(){
        LocalDate startDate = LocalDate.of(2025, 1, 1);
        LocalDate endDate = LocalDate.of(2025, 12, 1);

        lessonService.saveScheduledLessons(
                student,
                DayOfWeek.MONDAY,
                LocalTime.of(13,00),
                LocalTime.of(14,00),
                BigDecimal.valueOf(80),
                "Home",
                "Math",
                startDate,
                endDate);

        Mockito.verify(lessonDAO,Mockito.atLeastOnce()).save(Mockito.any(Lesson.class));
    }

    @Test
    void shouldUpdateLessonStatus(){
        Mockito.when(lessonDAO.findById(1)).thenReturn(Optional.of(lesson));

        lessonService.updateLessonStatus(1,LessonStatus.ZAKONCZONA);

        Assertions.assertThat(lesson.getStatus()).isEqualTo(LessonStatus.ZAKONCZONA);
        Mockito.verify(lessonDAO,Mockito.times(1)).save(lesson);

    }

    @Test
    void shouldFindLessonsWithStatusFinished(){
        Lesson l1 = Lesson.builder().lessonId(2).status(LessonStatus.ZAKONCZONA).build();
        Mockito.when(lessonDAO.findAll()).thenReturn(List.of(lesson, l1));

        List<Lesson> lessonsWithStatusFinished = lessonService.findLessonsWithStatusFinished();
        Assertions.assertThat(lessonsWithStatusFinished).containsExactly(l1);
    }

    @Test
    void shouldFindLessonsWithStatusCancelled(){
        Lesson l1 = Lesson.builder().lessonId(2).status(LessonStatus.ANULOWANA).build();
        Lesson l2 = Lesson.builder().lessonId(2).status(LessonStatus.ODWOŁANA).build();
        Lesson l3 = Lesson.builder().lessonId(2).status(LessonStatus.ODWOŁANA_USPRAWIEDLIWIONA).build();
        Lesson l4 = Lesson.builder().lessonId(2).status(LessonStatus.ZAKONCZONA).build();

       Mockito.when(lessonDAO.findAll()).thenReturn(List.of(lesson, l1, l2, l3, l4));

        List<Lesson> lessonsWithStatusCancelled = lessonService.findLessonsWithStatusCancelled();

        Assertions.assertThat(lessonsWithStatusCancelled).containsExactlyInAnyOrder(l1,l2,l3);
    }




}
