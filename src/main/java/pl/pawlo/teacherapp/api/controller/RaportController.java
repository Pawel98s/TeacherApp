package pl.pawlo.teacherapp.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.pawlo.teacherapp.business.RaportService;

@Controller
@AllArgsConstructor
@RequestMapping("/raport")
public class RaportController {

    private final RaportService raportService;

    @GetMapping()
    public String raportPage(Model model) {
        model.addAttribute("studentCount", raportService.countStudents());
        model.addAttribute("lessonCount", raportService.countLessons());
        return "raport";
    }


}
