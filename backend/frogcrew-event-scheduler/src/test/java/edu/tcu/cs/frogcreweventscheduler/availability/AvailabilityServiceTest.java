package edu.tcu.cs.frogcreweventscheduler.availability;

import edu.tcu.cs.frogcreweventscheduler.crew.CrewMember;
import edu.tcu.cs.frogcreweventscheduler.schedule.GameSchedule;
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
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class AvailabilityServiceTest {

    @Mock
    AvailabilityRepository repository;

    @InjectMocks
    AvailabilityService service;

    List<Availability> availabilities;

    @BeforeEach
    void setUp() {
        CrewMember cm = new CrewMember(); cm.setId(1); cm.setFirstName("Test");
        GameSchedule gs = new GameSchedule(); gs.setId(1); gs.setSport("Football");

        Availability a1 = new Availability();
        a1.setId(1); a1.setCrewMember(cm); a1.setGameSchedule(gs); a1.setAvailable(true);

        Availability a2 = new Availability();
        a2.setId(2); a2.setCrewMember(cm); a2.setGameSchedule(gs); a2.setAvailable(false);

        availabilities = Arrays.asList(a1, a2);
    }

    @Test
    void testFindAllSuccess() {
        given(repository.findAll()).willReturn(availabilities);

        List<Availability> actual = service.findAll();

        assertThat(actual).hasSize(2);
        verify(repository).findAll();
    }

    @Test
    void testFindByIdSuccess() {
        given(repository.findById(1)).willReturn(Optional.of(availabilities.get(0)));

        Availability found = service.findById(1);

        assertThat(found.getId()).isEqualTo(1);
        verify(repository).findById(1);
    }

    @Test
    void testFindByIdNotFound() {
        given(repository.findById(5)).willReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(5))
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessage("Could not find availability with id 5");

        verify(repository).findById(5);
    }

    @Test
    void testSaveSuccess() {
        Availability a = availabilities.get(0);
        given(repository.save(a)).willReturn(a);

        Availability saved = service.save(a);

        assertThat(saved).isNotNull();
        verify(repository).save(a);
    }

    @Test
    void testUpdateSuccess() {
        Availability existing = availabilities.get(0);
        Availability update = new Availability();
        update.setCrewMember(existing.getCrewMember());
        update.setGameSchedule(existing.getGameSchedule());
        update.setAvailable(false);
        update.setComment("Updated");

        given(repository.findById(1)).willReturn(Optional.of(existing));
        given(repository.save(existing)).willReturn(existing);

        Availability result = service.update(1, update);

        assertThat(result.getAvailable()).isFalse();
        assertThat(result.getComment()).isEqualTo("Updated");
        verify(repository).findById(1);
        verify(repository).save(existing);
    }

    @Test
    void testUpdateNotFound() {
        Availability update = new Availability();
        given(repository.findById(5)).willReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(5, update))
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessage("Could not find availability with id 5");

        verify(repository).findById(5);
    }

    @Test
    void testDeleteSuccess() {
        given(repository.findById(1)).willReturn(Optional.of(availabilities.get(0)));
        willDoNothing().given(repository).deleteById(1);

        service.delete(1);

        verify(repository).deleteById(1);
    }

    @Test
    void testDeleteNotFound() {
        given(repository.findById(5)).willReturn(Optional.empty());

        assertThatThrownBy(() -> service.delete(5))
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessage("Could not find availability with id 5");

        verify(repository).findById(5);
    }
}
