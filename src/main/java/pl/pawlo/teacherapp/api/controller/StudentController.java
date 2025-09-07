package pl.pawlo.teacherapp.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.pawlo.teacherapp.api.dto.StudentDTO;
import pl.pawlo.teacherapp.api.dto.mapper.StudentMapper;
import pl.pawlo.teacherapp.business.dao.StudentDAO;
import pl.pawlo.teacherapp.business.dao.StudentService;
import pl.pawlo.teacherapp.database.repository.StudentRepository;

@Controller
@AllArgsConstructor
public class StudentController {

    public static final String STUDENT = "/student";

    private final StudentService studentService;
    private final StudentMapper studentMapper;

    @GetMapping(value = STUDENT)
    public String studentPage(Model model) {
        model.addAttribute("student",new StudentDTO());
        return "student";
    }

    @PostMapping(value ="/student/add")
    public String addStudent(@ModelAttribute("student") StudentDTO student) {
        studentService.save(studentMapper.mapToDomain(student));
        return "redirect:/student";
    }

    @GetMapping("/student/add")
    public String addStudentPage(Model model) {
        model.addAttribute("student", new StudentDTO());
        return "addStudent";
    }

    @GetMapping("/student/list")
    public String studentsPage(Model model) {
        model.addAttribute("students", studentService.findAll());
        return "listStudents";
    }



}
