import java.util.ArrayList;
import java.util.Random;
import java.util.Deque;
import java.util.Queue;
import java.util.LinkedList;
import java.util.List;

class Elevator {
    private int currentFloor;
    public int elevatorCapacity;
    private String direction;
    List<Passenger> passengers;

    public Elevator(int elevatorCap){
        elevatorCapacity = elevatorCap;
        currentFloor = 1;
        direction = "NONE";
        this.passengers = new ArrayList<>();
    }

    public int getCurrentFloor(){
        return currentFloor;
    }

    public String getDirection(){
        return direction;
    }

    public List<Passenger> getPassengers(){
        return passengers;
    }

    //If the direction needs to be changed
    public void setDirection(String type){
        if (type.equals("UP")) {
            direction = "UP";
        } else if (type.equals("DOWN")) {
            direction = "DOWN";
        }
    }
    //Add passengers to the queue as they board
    public void boardPassengers(Passenger passenger){
        passengers.add(passenger);
        System.out.println("Passenger boarded at " + currentFloor);
    }

    //Remove the passengers on the list who reached their destination
    public void releasingPassengers(){
        List<Passenger> released = new ArrayList<>();
        for (Passenger passenger : passengers) {
            if (passenger.getDestination() == currentFloor) {
                released.add(passenger);
            }
        }
        for (Passenger passenger : released) {
            passengers.remove(passenger);
            passenger.setDestinationTime(); //This saves their total wait times
            System.out.println("Passenger released at floor " + currentFloor);
        }
    }

    //If the elevator needs to move to a specific floor
    public void moveToFloor(int destination){
        if (destination > currentFloor) {
            System.out.println("Moving up to floor " + destination);
        } else if (destination < currentFloor) {
            System.out.println("Moving down to floor " + destination);
        } else {
            System.out.println("Elevator is already on floor " + currentFloor);
        }
        currentFloor = destination;
        System.out.println("Elevator is now on floor " + currentFloor);
    }

    //Move up one floor at a time
    public void moveUp(){
        System.out.println("Elevator is going up. It is currently on floor " + currentFloor);
        currentFloor++;
    }
    //Move down one floor at a time 
    public void moveDown(){
        if (currentFloor > 1) {
        System.out.println("Elevator is going down. It is currently on floor " + currentFloor);
        currentFloor--;
        } else {
            System.out.println("Elevator is already on first floor");
        }
    }
}