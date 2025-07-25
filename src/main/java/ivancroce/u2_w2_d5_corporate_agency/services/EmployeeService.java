package ivancroce.u2_w2_d5_corporate_agency.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import ivancroce.u2_w2_d5_corporate_agency.entities.Employee;
import ivancroce.u2_w2_d5_corporate_agency.exceptions.BadRequestException;
import ivancroce.u2_w2_d5_corporate_agency.exceptions.NotFoundException;
import ivancroce.u2_w2_d5_corporate_agency.payloads.NewEmployeeDTO;
import ivancroce.u2_w2_d5_corporate_agency.repositories.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private Cloudinary imgUploader;

    public Employee saveEmployee(NewEmployeeDTO payload) {
        this.employeeRepository.findByUsername(payload.username()).ifPresent(employee -> {
            throw new BadRequestException("The username '" + payload.username() + "' is already in use.");
        });

        this.employeeRepository.findByEmail(payload.email()).ifPresent(employee -> {
            throw new BadRequestException("The email '" + payload.email() + "' is already in use.");
        });

        Employee newEmployee = new Employee(payload.username(), payload.firstName(), payload.lastName(), payload.email());
        newEmployee.setProfileImageUrl("https://ui-avatars.com/api/?name=" + payload.username());

        Employee savedEmployee = this.employeeRepository.save(newEmployee);

        log.info("The Employee with ID '" + savedEmployee.getId() + "' was created.");
        return savedEmployee;
    }

    public List<Employee> findAll() {
        return this.employeeRepository.findAll();
    }

    public Employee findById(UUID employeeId) {
        return this.employeeRepository.findById(employeeId).orElseThrow(() -> new NotFoundException(employeeId));
    }

    public Employee findByIdAndUpdate(UUID employeeId, NewEmployeeDTO payload) {
        Employee found = this.findById(employeeId);

        if (!found.getUsername().equals(payload.username()))
            this.employeeRepository.findByUsername(payload.username()).ifPresent(employee -> {
                throw new BadRequestException("The username '" + employee.getUsername() + "' is already in use.");
            });

        if (!found.getEmail().equals(payload.email()))
            this.employeeRepository.findByEmail(payload.email()).ifPresent(employee -> {
                throw new BadRequestException("The email '" + employee.getEmail() + "' is already in use.");
            });

        found.setUsername(payload.username());
        found.setFirstName(payload.firstName());
        found.setLastName(payload.lastName());
        found.setEmail(payload.email());
        found.setProfileImageUrl("https://ui-avatars.com/api/?name=" + payload.username());

        Employee updatedEmpoyee = this.employeeRepository.save(found);

        log.info("The Employee with ID '" + updatedEmpoyee.getId() + "' was updated.");

        return updatedEmpoyee;
    }

    public void findByIdAndDelete(UUID employeeId) {
        Employee found = this.findById(employeeId);
        this.employeeRepository.delete(found);
    }

    public Employee uploadAvatar(MultipartFile file, UUID employeeId) {
        try {
            Employee found = this.findById(employeeId);

            Map result = imgUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

            String imgUrl = (String) result.get("url");

            found.setProfileImageUrl(imgUrl);

            return employeeRepository.save(found);
        } catch (Exception e) {
            throw new BadRequestException("There were problems saving the file.");
        }
    }
}
