package pl.pawlo.teacherapp.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.pawlo.teacherapp.business.dao.LessonDAO;
import pl.pawlo.teacherapp.database.entity.LessonEntity;
import pl.pawlo.teacherapp.database.repository.jpa.LessonJpaRepository;
import pl.pawlo.teacherapp.database.repository.mapper.LessonEntityMapper;
import pl.pawlo.teacherapp.domain.Lesson;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class LessonRepository implements LessonDAO {

    LessonJpaRepository lessonJpaRepository;
    LessonEntityMapper lessonEntityMapper;



    @Override
    public void save(Lesson lesson) {
        lessonJpaRepository.save(lessonEntityMapper.mapToEntity(lesson));
    }

    @Override
    public List<Lesson> findAll() {
        return lessonJpaRepository.findAll()
                .stream()
                .map(lessonEntityMapper::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Lesson> findByDateOrderByStartLessonAsc(LocalDate date) {
        return lessonJpaRepository.findByDateOrderByStartLessonAsc(date)
                .stream()
                .map(lessonEntityMapper::mapToDomain)
                .toList();
    }

    @Override
    public Optional<Lesson> findById(Integer id) {
        return lessonJpaRepository.findById(id)
                .map(lessonEntityMapper::mapToDomain);


    }


}
