package pl.pawlo.teacherapp.api.controller;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/lesson")
public class LessonController {

    @GetMapping
    public String lessonPage() {
        return "lesson";
    }
}
