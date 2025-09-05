package pl.pawlo.teacherapp.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    static final String HOME = "/";

    @GetMapping(value = HOME)
    public String homePage() {
        return "home";
    }
}
