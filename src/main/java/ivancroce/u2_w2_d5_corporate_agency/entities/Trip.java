package ivancroce.u2_w2_d5_corporate_agency.entities;

import ivancroce.u2_w2_d5_corporate_agency.enums.TripStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "trips")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Trip {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    @Column(nullable = false)
    private String destination;
    @Column(name = "trip_date", nullable = false)
    private LocalDate tripDate;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TripStatus status;

    public Trip(String destination, LocalDate tripDate, TripStatus status) {
        this.destination = destination;
        this.tripDate = tripDate;
        this.status = status;
    }
}
