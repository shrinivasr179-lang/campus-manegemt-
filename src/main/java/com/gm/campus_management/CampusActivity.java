package com.gm.campus_management;

import java.time.LocalDateTime;

public class CampusActivity {
    private String activityId;
    private String activityName;
    private String description;
    private LocalDateTime activityDate;
    private String location;
    private String organizer;

    public CampusActivity(String activityId, String activityName,
                          String description, LocalDateTime activityDate,
                          String location, String organizer) {
        this.activityId = activityId;
        this.activityName = activityName;
        this.description = description;
        this.activityDate = activityDate;
        this.location = location;
        this.organizer = organizer;
    }

    public String getActivityId() {
        return activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getActivityDate() {
        return activityDate;
    }

    public String getLocation() {
        return location;
    }

    public String getOrganizer() {
        return organizer;
    }
}