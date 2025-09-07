package pl.pawlo.teacherapp.api.dto.mapper;


import org.mapstruct.Mapper;
import pl.pawlo.teacherapp.api.dto.StudentDTO;
import pl.pawlo.teacherapp.domain.Student;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    StudentDTO mapToDTO(final Student student);

    Student mapToDomain(final StudentDTO studentDTO);
}
