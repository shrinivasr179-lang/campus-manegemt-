package com.gm.campus_management;

import java.time.LocalDate;
import java.time.LocalTime;

public class Exam {
    private String examId;
    private String examName;
    private String courseCode;
    private LocalDate examDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String examHall;
    private int maxMarks;

    public Exam(String examId, String examName, String courseCode,
                LocalDate examDate, LocalTime startTime, LocalTime endTime,
                String examHall, int maxMarks) {
        this.examId = examId;
        this.examName = examName;
        this.courseCode = courseCode;
        this.examDate = examDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.examHall = examHall;
        this.maxMarks = maxMarks;
    }

    public String getExamId() {
        return examId;
    }

    public String getExamName() {
        return examName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public LocalDate getExamDate() {
        return examDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public String getExamHall() {
        return examHall;
    }

    public int getMaxMarks() {
        return maxMarks;
    }
}