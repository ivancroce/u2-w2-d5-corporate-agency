package ivancroce.u2_w2_d5_corporate_agency.repositories;

import ivancroce.u2_w2_d5_corporate_agency.entities.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TripRepository extends JpaRepository<Trip, UUID> {
}
