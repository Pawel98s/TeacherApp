package pl.pawlo.teacherapp.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.pawlo.teacherapp.api.dto.StudentDTO;
import pl.pawlo.teacherapp.api.dto.mapper.StudentMapper;
import pl.pawlo.teacherapp.business.RaportService;
import pl.pawlo.teacherapp.domain.Student;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping("/raport")
public class RaportController {

    private final RaportService raportService;
    private final StudentMapper studentMapper;

    @GetMapping()
    public String raportPage() {
        return "raport";
    }


    @GetMapping("/profit")
    public String profitPage(Model model) {

        Map<Student, BigDecimal> studentProfits = raportService.findStudentsWithIndividualProfit();

        List<StudentDTO> students = studentProfits.entrySet().stream()
                .map(entry -> {
                    Student student = entry.getKey();
                    BigDecimal profit = entry.getValue();
                    StudentDTO dto = studentMapper.mapToDTO(student);
                    dto.setProfit(profit);
                    return dto;
                })
                .toList();


        model.addAttribute("students", students);
        model.addAttribute("profit",raportService.countProfits());
        return "profitRaport";
    }

    @GetMapping("/presence")
    public String showStudentsPresence(Model model) {
        Map<Student, Double> studentsPresence = raportService.findStudentsPercentageOfPresence();


        Map<Student, String> formattedPresence = studentsPresence.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> String.format(Locale.US, "%.2f", e.getValue())
                ));

        model.addAttribute("studentsPresence", formattedPresence);
        return "studentsPresenceRaport";
    }

    @GetMapping("/absendedLessons")
    public String absendedLessonsPage(Model model) {
        List<Map.Entry<Student, Integer>> absences = raportService.findStudentsWithCountOfLessonsCancelled();

        model.addAttribute("absences", absences);
        return "absendedLessonsRaport";
    }



}
