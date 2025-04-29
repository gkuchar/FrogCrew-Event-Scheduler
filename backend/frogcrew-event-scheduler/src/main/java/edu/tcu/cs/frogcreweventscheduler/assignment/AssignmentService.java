// src/main/java/edu/tcu/cs/frogcreweventscheduler/assignment/AssignmentService.java
package edu.tcu.cs.frogcreweventscheduler.assignment;

import edu.tcu.cs.frogcreweventscheduler.system.exception.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssignmentService {

    private final AssignmentRepository repository;

    public AssignmentService(AssignmentRepository repository) {
        this.repository = repository;
    }

    public List<Assignment> findAll() {
        return repository.findAll();
    }

    public Assignment findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("assignment", id));
    }

    public Assignment save(Assignment assignment) {
        return repository.save(assignment);
    }

    public Assignment update(Integer id, Assignment assignment) {
        Assignment existing = findById(id);
        existing.setCrewMember(assignment.getCrewMember());
        existing.setGameSchedule(assignment.getGameSchedule());
        return repository.save(existing);
    }

    public void delete(Integer id) {
        // will throw if not found
        findById(id);
        repository.deleteById(id);
    }
}
