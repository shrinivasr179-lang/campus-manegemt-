package com.gm.campus_management;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ExamController {

    private final JdbcTemplate jdbcTemplate;

    public ExamController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/exams")
    public String showExams(
            @RequestParam(defaultValue = "") String search,
            Model model) {

        String searchText = "%" + search + "%";

        List<Exam> exams = jdbcTemplate.query(
            "SELECT exam_id, exam_name, course_code, exam_date, start_time, " +
            "end_time, exam_hall, max_marks FROM exams " +
            "WHERE exam_id LIKE ? OR exam_name LIKE ? " +
            "OR course_code LIKE ? OR exam_hall LIKE ? " +
            "ORDER BY exam_date, start_time",
            (result, rowNumber) -> new Exam(
                result.getString("exam_id"),
                result.getString("exam_name"),
                result.getString("course_code"),
                result.getDate("exam_date").toLocalDate(),
                result.getTime("start_time").toLocalTime(),
                result.getTime("end_time").toLocalTime(),
                result.getString("exam_hall"),
                result.getInt("max_marks")
            ),
            searchText, searchText, searchText, searchText
        );

        LocalDate today = LocalDate.now();

        model.addAttribute("exams", exams);
        model.addAttribute("search", search);
        model.addAttribute(
            "upcomingExams",
            exams.stream()
                .filter(exam -> exam.getExamDate().isAfter(today))
                .collect(Collectors.toList())
        );
        model.addAttribute(
            "todayExams",
            exams.stream()
                .filter(exam -> exam.getExamDate().isEqual(today))
                .collect(Collectors.toList())
        );
        model.addAttribute(
            "completedExams",
            exams.stream()
                .filter(exam -> exam.getExamDate().isBefore(today))
                .collect(Collectors.toList())
        );

        return "exams";
    }

    @PostMapping("/exams/add")
    public String addExam(
            @RequestParam String examId,
            @RequestParam String examName,
            @RequestParam String courseCode,
            @RequestParam String examDate,
            @RequestParam String startTime,
            @RequestParam String endTime,
            @RequestParam String examHall,
            @RequestParam int maxMarks,
            RedirectAttributes redirectAttributes) {

        try {
            LocalDate date = LocalDate.parse(examDate);
            LocalTime start = LocalTime.parse(startTime);
            LocalTime end = LocalTime.parse(endTime);

            if (!end.isAfter(start) || maxMarks <= 0) {
                redirectAttributes.addFlashAttribute(
                    "message", "Check the exam times and maximum marks."
                );
                return "redirect:/exams";
            }

            jdbcTemplate.update(
                "INSERT INTO exams " +
                "(exam_id, exam_name, course_code, exam_date, start_time, " +
                "end_time, exam_hall, max_marks) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                examId, examName, courseCode, Date.valueOf(date),
                Time.valueOf(start), Time.valueOf(end), examHall, maxMarks
            );

            redirectAttributes.addFlashAttribute(
                "message", "Exam added to the timetable."
            );
        } catch (DateTimeParseException error) {
            redirectAttributes.addFlashAttribute(
                "message", "Please enter a valid exam date and time."
            );
        }

        return "redirect:/exams";
    }

    @PostMapping("/exams/update")
    public String updateExam(
            @RequestParam String examId,
            @RequestParam String examName,
            @RequestParam String courseCode,
            @RequestParam String examDate,
            @RequestParam String startTime,
            @RequestParam String endTime,
            @RequestParam String examHall,
            @RequestParam int maxMarks,
            RedirectAttributes redirectAttributes) {

        try {
            LocalDate date = LocalDate.parse(examDate);
            LocalTime start = LocalTime.parse(startTime);
            LocalTime end = LocalTime.parse(endTime);

            if (!end.isAfter(start) || maxMarks <= 0) {
                redirectAttributes.addFlashAttribute(
                    "message", "Check the exam times and maximum marks."
                );
                return "redirect:/exams";
            }

            jdbcTemplate.update(
                "UPDATE exams SET exam_name = ?, course_code = ?, exam_date = ?, " +
                "start_time = ?, end_time = ?, exam_hall = ?, max_marks = ? " +
                "WHERE exam_id = ?",
                examName, courseCode, Date.valueOf(date),
                Time.valueOf(start), Time.valueOf(end), examHall, maxMarks, examId
            );

            redirectAttributes.addFlashAttribute(
                "message", "Exam timetable entry updated."
            );
        } catch (DateTimeParseException error) {
            redirectAttributes.addFlashAttribute(
                "message", "Please enter a valid exam date and time."
            );
        }

        return "redirect:/exams";
    }

    @PostMapping("/exams/delete")
    public String deleteExam(
            @RequestParam String examId,
            RedirectAttributes redirectAttributes) {

        jdbcTemplate.update(
            "DELETE FROM exams WHERE exam_id = ?",
            examId
        );

        redirectAttributes.addFlashAttribute(
            "message", "Exam deleted from the timetable."
        );

        return "redirect:/exams";
    }
}