package pl.pawlo.teacherapp.business.dao;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pawlo.teacherapp.api.dto.LessonDTO;
import pl.pawlo.teacherapp.domain.Lesson;
import pl.pawlo.teacherapp.domain.Student;

@Service
@AllArgsConstructor
public class LessonService {

    LessonDAO lessonDao;
    StudentService studentService;


    @Transactional
    public void save(Lesson lesson) {
        lessonDao.save(lesson);
    }

    public void saveFromDTO(LessonDTO lessonDTO) {

        Student student = studentService.findById(lessonDTO.getStudentId());
        if (student == null) throw new RuntimeException("Uczeń nie istnieje");

        Lesson lesson= Lesson.builder()
                .date(lessonDTO.getDate())
                .startLesson(lessonDTO.getStartLesson())
                .endLesson(lessonDTO.getEndLesson())
                .price(lessonDTO.getPrice())
                .location(lessonDTO.getLocation())
                .description(lessonDTO.getDescription())
                .student(student)
                .build();
        save(lesson);
    }
}
