package edu.tcu.cs.frogcreweventscheduler.schedule;

import edu.tcu.cs.frogcreweventscheduler.system.exception.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameScheduleService {

    private final GameScheduleRepository repository;

    public GameScheduleService(GameScheduleRepository repository) {
        this.repository = repository;
    }

    public List<GameSchedule> findAll() {
        return repository.findAll();
    }

    public GameSchedule findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("schedule", id));
    }

    public GameSchedule save(GameSchedule schedule) {
        return repository.save(schedule);
    }

    public GameSchedule update(Integer id, GameSchedule schedule) {
        GameSchedule existing = findById(id);
        existing.setSport(schedule.getSport());
        existing.setSeason(schedule.getSeason());
        return repository.save(existing);
    }

    public void delete(Integer id) {
        // will throw if not found
        findById(id);
        repository.deleteById(id);
    }
}
