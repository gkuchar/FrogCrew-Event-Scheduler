package edu.tcu.cs.frogcreweventscheduler.schedule.converter;

import edu.tcu.cs.frogcreweventscheduler.schedule.GameSchedule;
import edu.tcu.cs.frogcreweventscheduler.schedule.dto.GameScheduleDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class GameScheduleDtoToGameScheduleConverter
        implements Converter<GameScheduleDto, GameSchedule> {

    @Override
    public GameSchedule convert(GameScheduleDto src) {
        GameSchedule gs = new GameSchedule();
        gs.setId(src.getId());
        gs.setSport(src.getSport());
        gs.setSeason(src.getSeason());
        return gs;
    }
}
