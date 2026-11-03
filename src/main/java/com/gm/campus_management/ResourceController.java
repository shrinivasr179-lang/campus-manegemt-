package com.gm.campus_management;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ResourceController {

    private final JdbcTemplate jdbcTemplate;

    public ResourceController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/resources")
    public String showResources(
            @RequestParam(defaultValue = "") String search,
            Model model) {

        String searchText = "%" + search + "%";

        List<Resource> resources = jdbcTemplate.query(
            "SELECT resource_id, resource_name, resource_type, location, status " +
            "FROM resources " +
            "WHERE resource_id LIKE ? OR resource_name LIKE ? " +
            "OR resource_type LIKE ? OR location LIKE ? " +
            "ORDER BY resource_id",
            (result, rowNumber) -> new Resource(
                result.getString("resource_id"),
                result.getString("resource_name"),
                result.getString("resource_type"),
                result.getString("location"),
                result.getString("status")
            ),
            searchText, searchText, searchText, searchText
        );

        model.addAttribute("resources", resources);
        model.addAttribute("search", search);
        return "resources";
    }

    @PostMapping("/resources/add")
    public String addResource(
            @RequestParam String resourceId,
            @RequestParam String resourceName,
            @RequestParam String resourceType,
            @RequestParam String location,
            @RequestParam String status) {

        jdbcTemplate.update(
            "INSERT INTO resources " +
            "(resource_id, resource_name, resource_type, location, status) " +
            "VALUES (?, ?, ?, ?, ?)",
            resourceId, resourceName, resourceType, location, status
        );

        return "redirect:/resources";
    }

    @PostMapping("/resources/update")
    public String updateResource(
            @RequestParam String resourceId,
            @RequestParam String resourceName,
            @RequestParam String resourceType,
            @RequestParam String location,
            @RequestParam String status) {

        jdbcTemplate.update(
            "UPDATE resources SET resource_name = ?, resource_type = ?, " +
            "location = ?, status = ? WHERE resource_id = ?",
            resourceName, resourceType, location, status, resourceId
        );

        return "redirect:/resources";
    }

    @PostMapping("/resources/delete")
    public String deleteResource(@RequestParam String resourceId) {
        jdbcTemplate.update(
            "DELETE FROM resources WHERE resource_id = ?",
            resourceId
        );

        return "redirect:/resources";
    }
}