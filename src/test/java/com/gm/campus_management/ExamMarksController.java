package com.gm.campus_management;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ExamMarksController {

    private final JdbcTemplate jdbcTemplate;

    public ExamMarksController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/marks")
    public String showMarks(Model model) {
        List<Map<String, Object>> exams = jdbcTemplate.queryForList(
            "SELECT exam_id, exam_name, max_marks FROM exams ORDER BY exam_date"
        );

        List<Map<String, Object>> marks = jdbcTemplate.queryForList(
            "SELECT em.mark_id AS markId, s.name AS studentName, " +
            "e.exam_name AS examName, em.marks_obtained AS marksObtained, " +
            "e.max_marks AS maxMarks, " +
            "ROUND(em.marks_obtained * 100.0 / e.max_marks, 2) AS percentage " +
            "FROM exam_marks em " +
            "JOIN students s ON em.student_id = s.student_id " +
            "JOIN exams e ON em.exam_id = e.exam_id " +
            "ORDER BY s.name, e.exam_date"
        );

        model.addAttribute("exams", exams);
        model.addAttribute("marks", marks);
        return "marks";
    }

    @PostMapping("/marks/add")
    public String addMarks(
            @RequestParam String markId,
            @RequestParam String studentId,
            @RequestParam String examId,
            @RequestParam double marksObtained,
            RedirectAttributes redirectAttributes) {

        Integer maxMarks = jdbcTemplate.queryForObject(
            "SELECT max_marks FROM exams WHERE exam_id = ?",
            Integer.class,
            examId
        );

        if (maxMarks == null || marksObtained < 0 || marksObtained > maxMarks) {
            redirectAttributes.addFlashAttribute(
                "message", "Check the exam ID and marks. Marks must be between 0 and the exam maximum."
            );
            return "redirect:/marks";
        }

        jdbcTemplate.update(
            "INSERT INTO exam_marks (mark_id, student_id, exam_id, marks_obtained) " +
            "VALUES (?, ?, ?, ?)",
            markId, studentId, examId, marksObtained
        );

        redirectAttributes.addFlashAttribute("message", "Marks saved.");
        return "redirect:/marks";
    }
}