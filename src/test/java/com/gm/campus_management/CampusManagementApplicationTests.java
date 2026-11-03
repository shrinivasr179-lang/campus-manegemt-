package com.gm.campus_management;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CampusManagementApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
	}

	@Test
	void dataPagesLoadWithInitializedSchema() throws Exception {
		mockMvc.perform(get("/students")).andExpect(status().isOk());
		mockMvc.perform(get("/faculty")).andExpect(status().isOk());
		mockMvc.perform(get("/courses")).andExpect(status().isOk());
		mockMvc.perform(get("/resources")).andExpect(status().isOk());
		mockMvc.perform(get("/bookings")).andExpect(status().isOk());
		mockMvc.perform(get("/attendance")).andExpect(status().isOk());
		mockMvc.perform(get("/exams")).andExpect(status().isOk());
		mockMvc.perform(get("/activities")).andExpect(status().isOk());
	}

	@Test
	void createUpdateAndDeleteActionsWork() throws Exception {
		mockMvc.perform(post("/students/add")
				.param("studentId", "TEST-STUDENT")
				.param("name", "Test Student")
				.param("email", "student@example.com")
				.param("course", "TEST-COURSE"))
			.andExpect(status().is3xxRedirection());
		mockMvc.perform(post("/students/update")
				.param("studentId", "TEST-STUDENT")
				.param("name", "Updated Student")
				.param("email", "student@example.com")
				.param("course", "TEST-COURSE"))
			.andExpect(status().is3xxRedirection());
		mockMvc.perform(get("/students/profile").param("studentId", "TEST-STUDENT"))
			.andExpect(status().isOk());
		mockMvc.perform(post("/students/profile/achievement")
				.param("studentId", "TEST-STUDENT")
				.param("category", "Test")
				.param("activityName", "Test activity"))
			.andExpect(status().is3xxRedirection());
		mockMvc.perform(post("/students/delete").param("studentId", "TEST-STUDENT"))
			.andExpect(status().is3xxRedirection());

		mockMvc.perform(post("/faculty/add")
				.param("facultyId", "TEST-FACULTY")
				.param("name", "Test Faculty")
				.param("email", "faculty@example.com")
				.param("department", "Testing"))
			.andExpect(status().is3xxRedirection());
		mockMvc.perform(post("/faculty/update")
				.param("facultyId", "TEST-FACULTY")
				.param("name", "Updated Faculty")
				.param("email", "faculty@example.com")
				.param("department", "Testing"))
			.andExpect(status().is3xxRedirection());
		mockMvc.perform(post("/faculty/delete").param("facultyId", "TEST-FACULTY"))
			.andExpect(status().is3xxRedirection());

		mockMvc.perform(post("/courses/add")
				.param("courseCode", "TEST-COURSE")
				.param("courseName", "Test Course")
				.param("credits", "3")
				.param("department", "Testing"))
			.andExpect(status().is3xxRedirection());
		mockMvc.perform(post("/courses/update")
				.param("courseCode", "TEST-COURSE")
				.param("courseName", "Updated Course")
				.param("credits", "4")
				.param("department", "Testing"))
			.andExpect(status().is3xxRedirection());
		mockMvc.perform(post("/courses/delete").param("courseCode", "TEST-COURSE"))
			.andExpect(status().is3xxRedirection());

		mockMvc.perform(post("/resources/add")
				.param("resourceId", "TEST-RESOURCE")
				.param("resourceName", "Test Room")
				.param("resourceType", "Room")
				.param("location", "Test Building")
				.param("status", "Available"))
			.andExpect(status().is3xxRedirection());
		mockMvc.perform(post("/bookings/add")
				.param("bookingId", "TEST-BOOKING")
				.param("resourceId", "TEST-RESOURCE")
				.param("bookedBy", "Test User")
				.param("startTime", "2026-10-10T09:00")
				.param("endTime", "2026-10-10T10:00"))
			.andExpect(status().is3xxRedirection());
		mockMvc.perform(post("/bookings/delete").param("bookingId", "TEST-BOOKING"))
			.andExpect(status().is3xxRedirection());
		mockMvc.perform(post("/resources/delete").param("resourceId", "TEST-RESOURCE"))
			.andExpect(status().is3xxRedirection());

		mockMvc.perform(post("/attendance/add")
				.param("attendanceId", "TEST-ATTENDANCE")
				.param("studentId", "TEST-STUDENT")
				.param("courseCode", "TEST-COURSE")
				.param("attendanceDate", "2026-10-10")
				.param("status", "Present"))
			.andExpect(status().is3xxRedirection());
		mockMvc.perform(post("/attendance/update")
				.param("attendanceId", "TEST-ATTENDANCE")
				.param("status", "Absent"))
			.andExpect(status().is3xxRedirection());
		mockMvc.perform(post("/attendance/delete")
				.param("attendanceId", "TEST-ATTENDANCE"))
			.andExpect(status().is3xxRedirection());

		mockMvc.perform(post("/exams/add")
				.param("examId", "TEST-EXAM")
				.param("examName", "Test Exam")
				.param("courseCode", "TEST-COURSE")
				.param("examDate", "2026-10-10")
				.param("startTime", "09:00")
				.param("endTime", "10:00")
				.param("examHall", "Test Hall")
				.param("maxMarks", "100"))
			.andExpect(status().is3xxRedirection());
		mockMvc.perform(post("/exams/update")
				.param("examId", "TEST-EXAM")
				.param("examName", "Updated Exam")
				.param("courseCode", "TEST-COURSE")
				.param("examDate", "2026-10-10")
				.param("startTime", "09:00")
				.param("endTime", "10:00")
				.param("examHall", "Test Hall")
				.param("maxMarks", "100"))
			.andExpect(status().is3xxRedirection());
		mockMvc.perform(post("/exams/delete").param("examId", "TEST-EXAM"))
			.andExpect(status().is3xxRedirection());

		mockMvc.perform(post("/activities/add")
				.param("activityId", "TEST-ACTIVITY")
				.param("activityName", "Test Activity")
				.param("description", "Testing activity")
				.param("activityDate", "2026-10-10T09:00")
				.param("location", "Test Hall")
				.param("organizer", "Test User"))
			.andExpect(status().is3xxRedirection());
		mockMvc.perform(post("/activities/update")
				.param("activityId", "TEST-ACTIVITY")
				.param("activityName", "Updated Activity")
				.param("description", "Testing activity")
				.param("activityDate", "2026-10-10T09:00")
				.param("location", "Test Hall")
				.param("organizer", "Test User"))
			.andExpect(status().is3xxRedirection());
		mockMvc.perform(post("/activities/delete")
				.param("activityId", "TEST-ACTIVITY"))
			.andExpect(status().is3xxRedirection());
	}
}
