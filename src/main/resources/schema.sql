CREATE TABLE IF NOT EXISTS students (
    student_id VARCHAR(64) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    course VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS faculty (
    faculty_id VARCHAR(64) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    department VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS courses (
    course_code VARCHAR(64) PRIMARY KEY,
    course_name VARCHAR(255) NOT NULL,
    credits INTEGER NOT NULL,
    department VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS resources (
    resource_id VARCHAR(64) PRIMARY KEY,
    resource_name VARCHAR(255) NOT NULL,
    resource_type VARCHAR(100) NOT NULL,
    location VARCHAR(255) NOT NULL,
    status VARCHAR(64) NOT NULL
);

CREATE TABLE IF NOT EXISTS resource_bookings (
    booking_id VARCHAR(64) PRIMARY KEY,
    resource_id VARCHAR(64) NOT NULL,
    booked_by VARCHAR(255) NOT NULL,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    status VARCHAR(64) NOT NULL
);

CREATE TABLE IF NOT EXISTS attendance (
    attendance_id VARCHAR(64) PRIMARY KEY,
    student_id VARCHAR(64) NOT NULL,
    course_code VARCHAR(64) NOT NULL,
    attendance_date DATE NOT NULL,
    status VARCHAR(64) NOT NULL
);

CREATE TABLE IF NOT EXISTS exams (
    exam_id VARCHAR(64) PRIMARY KEY,
    exam_name VARCHAR(255) NOT NULL,
    course_code VARCHAR(64) NOT NULL,
    exam_date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    exam_hall VARCHAR(255) NOT NULL,
    max_marks INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS campus_activities (
    activity_id VARCHAR(64) PRIMARY KEY,
    activity_name VARCHAR(255) NOT NULL,
    description VARCHAR(2000) NOT NULL,
    activity_date TIMESTAMP NOT NULL,
    location VARCHAR(255) NOT NULL,
    organizer VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS exam_marks (
    exam_id VARCHAR(64) NOT NULL,
    student_id VARCHAR(64) NOT NULL,
    marks_obtained DECIMAL(8, 2) NOT NULL,
    PRIMARY KEY (exam_id, student_id)
);

CREATE TABLE IF NOT EXISTS student_achievements (
    achievement_id VARCHAR(64) PRIMARY KEY,
    student_id VARCHAR(64) NOT NULL,
    category VARCHAR(100) NOT NULL,
    activity_name VARCHAR(255) NOT NULL,
    activity_date DATE,
    achievement VARCHAR(255),
    details VARCHAR(2000)
);
