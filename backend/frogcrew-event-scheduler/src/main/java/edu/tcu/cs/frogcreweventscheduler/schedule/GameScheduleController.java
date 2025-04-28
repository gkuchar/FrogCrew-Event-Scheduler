package edu.tcu.cs.frogcreweventscheduler.schedule;

import edu.tcu.cs.frogcreweventscheduler.schedule.converter.GameScheduleDtoToGameScheduleConverter;
import edu.tcu.cs.frogcreweventscheduler.schedule.converter.GameScheduleToGameScheduleDtoConverter;
import edu.tcu.cs.frogcreweventscheduler.schedule.dto.GameScheduleDto;
import edu.tcu.cs.frogcreweventscheduler.system.Result;
import edu.tcu.cs.frogcreweventscheduler.system.StatusCode;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/gameSchedule")
public class GameScheduleController {

    private final GameScheduleService service;
    private final GameScheduleDtoToGameScheduleConverter dtoToEntity;
    private final GameScheduleToGameScheduleDtoConverter entityToDto;

    public GameScheduleController(GameScheduleService service,
                                  GameScheduleDtoToGameScheduleConverter dtoToEntity,
                                  GameScheduleToGameScheduleDtoConverter entityToDto) {
        this.service = service;
        this.dtoToEntity = dtoToEntity;
        this.entityToDto = entityToDto;
    }

    @GetMapping
    public Result findAll() {
        List<GameSchedule> list = service.findAll();
        List<GameScheduleDto> dtos = list.stream()
                .map(entityToDto::convert)
                .collect(Collectors.toList());
        return new Result(true, StatusCode.SUCCESS, "Find All Success", dtos);
    }

    @GetMapping("/{scheduleId}")
    public Result findById(@PathVariable Integer scheduleId) {
        var gs = service.findById(scheduleId);
        var dto = entityToDto.convert(gs);
        return new Result(true, StatusCode.SUCCESS, "Find One Success", dto);
    }

    @PostMapping
    public Result create(@Valid @RequestBody GameScheduleDto dto) {
        var toSave = dtoToEntity.convert(dto);
        var saved = service.save(toSave);
        var savedDto = entityToDto.convert(saved);
        return new Result(true, StatusCode.SUCCESS, "Add Success", savedDto);
    }

    @PutMapping("/{scheduleId}")
    public Result update(@PathVariable Integer scheduleId,
                         @Valid @RequestBody GameScheduleDto dto) {
        var update = dtoToEntity.convert(dto);
        var updated = service.update(scheduleId, update);
        var updatedDto = entityToDto.convert(updated);
        return new Result(true, StatusCode.SUCCESS, "Update Success", updatedDto);
    }

    @DeleteMapping("/{scheduleId}")
    public Result delete(@PathVariable Integer scheduleId) {
        service.delete(scheduleId);
        return new Result(true, StatusCode.SUCCESS, "Delete Success");
    }
}
