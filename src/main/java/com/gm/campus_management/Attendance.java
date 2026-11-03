package com.gm.campus_management;

import java.time.LocalDate;

public class Attendance {
    private String attendanceId;
    private String studentId;
    private String courseCode;
    private LocalDate attendanceDate;
    private String status;

    public Attendance(String attendanceId, String studentId, String courseCode,
                      LocalDate attendanceDate, String status) {
        this.attendanceId = attendanceId;
        this.studentId = studentId;
        this.courseCode = courseCode;
        this.attendanceDate = attendanceDate;
        this.status = status;
    }

    public String getAttendanceId() {
        return attendanceId;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public LocalDate getAttendanceDate() {
        return attendanceDate;
    }

    public String getStatus() {
        return status;
    }
}