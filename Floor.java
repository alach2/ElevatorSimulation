import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Random;
import java.util.Deque; 
import java.util.Comparator;
import java.util.List;

class Floor {

    private static Random random = new Random();
    public Deque<Passenger> goingUpPassenger;
    public Deque<Passenger> goingDownPassenger;
    private int totalFloors;
    public double passengerProbability;
    
    public Floor(int total, String type, double passProb){
        totalFloors = total;
        passengerProbability = passProb;
        goingUpPassenger = PassList.createPassengerQueue(type);
        goingDownPassenger = PassList.createPassengerQueue(type);
    }
    public void tick(Elevator elevator){
        System.out.println("yahoo");
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
                goingUpPassenger.remove(passenger);
                elevator.board(passenger);
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
                elevator.board(passenger);
                System.out.println(passenger.getName() + " just boarded the elevator on floor " + elevator.getCurrentFloor());
            }

        }
    }

    private void newPassenger(){
        if(random.nextDouble() < passengerProbability){
            Passenger lastPass = goingUpPassenger.peekLast();
            if(lastPass != null){
                int dest = lastPass.getDestination();
                int newDestination = random.nextInt(totalFloors) + 1;   
                int passengerNumber = random.nextInt(100);
                String passengerName = "Passenger" + passengerNumber;
                Passenger newPassenger = new Passenger(passengerName, newDestination, 0);
            
            if (newDestination > dest){
                goingUpPassenger.add(newPassenger);
                System.out.println(newPassenger.getName() + " is waiting to go to " + newDestination);
            } else {
                goingDownPassenger.add(newPassenger);
                System.out.println(newPassenger.getName() + " is waiting to go down to " + newDestination);
            }
            System.out.println("This passenger is going to " + newPassenger.getDestination());
            }

        }
        
    }

}