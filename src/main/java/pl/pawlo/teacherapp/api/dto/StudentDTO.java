package pl.pawlo.teacherapp.api.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.pawlo.teacherapp.domain.SchoolClass;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {


    String name;
    String surname;
    SchoolClass studentClass;
    List<Integer> grades;
    String phone;
    String notes;
}
