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
import java.util.Map;

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



}
