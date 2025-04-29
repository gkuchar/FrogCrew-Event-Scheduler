package edu.tcu.cs.frogcreweventscheduler.schedule.dto;

import jakarta.validation.constraints.NotBlank;

public class GameScheduleDto {

    private Integer id;

    @NotBlank(message = "Sport is required.")
    private String sport;

    @NotBlank(message = "Season is required.")
    private String season;

    public GameScheduleDto() { }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }
}
