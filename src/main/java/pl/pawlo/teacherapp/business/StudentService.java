package pl.pawlo.teacherapp.business;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.pawlo.teacherapp.business.dao.StudentDAO;
import pl.pawlo.teacherapp.domain.Student;

import java.util.List;

@Service
@AllArgsConstructor
public class StudentService {

    StudentDAO studentDAO;


    @Transactional
    public void save(Student student) {
        studentDAO.save(student);
    }

    @Transactional
    public List<Student> findAll(){
        return studentDAO.findAll();
    }

    @Transactional
    public void delete(Student student){
        studentDAO.delete(student);
    }

    @Transactional
    public void deleteById(Integer studentId){
        studentDAO.deleteById(studentId);
    }

    @Transactional
    public Student findById(Integer studentId){
        return studentDAO.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }







    @Transactional
    public Student updateStudent(Integer id,Student student){
        Student existing  = findById(id);

        Student updated = Student.builder()
                .studentId(existing.getStudentId())
                .name(student.getName() != null ? student.getName() : existing.getName())
                .surname(student.getSurname() != null ? student.getSurname() : existing.getSurname())
                .studentClass(student.getStudentClass() != null ? student.getStudentClass() : existing.getStudentClass())
                .phone(student.getPhone() != null ? student.getPhone() : existing.getPhone())
                .notes(student.getNotes() != null ? student.getNotes() : existing.getNotes())
                .grades(existing.getGrades())
                .build();


        save(updated );
        return updated ;
    }

    @Transactional
    public long countStudents(){
        return studentDAO.countStudents();
    }




}
