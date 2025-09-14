package pl.pawlo.teacherapp.api.controller;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.pawlo.teacherapp.api.dto.LessonDTO;
import pl.pawlo.teacherapp.api.dto.StudentDTO;
import pl.pawlo.teacherapp.api.dto.mapper.LessonMapper;
import pl.pawlo.teacherapp.api.dto.mapper.StudentMapper;
import pl.pawlo.teacherapp.business.dao.LessonService;
import pl.pawlo.teacherapp.business.dao.StudentService;
import pl.pawlo.teacherapp.database.entity.LessonEntity;
import pl.pawlo.teacherapp.database.entity.StudentEntity;
import pl.pawlo.teacherapp.domain.Lesson;
import pl.pawlo.teacherapp.domain.Student;

@Controller
@AllArgsConstructor
@RequestMapping("/lesson")
public class LessonController {

   private final StudentService studentService;
   private final StudentMapper studentMapper;
   private final LessonService lessonService;
   private final LessonMapper lessonMapper;


    @GetMapping
    public String lessonPage() {
        return "lesson";
    }

    @GetMapping("/add")
    public String addLessonPage(Model model) {
        model.addAttribute("lesson", new LessonDTO());
        model.addAttribute("students",studentService.findAll());
        return "addLesson";
    }

    @PostMapping("/add")
    public String saveLesson(@ModelAttribute("lesson") LessonDTO lesson) {
        lessonService.saveFromDTO(lesson);
        return "redirect:/lesson";
    }
}
