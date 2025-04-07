package edu.tcu.cs.frogcreweventscheduler.schedule;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class GameScheduleService {

    private final GameScheduleRepository gameScheduleRepository;

    public GameScheduleService(GameScheduleRepository gameScheduleRepository) {
        this.gameScheduleRepository = gameScheduleRepository;
    }

}
