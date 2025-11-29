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
import pl.pawlo.teacherapp.api.controller.StudentController;
import pl.pawlo.teacherapp.api.dto.StudentDTO;
import pl.pawlo.teacherapp.api.dto.mapper.StudentMapper;
import pl.pawlo.teacherapp.business.StudentService;
import pl.pawlo.teacherapp.domain.SchoolClass;
import pl.pawlo.teacherapp.domain.Student;

import java.util.List;

@WebMvcTest(StudentController.class)
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StudentService studentService;

    @MockitoBean
    private StudentMapper studentMapper;

    private Student student;

    @BeforeEach
    void setUp() {
        student=Student.builder()
                .name("Kamil")
                .surname("Nowak")
                .studentClass(SchoolClass.CLASS_2_TECHNIKUM)
                .build();
    }

    @Test
    void shouldReturnStudentPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/student"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("student"));
    }

    @Test
    void shouldShowAddStudentPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/student/add"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("addStudent"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("student"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("schoolClass"));
    }

    @Test
    void shouldAddStudent() throws Exception {
        StudentDTO studentDTO = StudentDTO.builder().build();

        Mockito.when(studentMapper.mapToDomain(Mockito.any(StudentDTO.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders.post("/student/add").flashAttr("student", studentDTO))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/student"));

        Mockito.verify(studentService, Mockito.times(1)).save(student);
    }

    @Test
    void shouldShowStudentsPage() throws Exception {
        Mockito.when(studentService.findAll()).thenReturn(List.of(student));

        mockMvc.perform(MockMvcRequestBuilders.get("/student/list"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("listStudents"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("students"));
    }


    @Test
    void shouldShowDeleteStudentPage() throws Exception {
        Mockito.when(studentService.findAll()).thenReturn(List.of(student));

        mockMvc.perform(MockMvcRequestBuilders.get("/student/delete"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("deleteStudent"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("students"));
    }

    @Test
    void shouldDeleteStudent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/student/delete").param("studentId", "1"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/student/delete"));

        Mockito.verify(studentService).deleteById(1);
    }

    @Test
    void shouldShowEditStudentPage() throws Exception {
        Mockito.when(studentService.findAll()).thenReturn(List.of(student));

        mockMvc.perform(MockMvcRequestBuilders.get("/student/edit"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("editStudent"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("student"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("students"));
    }

    @Test
    void shouldShowEditStudentForm() throws Exception {
        student.setStudentId(100);

        Mockito.when(studentService.findById(100)).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders.get("/student/edit/100"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("editStudentPage"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("student"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("schoolClass"));
    }

    @Test
    void shouldUpdateStudent() throws Exception {
        student.setStudentId(5);

        mockMvc.perform(MockMvcRequestBuilders.post("/student/update").flashAttr("student", student))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/student"));

        Mockito.verify(studentService).updateStudent(Mockito.eq(5), Mockito.any(Student.class));
    }

}
