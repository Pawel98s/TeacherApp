package pl.pawlo.teacherapp.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LessonStatus {
    W_REALIZACJI("W realizacji"),
    ZAKONCZONA("Zakończona"),
    ODWOŁANA("Odwołana"),
    ODWOŁANA_USPRAWIEDLIWIONA("Odwołana usprawiedliwiona"),
    ANULOWANA("Anulowana");

    final String label;

}
