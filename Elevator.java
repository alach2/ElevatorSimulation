import java.util.Properties;
import java.util.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.PriorityQueue;

public class Passenger {
    private String name;
    private int destination;
    private int currFloor;

    public Passenger(String name, int destination, int currFloor){
        name = this.name;
        destination = this.destination;
        currFloor = this.currFloor;
    }   
    public void waiting(int newDestination){
        destination = newDestination;
    }

    public void boarding(){
        currFloor = 0;
    }

    public void arriving(){
        currFloor = destination;
        destination = 0;
    }

    public String getName(){
        return name;
    }

    public int getDestination(){
        return destination;
    }

    public int getCurrentFloor(){
        return currFloor;
    }


}

public class List {
    public static Deque<Passenger> createPassengerQueue(String type) {
        if(type == "linked"){
            return new LinkedList<>();
        }else{
            return new ArrayDeque<>();
        }
    }
}

public class Floor {

    public Deque<Passenger> goingUpPassenger;
    public Deque<Passenger> goingDownPassenger;
    private int totalFloors;
    
    public Floor(int total, String type){
        totalFloors = total;
        goingUpPassenger = List.createPassengerQueue(type);
        goingDownPassenger = List.createPassengerQueue(type);

    }
    public void tick(Elevator elevator){
        loadingPassengers(elevator);
    }

}
public class Elevator {
    private int currFloor;
    private int elevatorCapacity;
    public PriorityQueue<Passenger> passengers;

    public boolean goingUp;
    public boolean goingDown;

    public Elevator(int capacity){
        currFloor = 1;
        elevatorCapacity = capacity;
        goingUp = true;
        goingDown = false;
        passengers = new PriorityQueue<>(Comparator.comparingInt(Passenger::getDestination));
    }   

    public void move(){
        if (goingUp == true) {
            currFloor++;
        } else {
            currFloor--;
        }
    }

    public void travelCertainFloors(int floors){
        if (isGoingUp()) {
            currFloor += floors;
        } else if (isGoingDown()) {
            currFloor -= floors;
        }
    }

    public void boardPassenger(Passenger passenger){
        if (passengers.size() < elevatorCapacity) {
            passengers.add(passenger);
            System.out.println("Passenger " + passenger.getName() + " entered the elevator");
        } else {
            System.out.println("Sorry, the elevator is full");
        }
    }
    
    public void releasePassenger(){
        Passenger passenger = passengers.poll();
        if (passenger != null) {
            System.out.println("Passenger " + passenger.getName() + " left the elevator on floor " + currFloor);
        }
    }

    public void adjustDirection(){
        if (passengers.isEmpty()) {
            goingUp = true;
            goingDown = false;
        } else {
            Passenger topPassenger = passengers.peek();
            if (currFloor < topPassenger.getDestination()){
                goingUp = true;
            } else {
                goingDown = true;
            }
        }
    }

    public boolean isGoingUp(){
        if (!(passengers.isEmpty()) && currFloor < passengers.peek().getDestination()){
            return true;
        }
        return false;
    }

    public boolean isGoingDown(){
        if (!(passengers.isEmpty()) && currFloor > passengers.peek().getDestination()){
            return true;
        }
        return false;
    }
    
}

public class Main {
    public static void main(String[] args) {
        Properties propertyFile = new Properties();
        if (args.length > 0) {
            String file = args[0];
            try (FileInputStream input = new FileInputStream(file)) {
                propertyFile.load(input);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            propertyFile.setProperty("structures", "linked");
            propertyFile.setProperty("floors", "32");
            propertyFile.setProperty("passengers", "0.03");
            propertyFile.setProperty("elevators", "1"); 
            propertyFile.setProperty("elevatorCapacity", "10");
            propertyFile.setProperty("duration", "500");

        }
        String structureType = propertyFile.getProperty(structures);
        int numFloors = Integer.parseInt(propertyFile.getProperty(floors));
        int passengerProbabilty = Integer.parseInt(propertyFile.getProperty(passengers));
        int elevatorCap = Integer.parseInt(propertyFile.getProperty(elevatorCapacity));
        int simDuration = Integer.parseInt(propertyFile.getProperty(duration));

        Elevator elevator = new Elevator(elevatorCap);

        for (int tick = 0; tick < simDuration; tick++){

        }

        //print every step of the simulation

        //longest time between arrival and destination
        //shortest time between arrival and destination
        //average time between trips 
    }
}
