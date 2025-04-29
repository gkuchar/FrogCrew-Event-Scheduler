// src/main/java/edu/tcu/cs/frogcreweventscheduler/availability/AvailabilityService.java
package edu.tcu.cs.frogcreweventscheduler.availability;

import edu.tcu.cs.frogcreweventscheduler.system.exception.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvailabilityService {

    private final AvailabilityRepository repository;

    public AvailabilityService(AvailabilityRepository repository) {
        this.repository = repository;
    }

    public List<Availability> findAll() {
        return repository.findAll();
    }

    public Availability findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("availability", id));
    }

    public Availability save(Availability availability) {
        return repository.save(availability);
    }

    public Availability update(Integer id, Availability availability) {
        Availability existing = findById(id);
        existing.setAvailable(availability.getAvailable());
        existing.setComment(availability.getComment());
        existing.setCrewMember(availability.getCrewMember());
        existing.setGameSchedule(availability.getGameSchedule());
        return repository.save(existing);
    }

    public void delete(Integer id) {
        // throws if not found
        findById(id);
        repository.deleteById(id);
    }
}
