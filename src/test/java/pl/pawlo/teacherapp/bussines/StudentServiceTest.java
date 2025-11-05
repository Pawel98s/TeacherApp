package pl.pawlo.teacherapp.bussines;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import pl.pawlo.teacherapp.business.StudentService;
import pl.pawlo.teacherapp.business.dao.StudentDAO;
import pl.pawlo.teacherapp.domain.SchoolClass;
import pl.pawlo.teacherapp.domain.Student;

import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {


    @Mock
    private StudentDAO studentDAO;

    @InjectMocks
    private StudentService studentService;

    private Student student;

    @BeforeEach
    void setUp() {

        student= Student.builder()
                .studentId(1)
                .name("Adam")
                .surname("Nowak")
                .studentClass(SchoolClass.CLASS_1_LICEUM)
                .phone("123456789")
                .notes("Good student")
                .build();
    }

    @Test
    void shouldSaveStudent() {
        studentService.save(student);

        Mockito.verify(studentDAO,Mockito.times(1)).save(student);
    }

    @Test
    void shouldFindAllStudents() {
        Mockito.when(studentDAO.findAll()).thenReturn(List.of(student));

        List<Student> students = studentService.findAll();

        Assertions.assertThat(students).hasSize(1);
        Assertions.assertThat(students.get(0).getName()).isEqualTo("Adam");
    }

    @Test
    void shouldFindStudentById() {
        Mockito.when(studentDAO.findById(1)).thenReturn(Optional.of(student));

        Student student = studentService.findById(1);

        Assertions.assertThat(student.getName()).isEqualTo("Adam");
    }

    @Test
    void shouldThrowExceptionIfStudentNotFound() {
        Mockito.when(studentDAO.findById(4)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(()->studentService.findById(4))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Student not found");
    }

    @Test
    void shouldDeleteStudentById(){
        studentService.deleteById(1);

        Mockito.verify(studentDAO,Mockito.times(1)).deleteById(1);
    }

    @Test
    void shouldUpdateStudent() {

        Student update = Student.builder()
                .name("Marek")
                .surname("Kowalski")
                .studentClass(SchoolClass.CLASS_2_TECHNIKUM)
                .build();

        Mockito.when(studentDAO.findById(1)).thenReturn(Optional.of(student));

        studentService.updateStudent(1,update);

        Assertions.assertThat(update.getName()).isEqualTo("Marek");
        Mockito.verify(studentDAO,Mockito.times(1)).save(Mockito.any(Student.class));

    }

    @Test
    void shouldCountStudents(){

        Mockito.when(studentDAO.countStudents()).thenReturn(10L);

        long count = studentService.countStudents();

        Assertions.assertThat(count).isEqualTo(10);
        Mockito.verify(studentDAO,Mockito.times(1)).countStudents();

    }

}
