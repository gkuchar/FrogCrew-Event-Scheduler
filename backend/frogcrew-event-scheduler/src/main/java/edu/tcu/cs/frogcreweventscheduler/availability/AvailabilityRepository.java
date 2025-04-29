// src/main/java/edu/tcu/cs/frogcreweventscheduler/availability/AvailabilityRepository.java
package edu.tcu.cs.frogcreweventscheduler.availability;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AvailabilityRepository extends JpaRepository<Availability, Integer> {
}
