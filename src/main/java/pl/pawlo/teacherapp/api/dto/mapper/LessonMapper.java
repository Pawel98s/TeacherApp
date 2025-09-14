package pl.pawlo.teacherapp.api.dto.mapper;

import org.mapstruct.Mapper;
import pl.pawlo.teacherapp.api.dto.LessonDTO;
import pl.pawlo.teacherapp.api.dto.StudentDTO;
import pl.pawlo.teacherapp.domain.Lesson;
import pl.pawlo.teacherapp.domain.Student;

@Mapper(componentModel = "spring")
public interface LessonMapper {


    LessonDTO mapToDTO(final Lesson lesson);

    Lesson mapToDomain(final LessonDTO lessonDTO);
}
