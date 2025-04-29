// src/main/java/edu/tcu/cs/frogcreweventscheduler/assignment/AssignmentController.java
package edu.tcu.cs.frogcreweventscheduler.assignment;

import edu.tcu.cs.frogcreweventscheduler.assignment.converter.AssignmentDtoToAssignmentConverter;
import edu.tcu.cs.frogcreweventscheduler.assignment.converter.AssignmentToAssignmentDtoConverter;
import edu.tcu.cs.frogcreweventscheduler.assignment.dto.AssignmentDto;
import edu.tcu.cs.frogcreweventscheduler.system.Result;
import edu.tcu.cs.frogcreweventscheduler.system.StatusCode;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

    private final AssignmentService service;
    private final AssignmentDtoToAssignmentConverter dtoToEntity;
    private final AssignmentToAssignmentDtoConverter entityToDto;

    public AssignmentController(AssignmentService service,
                                AssignmentDtoToAssignmentConverter dtoToEntity,
                                AssignmentToAssignmentDtoConverter entityToDto) {
        this.service = service;
        this.dtoToEntity = dtoToEntity;
        this.entityToDto = entityToDto;
    }

    @GetMapping
    public Result findAll() {
        List<Assignment> list = service.findAll();
        List<AssignmentDto> dtos = list.stream()
                .map(entityToDto::convert)
                .collect(Collectors.toList());
        return new Result(true, StatusCode.SUCCESS, "Find All Success", dtos);
    }

    @GetMapping("/{assignmentId}")
    public Result findById(@PathVariable Integer assignmentId) {
        Assignment a = service.findById(assignmentId);
        return new Result(true, StatusCode.SUCCESS, "Find One Success", entityToDto.convert(a));
    }

    @PostMapping
    public Result create(@Valid @RequestBody AssignmentDto dto) {
        Assignment toSave = dtoToEntity.convert(dto);
        Assignment saved = service.save(toSave);
        return new Result(true, StatusCode.SUCCESS, "Add Success", entityToDto.convert(saved));
    }

    @PutMapping("/{assignmentId}")
    public Result update(@PathVariable Integer assignmentId,
                         @Valid @RequestBody AssignmentDto dto) {
        Assignment toUpdate = dtoToEntity.convert(dto);
        Assignment updated = service.update(assignmentId, toUpdate);
        return new Result(true, StatusCode.SUCCESS, "Update Success", entityToDto.convert(updated));
    }

    @DeleteMapping("/{assignmentId}")
    public Result delete(@PathVariable Integer assignmentId) {
        service.delete(assignmentId);
        return new Result(true, StatusCode.SUCCESS, "Delete Success");
    }
}
