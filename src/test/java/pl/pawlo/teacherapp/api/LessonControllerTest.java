package pl.pawlo.teacherapp.api;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.pawlo.teacherapp.api.controller.LessonController;
import pl.pawlo.teacherapp.api.dto.LessonDTO;
import pl.pawlo.teacherapp.api.dto.mapper.LessonMapper;
import pl.pawlo.teacherapp.api.dto.mapper.StudentMapper;
import pl.pawlo.teacherapp.business.LessonService;
import pl.pawlo.teacherapp.business.StudentService;
import pl.pawlo.teacherapp.domain.Lesson;
import pl.pawlo.teacherapp.domain.LessonStatus;
import pl.pawlo.teacherapp.domain.Student;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@WebMvcTest(LessonController.class)
public class LessonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StudentService studentService;

    @MockitoBean
    private StudentMapper studentMapper;

    @MockitoBean
    private LessonService lessonService;

    @MockitoBean
    private LessonMapper lessonMapper;


    @Test
    void shouldReturnLessonPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/lesson"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("lesson"));
    }

    @Test
    void shouldShowAddLessonPage() throws Exception {
        Mockito.when(studentService.findAll()).thenReturn(List.of());

        mockMvc.perform(MockMvcRequestBuilders.get("/lesson/add"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("lesson"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("students"))
                .andExpect(MockMvcResultMatchers.view().name("addLesson"));
    }

    @Test
    void shouldSaveNewLesson() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/lesson/add")
                .param("studentId", "1")
                .param("startLesson", "13:00")
                .param("endLesson", "14:00")
                .param("date","2025-10-10"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/lesson"));

        Mockito.verify(lessonService).saveFromDTO(Mockito.any(LessonDTO.class));
    }

    @Test
    void shouldShowListAllLessons() throws Exception {
        Mockito.when(studentService.findAll()).thenReturn(List.of());

        mockMvc.perform(MockMvcRequestBuilders.get("/lesson/list"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("lessons"))
                .andExpect(MockMvcResultMatchers.view().name("listLessons"));
    }

    @Test
    void shouldFilterLessonByDate() throws Exception {
        LocalDate date = LocalDate.of(2025, 10, 10);

        Mockito.when(lessonService.findByDateOrderByStartLessonAsc(date)).thenReturn(List.of());

        mockMvc.perform(MockMvcRequestBuilders.get("/lesson/list")
                .param("date",date.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("lessons"))
                .andExpect(MockMvcResultMatchers.model().attribute("selectedDate", date))
                .andExpect(MockMvcResultMatchers.view().name("listLessons"));
    }

    @Test
    void shouldShowEditPage() throws Exception {
        Lesson lesson = Lesson.builder().build();
        LessonDTO lessonDTO = LessonDTO.builder().build();

        Mockito.when(lessonService.findById(1)).thenReturn(lesson);
        Mockito.when(lessonMapper.mapToDTO(lesson)).thenReturn(lessonDTO);
        Mockito.when(studentService.findAll()).thenReturn(List.of());

        mockMvc.perform(MockMvcRequestBuilders.get("/lesson/edit/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("lesson"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("students"))
                .andExpect(MockMvcResultMatchers.view().name("lessonEdit"));
    }

    @Test
    void shouldUpdateLesson() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/lesson/edit/1")
        .param("studentId", "1"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/lesson/list"));

    }

    @Test
    void shouldDeleteLesson() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/lesson/delete/1"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/lesson/list"));

        Mockito.verify(lessonService).deleteById(1);
    }

    @Test
    void shouldUpdateLessonStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/lesson/5/status")
                        .param("status", "ZAKONCZONA"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/lesson/list"));

        Mockito.verify(lessonService).updateLessonStatus(5, LessonStatus.ZAKONCZONA);
    }

    @Test
    void shouldUpdateLessonStatusForRealizationPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/lesson/7/statusRealization")
                        .param("status", "ANULOWANA"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/lesson/listForRealization"));

        Mockito.verify(lessonService).updateLessonStatus(7, LessonStatus.ANULOWANA);
    }

    @Test
    void shouldShowLessonScheduleForm() throws Exception {
        Student student = Student.builder().studentId(1).name("Jan").build();
        Mockito.when(studentService.findAll()).thenReturn(List.of(student));

        mockMvc.perform(MockMvcRequestBuilders.get("/lesson/schedule"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("students"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("daysOfWeek"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("lessonForm"))
                .andExpect(MockMvcResultMatchers.view().name("schedulePage"));
    }

    @Test
    void shouldSaveScheduledLessons() throws Exception {
        LessonDTO lessonDTO = LessonDTO.builder()
                .studentId(1)
                .dayOfWeek(DayOfWeek.MONDAY)
                .startLesson(LocalTime.of(10, 0))
                .endLesson(LocalTime.of(11, 0))
                .price(BigDecimal.valueOf(80))
                .location("Zdalnie")
                .description("Lekcja testowa")
                .startDate(LocalDate.of(2025, 10, 10))
                .endDate(LocalDate.of(2025, 10, 20))
                .build();

        Student student = Student.builder().studentId(1).name("Jan").build();
        Mockito.when(studentService.findById(1)).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders.post("/lesson/schedule")
                        .param("studentId", "1")
                        .param("dayOfWeek", "MONDAY")
                        .param("startLesson", "10:00")
                        .param("endLesson", "11:00")
                        .param("price", "80")
                        .param("location", "Zdalnie")
                        .param("description", "Lekcja testowa")
                        .param("startDate", "2025-10-10")
                        .param("endDate", "2025-10-20"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/lesson/list"));

        Mockito.verify(lessonService).saveScheduledLessons(
                student,
                DayOfWeek.MONDAY,
                LocalTime.of(10, 0),
                LocalTime.of(11, 0),
                BigDecimal.valueOf(80),
                "Zdalnie",
                "Lekcja testowa",
                LocalDate.of(2025, 10, 10),
                LocalDate.of(2025, 10, 20)
        );
    }

}
