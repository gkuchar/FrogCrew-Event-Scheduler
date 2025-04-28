package edu.tcu.cs.frogcreweventscheduler.schedule.converter;

import edu.tcu.cs.frogcreweventscheduler.schedule.GameSchedule;
import edu.tcu.cs.frogcreweventscheduler.schedule.dto.GameScheduleDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class GameScheduleToGameScheduleDtoConverter
        implements Converter<GameSchedule, GameScheduleDto> {

    @Override
    public GameScheduleDto convert(GameSchedule src) {
        GameScheduleDto dto = new GameScheduleDto();
        dto.setId(src.getId());
        dto.setSport(src.getSport());
        dto.setSeason(src.getSeason());
        return dto;
    }
}
