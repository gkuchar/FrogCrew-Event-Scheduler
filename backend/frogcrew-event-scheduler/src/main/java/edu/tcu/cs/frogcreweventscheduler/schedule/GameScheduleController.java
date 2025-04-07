package edu.tcu.cs.frogcreweventscheduler.schedule;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameScheduleController {

    private final GameScheduleService gameScheduleService;

    public GameScheduleController(GameScheduleService gameScheduleService) {
        this.gameScheduleService = gameScheduleService;
    }
}
