package edu.tcu.cs.frogcreweventscheduler.availability;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.tcu.cs.frogcreweventscheduler.availability.converter.AvailabilityDtoToAvailabilityConverter;
import edu.tcu.cs.frogcreweventscheduler.availability.converter.AvailabilityToAvailabilityDtoConverter;
import edu.tcu.cs.frogcreweventscheduler.availability.dto.AvailabilityDto;
import edu.tcu.cs.frogcreweventscheduler.system.StatusCode;
import edu.tcu.cs.frogcreweventscheduler.system.exception.GlobalExceptionHandler;
import edu.tcu.cs.frogcreweventscheduler.system.exception.ObjectNotFoundException;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class AvailabilityControllerTest {

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock private AvailabilityService service;
    @Mock private AvailabilityDtoToAvailabilityConverter dtoToEntity;
    @Mock private AvailabilityToAvailabilityDtoConverter entityToDto;

    private AvailabilityController controller;
    private static final String BASE = "/api/availabilities";

    @BeforeEach
    void setUp() {
        controller = new AvailabilityController(service, dtoToEntity, entityToDto);
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void testFindAllSuccess() throws Exception {
        // prepare two Availability entities
        Availability a1 = new Availability(); a1.setId(1);
        Availability a2 = new Availability(); a2.setId(2);
        // and their DTOs
        AvailabilityDto d1 = new AvailabilityDto(); d1.setId(1);
        AvailabilityDto d2 = new AvailabilityDto(); d2.setId(2);

        given(service.findAll()).willReturn(Arrays.asList(a1, a2));
        given(entityToDto.convert(a1)).willReturn(d1);
        given(entityToDto.convert(a2)).willReturn(d2);

        mockMvc.perform(get(BASE).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find All Success"))
                .andExpect(jsonPath("$.data", Matchers.hasSize(2)));
    }

    @Test
    void testFindByIdNotFound() throws Exception {
        doThrow(new ObjectNotFoundException("availability", 5))
                .when(service).findById(5);

        mockMvc.perform(get(BASE + "/5").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message")
                        .value("Could not find availability with id 5"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testCreateSuccess() throws Exception {
        // incoming JSON DTO
        AvailabilityDto in = new AvailabilityDto();
        in.setCrewMemberId(1);
        in.setGameScheduleId(1);
        in.setAvailable(true);
        in.setComment("Available");
        String json = objectMapper.writeValueAsString(in);

        // what dtoToEntity should hand to the service
        Availability toSave = new Availability();
        toSave.setAvailable(true);
        toSave.setComment("Available");

        // what service returns
        Availability saved = new Availability();
        saved.setId(3);
        saved.setAvailable(true);

        // what we want back in the JSON
        AvailabilityDto out = new AvailabilityDto();
        out.setId(3);
        out.setAvailable(true);

        given(dtoToEntity.convert(any(AvailabilityDto.class))).willReturn(toSave);
        given(service.save(toSave)).willReturn(saved);
        given(entityToDto.convert(saved)).willReturn(out);

        mockMvc.perform(post(BASE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Add Success"))
                .andExpect(jsonPath("$.data.id").value(3));
    }

    @Test
    void testUpdateNotFound() throws Exception {
        AvailabilityDto in = new AvailabilityDto();
        in.setCrewMemberId(1);
        in.setGameScheduleId(1);
        in.setAvailable(false);
        String json = objectMapper.writeValueAsString(in);

        // we still need dtoToEntity to run without NPE:
        given(dtoToEntity.convert(any(AvailabilityDto.class)))
                .willReturn(new Availability());

        doThrow(new ObjectNotFoundException("availability", 5))
                .when(service).update(eq(5), any(Availability.class));

        mockMvc.perform(put(BASE + "/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message")
                        .value("Could not find availability with id 5"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testDeleteSuccess() throws Exception {
        willDoNothing().given(service).delete(2);

        mockMvc.perform(delete(BASE + "/2").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Delete Success"))
                .andExpect(jsonPath("$.data").isEmpty());
    }
}
