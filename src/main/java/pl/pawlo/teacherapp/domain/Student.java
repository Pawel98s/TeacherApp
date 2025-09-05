package pl.pawlo.teacherapp.domain;


import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.With;

import java.util.List;

@With
@Value
@Builder
@EqualsAndHashCode(of = "studentId")
public class Student {

    Integer studentId;
    String name;
    String surname;
    SchoolClass studentClass;
    List<Integer> grades;
    String phone;
    String notes;


}
