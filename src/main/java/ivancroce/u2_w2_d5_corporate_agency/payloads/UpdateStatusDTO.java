package ivancroce.u2_w2_d5_corporate_agency.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UpdateStatusDTO(
        @NotEmpty(message = "The status is mandatory (SCHEDULED or COMPLETED)")
        @Size(min = 9, max = 9, message = "The status must be of 9 characters")
        String status
) {
}
