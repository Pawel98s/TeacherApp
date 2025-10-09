package pl.pawlo.teacherapp.business.dao;

import pl.pawlo.teacherapp.domain.Lesson;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LessonDAO {

    void save(Lesson lesson);

    List<Lesson> findAll();

    List<Lesson> findByDateOrderByStartLessonAsc(LocalDate date);

    Optional<Lesson> findById(Integer id);

    void deleteById(Integer id);

    void saveAll(List<Lesson> lessons);
}
