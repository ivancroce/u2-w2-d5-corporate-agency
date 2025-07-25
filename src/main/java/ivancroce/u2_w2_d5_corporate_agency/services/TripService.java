package ivancroce.u2_w2_d5_corporate_agency.services;

import ivancroce.u2_w2_d5_corporate_agency.entities.Trip;
import ivancroce.u2_w2_d5_corporate_agency.enums.TripStatus;
import ivancroce.u2_w2_d5_corporate_agency.exceptions.BadRequestException;
import ivancroce.u2_w2_d5_corporate_agency.exceptions.NotFoundException;
import ivancroce.u2_w2_d5_corporate_agency.payloads.NewTripDTO;
import ivancroce.u2_w2_d5_corporate_agency.repositories.TripRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class TripService {
    @Autowired
    private TripRepository tripRepository;

    public Trip saveTrip(NewTripDTO payload) {
        LocalDate tripDate = payload.tripDate();
        TripStatus status = TripStatus.valueOf(payload.status().toUpperCase());

        this.validateTrip(tripDate, status);

        Trip newTrip = new Trip(payload.destination(), tripDate, status);
        Trip savedTrip = this.tripRepository.save(newTrip);

        log.info("The Trip with ID '" + savedTrip.getId() + "' was created.");
        return savedTrip;
    }

    public List<Trip> findAll() {
        return this.tripRepository.findAll();
    }

    public Trip findById(UUID tripId) {
        return this.tripRepository.findById(tripId).orElseThrow(() -> new NotFoundException(tripId));
    }

    public Trip findByIdAndUpdate(UUID tripId, NewTripDTO payload) {
        Trip found = this.findById(tripId);


        LocalDate tripDate = payload.tripDate();
        TripStatus status = TripStatus.valueOf(payload.status().toUpperCase());

        this.validateTrip(tripDate, status);

        found.setDestination(payload.destination());
        found.setTripDate(tripDate);
        found.setStatus(status);

        Trip updatedTrip = this.tripRepository.save(found);

        log.info("The Trip with ID '" + updatedTrip.getId() + "' was updated.");

        return updatedTrip;
    }

    public void findByIdAndDelete(UUID tripId) {
        Trip found = this.findById(tripId);
        this.tripRepository.delete(found);
    }

    private void validateTrip(LocalDate tripDate, TripStatus status) {
        if (tripDate.isBefore(LocalDate.now()) && status == TripStatus.SCHEDULED) {
            throw new BadRequestException("A trip in the past cannot have the status 'SCHEDULED'");
        }
        if (!tripDate.isBefore(LocalDate.now()) && status == TripStatus.COMPLETED) {
            throw new BadRequestException("A trip in the present or future cannot have the status 'COMPLETED'. It must be 'SCHEDULED'.");
        }
    }
}
