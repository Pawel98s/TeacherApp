package pl.pawlo.teacherapp.database.repository.jpa;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pawlo.teacherapp.database.entity.LessonEntity;
import pl.pawlo.teacherapp.domain.Lesson;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LessonJpaRepository extends JpaRepository<LessonEntity, Integer> {

    List<LessonEntity> findByDateOrderByStartLessonAsc(LocalDate date);


}
