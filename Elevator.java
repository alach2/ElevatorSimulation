import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Random;
import java.util.Deque;
import java.util.Comparator;
import java.util.LinkedList;

class Elevator {
    private int currFloor;
    private int elevatorCapacity;
    public PriorityQueue<Passenger> passengers;
    public Deque<Passenger> goingUpPassenger;
    public Deque<Passenger> goingDownPassenger;

    public boolean goingUp;
    public boolean goingDown;

    public Elevator(int capacity){
        currFloor = 1;
        elevatorCapacity = capacity;
        goingUp = true;
        goingDown = false;
        this.goingUpPassenger = new LinkedList<>();
        this.goingDownPassenger = new LinkedList<>();
        this.passengers = new PriorityQueue<>();
    }   

    public void move(){
        if (goingUp) {
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

    public void boardReleasePassenger(){
        releasePassenger();
        boardCurrFloor();
    }

    public void boardPassenger(Deque<Passenger> passengerEntering){
        while(passengers.size() < elevatorCapacity && !(passengerEntering.isEmpty())){
            Passenger pass = passengerEntering.poll();
            board(pass);
            System.out.println("Passenger " + pass.getName() + " entered the elevator");
        }
    }
    public void board(Passenger passenger){
        passengers.add(passenger);
    }

    public void releasePassenger(){
        Passenger passenger = passengers.poll();
        if (passenger != null) {
            System.out.println("Passenger " + passenger.getName() + " left the elevator on floor " + currFloor);
        }
    }

    public void boardCurrFloor(){
        if(goingUp){
            boardPassenger(goingUpPassenger);
        }else{
            boardPassenger(goingDownPassenger);
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
                goingDown = false;
            } else {
                goingDown = true;
                goingUp = false;
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

