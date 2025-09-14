package pl.pawlo.teacherapp.business.dao;

import pl.pawlo.teacherapp.api.dto.LessonDTO;
import pl.pawlo.teacherapp.domain.Lesson;

public interface LessonDAO {

    void save(Lesson lesson);


}
