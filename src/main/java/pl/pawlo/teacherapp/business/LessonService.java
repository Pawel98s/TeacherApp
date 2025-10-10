package pl.pawlo.teacherapp.business;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.pawlo.teacherapp.api.dto.LessonDTO;
import pl.pawlo.teacherapp.business.dao.LessonDAO;
import pl.pawlo.teacherapp.domain.Lesson;
import pl.pawlo.teacherapp.domain.LessonStatus;
import pl.pawlo.teacherapp.domain.Student;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
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

    @Transactional
    public void deleteById(Integer id) {
        lessonDao.deleteById(id);
    }


    @Transactional
    public void saveAll(List<Lesson> lessons) {
        lessonDao.saveAll(lessons);
    }


    @Transactional
    public void saveScheduledLessons(
            Student student,
            DayOfWeek dayOfWeek,
            LocalTime startLesson,
            LocalTime endLesson,
            BigDecimal price,
            String location,
            String description,
            LocalDate startDate,
            LocalDate endDate
    ){
        List<Lesson> lessons= new ArrayList<>();

        LocalDate currentDate = startDate;

        while(currentDate.getDayOfWeek() != dayOfWeek){
            currentDate=currentDate.plusDays(1);
        }

        while (!currentDate.isAfter(endDate)){
            Lesson lesson = Lesson.builder()
                    .student(student)
                    .date(currentDate)
                    .startLesson(startLesson)
                    .endLesson(endLesson)
                    .price(price)
                    .location(location)
                    .description(description)
                    .build();

            lessons.add(lesson);
            save(lesson);
            currentDate= currentDate.plusWeeks(1);

        }
    }


    @Transactional
    public void updateLessonStatus(Integer id, LessonStatus status) {
        Lesson lesson = findById(id);
        lesson.setStatus(status);
        save(lesson);
    }
}
