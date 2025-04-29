// src/test/java/edu/tcu/cs/frogcreweventscheduler/invite/InvitationServiceTest.java
package edu.tcu.cs.frogcreweventscheduler.invite;

import edu.tcu.cs.frogcreweventscheduler.system.exception.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

class InvitationServiceTest {

    @Mock
    InvitationRepository repository;

    @InjectMocks
    InvitationService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testInviteSuccess() {
        // stub save to just echo back
        given(repository.save(any(Invitation.class)))
                .willAnswer(inv -> inv.getArgument(0));

        var emails = List.of("a@x.com", "b@x.com");
        List<String> result = service.invite(emails);

        assertThat(result).containsExactly("a@x.com", "b@x.com");
        verify(repository, times(2)).save(any());
    }

    @Test
    void testValidateSuccess() {
        Invitation inv = new Invitation();
        inv.setToken("tkn123");
        given(repository.findByToken("tkn123")).willReturn(Optional.of(inv));

        Invitation found = service.validate("tkn123");
        assertThat(found.getToken()).isEqualTo("tkn123");
        then(repository).should().findByToken("tkn123");
    }

    @Test
    void testValidateNotFound() {
        given(repository.findByToken("bad")).willReturn(Optional.empty());

        Throwable thrown = catchThrowable(() -> service.validate("bad"));
        assertThat(thrown)
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessage("Invitation not valid");

        then(repository).should().findByToken("bad");
    }
}
