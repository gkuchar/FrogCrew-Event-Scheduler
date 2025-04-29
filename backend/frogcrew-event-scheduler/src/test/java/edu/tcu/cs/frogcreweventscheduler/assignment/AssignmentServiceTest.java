// src/test/java/edu/tcu/cs/frogcreweventscheduler/assignment/AssignmentServiceTest.java
package edu.tcu.cs.frogcreweventscheduler.assignment;

import edu.tcu.cs.frogcreweventscheduler.system.exception.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class AssignmentServiceTest {

    @Mock
    private AssignmentRepository repository;

    private AssignmentService service;

    @BeforeEach
    void setUp() {
        service = new AssignmentService(repository);
    }

    @Test
    void testFindAll() {
        Assignment a1 = new Assignment(); a1.setId(1);
        Assignment a2 = new Assignment(); a2.setId(2);
        given(repository.findAll()).willReturn(Arrays.asList(a1, a2));

        var all = service.findAll();
        assertEquals(2, all.size());
        assertTrue(all.contains(a1));
        assertTrue(all.contains(a2));
    }

    @Test
    void testFindByIdSuccess() {
        Assignment a = new Assignment(); a.setId(1);
        given(repository.findById(1)).willReturn(Optional.of(a));

        var found = service.findById(1);
        assertEquals(a, found);
    }

    @Test
    void testFindByIdNotFound() {
        given(repository.findById(5)).willReturn(Optional.empty());
        assertThrows(ObjectNotFoundException.class, () -> service.findById(5));
    }

    @Test
    void testSave() {
        Assignment a = new Assignment(); a.setId(3);
        given(repository.save(a)).willReturn(a);

        var saved = service.save(a);
        assertEquals(3, saved.getId());
    }

    @Test
    void testUpdateSuccess() {
        Assignment existing = new Assignment(); existing.setId(4);
        Assignment updated = new Assignment();
        given(repository.findById(4)).willReturn(Optional.of(existing));
        given(repository.save(existing)).willReturn(existing);

        var result = service.update(4, updated);
        assertEquals(existing, result);
    }

    @Test
    void testUpdateNotFound() {
        Assignment updated = new Assignment();
        given(repository.findById(5)).willReturn(Optional.empty());
        assertThrows(ObjectNotFoundException.class, () -> service.update(5, updated));
    }

    @Test
    void testDeleteSuccess() {
        Assignment a = new Assignment(); a.setId(6);
        given(repository.findById(6)).willReturn(Optional.of(a));

        assertDoesNotThrow(() -> service.delete(6));
        then(repository).should().deleteById(6);
    }

    @Test
    void testDeleteNotFound() {
        given(repository.findById(7)).willReturn(Optional.empty());
        assertThrows(ObjectNotFoundException.class, () -> service.delete(7));
    }
}
