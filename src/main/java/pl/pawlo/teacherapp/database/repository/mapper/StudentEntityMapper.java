package pl.pawlo.teacherapp.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import pl.pawlo.teacherapp.api.dto.StudentDTO;
import pl.pawlo.teacherapp.database.entity.StudentEntity;
import pl.pawlo.teacherapp.domain.Student;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StudentEntityMapper {


    Student mapToDomain(final StudentEntity entity);


    StudentEntity mapToEntity(final Student student);


}
