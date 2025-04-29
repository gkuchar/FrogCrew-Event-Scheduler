package edu.tcu.cs.frogcreweventscheduler.crew.converter;

import edu.tcu.cs.frogcreweventscheduler.crew.CrewMember;
import edu.tcu.cs.frogcreweventscheduler.crew.dto.CrewMemberDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CrewMemberDtoToCrewMemberConverter implements Converter<CrewMemberDto, CrewMember> {
    @Override
    public CrewMember convert(CrewMemberDto dto) {
        CrewMember entity = new CrewMember();
        entity.setId(dto.getId());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setRole(dto.getRole());
        entity.setQualifiedPosition(dto.getQualifiedPosition());
        entity.setPassword(dto.getPassword());
        return entity;
    }
}
