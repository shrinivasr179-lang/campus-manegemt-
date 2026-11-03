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
public class ResourceBookingController {

    private final JdbcTemplate jdbcTemplate;

    public ResourceBookingController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/bookings")
    public String showBookings(
            @RequestParam(defaultValue = "") String search,
            Model model) {

        String searchText = "%" + search + "%";

        List<ResourceBooking> bookings = jdbcTemplate.query(
            "SELECT booking_id, resource_id, booked_by, start_time, end_time, status " +
            "FROM resource_bookings " +
            "WHERE booking_id LIKE ? OR resource_id LIKE ? OR booked_by LIKE ? " +
            "ORDER BY start_time",
            (result, rowNumber) -> new ResourceBooking(
                result.getString("booking_id"),
                result.getString("resource_id"),
                result.getString("booked_by"),
                result.getTimestamp("start_time").toLocalDateTime(),
                result.getTimestamp("end_time").toLocalDateTime(),
                result.getString("status")
            ),
            searchText, searchText, searchText
        );

        model.addAttribute("bookings", bookings);
        model.addAttribute("search", search);
        return "bookings";
    }

    @PostMapping("/bookings/add")
    public String addBooking(
            @RequestParam String bookingId,
            @RequestParam String resourceId,
            @RequestParam String bookedBy,
            @RequestParam String startTime,
            @RequestParam String endTime,
            RedirectAttributes redirectAttributes) {

        try {
            LocalDateTime start = LocalDateTime.parse(startTime);
            LocalDateTime end = LocalDateTime.parse(endTime);

            if (!end.isAfter(start)) {
                redirectAttributes.addFlashAttribute(
                    "message", "End time must be after start time."
                );
                return "redirect:/bookings";
            }

            Integer resourceCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM resources WHERE resource_id = ?",
                Integer.class,
                resourceId
            );

            if (resourceCount == null || resourceCount == 0) {
                redirectAttributes.addFlashAttribute(
                    "message", "That Resource ID was not found. Add the resource first."
                );
                return "redirect:/bookings";
            }

            Integer conflicts = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM resource_bookings " +
                "WHERE resource_id = ? AND status = 'BOOKED' " +
                "AND start_time < ? AND end_time > ?",
                Integer.class,
                resourceId,
                Timestamp.valueOf(end),
                Timestamp.valueOf(start)
            );

            if (conflicts != null && conflicts > 0) {
                redirectAttributes.addFlashAttribute(
                    "message", "That resource is already booked during this time."
                );
                return "redirect:/bookings";
            }

            jdbcTemplate.update(
                "INSERT INTO resource_bookings " +
                "(booking_id, resource_id, booked_by, start_time, end_time, status) " +
                "VALUES (?, ?, ?, ?, ?, ?)",
                bookingId,
                resourceId,
                bookedBy,
                Timestamp.valueOf(start),
                Timestamp.valueOf(end),
                "BOOKED"
            );

            redirectAttributes.addFlashAttribute(
                "message", "Booking added successfully."
            );

        } catch (DateTimeParseException error) {
            redirectAttributes.addFlashAttribute(
                "message", "Please enter valid start and end times."
            );
        }

        return "redirect:/bookings";
    }

    @PostMapping("/bookings/delete")
    public String deleteBooking(
            @RequestParam String bookingId,
            RedirectAttributes redirectAttributes) {

        jdbcTemplate.update(
            "DELETE FROM resource_bookings WHERE booking_id = ?",
            bookingId
        );

        redirectAttributes.addFlashAttribute(
            "message", "Booking deleted."
        );

        return "redirect:/bookings";
    }
}