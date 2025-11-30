package pl.pawlo.teacherapp.repository;


import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import pl.pawlo.teacherapp.business.dao.StudentDAO;
import pl.pawlo.teacherapp.domain.SchoolClass;
import pl.pawlo.teacherapp.domain.Student;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class StudentRepositoryIntegrationTest {


    @Autowired
    private StudentDAO studentRepository;


    @Test
    void shouldSaveAndFindStudentById(){
        Student student = Student.builder()
                .name("Marek")
                .surname("Kowalski")
                .studentClass(SchoolClass.CLASS_2_TECHNIKUM)
                .build();

        Student save = studentRepository.save(student);
        Optional<Student> found = studentRepository.findById(save.getStudentId());

        Assertions.assertTrue(found.isPresent());
        Assertions.assertEquals("Marek", found.get().getName());
        Assertions.assertEquals("Kowalski", found.get().getSurname());
    }

    @Test
    void shouldReturnAllStudents(){
        studentRepository.save(
                Student.builder()
                        .name("Marek")
                        .surname("Kowalski")
                        .studentClass(SchoolClass.CLASS_2_TECHNIKUM)
                        .build());

        studentRepository.save(
                Student.builder()
                        .name("Tomasz")
                        .surname("Nowak")
                        .studentClass(SchoolClass.CLASS_1_LICEUM)
                        .build());

        List<Student> students = studentRepository.findAll();

        Assertions.assertEquals(2, students.size());
    }

    @Test
    void shouldDeleteStudent(){
        Student student=studentRepository.save(
                Student.builder()
                        .name("Marek")
                        .surname("Kowalski")
                        .studentClass(SchoolClass.CLASS_2_TECHNIKUM)
                        .build());

        studentRepository.delete(student);

        Assertions.assertFalse(studentRepository.findById(student.getStudentId()).isPresent());
    }

    @Test
    void shouldDeleteStudentByStudentId(){
        Student student=studentRepository.save(
                Student.builder()
                        .name("Marek")
                        .surname("Kowalski")
                        .studentClass(SchoolClass.CLASS_2_TECHNIKUM)
                        .build());

        studentRepository.deleteById(student.getStudentId());

        Assertions.assertFalse(studentRepository.findById(student.getStudentId()).isPresent());
    }

    @Test
    void shouldReturnCorrectNumberOfStudents(){
        studentRepository.save(
                Student.builder()
                        .name("Marek")
                        .surname("Kowalski")
                        .studentClass(SchoolClass.CLASS_2_TECHNIKUM)
                        .build());

        studentRepository.save(
                Student.builder()
                        .name("Tomasz")
                        .surname("Nowak")
                        .studentClass(SchoolClass.CLASS_1_LICEUM)
                        .build());

        long count = studentRepository.countStudents();

        Assertions.assertEquals(2, count);
    }


}
