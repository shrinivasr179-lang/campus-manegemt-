package com.gm.campus_management;

public class Course {
    private String courseCode;
    private String courseName;
    private int credits;
    private String department;

    public Course(String courseCode, String courseName, int credits, String department) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.credits = credits;
        this.department = department;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getCredits() {
        return credits;
    }

    public String getDepartment() {
        return department;
    }
}
