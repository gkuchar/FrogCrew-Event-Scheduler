package edu.tcu.cs.frogcreweventscheduler.crew.converter;

import edu.tcu.cs.frogcreweventscheduler.crew.CrewMember;
import edu.tcu.cs.frogcreweventscheduler.crew.dto.CrewMemberDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CrewMemberToCrewMemberDtoConverter implements Converter<CrewMember, CrewMemberDto> {
    @Override
    public CrewMemberDto convert(CrewMember entity) {
        return new CrewMemberDto(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail(),
                entity.getPhoneNumber(),
                entity.getRole(),
                entity.getQualifiedPosition(),
                entity.getPassword()
        );
    }
}
