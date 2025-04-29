// src/main/java/edu/tcu/cs/frogcreweventscheduler/assignment/dto/AssignmentDto.java
package edu.tcu.cs.frogcreweventscheduler.assignment.dto;

import jakarta.validation.constraints.NotNull;

public class AssignmentDto {

    private Integer id;

    @NotNull(message = "Crew member id is required.")
    private Integer crewMemberId;

    @NotNull(message = "Game schedule id is required.")
    private Integer gameScheduleId;

    public AssignmentDto() { }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getCrewMemberId() { return crewMemberId; }
    public void setCrewMemberId(Integer crewMemberId) { this.crewMemberId = crewMemberId; }

    public Integer getGameScheduleId() { return gameScheduleId; }
    public void setGameScheduleId(Integer gameScheduleId) { this.gameScheduleId = gameScheduleId; }
}
