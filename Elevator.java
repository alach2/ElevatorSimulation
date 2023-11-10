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

    public static 
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

    //tick

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
        goingUp = null;

    }   

    public void move(){
        if(goingUp == true){
            currFloor++;
        }else{
            currFloor--;
        }
    }
    //boardPassenger

    //releasePassenger

    //adjustDirection
    
}

public class Main {
    public static void main(String[] args) {
        if (args.length > 0) {
            String file = args[0];
            try (FileInputStream input = new FileInputStream(file)) {
                Properties propertyFile = new Properties();
                propertyFile.load(input);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Properties defaultProperties = new Properties();
            defaultProperties.setProperty("structures", "linked");
            defaultProperties.setProperty("floors", "32");
            defaultProperties.setProperty("passengers", "0.03");
            defaultProperties.setProperty("elevators", "1"); 
            defaultProperties.setProperty("elevatorCapacity", "10");
            defaultProperties.setProperty("duration", "500");

        }

        long currentTime = 0;


        //print every step of the simulation

        //longest time between arrival and destination
        //shortest time between arrival and destination
        //average time between trips 
    }
}
