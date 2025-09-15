package pl.pawlo.teacherapp.business;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pawlo.teacherapp.api.dto.LessonDTO;
import pl.pawlo.teacherapp.business.dao.LessonDAO;
import pl.pawlo.teacherapp.domain.Lesson;
import pl.pawlo.teacherapp.domain.Student;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class LessonService {

    LessonDAO lessonDao;
    StudentService studentService;


    @Transactional
    public void save(Lesson lesson) {
        lessonDao.save(lesson);
    }

    @Transactional
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

    @Transactional
    public List<Lesson> findAll(){
        return lessonDao.findAll();
    }

    @Transactional
    public List<Lesson> findByDateOrderByStartLessonAsc(LocalDate date){
        return lessonDao.findByDateOrderByStartLessonAsc(date);
    }

    @Transactional
    public Lesson findById(Integer id){
        return lessonDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));
    }

    @Transactional
    public void updateLesson(Integer id, LessonDTO lessonDTO) {
        Lesson lesson = findById(id);

        lesson.setDate(lessonDTO.getDate());
        lesson.setStartLesson(lessonDTO.getStartLesson());
        lesson.setEndLesson(lessonDTO.getEndLesson());
        lesson.setPrice(lessonDTO.getPrice());
        lesson.setLocation(lessonDTO.getLocation());
        lesson.setDescription(lessonDTO.getDescription());

        if (lessonDTO.getStudentId() != null){
            Student student = studentService.findById(lessonDTO.getStudentId());
            lesson.setStudent(student);
        }
        save(lesson);

    }

}
