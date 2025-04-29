// src/main/java/edu/tcu/cs/frogcreweventscheduler/availability/converter/AvailabilityToAvailabilityDtoConverter.java
package edu.tcu.cs.frogcreweventscheduler.availability.converter;

import edu.tcu.cs.frogcreweventscheduler.availability.Availability;
import edu.tcu.cs.frogcreweventscheduler.availability.dto.AvailabilityDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AvailabilityToAvailabilityDtoConverter implements Converter<Availability, AvailabilityDto> {

    @Override
    public AvailabilityDto convert(Availability a) {
        AvailabilityDto dto = new AvailabilityDto();
        dto.setId(a.getId());
        dto.setCrewMemberId(a.getCrewMember().getId());
        dto.setGameScheduleId(a.getGameSchedule().getId());
        dto.setAvailable(a.getAvailable());
        dto.setComment(a.getComment());
        return dto;
    }
}
