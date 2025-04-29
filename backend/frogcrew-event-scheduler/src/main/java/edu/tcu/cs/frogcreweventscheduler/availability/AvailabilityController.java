// src/main/java/edu/tcu/cs/frogcreweventscheduler/availability/AvailabilityController.java
package edu.tcu.cs.frogcreweventscheduler.availability;

import edu.tcu.cs.frogcreweventscheduler.availability.converter.AvailabilityDtoToAvailabilityConverter;
import edu.tcu.cs.frogcreweventscheduler.availability.converter.AvailabilityToAvailabilityDtoConverter;
import edu.tcu.cs.frogcreweventscheduler.availability.dto.AvailabilityDto;
import edu.tcu.cs.frogcreweventscheduler.system.Result;
import edu.tcu.cs.frogcreweventscheduler.system.StatusCode;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/availabilities")
public class AvailabilityController {

    private final AvailabilityService service;
    private final AvailabilityDtoToAvailabilityConverter dtoToEntity;
    private final AvailabilityToAvailabilityDtoConverter entityToDto;

    public AvailabilityController(AvailabilityService service,
                                  AvailabilityDtoToAvailabilityConverter dtoToEntity,
                                  AvailabilityToAvailabilityDtoConverter entityToDto) {
        this.service = service;
        this.dtoToEntity = dtoToEntity;
        this.entityToDto = entityToDto;
    }

    @GetMapping
    public Result findAll() {
        List<Availability> list = service.findAll();
        List<AvailabilityDto> dtos = list.stream()
                .map(entityToDto::convert)
                .collect(Collectors.toList());
        return new Result(true, StatusCode.SUCCESS, "Find All Success", dtos);
    }

    @GetMapping("/{availabilityId}")
    public Result findById(@PathVariable Integer availabilityId) {
        Availability a = service.findById(availabilityId);
        return new Result(true, StatusCode.SUCCESS, "Find One Success", entityToDto.convert(a));
    }

    @PostMapping
    public Result create(@Valid @RequestBody AvailabilityDto dto) {
        Availability toSave = dtoToEntity.convert(dto);
        Availability saved = service.save(toSave);
        return new Result(true, StatusCode.SUCCESS, "Add Success", entityToDto.convert(saved));
    }

    @PutMapping("/{availabilityId}")
    public Result update(@PathVariable Integer availabilityId,
                         @Valid @RequestBody AvailabilityDto dto) {
        Availability toUpdate = dtoToEntity.convert(dto);
        Availability updated = service.update(availabilityId, toUpdate);
        return new Result(true, StatusCode.SUCCESS, "Update Success", entityToDto.convert(updated));
    }

    @DeleteMapping("/{availabilityId}")
    public Result delete(@PathVariable Integer availabilityId) {
        service.delete(availabilityId);
        return new Result(true, StatusCode.SUCCESS, "Delete Success");
    }
}
