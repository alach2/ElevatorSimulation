import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;
import java.util.*;

class Main {
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

        String structureType = propertyFile.getProperty("structures");
        int numFloors = Integer.parseInt(propertyFile.getProperty("floors"));
        double passengerProbability = Double.parseDouble(propertyFile.getProperty("passengers"));
        int elevatorCap = Integer.parseInt(propertyFile.getProperty("elevatorCapacity"));
        int simDuration = Integer.parseInt(propertyFile.getProperty("duration"));
        
        Elevator elevator = new Elevator(elevatorCap);
        List<Floor> floors = new ArrayList<>();
        List<Passenger> allPassengers = new ArrayList<>();
        Random random = new Random();

        for (int i = 1; i <= numFloors; i++) {
            floors.add(new Floor(i));
        }
        
        for(int tick = 0; tick < simDuration; tick++){
            elevator.releasingPassengers();

            int closestFloor = findClosestFloorWithPassengers(elevator, floors);
            System.out.println("Closest floor with passengers: " + closestFloor);
            if(elevator.getCurrentFloor() != closestFloor){
            moveElevator(elevator, closestFloor, floors);
            
            boardingPassengers(elevator, floors.get(elevator.getCurrentFloor() - 1));
            elevator.releasingPassengers();

            moveElevator(elevator, elevator.getPassengers().get(0).getDestination(), floors);
            elevator.releasingPassengers();
         
            }else{
                System.out.println("Elevator is already on closest floor with passengers");
                Floor currentSimulationFloor = floors.get(closestFloor-1);
                Queue<Passenger> waitingPassenger = currentSimulationFloor.getWaitingPassengers();

                if(!waitingPassenger.isEmpty()){
                    for(Passenger passenger : waitingPassenger){
                        System.out.println("Passenger boarded at floor " + closestFloor);
                        elevator.boardPassengers(passenger);
                        currentSimulationFloor.removePassenger(passenger);
                    }
                }
            }

         for (Floor floor : floors) {
            if (random.nextDouble() < passengerProbability) {
                int newDestination = random.nextInt(numFloors) + 1;
                Passenger newPassenger = new Passenger(floor.getFloorNum(), newDestination);
                floor.addPassenger(newPassenger);
                allPassengers.add(newPassenger);
                System.out.println("New passenger on floor " + floor.getFloorNum() + " going to floor " + newDestination);
            }
        }
    }
    simulationStatistic(allPassengers);
    }

    private static int findClosestFloorWithPassengers(Elevator elevator, List<Floor> floors){
        int closestFloor = -1;
        int minDistance = Integer.MAX_VALUE;
        for(Floor floor : floors){
            if(!floor.getWaitingPassengers().isEmpty()){
                int distance = Math.abs(floor.getFloorNum() - elevator.getCurrentFloor());
                if(distance < minDistance){
                    minDistance = distance;
                    closestFloor = floor.getFloorNum();
                }
            }
        }
        if (closestFloor == -1) {
        closestFloor = elevator.getCurrentFloor();
        }

        return closestFloor;
    }

    private static void moveElevator(Elevator elevator, int destinationFloor, List<Floor> floors){
        if(elevator.getCurrentFloor() < destinationFloor){
            while(elevator.getCurrentFloor() < destinationFloor){
            int currentFloor = elevator.getCurrentFloor();
            elevator.moveUp();

            Floor floorObj = floors.get(currentFloor-1);
            if(!floorObj.getWaitingPassengers().isEmpty()){
            boardingPassengers(elevator, floorObj);
            System.out.println("Passenger was picked up on floor " + currentFloor);
            }
            }
            
        } else if(elevator.getCurrentFloor() > destinationFloor){
            while(elevator.getCurrentFloor() > destinationFloor){
            int currentFloor = elevator.getCurrentFloor();
            elevator.moveDown();
            
            Floor floorObj = floors.get(currentFloor-1);
            if(!floorObj.getWaitingPassengers().isEmpty()){
            boardingPassengers(elevator, floorObj);
            System.out.println("Passenger was picked up on floor " + currentFloor);
            }
            }
        }
    }   

    private static void boardingPassengers(Elevator elevator, Floor floor){
        while(!floor.getWaitingPassengers().isEmpty()){
            Passenger passenger = floor.getWaitingPassengers().poll();
            elevator.boardPassengers(passenger);
        }
    }

    public static void simulationStatistic(List<Passenger> passengers){
        long totalConveyanceTime = 0;
        long longestTime = Long.MIN_VALUE;
        long shortestTime = Long.MAX_VALUE;

        for(Passenger passenger : passengers){
            long conveyanceTime = passenger.getConveyanceTime() - passenger.getArrivalTime();
            totalConveyanceTime += conveyanceTime;

            if(conveyanceTime > longestTime){
                longestTime = conveyanceTime;
            }

            if(conveyanceTime < shortestTime){
                shortestTime = conveyanceTime;
            }
        }

        int totalPassengers = passengers.size();
        long averageTime = totalConveyanceTime / totalPassengers;

        System.out.println("Average Time: " + averageTime + " milliseconds");
        System.out.println("Longest Time: " + longestTime + " milliseconds");
        System.out.println("Shortest Time: " + shortestTime + " milliseconds");
    }    
}

