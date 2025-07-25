package ivancroce.u2_w2_d5_corporate_agency.controllers;

import ivancroce.u2_w2_d5_corporate_agency.entities.Booking;
import ivancroce.u2_w2_d5_corporate_agency.exceptions.ValidationException;
import ivancroce.u2_w2_d5_corporate_agency.payloads.NewBookingDTO;
import ivancroce.u2_w2_d5_corporate_agency.payloads.NewBookingRespDTO;
import ivancroce.u2_w2_d5_corporate_agency.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    // 1. GET http://localhost:3001/bookings
    @GetMapping
    public List<Booking> getBookings() {
        return this.bookingService.findAll();
    }

    // 2. POST http://localhost:3001/bookings (+ payload)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewBookingRespDTO createBooking(@RequestBody @Validated NewBookingDTO payload, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            validationResult.getAllErrors().forEach(System.out::println);
            throw new ValidationException(validationResult.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList());
        } else {
            Booking newBooking = this.bookingService.saveBooking(payload);
            return new NewBookingRespDTO(newBooking.getId());
        }
    }

    // 3. GET http://localhost:3001/bookings/{/bookingId}
    @GetMapping("/{bookingId}")
    public Booking getBookingById(@PathVariable UUID bookingId) {
        return this.bookingService.findById(bookingId);
    }

    // 4. PUT http://localhost:3001/bookings/{/bookingId} (+ payload)
    @PutMapping("/{bookingId}")
    public Booking findBookingByIdAndUpdate(@PathVariable UUID bookingId, @RequestBody NewBookingDTO payload) {
        return this.bookingService.findByIdAndUpdate(bookingId, payload);
    }

    // 5. DELETE http://localhost:3001/bookings/{/bookingId}
    @DeleteMapping("/{bookingId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findBookingByIdAndDelete(@PathVariable UUID bookingId) {
        this.bookingService.findByIdAndDelete(bookingId);
    }
}
