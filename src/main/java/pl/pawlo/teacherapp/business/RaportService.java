package pl.pawlo.teacherapp.business;


import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pawlo.teacherapp.domain.Student;

import java.util.List;

@Service
@AllArgsConstructor
public class RaportService {

    StudentService studentService;
    LessonService lessonService;

    @Transactional
    public long countStudents(){
        return studentService.countStudents();
    }

    @Transactional
    public long countLessons(){
        return lessonService.findAll().size();
    }

    @Transactional
    public Integer countProfits(){
        return lessonService.findAll().stream()
                .filter(lesson -> lesson.getStatus().name().equals("ZREALIZOWANA"))
                .mapToInt(lesson -> lesson.getPrice().intValue())
                .sum();
    }

    @Transactional
    public List<Student> findStudentsWithProfit(){
        return studentService.findAll().stream()
                .filter(student -> lessonService.findAll().stream()
                        .filter(lesson -> lesson.getStatus().name().equals("ZREALIZOWANA"))
                        .filter(lesson -> lesson.getStudent().equals(student))
                        .mapToInt(lesson -> lesson.getPrice().intValue())
                        .sum() > 0)
                .toList();
    }

    @Transactional
    public List<Student> findStudentsAbandonedLessons(){
        return studentService.findAll().stream()
                .filter(student -> lessonService.findAll().stream()
                        .filter(lesson -> lesson.getStatus().name().equals("ODWOŁANA"))
                        .filter(lesson -> lesson.getStudent().equals(student))
                        .count() > 0)
                .toList();
    }


}
