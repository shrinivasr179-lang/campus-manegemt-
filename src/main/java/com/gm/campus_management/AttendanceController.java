package com.gm.campus_management;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AttendanceController {

    private final JdbcTemplate jdbcTemplate;

    public AttendanceController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/attendance")
    public String showAttendance(
            @RequestParam(defaultValue = "") String search,
            Model model) {

        String searchText = "%" + search + "%";

        List<Attendance> records = jdbcTemplate.query(
            "SELECT attendance_id, student_id, course_code, attendance_date, status " +
            "FROM attendance " +
            "WHERE attendance_id LIKE ? OR student_id LIKE ? OR course_code LIKE ? " +
            "ORDER BY attendance_date DESC",
            (result, rowNumber) -> new Attendance(
                result.getString("attendance_id"),
                result.getString("student_id"),
                result.getString("course_code"),
                result.getDate("attendance_date").toLocalDate(),
                result.getString("status")
            ),
            searchText, searchText, searchText
        );

        model.addAttribute("records", records);
        model.addAttribute("search", search);
        return "attendance";
    }

    @PostMapping("/attendance/add")
    public String addAttendance(
            @RequestParam String attendanceId,
            @RequestParam String studentId,
            @RequestParam String courseCode,
            @RequestParam String attendanceDate,
            @RequestParam String status,
            RedirectAttributes redirectAttributes) {

        try {
            LocalDate date = LocalDate.parse(attendanceDate);

            jdbcTemplate.update(
                "INSERT INTO attendance " +
                "(attendance_id, student_id, course_code, attendance_date, status) " +
                "VALUES (?, ?, ?, ?, ?)",
                attendanceId, studentId, courseCode, Date.valueOf(date), status
            );

            redirectAttributes.addFlashAttribute(
                "message", "Attendance record added."
            );
        } catch (DateTimeParseException error) {
            redirectAttributes.addFlashAttribute(
                "message", "Please enter a valid date."
            );
        }

        return "redirect:/attendance";
    }

    @PostMapping("/attendance/update")
    public String updateAttendance(
            @RequestParam String attendanceId,
            @RequestParam String status,
            RedirectAttributes redirectAttributes) {

        jdbcTemplate.update(
            "UPDATE attendance SET status = ? WHERE attendance_id = ?",
            status, attendanceId
        );

        redirectAttributes.addFlashAttribute(
            "message", "Attendance status updated."
        );

        return "redirect:/attendance";
    }

    @PostMapping("/attendance/delete")
    public String deleteAttendance(
            @RequestParam String attendanceId,
            RedirectAttributes redirectAttributes) {

        jdbcTemplate.update(
            "DELETE FROM attendance WHERE attendance_id = ?",
            attendanceId
        );

        redirectAttributes.addFlashAttribute(
            "message", "Attendance record deleted."
        );

        return "redirect:/attendance";
    }
}
