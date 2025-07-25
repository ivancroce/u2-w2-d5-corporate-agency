package ivancroce.u2_w2_d5_corporate_agency.payloads;

import java.time.LocalDateTime;

public record ErrorRespDTO(String message, LocalDateTime timestamp) {
}
