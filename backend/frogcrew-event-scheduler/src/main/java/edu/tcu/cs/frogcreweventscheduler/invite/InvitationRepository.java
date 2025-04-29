// src/main/java/edu/tcu/cs/frogcreweventscheduler/invite/InvitationRepository.java
package edu.tcu.cs.frogcreweventscheduler.invite;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface InvitationRepository extends JpaRepository<Invitation, Integer> {
    Optional<Invitation> findByToken(String token);
}
