package ivancroce.u2_w2_d5_corporate_agency.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record NewBookingDTO(
        @NotNull(message = "The employee ID is mandatory")
        UUID employeeId,
        @NotNull(message = "The trip ID is mandatory")
        UUID tripId,
        @NotEmpty(message = "The notes is mandatory")
        @Size(max = 255, message = "The notes cannot exceed 255 characters")
        String notes
) {
}
