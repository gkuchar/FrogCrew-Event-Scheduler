package edu.tcu.cs.frogcreweventscheduler.availability.dto;

import jakarta.validation.constraints.NotNull;

public class AvailabilityDto {

    private Integer id;

    @NotNull(message = "Crew member id is required.")
    private Integer crewMemberId;

    @NotNull(message = "Game schedule id is required.")
    private Integer gameScheduleId;

    // Change primitive `boolean` --> to `Boolean` object
    private Boolean available;

    private String comment;

    public AvailabilityDto() { }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCrewMemberId() {
        return crewMemberId;
    }

    public void setCrewMemberId(Integer crewMemberId) {
        this.crewMemberId = crewMemberId;
    }

    public Integer getGameScheduleId() {
        return gameScheduleId;
    }

    public void setGameScheduleId(Integer gameScheduleId) {
        this.gameScheduleId = gameScheduleId;
    }

    // Now the getter will be `getAvailable()`
    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
