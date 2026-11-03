package com.gm.campus_management;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CourseController {

    private final JdbcTemplate jdbcTemplate;

    public CourseController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/courses")
    public String showCourses(
            @RequestParam(defaultValue = "") String search,
            Model model) {

        String searchText = "%" + search + "%";

        List<Course> courses = jdbcTemplate.query(
            "SELECT course_code, course_name, credits, department " +
            "FROM courses " +
            "WHERE course_code LIKE ? OR course_name LIKE ? OR department LIKE ? " +
            "ORDER BY course_code",
            (result, rowNumber) -> new Course(
                result.getString("course_code"),
                result.getString("course_name"),
                result.getInt("credits"),
                result.getString("department")
            ),
            searchText, searchText, searchText
        );

        model.addAttribute("courses", courses);
        model.addAttribute("search", search);
        return "courses";
    }

    @PostMapping("/courses/add")
    public String addCourse(
            @RequestParam String courseCode,
            @RequestParam String courseName,
            @RequestParam int credits,
            @RequestParam String department) {

        jdbcTemplate.update(
            "INSERT INTO courses (course_code, course_name, credits, department) " +
            "VALUES (?, ?, ?, ?)",
            courseCode, courseName, credits, department
        );

        return "redirect:/courses";
    }

    @PostMapping("/courses/update")
    public String updateCourse(
            @RequestParam String courseCode,
            @RequestParam String courseName,
            @RequestParam int credits,
            @RequestParam String department) {

        jdbcTemplate.update(
            "UPDATE courses SET course_name = ?, credits = ?, department = ? " +
            "WHERE course_code = ?",
            courseName, credits, department, courseCode
        );

        return "redirect:/courses";
    }

    @PostMapping("/courses/delete")
    public String deleteCourse(@RequestParam String courseCode) {
        jdbcTemplate.update(
            "DELETE FROM courses WHERE course_code = ?",
            courseCode
        );

        return "redirect:/courses";
    }
}