package edu.tcu.cs.frogcreweventscheduler.crew;

import edu.tcu.cs.frogcreweventscheduler.system.exception.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CrewMemberServiceTest {

    @Mock
    CrewMemberRepository repository;

    @InjectMocks
    CrewMemberService service;

    CrewMember c1, c2;

    @BeforeEach
    void setUp() {
        c1 = new CrewMember();
        c1.setId(1);
        c1.setFirstName("Alice");
        c1.setLastName("A");
        c1.setEmail("alice@example.com");
        c1.setPhoneNumber("123-456-7890");
        c1.setRole("Camera");
        c1.setQualifiedPosition("Operator");
        c1.setPassword("secret");

        c2 = new CrewMember();
        c2.setId(2);
        c2.setFirstName("Bob");
        c2.setLastName("B");
        c2.setEmail("bob@example.com");
        c2.setPhoneNumber("321-654-0987");
        c2.setRole("Audio");
        c2.setQualifiedPosition("Mixer");
        c2.setPassword("hunter2");
    }

    @Test
    void testFindAllSuccess() {
        given(repository.findAll()).willReturn(Arrays.asList(c1, c2));
        List<CrewMember> actual = service.findAll();
        assertThat(actual).hasSize(2);
        verify(repository).findAll();
    }

    @Test
    void testFindByIdSuccess() {
        given(repository.findById(1)).willReturn(Optional.of(c1));
        CrewMember found = service.findById(1);
        assertThat(found.getEmail()).isEqualTo("alice@example.com");
        verify(repository).findById(1);
    }

    @Test
    void testFindByIdNotFound() {
        given(repository.findById(5)).willReturn(Optional.empty());
        assertThatThrownBy(() -> service.findById(5))
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessage("Could not find crew member with id 5");
        verify(repository).findById(5);
    }

    @Test
    void testSaveSuccess() {
        CrewMember toSave = new CrewMember();
        toSave.setFirstName("Carol");
        toSave.setLastName("C");
        toSave.setEmail("carol@example.com");
        toSave.setPhoneNumber("111-222-3333");
        toSave.setRole("Graphics");
        toSave.setQualifiedPosition("Designer");
        toSave.setPassword("pass");

        given(repository.save(toSave)).willReturn(toSave);
        CrewMember saved = service.save(toSave);
        assertThat(saved.getEmail()).isEqualTo("carol@example.com");
        verify(repository).save(toSave);
    }

    @Test
    void testUpdateSuccess() {
        CrewMember update = new CrewMember();
        update.setFirstName("Alice");
        update.setLastName("Updated");
        update.setEmail("alice2@example.com");
        update.setPhoneNumber("999-999-9999");
        update.setRole("Director");
        update.setQualifiedPosition("Lead");
        update.setPassword("newpass");

        given(repository.findById(1)).willReturn(Optional.of(c1));
        given(repository.save(c1)).willReturn(c1);

        CrewMember result = service.update(1, update);
        assertThat(result.getLastName()).isEqualTo("Updated");
        assertThat(result.getEmail()).isEqualTo("alice2@example.com");
        verify(repository).findById(1);
        verify(repository).save(c1);
    }

    @Test
    void testUpdateNotFound() {
        given(repository.findById(5)).willReturn(Optional.empty());
        CrewMember update = new CrewMember();
        assertThatThrownBy(() -> service.update(5, update))
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessage("Could not find crew member with id 5");
        verify(repository).findById(5);
    }

    @Test
    void testDeleteSuccess() {
        given(repository.findById(1)).willReturn(Optional.of(c1));
        doNothing().when(repository).deleteById(1);
        service.delete(1);
        verify(repository).deleteById(1);
    }

    @Test
    void testDeleteNotFound() {
        given(repository.findById(5)).willReturn(Optional.empty());
        assertThatThrownBy(() -> service.delete(5))
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessage("Could not find crew member with id 5");
        verify(repository).findById(5);
    }
}
