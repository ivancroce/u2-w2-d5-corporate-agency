package ivancroce.u2_w2_d5_corporate_agency.exceptions.exceptions;

import java.util.UUID;

public class NotFoundException extends RuntimeException {
    public NotFoundException(UUID id) {
        super("Resource with id '" + id + "' not found.");
    }
}
