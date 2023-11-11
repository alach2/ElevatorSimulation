import java.util.Properties;
import java.util.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.PriorityQueue;
import java.util.ArrayList;

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
    public int passengerProbability;
    
    public Floor(int total, String type, int passProb){
        totalFloors = total;
        passengerProbability = passProb;
        goingUpPassenger = List.createPassengerQueue(type);
        goingDownPassenger = List.createPassengerQueue(type);
    }
    public void tick(Elevator elevator){
        loadingPassengers(elevator);

        if (elevator.isGoingUp() && elevator.getCurrentFloor() < totalFloors) {
            int travelingFloors = Math.min(5, totalFloors - elevator.getCurrentFloor());
            elevator.travelCertainFloors(travelingFloors);
        } else if (elevator.isGoingDown() && elevator.getCurrentFloor() > 1){
            int travelingFloors = Math.min(5, elevator.getCurrentFloor() - 1);
            elevator.travelCertainFloors(travelingFloors);
        }
        newPassenger();
    }

    // method to unload and load passengers   
    private void loadingPassengers(Elevator elevator){
        if(elevator.isGoingUp() && elevator.getCurrentFloor() <= totalFloors){
            List<Passenger> passengerBoarding = new ArrayList<>();
            for(Passenger passenger : goingUpPassenger){
                if(passenger.getDestination() == elevator.getCurrentFloor()){
                    passengerBoarding.add(passenger);
                }
            }

            for(Passenger passenger : passengerBoarding){
                goingUpPassenger.remover(passenger);
                elevator.boardPassenger(passenger);
                System.out.println(passenger.getName() + " just boarded the elevator on floor " + elevator.getCurrentFloor());
            }

        } else if (elevator.isGoingDown() && elevator.getCurrentFloor() >= 1){
            List<Passenger> passengerBoarding = new ArrayList<>();
            for(Passenger passenger : goingDownPassenger){
                if(passenger.getDestination() == elevator.getCurrentFloor()){
                    passengerBoarding.add(passenger);
                }
            }

            for(Passenger passenger : passengerBoarding){
                goingDownPassenger.remove(passenger);
                elevator.boardPassenger(passenger);
                System.out.println(passenger.getName() + " just boarded the elevator on floor " + elevator.getCurrentFloor());
            }

        }
    }

    private void newPassenger(){
        if(random.nextDouble() < passengerProbability){
            int destination = random.nextInt(totalFloors) + 1;
            int passengerNumber = random.nextInt(100);
            String passengerName = "Passenger" + passengerNumber;
            Passenger newPassenger = new Passenger(passengerName, destination, 0);

            if(destination > goingUpPassenger.peekLast().getDestination()){
                goingUpPassenger.add(newPassenger);
                System.out.println(newPassenger.getName() + " is waiting to go to " + destination);
            } else {
                goingDownPassenger.add(newPassenger);
                System.out.println(newPassenger.getName() + " is waiting to go down to " + destination);
            }
        }

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
        if (isGoingUp() == true ) {
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

    public int getCurrentFloor(){
        return currFloor;
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
