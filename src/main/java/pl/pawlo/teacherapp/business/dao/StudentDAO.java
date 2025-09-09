package pl.pawlo.teacherapp.business.dao;


import pl.pawlo.teacherapp.domain.Student;

import java.util.List;
import java.util.Optional;

public interface StudentDAO {

    void save(Student student);

    List<Student> findAll();

    void delete(Student student);

    void deleteById(Integer studentId);

    Optional<Student> findById(Integer studentId);

}
