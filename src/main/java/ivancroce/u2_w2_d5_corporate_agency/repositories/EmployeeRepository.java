package ivancroce.u2_w2_d5_corporate_agency.repositories;

import ivancroce.u2_w2_d5_corporate_agency.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
    Optional<Employee> findByUsername(String username);

    Optional<Employee> findByEmail(String email);
}
