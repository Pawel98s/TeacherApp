package pl.pawlo.teacherapp.api;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.pawlo.teacherapp.api.controller.RaportController;
import pl.pawlo.teacherapp.api.dto.StudentDTO;
import pl.pawlo.teacherapp.api.dto.mapper.StudentMapper;
import pl.pawlo.teacherapp.business.RaportService;
import pl.pawlo.teacherapp.domain.Student;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@WebMvcTest(RaportController.class)
public class RaportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RaportService raportService;

    @MockitoBean
    private StudentMapper studentMapper;

    @Test
    void shouldReturnProfitRaportViewWithStudents() throws Exception {
        Student student = Student.builder()
                .studentId(1)
                .name("Kamil")
                .surname("Nowak")
                .build();

        StudentDTO studentDTO = StudentDTO.builder()
                .name("Kamil")
                .surname("Nowak")
                .profit(BigDecimal.valueOf(300))
                .build();

        Mockito.when(raportService.findStudentsWithIndividualProfit())
                .thenReturn(Map.of(student,BigDecimal.valueOf(300)));

        Mockito.when(studentMapper.mapToDTO(student)).thenReturn(studentDTO);

        Mockito.when(raportService.countProfits()).thenReturn(500);

        mockMvc.perform(MockMvcRequestBuilders.get("/raport/profit"))
                .andExpect(MockMvcResultMatchers.view().name("profitRaport"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("students"))
                .andExpect(MockMvcResultMatchers.model().attribute("profit", 500))
                .andExpect(MockMvcResultMatchers.model().attribute("students", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.model().attribute("students", Matchers.hasItem(Matchers.allOf(
                        Matchers.hasProperty("name",Matchers.is("Kamil")),
                        Matchers.hasProperty("surname",Matchers.is("Nowak")),
                        Matchers.hasProperty("profit",Matchers.is(BigDecimal.valueOf(300)))
                ))));

    }

    @Test
    void shouldReturnAbsendedLessonRaportView() throws Exception {
        Student student = Student.builder()
                .name("Kamil")
                .surname("Nowak")
                .build();

        Mockito.when(raportService.findStudentsWithCountOfLessonsCancelled())
                .thenReturn(List.of(Map.entry(student,3)));

        mockMvc.perform(MockMvcRequestBuilders.get("/raport/absendedLessons"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("absendedLessonsRaport"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("absences"))
                .andExpect(MockMvcResultMatchers.model().attribute("absences", Matchers.hasSize(1)));


    }

    @Test
    void shouldReturnPresenceRaportView() throws Exception {
        Student student = Student.builder()
                .name("Kamil")
                .surname("Nowak")
                .build();

        Mockito.when(raportService.findStudentsPercentageOfPresence())
                .thenReturn(Map.of(student,70.0));

        mockMvc.perform(MockMvcRequestBuilders.get("/raport/presence"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("studentsPresenceRaport"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("studentsPresence"))
                .andExpect(MockMvcResultMatchers.model().attribute("studentsPresence", Matchers.aMapWithSize(1)));
    }

    @Test
    void shouldReturnRaportPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/raport"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("raport"));
    }


}
