package com.gm.campus_management;

public class ExamMark {
    private String markId;
    private String studentId;
    private String examId;
    private double marksObtained;

    public ExamMark(String markId, String studentId, String examId, double marksObtained) {
        this.markId = markId;
        this.studentId = studentId;
        this.examId = examId;
        this.marksObtained = marksObtained;
    }

    public String getMarkId() {
        return markId;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getExamId() {
        return examId;
    }

    public double getMarksObtained() {
        return marksObtained;
    }
}