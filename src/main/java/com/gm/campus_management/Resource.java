package com.gm.campus_management;

public class Resource {
    private String resourceId;
    private String resourceName;
    private String resourceType;
    private String location;
    private String status;

    public Resource(String resourceId, String resourceName,
                    String resourceType, String location, String status) {
        this.resourceId = resourceId;
        this.resourceName = resourceName;
        this.resourceType = resourceType;
        this.location = location;
        this.status = status;
    }

    public String getResourceId() {
        return resourceId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getResourceType() {
        return resourceType;
    }

    public String getLocation() {
        return location;
    }

    public String getStatus() {
        return status;
    }
}
