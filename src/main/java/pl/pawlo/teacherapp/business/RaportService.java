package pl.pawlo.teacherapp.business;


import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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

}
