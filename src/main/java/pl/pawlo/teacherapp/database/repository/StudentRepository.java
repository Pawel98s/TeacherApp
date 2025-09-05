package pl.pawlo.teacherapp.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.pawlo.teacherapp.business.dao.StudentDAO;
import pl.pawlo.teacherapp.database.repository.jpa.StudentJpaRepository;
import pl.pawlo.teacherapp.database.repository.mapper.StudentEntityMapper;

@Repository
@AllArgsConstructor
public class StudentRepository implements StudentDAO {


   private final StudentJpaRepository studentJpaRepository;
   private final StudentEntityMapper studentEntityMapper;







}
