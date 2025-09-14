package pl.pawlo.teacherapp.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.pawlo.teacherapp.business.dao.LessonDAO;
import pl.pawlo.teacherapp.database.repository.jpa.LessonJpaRepository;
import pl.pawlo.teacherapp.database.repository.mapper.LessonEntityMapper;
import pl.pawlo.teacherapp.domain.Lesson;

@Repository
@AllArgsConstructor
public class LessonRepository implements LessonDAO {

    LessonJpaRepository lessonJpaRepository;
    LessonEntityMapper lessonEntityMapper;



    @Override
    public void save(Lesson lesson) {
        lessonJpaRepository.save(lessonEntityMapper.mapToEntity(lesson));
    }
}
