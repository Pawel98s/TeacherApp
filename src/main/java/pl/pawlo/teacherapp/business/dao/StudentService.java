package pl.pawlo.teacherapp.business.dao;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pawlo.teacherapp.database.repository.StudentRepository;
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




}
