package com.gm.campus_management;

public class Faculty {
    private String facultyId;
    private String name;
    private String email;
    private String department;

    public Faculty(String facultyId, String name, String email, String department) {
        this.facultyId = facultyId;
        this.name = name;
        this.email = email;
        this.department = department;
    }

    public String getFacultyId() {
        return facultyId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getDepartment() {
        return department;
    }
}