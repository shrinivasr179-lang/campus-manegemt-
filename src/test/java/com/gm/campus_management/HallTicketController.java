package com.gm.campus_management;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HallTicketController {

    private final JdbcTemplate jdbcTemplate;

    public HallTicketController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/hallticket")
    public String showHallTicket(
            @RequestParam(defaultValue = "") String studentId,
            Model model) {

        model.addAttribute("studentId", studentId);

        if (!studentId.isBlank()) {
            List<Map<String, Object>> students = jdbcTemplate.queryForList(
                "SELECT student_id, name, course FROM students WHERE student_id = ?",
                studentId
            );

            if (!students.isEmpty()) {
                model.addAttribute("student", students.get(0));

                List<Map<String, Object>> exams = jdbcTemplate.queryForList(
                    "SELECT exam_name, course_code, exam_date, start_time, " +
                    "end_time, exam_hall FROM exams ORDER BY exam_date"
                );

                model.addAttribute("exams", exams);
            } else {
                model.addAttribute("message", "No student found with that ID.");
            }
        }

        return "hallticket";
    }
}