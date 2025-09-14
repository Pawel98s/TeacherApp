package pl.pawlo.teacherapp.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pl.pawlo.teacherapp.database.entity.LessonEntity;
import pl.pawlo.teacherapp.database.entity.StudentEntity;
import pl.pawlo.teacherapp.domain.Lesson;
import pl.pawlo.teacherapp.domain.Student;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LessonEntityMapper {

    Lesson mapToDomain(final LessonEntity lessonEntity);


    LessonEntity mapToEntity(final Lesson lesson);

}
