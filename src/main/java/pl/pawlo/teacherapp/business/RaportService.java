package pl.pawlo.teacherapp.business;


import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pawlo.teacherapp.domain.Student;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
                .filter(lesson -> lesson.getStatus().name().equals("ZAKONCZONA"))
                .mapToInt(lesson -> lesson.getPrice().intValue())
                .sum();
    }

    @Transactional
    public Map<Student, BigDecimal> findStudentsWithIndividualProfit() {
        return studentService.findAll().stream()
                .collect(Collectors.toMap(
                        student -> student,
                        student -> lessonService.findAll().stream()
                                .filter(lesson -> lesson.getStatus().name().equals("ZAKONCZONA"))
                                .filter(lesson -> lesson.getStudent().equals(student))
                                .map(lesson -> lesson.getPrice())
                                .reduce(BigDecimal.ZERO, BigDecimal::add)
                ))
                .entrySet().stream()
                .filter(entry -> entry.getValue().compareTo(BigDecimal.ZERO) > 0)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
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
