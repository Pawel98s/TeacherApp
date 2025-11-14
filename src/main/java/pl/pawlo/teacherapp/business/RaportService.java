package pl.pawlo.teacherapp.business;


import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pawlo.teacherapp.domain.Lesson;
import pl.pawlo.teacherapp.domain.LessonStatus;
import pl.pawlo.teacherapp.domain.Student;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RaportService {

    StudentService studentService;
    LessonService lessonService;

    public long countStudents(){
        return studentService.countStudents();
    }

    public long countLessons(){
        return lessonService.findAll().size();
    }

    public Integer countProfits(){
        return lessonService.findAll().stream()
                .filter(lesson -> lesson.getStatus().name().equals("ZAKONCZONA"))
                .mapToInt(lesson -> lesson.getPrice().intValue())
                .sum();
    }

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


    public List<Student> findStudentsAbandonedLessons() {
        return lessonService.findAll().stream()
                .filter(lesson -> lesson.getStatus() == LessonStatus.ODWOŁANA)
                .map(Lesson::getStudent)
                .filter(Objects::nonNull)
                .distinct()
                .toList();
    }


    public Map<Student, Double> findStudentsPercentageOfPresence() {
        List<Lesson> allLessons = lessonService.findAll();

        Map<Student, List<Lesson>> lessonsByStudent = allLessons.stream()
                .collect(Collectors.groupingBy(Lesson::getStudent));

        return studentService.findAll().stream()
                .collect(Collectors.toMap(
                        student -> student,
                        student -> {
                            List<Lesson> studentLessons = lessonsByStudent.getOrDefault(student, List.of())
                                    .stream()
                                    .filter(lesson -> lesson.getStatus() == LessonStatus.ZAKONCZONA
                                            || lesson.getStatus() == LessonStatus.ODWOŁANA
                                            || lesson.getStatus() == LessonStatus.ANULOWANA
                                            || lesson.getStatus() == LessonStatus.ODWOŁANA_USPRAWIEDLIWIONA)
                                    .toList();

                            if (studentLessons.isEmpty()) {
                                return 0.0;
                            }

                            long attendedLessons = studentLessons.stream()
                                    .filter(lesson -> lesson.getStatus() == LessonStatus.ZAKONCZONA)
                                    .count();

                            return (attendedLessons * 100.0) / studentLessons.size();
                        }
                ));
    }


    public List<Map.Entry<Student, Integer>> findStudentsWithCountOfLessonsCancelled() {
        List<Lesson> allLessons = lessonService.findAll();

        Map<Student, List<Lesson>> lessonsByStudent = allLessons.stream()
                .collect(Collectors.groupingBy(Lesson::getStudent));

        Map<Student, Integer> absences = studentService.findAll().stream()
                .collect(Collectors.toMap(
                        student -> student,
                        student -> (int) lessonsByStudent.getOrDefault(student, List.of())
                                .stream()
                                .filter(lesson -> lesson.getStatus() == LessonStatus.ANULOWANA
                                        || lesson.getStatus() == LessonStatus.ODWOŁANA
                                        || lesson.getStatus() == LessonStatus.ODWOŁANA_USPRAWIEDLIWIONA)
                                .count()
                ));


        return absences.entrySet().stream()
                .sorted(Map.Entry.<Student, Integer>comparingByValue().reversed())
                .toList();
    }



}
