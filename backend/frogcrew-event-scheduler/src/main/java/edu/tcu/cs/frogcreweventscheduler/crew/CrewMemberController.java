package edu.tcu.cs.frogcreweventscheduler.crew;

import edu.tcu.cs.frogcreweventscheduler.crew.converter.CrewMemberDtoToCrewMemberConverter;
import edu.tcu.cs.frogcreweventscheduler.crew.converter.CrewMemberToCrewMemberDtoConverter;
import edu.tcu.cs.frogcreweventscheduler.crew.dto.CrewMemberDto;
import edu.tcu.cs.frogcreweventscheduler.system.Result;
import edu.tcu.cs.frogcreweventscheduler.system.StatusCode;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/crewMembers")
public class CrewMemberController {

    private final CrewMemberService service;
    private final CrewMemberDtoToCrewMemberConverter dtoToEntity;
    private final CrewMemberToCrewMemberDtoConverter entityToDto;

    public CrewMemberController(
            CrewMemberService service,
            CrewMemberDtoToCrewMemberConverter dtoToEntity,
            CrewMemberToCrewMemberDtoConverter entityToDto
    ) {
        this.service = service;
        this.dtoToEntity = dtoToEntity;
        this.entityToDto = entityToDto;
    }

    @GetMapping
    public Result findAll() {
        List<CrewMember> list = service.findAll();
        List<CrewMemberDto> dtos = list.stream()
                .map(entityToDto::convert)
                .collect(Collectors.toList());
        return new Result(true, StatusCode.SUCCESS, "Find All Success", dtos);
    }

    @GetMapping("/{memberId}")
    public Result findById(@PathVariable Integer memberId) {
        CrewMember cm = service.findById(memberId);
        return new Result(true, StatusCode.SUCCESS, "Find One Success", entityToDto.convert(cm));
    }

    @PostMapping
    public Result create(@Valid @RequestBody CrewMemberDto dto) {
        CrewMember toSave = dtoToEntity.convert(dto);
        CrewMember saved = service.save(toSave);
        return new Result(true, StatusCode.SUCCESS, "Add Success", entityToDto.convert(saved));
    }

    @PutMapping("/{memberId}")
    public Result update(@PathVariable Integer memberId,
                         @Valid @RequestBody CrewMemberDto dto) {
        CrewMember update = dtoToEntity.convert(dto);
        CrewMember updated = service.update(memberId, update);
        return new Result(true, StatusCode.SUCCESS, "Update Success", entityToDto.convert(updated));
    }

    @DeleteMapping("/{memberId}")
    public Result delete(@PathVariable Integer memberId) {
        service.delete(memberId);
        return new Result(true, StatusCode.SUCCESS, "Delete Success");
    }
}
