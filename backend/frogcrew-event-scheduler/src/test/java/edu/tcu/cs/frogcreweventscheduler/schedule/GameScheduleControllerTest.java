package edu.tcu.cs.frogcreweventscheduler.schedule;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.tcu.cs.frogcreweventscheduler.schedule.converter.GameScheduleDtoToGameScheduleConverter;
import edu.tcu.cs.frogcreweventscheduler.schedule.converter.GameScheduleToGameScheduleDtoConverter;
import edu.tcu.cs.frogcreweventscheduler.schedule.dto.GameScheduleDto;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class GameScheduleControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private GameScheduleService gameScheduleService;

    private GameScheduleController controller;

    private static final String BASE = "/api/gameSchedule";

    @BeforeEach
    void setUp() {
        controller = new GameScheduleController(
                gameScheduleService,
                new GameScheduleDtoToGameScheduleConverter(),
                new GameScheduleToGameScheduleDtoConverter()
        );

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void testFindAllSchedulesSuccess() throws Exception {
        var g1 = new GameSchedule(); g1.setId(1); g1.setSport("Football"); g1.setSeason("2024-2025");
        var g2 = new GameSchedule(); g2.setId(2); g2.setSport("Basketball"); g2.setSeason("2024-2025");
        given(gameScheduleService.findAll()).willReturn(Arrays.asList(g1, g2));

        mockMvc.perform(get(BASE).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find All Success"))
                .andExpect(jsonPath("$.data", Matchers.hasSize(2)));
    }

    @Test
    void testFindByIdSuccess() throws Exception {
        var g = new GameSchedule(); g.setId(1); g.setSport("Football"); g.setSeason("2024-2025");
        given(gameScheduleService.findById(1)).willReturn(g);

        mockMvc.perform(get(BASE + "/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find One Success"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.sport").value("Football"));
    }

    @Test
    void testFindByIdNotFound() throws Exception {
        doThrow(new ObjectNotFoundException("schedule", 5))
                .when(gameScheduleService).findById(5);

        mockMvc.perform(get(BASE + "/5").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find schedule with id 5"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testCreateScheduleSuccess() throws Exception {
        var dto = new GameScheduleDto();
        dto.setSport("Soccer");
        dto.setSeason("2025-2026");
        String body = objectMapper.writeValueAsString(dto);

        var saved = new GameSchedule();
        saved.setId(3);
        saved.setSport("Soccer");
        saved.setSeason("2025-2026");
        given(gameScheduleService.save(any(GameSchedule.class))).willReturn(saved);

        mockMvc.perform(post(BASE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Add Success"))
                .andExpect(jsonPath("$.data.id").value(3))
                .andExpect(jsonPath("$.data.sport").value("Soccer"));
    }

    @Test
    void testUpdateScheduleSuccess() throws Exception {
        var dto = new GameScheduleDto();
        dto.setSport("Soccer");
        dto.setSeason("2025-2026");
        String body = objectMapper.writeValueAsString(dto);

        var updated = new GameSchedule();
        updated.setId(1);
        updated.setSport("Soccer");
        updated.setSeason("2025-2026");
        given(gameScheduleService.update(eq(1), any(GameSchedule.class))).willReturn(updated);

        mockMvc.perform(put(BASE + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Update Success"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.sport").value("Soccer"));
    }

    @Test
    void testUpdateScheduleNotFound() throws Exception {
        var dto = new GameScheduleDto();
        dto.setSport("Soccer");
        dto.setSeason("2025-2026");
        String body = objectMapper.writeValueAsString(dto);

        doThrow(new ObjectNotFoundException("schedule", 5))
                .when(gameScheduleService).update(eq(5), any(GameSchedule.class));

        mockMvc.perform(put(BASE + "/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find schedule with id 5"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testDeleteScheduleSuccess() throws Exception {
        doNothing().when(gameScheduleService).delete(2);

        mockMvc.perform(delete(BASE + "/2").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Delete Success"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testDeleteScheduleNotFound() throws Exception {
        doThrow(new ObjectNotFoundException("schedule", 5))
                .when(gameScheduleService).delete(5);

        mockMvc.perform(delete(BASE + "/5").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find schedule with id 5"))
                .andExpect(jsonPath("$.data").isEmpty());
    }
}
