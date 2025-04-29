package edu.tcu.cs.frogcreweventscheduler.crew;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CrewMemberRepository extends JpaRepository<CrewMember,Integer> {
    Optional<CrewMember> findByEmail(String email);
}
