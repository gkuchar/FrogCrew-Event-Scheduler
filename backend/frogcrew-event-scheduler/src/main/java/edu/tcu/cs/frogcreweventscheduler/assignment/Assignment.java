// src/main/java/edu/tcu/cs/frogcreweventscheduler/assignment/Assignment.java
package edu.tcu.cs.frogcreweventscheduler.assignment;

import jakarta.persistence.*;
import edu.tcu.cs.frogcreweventscheduler.crew.CrewMember;
import edu.tcu.cs.frogcreweventscheduler.schedule.GameSchedule;

@Entity
@Table(name = "assignment")
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "crew_member_id", nullable = false)
    private CrewMember crewMember;

    @ManyToOne
    @JoinColumn(name = "game_schedule_id", nullable = false)
    private GameSchedule gameSchedule;

    public Assignment() { }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public CrewMember getCrewMember() { return crewMember; }
    public void setCrewMember(CrewMember crewMember) { this.crewMember = crewMember; }

    public GameSchedule getGameSchedule() { return gameSchedule; }
    public void setGameSchedule(GameSchedule gameSchedule) { this.gameSchedule = gameSchedule; }
}
