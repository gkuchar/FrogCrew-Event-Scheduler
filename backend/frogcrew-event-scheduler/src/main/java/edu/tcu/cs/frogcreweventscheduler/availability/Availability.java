package edu.tcu.cs.frogcreweventscheduler.availability;

import edu.tcu.cs.frogcreweventscheduler.crew.CrewMember;
import edu.tcu.cs.frogcreweventscheduler.schedule.GameSchedule;
import jakarta.persistence.*;

@Entity
@Table(name = "availability")
public class Availability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "crew_member_id", nullable = false)
    private CrewMember crewMember;

    @ManyToOne
    @JoinColumn(name = "game_schedule_id", nullable = false)
    private GameSchedule gameSchedule;

    private Boolean available;

    private String comment;

    public Availability() { }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CrewMember getCrewMember() {
        return crewMember;
    }

    public void setCrewMember(CrewMember crewMember) {
        this.crewMember = crewMember;
    }

    public GameSchedule getGameSchedule() {
        return gameSchedule;
    }

    public void setGameSchedule(GameSchedule gameSchedule) {
        this.gameSchedule = gameSchedule;
    }

    public boolean getAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
