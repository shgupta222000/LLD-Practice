package Revision.BMS;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Movies {
    private String id;
    private String name;
    private int durationMinutes;

    public Movies(String id, String name, int durationMinutes) {
        this.id = id;
        this.name = name;
        this.durationMinutes = durationMinutes;
    }
    public String getName() {
        return name;
    }
    public int getDurationMinutes() {
        return durationMinutes;
    }
}

class Seat{
    String id;
    SeatStatus status;
    public Seat(String id) {
        this.id = id;
        this.status = SeatStatus.AVAILABLE;
    }
    public boolean book(){
        if (status == SeatStatus.AVAILABLE) {
            status = SeatStatus.BOOKED;
            return true;
        }
        return false;
    }
    public String getId() {
        return id;
    }
    public SeatStatus getStatus() {
        return status;
    }
}
class Screen{
    String id;
    List<Seat> seats;
    public Screen(String id, List<Seat> seats) {
        this.id = id;
        this.seats = seats;
    }

    public List<Seat> getSeats() {
        return seats;
    }
}
class Show{
    String showId;
    Screen screen;
    Movies movies;
    LocalDateTime startTime;
    public Show(String showId, Screen screen, Movies movies, LocalDateTime startTime) {
        this.showId = showId;
        this.screen = screen;
        this.movies = movies;
        this.startTime = startTime;
    }

    public Screen getScreen() {
        return screen;
    }
    public Movies getMovies() {
        return movies;
    }
}
class Booking{
    String bookingId;
    Show show;
    List<Seat> seats;
    public Booking(String bookingId, Show show, List<Seat> seats) {
        this.bookingId = bookingId;
        this.show = show;
        this.seats = seats;
    }
}
enum SeatStatus{
    AVAILABLE,
    BOOKED
}

//interface
interface SeatBookingStrategy {
    boolean bookSeats(List<Seat> seats);
}

class DefaultSeatBookingStrategy implements SeatBookingStrategy {

    @Override
    public boolean bookSeats(List<Seat> seats) {
        for (Seat seat : seats) {
            if (!seat.book()) {
                return false;
            }
        }
        return true;
    }
}


class BookingService {

    private final SeatBookingStrategy bookingStrategy;

    public BookingService(SeatBookingStrategy bookingStrategy) {
        this.bookingStrategy = bookingStrategy;
    }

    public Booking bookSeats(Show show, List<Seat> seats) {

        boolean success = bookingStrategy.bookSeats(seats);

        if (!success) {
            throw new RuntimeException("Seat already booked");
        }

        return new Booking(
                UUID.randomUUID().toString(),
                show,
                seats
        );
    }
}

/*
1️⃣ How to prevent partial booking?
code Improvement
class DefaultSeatBookingStrategy implements SeatBookingStrategy {

    @Override
    public synchronized boolean bookSeats(List<Seat> seats) {

        // Step 1: validate
        for (Seat seat : seats) {
            if (seat.getStatus() != SeatStatus.AVAILABLE) {
                return false;
            }
        }

        // Step 2: book all
        for (Seat seat : seats) {
            seat.book();
        }

        return true;
    }
}
2️⃣ How to add seat locking with timeout?
enum SeatStatus {
    AVAILABLE, LOCKED, BOOKED
}
seat are locked temporarily and released after timeout
class Seat {
    private SeatStatus status;
    private long lockTime;

    public synchronized boolean lock() {
        if (status == SeatStatus.AVAILABLE) {
            status = SeatStatus.LOCKED;
            lockTime = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public synchronized void unlockIfExpired(long timeoutMs) {
        if (status == SeatStatus.LOCKED &&
            System.currentTimeMillis() - lockTime > timeoutMs) {
            status = SeatStatus.AVAILABLE;
        }
    }
}
3️⃣ How to handle concurrent booking requests?
synchronized (seat) {
    if (seat.getStatus() == SeatStatus.AVAILABLE) {
        seat.book();
    }
}
4️⃣ How to show available seats efficiently?
5️⃣ How to support multiple theatres & cities?
 */