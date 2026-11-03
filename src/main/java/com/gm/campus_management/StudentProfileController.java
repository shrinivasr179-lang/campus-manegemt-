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
public class StudentProfileController {

    private final JdbcTemplate jdbcTemplate;

    public StudentProfileController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/students/profile")
    public String showProfile(
            @RequestParam String studentId,
            Model model) {

        List<Map<String, Object>> students = jdbcTemplate.queryForList(
            "SELECT student_id, name, email, course " +
            "FROM students WHERE student_id = ?",
            studentId
        );

        if (students.isEmpty()) {
            model.addAttribute("message", "Student not found. Check the Student ID.");
            return "student-profile";
        }

        model.addAttribute("student", students.get(0));

        model.addAttribute("marks", jdbcTemplate.queryForList(
            "SELECT e.exam_name, e.max_marks, em.marks_obtained, " +
            "ROUND(em.marks_obtained * 100.0 / e.max_marks, 2) AS percentage " +
            "FROM exam_marks em JOIN exams e ON em.exam_id = e.exam_id " +
            "WHERE em.student_id = ? ORDER BY e.exam_date",
            studentId
        ));

        model.addAttribute("attendance", jdbcTemplate.queryForList(
            "SELECT course_code, attendance_date, status " +
            "FROM attendance WHERE student_id = ? " +
            "ORDER BY attendance_date DESC",
            studentId
        ));

        model.addAttribute("achievements", jdbcTemplate.queryForList(
            "SELECT category, activity_name, activity_date, achievement, details " +
            "FROM student_achievements WHERE student_id = ? " +
            "ORDER BY activity_date DESC",
            studentId
        ));

        return "student-profile";
    }

    @PostMapping("/students/profile/achievement")
    public String addAchievement(
            @RequestParam String studentId,
            @RequestParam String category,
            @RequestParam String activityName,
            @RequestParam(required = false) String activityDate,
            @RequestParam(required = false) String achievement,
            @RequestParam(required = false) String details,
            RedirectAttributes redirectAttributes) {

        java.sql.Date date = (activityDate == null || activityDate.isBlank())
                ? null
                : java.sql.Date.valueOf(activityDate);

        String achievementId = "ACH-" + System.currentTimeMillis();

        jdbcTemplate.update(
            "INSERT INTO student_achievements " +
            "(achievement_id, student_id, category, activity_name, activity_date, achievement, details) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)",
            achievementId, studentId, category, activityName, date, achievement, details
        );

        redirectAttributes.addAttribute("studentId", studentId);
        return "redirect:/students/profile";
    }
}