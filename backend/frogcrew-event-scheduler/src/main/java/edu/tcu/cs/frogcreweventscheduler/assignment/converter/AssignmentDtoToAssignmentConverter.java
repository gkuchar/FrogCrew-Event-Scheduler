// src/main/java/edu/tcu/cs/frogcreweventscheduler/assignment/converter/AssignmentDtoToAssignmentConverter.java
package edu.tcu.cs.frogcreweventscheduler.assignment.converter;

import edu.tcu.cs.frogcreweventscheduler.assignment.Assignment;
import edu.tcu.cs.frogcreweventscheduler.assignment.dto.AssignmentDto;
import edu.tcu.cs.frogcreweventscheduler.crew.CrewMember;
import edu.tcu.cs.frogcreweventscheduler.crew.CrewMemberRepository;
import edu.tcu.cs.frogcreweventscheduler.schedule.GameSchedule;
import edu.tcu.cs.frogcreweventscheduler.schedule.GameScheduleRepository;
import edu.tcu.cs.frogcreweventscheduler.system.exception.ObjectNotFoundException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AssignmentDtoToAssignmentConverter implements Converter<AssignmentDto, Assignment> {

    private final CrewMemberRepository crewRepo;
    private final GameScheduleRepository scheduleRepo;

    public AssignmentDtoToAssignmentConverter(CrewMemberRepository crewRepo,
                                              GameScheduleRepository scheduleRepo) {
        this.crewRepo = crewRepo;
        this.scheduleRepo = scheduleRepo;
    }

    @Override
    public Assignment convert(AssignmentDto dto) {
        Assignment a = new Assignment();
        a.setId(dto.getId());

        CrewMember cm = crewRepo.findById(dto.getCrewMemberId())
                .orElseThrow(() -> new ObjectNotFoundException("crew member", dto.getCrewMemberId()));
        a.setCrewMember(cm);

        GameSchedule gs = scheduleRepo.findById(dto.getGameScheduleId())
                .orElseThrow(() -> new ObjectNotFoundException("schedule", dto.getGameScheduleId()));
        a.setGameSchedule(gs);

        return a;
    }
}
