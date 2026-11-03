package com.gm.campus_management;

import java.time.LocalDateTime;

public class ResourceBooking {
    private String bookingId;
    private String resourceId;
    private String bookedBy;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status;

    public ResourceBooking(String bookingId, String resourceId, String bookedBy,
                           LocalDateTime startTime, LocalDateTime endTime, String status) {
        this.bookingId = bookingId;
        this.resourceId = resourceId;
        this.bookedBy = bookedBy;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public String getBookedBy() {
        return bookedBy;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public String getStatus() {
        return status;
    }
}