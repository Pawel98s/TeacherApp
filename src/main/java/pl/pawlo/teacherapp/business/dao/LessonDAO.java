package pl.pawlo.teacherapp.business.dao;

import pl.pawlo.teacherapp.domain.Lesson;

import java.time.LocalDate;
import java.util.List;

public interface LessonDAO {

    void save(Lesson lesson);


    List<Lesson> findAll();

    List<Lesson> findByDateOrderByStartLessonAsc(LocalDate date);

}
