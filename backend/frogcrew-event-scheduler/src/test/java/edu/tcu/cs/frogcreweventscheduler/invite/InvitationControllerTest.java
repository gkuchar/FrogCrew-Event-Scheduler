// src/test/java/edu/tcu/cs/frogcreweventscheduler/invite/InvitationControllerTest.java
package edu.tcu.cs.frogcreweventscheduler.invite;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.tcu.cs.frogcreweventscheduler.invite.dto.InviteRequestDto;
import edu.tcu.cs.frogcreweventscheduler.invite.dto.InvitationDto;
import edu.tcu.cs.frogcreweventscheduler.system.StatusCode;
import edu.tcu.cs.frogcreweventscheduler.system.exception.GlobalExceptionHandler;
import edu.tcu.cs.frogcreweventscheduler.system.exception.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class InvitationControllerTest {

    @Mock
    InvitationService service;

    @InjectMocks
    InvitationController controller;

    MockMvc mockMvc;
    ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void testInviteSuccess() throws Exception {
        var emails = List.of("x@a.com","y@b.com");
        InviteRequestDto req = new InviteRequestDto();
        req.setEmails(emails);
        given(service.invite(emails)).willReturn(emails);

        mockMvc.perform(post("/invite")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(req))
                        .accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Invite Success"))
                .andExpect(jsonPath("$.data", List.class).isArray())
                .andExpect(jsonPath("$.data[0]").value("x@a.com"));
    }

    @Test
    void testValidateSuccess() throws Exception {
        Invitation inv = new Invitation();
        inv.setToken("tok123");
        given(service.validate("tok123")).willReturn(inv);

        mockMvc.perform(get("/invite/tok123").accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Invitation valid"))
                .andExpect(jsonPath("$.data.token").value("tok123"));
    }

    @Test
    void testValidateNotFound() throws Exception {
        willThrow(new ObjectNotFoundException("Invitation not valid"))
                .given(service).validate("badtok");

        mockMvc.perform(get("/invite/badtok").accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Invitation not valid"))
                .andExpect(jsonPath("$.data").isEmpty());
    }
}
