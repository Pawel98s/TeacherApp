package pl.pawlo.teacherapp.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.pawlo.teacherapp.api.dto.StudentDTO;
import pl.pawlo.teacherapp.api.dto.mapper.StudentMapper;
import pl.pawlo.teacherapp.business.dao.StudentDAO;
import pl.pawlo.teacherapp.business.dao.StudentService;
import pl.pawlo.teacherapp.database.repository.StudentRepository;
import pl.pawlo.teacherapp.domain.SchoolClass;

@Controller
@AllArgsConstructor
@RequestMapping("/student")
public class StudentController {



    private final StudentService studentService;
    private final StudentMapper studentMapper;

    @GetMapping()
    public String studentPage(Model model) {
        model.addAttribute("student",new StudentDTO());
        return "student";
    }

    @GetMapping("/add")
    public String addStudentPage(Model model) {
        model.addAttribute("student", new StudentDTO());
        model.addAttribute("schoolClass",SchoolClass.values());
        return "addStudent";
    }

    @PostMapping(value ="/add")
    public String addStudent(@ModelAttribute("student") StudentDTO student) {
        studentService.save(studentMapper.mapToDomain(student));
        return "redirect:/student";
    }

    @GetMapping("/list")
    public String studentsPage(Model model) {
        model.addAttribute("students", studentService.findAll());
        return "listStudents";
    }

    @GetMapping("/delete")
    public String deleteStudentPage(Model model){
        model.addAttribute("students",studentService.findAll());
        return "deleteStudent";
    }

    @PostMapping("/delete")
    public String deleteStudent(@RequestParam Integer studentId) {
        studentService.deleteById(studentId);
        return "redirect:/deleteStudent";
    }



}
