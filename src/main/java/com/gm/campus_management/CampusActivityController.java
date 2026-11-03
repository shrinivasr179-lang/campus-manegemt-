package com.gm.campus_management;

import java.sql.Timestamp;
import java.time.LocalDateTime;
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
public class CampusActivityController {

    private final JdbcTemplate jdbcTemplate;

    public CampusActivityController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/activities")
    public String showActivities(
            @RequestParam(defaultValue = "") String search,
            Model model) {

        String searchText = "%" + search + "%";

        List<CampusActivity> activities = jdbcTemplate.query(
            "SELECT activity_id, activity_name, description, activity_date, location, organizer " +
            "FROM campus_activities " +
            "WHERE activity_id LIKE ? OR activity_name LIKE ? " +
            "OR location LIKE ? OR organizer LIKE ? " +
            "ORDER BY activity_date",
            (result, rowNumber) -> new CampusActivity(
                result.getString("activity_id"),
                result.getString("activity_name"),
                result.getString("description"),
                result.getTimestamp("activity_date").toLocalDateTime(),
                result.getString("location"),
                result.getString("organizer")
            ),
            searchText, searchText, searchText, searchText
        );

        model.addAttribute("activities", activities);
        model.addAttribute("search", search);
        return "activities";
    }

    @PostMapping("/activities/add")
    public String addActivity(
            @RequestParam String activityId,
            @RequestParam String activityName,
            @RequestParam String description,
            @RequestParam String activityDate,
            @RequestParam String location,
            @RequestParam String organizer,
            RedirectAttributes redirectAttributes) {

        try {
            LocalDateTime date = LocalDateTime.parse(activityDate);

            jdbcTemplate.update(
                "INSERT INTO campus_activities " +
                "(activity_id, activity_name, description, activity_date, location, organizer) " +
                "VALUES (?, ?, ?, ?, ?, ?)",
                activityId, activityName, description,
                Timestamp.valueOf(date), location, organizer
            );

            redirectAttributes.addFlashAttribute(
                "message", "Activity added successfully."
            );
        } catch (DateTimeParseException error) {
            redirectAttributes.addFlashAttribute(
                "message", "Please enter a valid activity date and time."
            );
        }

        return "redirect:/activities";
    }

    @PostMapping("/activities/update")
    public String updateActivity(
            @RequestParam String activityId,
            @RequestParam String activityName,
            @RequestParam String description,
            @RequestParam String activityDate,
            @RequestParam String location,
            @RequestParam String organizer,
            RedirectAttributes redirectAttributes) {

        try {
            LocalDateTime date = LocalDateTime.parse(activityDate);

            jdbcTemplate.update(
                "UPDATE campus_activities SET activity_name = ?, description = ?, " +
                "activity_date = ?, location = ?, organizer = ? WHERE activity_id = ?",
                activityName, description, Timestamp.valueOf(date),
                location, organizer, activityId
            );

            redirectAttributes.addFlashAttribute(
                "message", "Activity updated."
            );
        } catch (DateTimeParseException error) {
            redirectAttributes.addFlashAttribute(
                "message", "Please enter a valid activity date and time."
            );
        }

        return "redirect:/activities";
    }

    @PostMapping("/activities/delete")
    public String deleteActivity(
            @RequestParam String activityId,
            RedirectAttributes redirectAttributes) {

        jdbcTemplate.update(
            "DELETE FROM campus_activities WHERE activity_id = ?",
            activityId
        );

        redirectAttributes.addFlashAttribute(
            "message", "Activity deleted."
        );

        return "redirect:/activities";
    }
}