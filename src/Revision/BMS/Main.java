package Revision.BMS;

import java.time.LocalDateTime;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        Movies movie = new Movies("m1", "Inception", 150);

        Seat s1 = new Seat("A1");
        Seat s2 = new Seat("A2");

        Screen screen = new Screen("screen1", Arrays.asList(s1, s2));

        Show show = new Show(
                "show1",
                screen,
                movie,
                LocalDateTime.now()
        );

        BookingService bookingService =
                new BookingService(new DefaultSeatBookingStrategy());

        Booking booking = bookingService.bookSeats(
                show,
                Arrays.asList(s1, s2)
        );

        System.out.println("Booking successful!");
    }
}
