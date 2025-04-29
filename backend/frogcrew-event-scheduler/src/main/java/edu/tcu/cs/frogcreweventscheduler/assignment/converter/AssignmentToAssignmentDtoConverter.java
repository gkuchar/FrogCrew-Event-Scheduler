// src/main/java/edu/tcu/cs/frogcreweventscheduler/assignment/converter/AssignmentToAssignmentDtoConverter.java
package edu.tcu.cs.frogcreweventscheduler.assignment.converter;

import edu.tcu.cs.frogcreweventscheduler.assignment.Assignment;
import edu.tcu.cs.frogcreweventscheduler.assignment.dto.AssignmentDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AssignmentToAssignmentDtoConverter implements Converter<Assignment, AssignmentDto> {

    @Override
    public AssignmentDto convert(Assignment a) {
        AssignmentDto dto = new AssignmentDto();
        dto.setId(a.getId());
        dto.setCrewMemberId(a.getCrewMember().getId());
        dto.setGameScheduleId(a.getGameSchedule().getId());
        return dto;
    }
}
