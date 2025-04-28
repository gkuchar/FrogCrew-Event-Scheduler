package edu.tcu.cs.frogcreweventscheduler.schedule;

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
class GameScheduleServiceTest {

    @Mock
    GameScheduleRepository repository;

    @InjectMocks
    GameScheduleService service;

    List<GameSchedule> schedules;

    @BeforeEach
    void setUp() {
        GameSchedule g1 = new GameSchedule();
        g1.setId(1);
        g1.setSport("Football");
        g1.setSeason("2024-2025");

        GameSchedule g2 = new GameSchedule();
        g2.setId(2);
        g2.setSport("Basketball");
        g2.setSeason("2024-2025");

        schedules = Arrays.asList(g1, g2);
    }

    @Test
    void testFindAllSuccess() {
        given(repository.findAll()).willReturn(schedules);

        List<GameSchedule> actual = service.findAll();

        assertThat(actual).hasSize(2);
        verify(repository, times(1)).findAll();
    }

    @Test
    void testFindByIdSuccess() {
        given(repository.findById(1)).willReturn(Optional.of(schedules.get(0)));

        GameSchedule found = service.findById(1);

        assertThat(found.getId()).isEqualTo(1);
        assertThat(found.getSport()).isEqualTo("Football");
        verify(repository, times(1)).findById(1);
    }

    @Test
    void testFindByIdNotFound() {
        given(repository.findById(5)).willReturn(Optional.empty());

        Throwable thrown = catchThrowable(() -> service.findById(5));

        assertThat(thrown)
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessage("Could not find schedule with id 5");
        verify(repository).findById(5);
    }

    @Test
    void testSaveSuccess() {
        GameSchedule toSave = new GameSchedule();
        toSave.setSport("Soccer");
        toSave.setSeason("2025-2026");

        given(repository.save(toSave)).willReturn(toSave);

        GameSchedule saved = service.save(toSave);

        assertThat(saved.getSport()).isEqualTo("Soccer");
        verify(repository).save(toSave);
    }

    @Test
    void testUpdateSuccess() {
        GameSchedule existing = schedules.get(0);
        GameSchedule update = new GameSchedule();
        update.setSport("Soccer");
        update.setSeason("2025-2026");

        given(repository.findById(1)).willReturn(Optional.of(existing));
        given(repository.save(existing)).willReturn(existing);

        GameSchedule result = service.update(1, update);

        assertThat(result.getSport()).isEqualTo("Soccer");
        assertThat(result.getSeason()).isEqualTo("2025-2026");
        verify(repository).findById(1);
        verify(repository).save(existing);
    }

    @Test
    void testUpdateNotFound() {
        given(repository.findById(5)).willReturn(Optional.empty());

        GameSchedule update = new GameSchedule();
        update.setSport("Soccer");
        update.setSeason("2025-2026");

        assertThatThrownBy(() -> service.update(5, update))
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessage("Could not find schedule with id 5");
        verify(repository).findById(5);
    }

    @Test
    void testDeleteSuccess() {
        given(repository.findById(1)).willReturn(Optional.of(schedules.get(0)));
        doNothing().when(repository).deleteById(1);

        service.delete(1);

        verify(repository).deleteById(1);
    }

    @Test
    void testDeleteNotFound() {
        given(repository.findById(5)).willReturn(Optional.empty());

        assertThatThrownBy(() -> service.delete(5))
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessage("Could not find schedule with id 5");
        verify(repository).findById(5);
    }
}
