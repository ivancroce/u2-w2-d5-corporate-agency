package ivancroce.u2_w2_d5_corporate_agency.services;

import ivancroce.u2_w2_d5_corporate_agency.entities.Booking;
import ivancroce.u2_w2_d5_corporate_agency.entities.Employee;
import ivancroce.u2_w2_d5_corporate_agency.entities.Trip;
import ivancroce.u2_w2_d5_corporate_agency.enums.TripStatus;
import ivancroce.u2_w2_d5_corporate_agency.exceptions.BadRequestException;
import ivancroce.u2_w2_d5_corporate_agency.exceptions.NotFoundException;
import ivancroce.u2_w2_d5_corporate_agency.payloads.NewBookingDTO;
import ivancroce.u2_w2_d5_corporate_agency.repositories.BookingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private TripService tripService;

    public Booking saveBooking(NewBookingDTO payload) {
        Employee employee = employeeService.findById(payload.employeeId());
        Trip trip = tripService.findById(payload.tripId());

        if (trip.getStatus() != TripStatus.SCHEDULED) {
            throw new BadRequestException("Cannot create a booking for a trip that is not 'SCHEDULED'. Current status: " + trip.getStatus());
        }

        if (trip.getTripDate().isBefore(LocalDate.now())) {
            throw new BadRequestException("Cannot create a booking for a trip that has already passed. Trip date: " + trip.getTripDate());
        }

        if (bookingRepository.existsByEmployeeAndTrip_TripDate(employee, trip.getTripDate())) {
            throw new BadRequestException("Employee " + employee.getUsername() + " is already booked for a trip on date" + trip.getTripDate());
        }

        Booking newBooking = new Booking(employee, trip, payload.notes());

        Booking savedBooking = this.bookingRepository.save(newBooking);

        log.info("New booking with ID " + savedBooking.getId() + " created for employee " + employee.getUsername());

        return savedBooking;
    }

    public List<Booking> findAll() {
        return this.bookingRepository.findAll();
    }

    public Booking findById(UUID bookingId) {
        return this.bookingRepository.findById(bookingId).orElseThrow(() -> new NotFoundException(bookingId));
    }

    public Booking findByIdAndUpdate(UUID bookingId, NewBookingDTO payload) {

        Booking found = this.findById(bookingId);

        found.setNotes(payload.notes());

        Booking updatedBookingNotes = this.bookingRepository.save(found);

        log.info("The Notes for the booking with ID " + bookingId + " was updated.");

        return updatedBookingNotes;
    }

    public void findByIdAndDelete(UUID bookingId) {
        Booking found = this.findById(bookingId);
        this.bookingRepository.delete(found);
    }
}
