package pl.pawlo.teacherapp.database.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pawlo.teacherapp.database.entity.StudentEntity;

@Repository
public interface StudentJpaRepository extends JpaRepository<StudentEntity, Integer> {
}
