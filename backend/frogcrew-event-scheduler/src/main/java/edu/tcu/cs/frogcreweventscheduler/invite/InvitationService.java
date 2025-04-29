// src/main/java/edu/tcu/cs/frogcreweventscheduler/invite/InvitationService.java
package edu.tcu.cs.frogcreweventscheduler.invite;

import edu.tcu.cs.frogcreweventscheduler.system.exception.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class InvitationService {

    private final InvitationRepository repository;

    public InvitationService(InvitationRepository repository) {
        this.repository = repository;
    }

    /** Use‐case “POST /invite” */
    public List<String> invite(List<String> emails) {
        List<String> sent = new ArrayList<>();
        for (String email : emails) {
            Invitation inv = new Invitation();
            inv.setEmail(email);
            inv.setToken(UUID.randomUUID().toString());
            inv.setCreatedAt(LocalDateTime.now());
            repository.save(inv);
            sent.add(email);
        }
        return sent;
    }

    /** Use‐case “GET /invite/{token}” */
    public Invitation validate(String token) {
        return repository.findByToken(token)
                .orElseThrow(() -> new ObjectNotFoundException("Invitation not valid"));
    }
}
