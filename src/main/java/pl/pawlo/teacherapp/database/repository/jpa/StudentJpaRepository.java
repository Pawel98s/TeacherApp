package pl.pawlo.teacherapp.database.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pawlo.teacherapp.api.dto.StudentDTO;
import pl.pawlo.teacherapp.database.entity.StudentEntity;

import java.util.List;

@Repository
public interface StudentJpaRepository extends JpaRepository<StudentEntity, Integer> {

    List<StudentEntity> findAll();

}
