package pl.pawlo.teacherapp.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class StudentController {

    public static final String STUDENT = "/student";

    @GetMapping(value = STUDENT)
    public String studentPage() {
        return "student";
    }
}
