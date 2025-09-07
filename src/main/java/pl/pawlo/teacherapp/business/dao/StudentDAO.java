package pl.pawlo.teacherapp.business.dao;


import pl.pawlo.teacherapp.domain.Student;

import java.util.List;

public interface StudentDAO {

    void save(Student student);

    List<Student> findAll();


}
