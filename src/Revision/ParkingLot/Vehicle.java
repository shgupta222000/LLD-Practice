package Revision.ParkingLot;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Vehicle {
    private String vehicleNumber;
    private VehicleType vehicleType;

    public Vehicle(String vehicleNumber, VehicleType vehicleType){
        this.vehicleNumber = vehicleNumber;
        this.vehicleType = vehicleType;
    }
    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }
}
class ParkingSlot{
    private int slotNumber;
    private VehicleType vehicleType;
    private boolean isOccupied;
    private Vehicle vehicle;
    public ParkingSlot(int slotNumber, VehicleType vehicleType) {
        this.slotNumber = slotNumber;
        this.vehicleType = vehicleType;
    }
    public boolean canPark(Vehicle vehicle){
        return (!isOccupied && vehicle.getVehicleType()==vehicleType);
    }
    public void park(Vehicle vehicle){
        this.vehicle = vehicle;
        this.isOccupied = true;
    }
    public void remove(){
        this.vehicle = null;
        this.isOccupied = false;
    }

    public boolean isOccupied() {
        return isOccupied;
    }
    public int getSlotNumber() {
        return slotNumber;
    }
    public Vehicle getVehicle() {
        return vehicle;
    }
}
class ParkingFloor{
    private int floorNumber;
    private List<ParkingSlot> parkingSlots;

    public ParkingFloor(int floorNumber, List<ParkingSlot> parkingSlots) {
        this.floorNumber = floorNumber;
        this.parkingSlots = parkingSlots;
    }

    public List<ParkingSlot> getParkingSlots() {
        return parkingSlots;
    }

    public int getFloorNumber() {
        return floorNumber;
    }
}

class Ticket{
    private String ticketId;
    private Vehicle vehicle;
    private ParkingSlot parkingSlot;
    private LocalDateTime entryTime;
    public Ticket(String ticketId, Vehicle vehicle, ParkingSlot parkingSlot) {
        this.ticketId = ticketId;
        this.vehicle = vehicle;
        this.parkingSlot = parkingSlot;
        this.entryTime = LocalDateTime.now();
    }
    public String getTicketId() {
        return ticketId;
    }
    public Vehicle getVehicle() {
        return vehicle;
    }
    public ParkingSlot getParkingSlot() {
        return parkingSlot;
    }
    public LocalDateTime getEntryTime() {
        return entryTime;
    }

}

//interfaces
interface SlotAllocationStrategy{
    ParkingSlot allocate(Vehicle vehicle,ParkingLot parkingLot);
}
interface PricingStrategy{
    double calculatePrice(Ticket ticket);
}

class NearestAvailableSlotsStrategy implements SlotAllocationStrategy{
    @Override
    public ParkingSlot allocate(Vehicle vehicle, ParkingLot lot) {
        for(ParkingFloor floor : lot.getParkingFloors()){
            for(ParkingSlot slot : floor.getParkingSlots()){
                if(slot.canPark(vehicle)){
                    return slot;
                }
            }
        }
        return null;
    }
}
class HourlyPriceStrategy implements PricingStrategy{
    @Override
    public double calculatePrice(Ticket ticket) {
        long hours = Duration.between(LocalDateTime.now(), ticket.getEntryTime()).toHours()+1;
        return hours*50;
    }
}
enum VehicleType{
    CAR,TRUCK,BIKE;
}

//service

class ParkingLot{
    //private int parkingLotNumber;
    private List<ParkingFloor> parkingFloors;
    private SlotAllocationStrategy slotAllocationStrategy;
    private PricingStrategy pricingStrategy;

   public ParkingLot( List<ParkingFloor>parkingFloors, SlotAllocationStrategy slotAllocationStrategy, PricingStrategy pricingStrategy){
       this.parkingFloors = parkingFloors;
       this.slotAllocationStrategy = slotAllocationStrategy;
       this.pricingStrategy = pricingStrategy;
   }
   public Ticket parkVehicle(Vehicle vehicle){
       ParkingSlot slot = slotAllocationStrategy.allocate(vehicle,this);
       if (slot == null) return null;//or throw Runtime exception
       slot.park(vehicle);
       return new Ticket(UUID.randomUUID().toString(),vehicle,slot);

   }
   public double exitVehicle(Ticket ticket){
       ParkingSlot slot = ticket.getParkingSlot();
       slot.remove();
       return pricingStrategy.calculatePrice(ticket);

   }
    public List<ParkingFloor> getParkingFloors() {
        return parkingFloors;
    }
}