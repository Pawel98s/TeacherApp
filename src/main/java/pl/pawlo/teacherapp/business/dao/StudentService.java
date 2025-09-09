package pl.pawlo.teacherapp.business.dao;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pawlo.teacherapp.database.repository.StudentRepository;
import pl.pawlo.teacherapp.domain.Student;

import java.util.List;
import java.util.Optional;

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
        Student edited = findById(id);
        if (student.getName() != null) edited.setName(student.getName());
        if (student.getSurname() != null) edited.setSurname(student.getSurname());
        if (student.getStudentClass() != null) edited.setStudentClass(student.getStudentClass());
        if (student.getPhone() != null) edited.setPhone(student.getPhone());
        if (student.getNotes() != null) edited.setNotes(student.getNotes());

        save(edited);
        return edited;
    }




}
