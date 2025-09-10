package pl.pawlo.teacherapp.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.pawlo.teacherapp.api.dto.StudentDTO;
import pl.pawlo.teacherapp.business.dao.StudentDAO;
import pl.pawlo.teacherapp.database.entity.StudentEntity;
import pl.pawlo.teacherapp.database.repository.jpa.StudentJpaRepository;
import pl.pawlo.teacherapp.database.repository.mapper.StudentEntityMapper;
import pl.pawlo.teacherapp.domain.Student;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class StudentRepository implements StudentDAO {


   private final StudentJpaRepository studentJpaRepository;
   private final StudentEntityMapper studentEntityMapper;


   @Override
   public void save(Student student) {
      studentJpaRepository.save(studentEntityMapper.mapToEntity(student));
   }

   @Override
   public List<Student> findAll() {
      return studentJpaRepository.findAll()
              .stream()
              .map(studentEntityMapper::mapToDomain)
              .collect(Collectors.toList());
   }

   @Override
   public void delete(Student student) {
      StudentEntity studentEntity = studentEntityMapper.mapToEntity(student);
      studentJpaRepository.delete(studentEntity);
   }

   @Override
   public void deleteById(Integer studentId) {
      studentJpaRepository.deleteById(studentId);
   }

   @Override
   public Optional<Student> findById(Integer studentId) {
      Optional<StudentEntity> student = studentJpaRepository.findById(studentId);
      return student.map(studentEntityMapper::mapToDomain);
   }


}
