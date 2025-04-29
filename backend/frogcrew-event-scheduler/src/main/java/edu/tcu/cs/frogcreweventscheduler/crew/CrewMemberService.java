package edu.tcu.cs.frogcreweventscheduler.crew;

import edu.tcu.cs.frogcreweventscheduler.system.exception.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrewMemberService {

    private final CrewMemberRepository repository;

    public CrewMemberService(CrewMemberRepository repository) {
        this.repository = repository;
    }

    public List<CrewMember> findAll() {
        return repository.findAll();
    }

    public CrewMember findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("crew member", id));
    }

    public CrewMember save(CrewMember member) {
        return repository.save(member);
    }

    public CrewMember update(Integer id, CrewMember updated) {
        CrewMember existing = findById(id);
        existing.setFirstName(updated.getFirstName());
        existing.setLastName(updated.getLastName());
        existing.setEmail(updated.getEmail());
        existing.setPhoneNumber(updated.getPhoneNumber());
        existing.setRole(updated.getRole());
        existing.setQualifiedPosition(updated.getQualifiedPosition());
        existing.setPassword(updated.getPassword());
        return repository.save(existing);
    }

    public void delete(Integer id) {
        findById(id);
        repository.deleteById(id);
    }
}
