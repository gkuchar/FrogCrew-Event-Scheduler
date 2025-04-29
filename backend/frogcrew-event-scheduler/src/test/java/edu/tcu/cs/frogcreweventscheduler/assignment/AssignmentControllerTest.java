// src/test/java/edu/tcu/cs/frogcreweventscheduler/assignment/AssignmentControllerTest.java
package edu.tcu.cs.frogcreweventscheduler.assignment;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.tcu.cs.frogcreweventscheduler.assignment.converter.AssignmentDtoToAssignmentConverter;
import edu.tcu.cs.frogcreweventscheduler.assignment.converter.AssignmentToAssignmentDtoConverter;
import edu.tcu.cs.frogcreweventscheduler.assignment.dto.AssignmentDto;
import edu.tcu.cs.frogcreweventscheduler.system.StatusCode;
import edu.tcu.cs.frogcreweventscheduler.system.exception.GlobalExceptionHandler;
import edu.tcu.cs.frogcreweventscheduler.system.exception.ObjectNotFoundException;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.BDDMockito.*;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class AssignmentControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock AssignmentService service;
    @Mock AssignmentDtoToAssignmentConverter dtoToEntity;
    @Mock AssignmentToAssignmentDtoConverter entityToDto;

    private AssignmentController controller;
    private static final String BASE = "/api/assignments";

    @BeforeEach
    void setUp() {
        controller = new AssignmentController(service, dtoToEntity, entityToDto);
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void testFindAllSuccess() throws Exception {
        Assignment a1 = new Assignment(); a1.setId(1);
        Assignment a2 = new Assignment(); a2.setId(2);
        given(service.findAll()).willReturn(Arrays.asList(a1, a2));

        AssignmentDto d1 = new AssignmentDto(); d1.setId(1);
        AssignmentDto d2 = new AssignmentDto(); d2.setId(2);
        given(entityToDto.convert(a1)).willReturn(d1);
        given(entityToDto.convert(a2)).willReturn(d2);

        mockMvc.perform(get(BASE).accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find All Success"))
                .andExpect(jsonPath("$.data", Matchers.hasSize(2)));
    }

    @Test
    void testFindByIdNotFound() throws Exception {
        doThrow(new ObjectNotFoundException("assignment", 5))
                .when(service).findById(5);

        mockMvc.perform(get(BASE + "/5").accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find assignment with id 5"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testCreateSuccess() throws Exception {
        AssignmentDto dto = new AssignmentDto();
        dto.setCrewMemberId(1);
        dto.setGameScheduleId(1);
        String json = objectMapper.writeValueAsString(dto);

        Assignment toSave = new Assignment();
        Assignment saved = new Assignment(); saved.setId(3);
        AssignmentDto respDto = new AssignmentDto(); respDto.setId(3);

        given(dtoToEntity.convert(any(AssignmentDto.class))).willReturn(toSave);
        given(service.save(toSave)).willReturn(saved);
        given(entityToDto.convert(saved)).willReturn(respDto);

        mockMvc.perform(post(BASE)
                        .contentType("application/json")
                        .content(json)
                        .accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Add Success"))
                .andExpect(jsonPath("$.data.id").value(3));
    }

    @Test
    void testUpdateNotFound() throws Exception {
        AssignmentDto dto = new AssignmentDto();
        dto.setCrewMemberId(1);
        dto.setGameScheduleId(1);
        String json = objectMapper.writeValueAsString(dto);

        given(dtoToEntity.convert(any())).willReturn(new Assignment());
        doThrow(new ObjectNotFoundException("assignment", 5))
                .when(service).update(eq(5), any(Assignment.class));

        mockMvc.perform(put(BASE + "/5")
                        .contentType("application/json")
                        .content(json)
                        .accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find assignment with id 5"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testDeleteSuccess() throws Exception {
        willDoNothing().given(service).delete(2);

        mockMvc.perform(delete(BASE + "/2").accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Delete Success"))
                .andExpect(jsonPath("$.data").isEmpty());
    }
}
