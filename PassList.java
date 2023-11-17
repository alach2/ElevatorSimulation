import java.util.PriorityQueue;
import java.util.Queue;
import java.util.ArrayList;
import java.util.Random;
import java.util.Deque;
import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.List;

class PassList {
    private Elevator elevator;
    private List<Floor> floors;
    private static Queue<Passenger> mainPassengers;

    public PassList(int numFloors){
        elevator = new Elevator();
        floors = new ArrayList<>();
        mainPassengers = new LinkedList<>();

        for(int i = 1; i <= numFloors; i++){
        floors.add(new Floor(i));
        }
    }

    public void requestElevator(int sourceFloor, int destFloor){
        Floor source = floors.get(sourceFloor - 1);
        Floor dest = floors.get(destFloor - 1);
        Passenger pass = new Passenger(sourceFloor, destFloor);
        mainPassengers.offer(pass);

        if(elevator.getDirection().equals("NONE")){
            if(sourceFloor > elevator.getCurrentFloor()){
                elevator.setDirection("UP");
            }else{
                elevator.setDirection("DOWN");
            }
        }
    }
    public void processNextRequest(){
        while(!mainPassengers.isEmpty()){
            Passenger passenger = mainPassengers.poll();
            int dest = passenger.getDestination();

            if(dest > elevator.getCurrentFloor()){
                elevator.moveToFloor(dest);
            } else if(dest < elevator.getCurrentFloor()){
                elevator.moveToFloor(dest);
            }
        }
    }

    public static void tick(Elevator elevator, List<Floor> floors, int duration, int numFloors, double passengerProbability){
        Random random = new Random();
        for(int tick = 0; tick < duration; tick++){
            List<Passenger> unloadedPassengers = elevator.unloadingPassengers();
            for(Passenger passenger : unloadedPassengers){
            System.out.println("Passenger arrived at " + elevator.getCurrentFloor());
            }
        
            for(Floor floor : floors){
                if(random.nextDouble() < passengerProbability){
                    floor.generateRandomPassneger(numFloors);
                }
                List<Passenger> releasedPassengers = floor.releasePassengers(elevator.getCurrentFloor());
                for(Passenger passenger : releasedPassengers){
                    System.out.println("Passenger released at floor " + elevator.getCurrentFloor());
                }
            }
            
            if(elevator.hasRequests()){
                int nextFloor = elevator.getNextRequestFloor();
                System.out.println("Elevator is going " + elevator.getDirection() + ". Passenger is waiting on floor " + elevator.getCurrentFloor() + " going " + elevator.getDirection() + " to floor " + nextFloor);
                elevator.moveToFloor(nextFloor);
            }else{
                if(!floors.isEmpty() && !floors.get(0).getWaitingPassengers().isEmpty()){
                    Passenger nextPassenger = floors.get(0).getWaitingPassengers().peek();
                    int dest = nextPassenger.getDestination();
                    if(dest > elevator.getCurrentFloor()){
                        elevator.setDirection("UP");
                    }else if(dest <elevator.getCurrentFloor()){
                        elevator.setDirection("UP");
                    }
                }else{
                    elevator.setDirection("NONE");
                }
            }

            System.out.println("---------- Tick " + (tick + 1) + " ----------");
        }
    }
        }
    




