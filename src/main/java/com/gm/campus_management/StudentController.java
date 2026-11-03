package com.gm.campus_management;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StudentController {

    private final JdbcTemplate jdbcTemplate;

    public StudentController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/students")
    public String showStudents(
            @RequestParam(defaultValue = "") String search,
            Model model) {

        String searchText = "%" + search + "%";

        List<Student> students = jdbcTemplate.query(
            "SELECT student_id, name, email, course FROM students " +
            "WHERE student_id LIKE ? OR name LIKE ? ORDER BY student_id",
            (result, rowNumber) -> new Student(
                result.getString("student_id"),
                result.getString("name"),
                result.getString("email"),
                result.getString("course")
            ),
            searchText, searchText
        );

        model.addAttribute("students", students);
        model.addAttribute("search", search);
        return "students";
    }

    @PostMapping("/students/add")
    public String addStudent(
            @RequestParam String studentId,
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String course) {

        jdbcTemplate.update(
            "INSERT INTO students (student_id, name, email, course) VALUES (?, ?, ?, ?)",
            studentId, name, email, course
        );

        return "redirect:/students";
    }

    @PostMapping("/students/update")
    public String updateStudent(
            @RequestParam String studentId,
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String course) {

        jdbcTemplate.update(
            "UPDATE students SET name = ?, email = ?, course = ? WHERE student_id = ?",
            name, email, course, studentId
        );

        return "redirect:/students";
    }

    @PostMapping("/students/delete")
    public String deleteStudent(@RequestParam String studentId) {
        jdbcTemplate.update(
            "DELETE FROM students WHERE student_id = ?",
            studentId
        );

        return "redirect:/students";
    }
}