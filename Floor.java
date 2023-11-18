import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.LinkedList;
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

    //Methods to remove and add passengers to the waitingPassenger queue
    public void addPassenger(Passenger passenger){
        waitingPassengers.offer(passenger);
    }

    public void removePassenger(Passenger passenger){
        waitingPassengers.remove(passenger);
    }

}