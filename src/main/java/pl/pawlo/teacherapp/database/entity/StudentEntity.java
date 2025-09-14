package pl.pawlo.teacherapp.database.entity;

import jakarta.persistence.*;
import lombok.*;
import pl.pawlo.teacherapp.domain.SchoolClass;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(of = "studentId")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "student")
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    Integer studentId;

    @Column(name = "name")
    String name;

    @Column(name = "surname")
    String surname;

    @Enumerated(EnumType.STRING)
    SchoolClass studentClass;

    @ElementCollection
    @CollectionTable(
            name = "student_grades",
            joinColumns = @JoinColumn(name = "student_id")
    )
    @Column(name = "grade")
    List<Integer> grades;

    @Column(name = "phone")
    String phone;

    @Column(name = "notes")
    String notes;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    List<LessonEntity> lessons=new ArrayList<>();
}
