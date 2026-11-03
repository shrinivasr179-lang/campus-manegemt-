package com.gm.campus_management;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FacultyController {

    private final JdbcTemplate jdbcTemplate;

    public FacultyController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/faculty")
    public String showFaculty(
            @RequestParam(defaultValue = "") String search,
            Model model) {

        String searchText = "%" + search + "%";

        List<Faculty> facultyMembers = jdbcTemplate.query(
            "SELECT faculty_id, name, email, department FROM faculty " +
            "WHERE faculty_id LIKE ? OR name LIKE ? OR department LIKE ? " +
            "ORDER BY faculty_id",
            (result, rowNumber) -> new Faculty(
                result.getString("faculty_id"),
                result.getString("name"),
                result.getString("email"),
                result.getString("department")
            ),
            searchText, searchText, searchText
        );

        model.addAttribute("facultyMembers", facultyMembers);
        model.addAttribute("search", search);
        return "faculty";
    }

    @PostMapping("/faculty/add")
    public String addFaculty(
            @RequestParam String facultyId,
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String department) {

        jdbcTemplate.update(
            "INSERT INTO faculty (faculty_id, name, email, department) VALUES (?, ?, ?, ?)",
            facultyId, name, email, department
        );

        return "redirect:/faculty";
    }

    @PostMapping("/faculty/update")
    public String updateFaculty(
            @RequestParam String facultyId,
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String department) {

        jdbcTemplate.update(
            "UPDATE faculty SET name = ?, email = ?, department = ? WHERE faculty_id = ?",
            name, email, department, facultyId
        );

        return "redirect:/faculty";
    }

    @PostMapping("/faculty/delete")
    public String deleteFaculty(@RequestParam String facultyId) {
        jdbcTemplate.update(
            "DELETE FROM faculty WHERE faculty_id = ?",
            facultyId
        );

        return "redirect:/faculty";
    }
}