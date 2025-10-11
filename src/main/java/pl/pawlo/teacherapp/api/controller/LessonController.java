package pl.pawlo.teacherapp.api.controller;


import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.pawlo.teacherapp.api.dto.LessonDTO;
import pl.pawlo.teacherapp.api.dto.mapper.LessonMapper;
import pl.pawlo.teacherapp.api.dto.mapper.StudentMapper;
import pl.pawlo.teacherapp.business.LessonService;
import pl.pawlo.teacherapp.business.StudentService;
import pl.pawlo.teacherapp.domain.Lesson;
import pl.pawlo.teacherapp.domain.LessonStatus;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/lesson")
public class LessonController {

   private final StudentService studentService;
   private final StudentMapper studentMapper;
   private final LessonService lessonService;
   private final LessonMapper lessonMapper;


    @GetMapping
    public String lessonPage() {
        return "lesson";
    }

    @GetMapping("/add")
    public String addLessonPage(Model model) {
        model.addAttribute("lesson", new LessonDTO());
        model.addAttribute("students",studentService.findAll());
        return "addLesson";
    }

    @PostMapping("/add")
    public String saveLesson(@ModelAttribute("lesson") LessonDTO lesson) {
        lessonService.saveFromDTO(lesson);
        return "redirect:/lesson";
    }

//    @GetMapping("/list")
//    public String getLessonList(Model model) {
//        List<Lesson> lessons = lessonService.findAll();
//        model.addAttribute("lessons", lessons);
//        return "listLessons";
//    }

    @GetMapping("/list")
    public String getLessonList(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                Model model) {
        List<Lesson> lessons;

        if (date != null) {
            lessons = lessonService.findByDateOrderByStartLessonAsc(date);
        } else {
            lessons = lessonService.findAll();
        }

        model.addAttribute("lessons", lessons);
        model.addAttribute("selectedDate", date);

        return "listLessons";
    }

    @GetMapping("/listForRealization")
    public String getLessonListWithStatusForRealization(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                Model model) {
        List<Lesson> lessons;

        if (date != null) {
            lessons = lessonService.findByDateOrderByStartLessonAsc(date);
        } else {
            lessons = lessonService.findLessonsWithStatusForRealization();
        }

        model.addAttribute("lessons", lessons);
        model.addAttribute("selectedDate", date);

        return "listLessonsForRealization";
    }

    @GetMapping("/listFinished")
    public String getLessonListWithStatusFinished(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            Model model) {
        List<Lesson> lessons;

        if (date != null) {
            lessons = lessonService.findByDateOrderByStartLessonAsc(date);
        } else {
            lessons = lessonService.findLessonsWithStatusFinished();
        }

        model.addAttribute("lessons", lessons);
        model.addAttribute("selectedDate", date);

        return "listLessonsFinished";
    }

    @GetMapping("/listCancelled")
    public String getLessonListWithStatusCancelled(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            Model model) {
        List<Lesson> lessons;

        if (date != null) {
            lessons = lessonService.findByDateOrderByStartLessonAsc(date);
        } else {
            lessons = lessonService.findLessonsWithStatusCancelled();
        }

        model.addAttribute("lessons", lessons);
        model.addAttribute("selectedDate", date);

        return "listLessonsCanselled";
    }


    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        LessonDTO lesson = lessonMapper.mapToDTO(lessonService.findById(id));
        model.addAttribute("lesson", lesson);
        model.addAttribute("students", studentService.findAll());
        return "lessonEdit";
    }

    @PostMapping("/edit/{id}")
    public String updateLesson(@PathVariable("id") Integer id,
                               @ModelAttribute("lesson") LessonDTO lessonDTO) {
        lessonService.updateLesson(id, lessonDTO);
        return "redirect:/lesson/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteLesson(@PathVariable("id") Integer lessonId) {
        lessonService.deleteById(lessonId);
        return "redirect:/lesson/list";
    }

    @GetMapping("/schedule")
    public String showScheduleForm(Model model) {
        model.addAttribute("students", studentService.findAll());
        model.addAttribute("daysOfWeek", DayOfWeek.values());
        model.addAttribute("lessonForm", new LessonDTO());
        return "schedulePage";
    }
    @PostMapping("/schedule")
    public String saveScheduledLessons(@ModelAttribute LessonDTO lessonDTO, Model model) {

        lessonService.saveScheduledLessons(
                studentService.findById(lessonDTO.getStudentId()),
                lessonDTO.getDayOfWeek(),
                lessonDTO.getStartLesson(),
                lessonDTO.getEndLesson(),
                lessonDTO.getPrice(),
                lessonDTO.getLocation(),
                lessonDTO.getDescription(),
                lessonDTO.getStartDate(),
                lessonDTO.getEndDate()

        );

        model.addAttribute("message", "Lekcje zostały zapisane!");
        return "redirect:/lesson/list";
    }

    @PostMapping("/{id}/status")
    public String updateLessonStatus(@PathVariable("id") Integer id,
                                     @RequestParam("status") LessonStatus status) {
        lessonService.updateLessonStatus(id, status);

        return "redirect:/lesson/list";
    }

    @PostMapping("/{id}/statusRealization")
    public String updateLessonStatusForRealizationPage(@PathVariable("id") Integer id,
                                     @RequestParam("status") LessonStatus status) {
        lessonService.updateLessonStatus(id, status);

        return "redirect:/lesson/listForRealization";
    }



}
