package edu.tcu.cs.frogcreweventscheduler.crew;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.tcu.cs.frogcreweventscheduler.crew.converter.CrewMemberDtoToCrewMemberConverter;
import edu.tcu.cs.frogcreweventscheduler.crew.converter.CrewMemberToCrewMemberDtoConverter;
import edu.tcu.cs.frogcreweventscheduler.crew.dto.CrewMemberDto;
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

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class CrewMemberControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private CrewMemberService service;

    private CrewMemberController controller;
    private static final String BASE = "/api/crewMembers";

    @BeforeEach
    void setUp() {
        controller = new CrewMemberController(
                service,
                new CrewMemberDtoToCrewMemberConverter(),
                new CrewMemberToCrewMemberDtoConverter()
        );

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void testFindAllSuccess() throws Exception {
        var c1 = new CrewMember(); c1.setId(1); c1.setFirstName("A"); c1.setLastName("One"); c1.setEmail("a@x"); c1.setPhoneNumber("111"); c1.setRole("R"); c1.setQualifiedPosition("P"); c1.setPassword("p");
        var c2 = new CrewMember(); c2.setId(2); c2.setFirstName("B"); c2.setLastName("Two"); c2.setEmail("b@x"); c2.setPhoneNumber("222"); c2.setRole("R"); c2.setQualifiedPosition("P"); c2.setPassword("p");
        given(service.findAll()).willReturn(Arrays.asList(c1, c2));

        mockMvc.perform(get(BASE).accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find All Success"))
                .andExpect(jsonPath("$.data", Matchers.hasSize(2)));
    }

    @Test
    void testFindByIdNotFound() throws Exception {
        doThrow(new ObjectNotFoundException("crew member", 5)).when(service).findById(5);

        mockMvc.perform(get(BASE + "/5").accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find crew member with id 5"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testCreateSuccess() throws Exception {
        var dto = new CrewMemberDto(null, "A","One","a@x","111","R","P","pw");
        String body = objectMapper.writeValueAsString(dto);

        var saved = new CrewMember(); saved.setId(3); saved.setFirstName("A"); saved.setLastName("One"); saved.setEmail("a@x"); saved.setPhoneNumber("111"); saved.setRole("R"); saved.setQualifiedPosition("P"); saved.setPassword("pw");
        given(service.save(org.mockito.ArgumentMatchers.any(CrewMember.class))).willReturn(saved);

        mockMvc.perform(post(BASE)
                        .contentType("application/json")
                        .content(body)
                        .accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Add Success"))
                .andExpect(jsonPath("$.data.id").value(3))
                .andExpect(jsonPath("$.data.email").value("a@x"));
    }

    @Test
    void testUpdateNotFound() throws Exception {
        var dto = new CrewMemberDto(null, "A","One","a@x","111","R","P","pw");
        String body = objectMapper.writeValueAsString(dto);
        doThrow(new ObjectNotFoundException("crew member", 5)).when(service).update(org.mockito.ArgumentMatchers.eq(5), org.mockito.ArgumentMatchers.any(CrewMember.class));

        mockMvc.perform(put(BASE + "/5")
                        .contentType("application/json")
                        .content(body)
                        .accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find crew member with id 5"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testDeleteSuccess() throws Exception {
        doNothing().when(service).delete(2);

        mockMvc.perform(delete(BASE + "/2").accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Delete Success"))
                .andExpect(jsonPath("$.data").isEmpty());
    }
}
