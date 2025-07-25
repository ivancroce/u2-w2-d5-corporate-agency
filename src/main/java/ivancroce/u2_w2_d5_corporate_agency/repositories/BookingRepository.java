package ivancroce.u2_w2_d5_corporate_agency.repositories;

import ivancroce.u2_w2_d5_corporate_agency.entities.Booking;
import ivancroce.u2_w2_d5_corporate_agency.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, UUID> {
    boolean existsByEmployeeAndTrip_TripDate(Employee employee, LocalDate tripDate);
}
