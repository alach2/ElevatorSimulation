import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Random;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Comparator;
import java.util.List;
import java.util.Queue;

class Floor {
    private int floorNum;
    private Queue<Passenger> waitingPassengers;

    public Floor(int floorNum){
        this.floorNum = floorNum;
        this.waitingPassengers = new LinkedList<>();
    }
    public int getFloorNum(){
        return floorNum;
    }
    public Queue<Passenger> getWaitingPassengers(){
        return waitingPassengers;
    }

    public void addPassenger(Passenger passenger){
        waitingPassengers.offer(passenger);
        System.out.println("New passenger on floor " + floorNum + " going to floor " + passenger.getDestination());
    }

    public void removePassenger(Passenger passenger){
        waitingPassengers.remove(passenger);
    }

}