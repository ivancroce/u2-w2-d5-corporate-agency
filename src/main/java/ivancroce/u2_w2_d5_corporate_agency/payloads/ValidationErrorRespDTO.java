package ivancroce.u2_w2_d5_corporate_agency.payloads;

import java.time.LocalDateTime;
import java.util.List;

public record ValidationErrorRespDTO(String message, LocalDateTime timestamp, List<String> errorsList) {
}
