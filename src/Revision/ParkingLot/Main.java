package Revision.ParkingLot;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        ParkingSlot s1 = new ParkingSlot(1, VehicleType.CAR);
        ParkingSlot s2 = new ParkingSlot(2, VehicleType.CAR);

        ParkingFloor floor1 = new ParkingFloor(1, Arrays.asList(s1, s2));

        ParkingLot lot = new ParkingLot(
                Arrays.asList(floor1),
                new NearestAvailableSlotsStrategy(),
                new HourlyPriceStrategy()
        );

        Vehicle car = new Vehicle("UP32AB1234", VehicleType.CAR);

        Ticket ticket = lot.parkVehicle(car);
        System.out.println("Vehicle parked");

        double price = lot.exitVehicle(ticket);
        System.out.println("Total price: " + price);
    }
}

/*
1️⃣ How to support multiple vehicle types per slot?
2️⃣ How to make allocation thread-safe?
3️⃣ How to add different pricing per vehicle?
4️⃣ Why Strategy pattern here?
5️⃣ What happens if 2 cars arrive simultaneously?
 */