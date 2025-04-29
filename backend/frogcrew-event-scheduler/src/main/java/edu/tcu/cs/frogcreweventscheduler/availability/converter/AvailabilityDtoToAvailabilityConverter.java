// src/main/java/edu/tcu/cs/frogcreweventscheduler/availability/converter/AvailabilityDtoToAvailabilityConverter.java
package edu.tcu.cs.frogcreweventscheduler.availability.converter;

import edu.tcu.cs.frogcreweventscheduler.availability.Availability;
import edu.tcu.cs.frogcreweventscheduler.availability.dto.AvailabilityDto;
import edu.tcu.cs.frogcreweventscheduler.crew.CrewMember;
import edu.tcu.cs.frogcreweventscheduler.crew.CrewMemberRepository;
import edu.tcu.cs.frogcreweventscheduler.schedule.GameSchedule;
import edu.tcu.cs.frogcreweventscheduler.schedule.GameScheduleRepository;
import edu.tcu.cs.frogcreweventscheduler.system.exception.ObjectNotFoundException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AvailabilityDtoToAvailabilityConverter implements Converter<AvailabilityDto, Availability> {

    private final CrewMemberRepository crewRepo;
    private final GameScheduleRepository scheduleRepo;

    public AvailabilityDtoToAvailabilityConverter(CrewMemberRepository crewRepo,
                                                  GameScheduleRepository scheduleRepo) {
        this.crewRepo = crewRepo;
        this.scheduleRepo = scheduleRepo;
    }

    @Override
    public Availability convert(AvailabilityDto dto) {
        Availability a = new Availability();
        a.setId(dto.getId());
        a.setAvailable(dto.getAvailable());
        a.setComment(dto.getComment());

        CrewMember cm = crewRepo.findById(dto.getCrewMemberId())
                .orElseThrow(() -> new ObjectNotFoundException("crew member", dto.getCrewMemberId()));
        a.setCrewMember(cm);

        GameSchedule gs = scheduleRepo.findById(dto.getGameScheduleId())
                .orElseThrow(() -> new ObjectNotFoundException("schedule", dto.getGameScheduleId()));
        a.setGameSchedule(gs);

        return a;
    }
}
