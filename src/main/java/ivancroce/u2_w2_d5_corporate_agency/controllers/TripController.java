package ivancroce.u2_w2_d5_corporate_agency.controllers;

import ivancroce.u2_w2_d5_corporate_agency.entities.Trip;
import ivancroce.u2_w2_d5_corporate_agency.exceptions.ValidationException;
import ivancroce.u2_w2_d5_corporate_agency.payloads.NewTripDTO;
import ivancroce.u2_w2_d5_corporate_agency.payloads.NewTripRespDTO;
import ivancroce.u2_w2_d5_corporate_agency.services.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/trips")
public class TripController {

    @Autowired
    private TripService tripService;

    // 1. GET http://localhost:3001/trips
    @GetMapping
    public List<Trip> getTrips() {
        return this.tripService.findAll();
    }

    // 2. POST http://localhost:3001/trips (+ payload)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewTripRespDTO createTrip(@RequestBody @Validated NewTripDTO payload, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            validationResult.getAllErrors().forEach(System.out::println);
            throw new ValidationException(validationResult.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList());
        } else {
            Trip newTrip = this.tripService.saveTrip(payload);
            return new NewTripRespDTO(newTrip.getId());
        }
    }

    // 3. GET http://localhost:3001/trips/{/tripId}
    @GetMapping("/{tripId}")
    public Trip getTripById(@PathVariable UUID tripId) {
        return this.tripService.findById(tripId);
    }

    // 4. PUT http://localhost:3001/trips/{/tripId} (+ payload)
    @PutMapping("/{tripId}")
    public Trip findTripByIdAndUpdate(@PathVariable UUID tripId, @RequestBody NewTripDTO payload) {
        return this.tripService.findByIdAndUpdate(tripId, payload);
    }

    // 5. DELETE http://localhost:3001/trips/{/tripId}
    @DeleteMapping("/{tripId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findTripByIdAndDelete(@PathVariable UUID tripId) {
        this.tripService.findByIdAndDelete(tripId);
    }
}
