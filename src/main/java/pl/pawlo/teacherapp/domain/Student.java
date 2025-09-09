package pl.pawlo.teacherapp.domain;


import lombok.*;

import java.util.List;


@Builder
@Data
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
