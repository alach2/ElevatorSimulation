import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

class Main {
    public static void main(String[] args) {
        //Read in values from a property file to use in the rest of my code
        Properties propertyFile = new Properties();
        if (args.length > 0) {
            String file = args[0]; //set it as the first agrument
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
        //Instantiate the property file keys and values
        String structureType = propertyFile.getProperty("structures");
        int numFloors = Integer.parseInt(propertyFile.getProperty("floors"));
        double passengerProbability = Double.parseDouble(propertyFile.getProperty("passengers"));
        int elevatorCap = Integer.parseInt(propertyFile.getProperty("elevatorCapacity"));
        int simDuration = Integer.parseInt(propertyFile.getProperty("duration"));
        
        //Create an elevator, arraylist of floors, and a deque of all the passengers based on a structure type
        Elevator elevator = new Elevator(elevatorCap);
        List<Floor> floors = new ArrayList<>();
        Deque<Passenger> allPassengers = PassList.createPassengerQueue(structureType);
        Random random = new Random();

        //Initialize all the floors till the total
        for (int i = 1; i <= numFloors; i++) {
            floors.add(new Floor(i));
        }
        
        //For each tick, the elevator releases passengers, moves to the next closest floor, boards/releases passengers
        for (int tick = 0; tick < simDuration; tick++) {

            //For some floors, I create new passengers that are waiting
            for (Floor floor : floors) {
                if (random.nextDouble() < passengerProbability) {
                    if (elevator.getCurrentFloor() < numFloors) {
                        int newDestination = Math.min(5, numFloors - elevator.getCurrentFloor());
                        Passenger newPassenger = new Passenger(floor.getFloorNum(), newDestination);
                        floor.addPassenger(newPassenger);
                        allPassengers.add(newPassenger);
                        System.out.println("New passenger on floor " + floor.getFloorNum() + " going to floor " + newDestination);
                    } else if ( elevator.getCurrentFloor() > 1) {
                        int newDestination = Math.min(5, elevator.getCurrentFloor() - 1);
                        Passenger newPassenger = new Passenger(floor.getFloorNum(), newDestination);
                        floor.addPassenger(newPassenger);
                        allPassengers.add(newPassenger);
                        System.out.println("New passenger on floor " + floor.getFloorNum() + " going to floor " + newDestination);
                    }
                }
            }
            elevator.releasingPassengers(); //if there are passengers to be released on the current floor

            //Go to the closest floor then board/release and move further
            int closestFloor = findClosestFloorWithPassengers(elevator, floors);
            System.out.println("Closest floor with passengers: " + closestFloor);
            if (elevator.getCurrentFloor() != closestFloor) {
                moveElevator(elevator, closestFloor, floors);
            
                boardingPassengers(elevator, floors.get(elevator.getCurrentFloor() - 1));
                elevator.releasingPassengers();

                moveElevator(elevator, elevator.getPassengers().get(0).getDestination(), floors);
                elevator.releasingPassengers();
         
            } else {
                System.out.println("Elevator is already on closest floor with passengers");
                Floor currentSimulationFloor = floors.get(closestFloor-1);
                Queue<Passenger> waitingPassenger = currentSimulationFloor.getWaitingPassengers();

                //Checks if there are any passengers waiting on the closest floor
                if (!waitingPassenger.isEmpty()) {
                    for (Passenger passenger : waitingPassenger) {
                        elevator.boardPassengers(passenger);
                        moveElevator(elevator, closestFloor, floors);
                        currentSimulationFloor.removePassenger(passenger);
                    }
                }
            }
        }   
        simulationStatistic(allPassengers); //Prints the simulation times
    }

    private static int findClosestFloorWithPassengers(Elevator elevator, List<Floor> floors){
        int closestFloor = -1;
        int minDistance = Integer.MAX_VALUE;
        for (Floor floor : floors) {
            if (!floor.getWaitingPassengers().isEmpty()) {
                //Finds the distance of each floor to the current floor to find the closest one with passengers
                int distance = Math.abs(floor.getFloorNum() - elevator.getCurrentFloor());
                if (distance < minDistance) {
                    minDistance = distance;
                    closestFloor = floor.getFloorNum();
                }
            }
        }
        //This means it did not find any closestFloor
        if (closestFloor == -1) {
            closestFloor = elevator.getCurrentFloor();
        }
        return closestFloor;
    }

    //This method moves the elevator towards the desired floor, stoppig to pick up passengers along the way
    private static void moveElevator(Elevator elevator, int destinationFloor, List<Floor> floors){
        if (elevator.getCurrentFloor() < destinationFloor) {
            while (elevator.getCurrentFloor() < destinationFloor) {
                int currentFloor = elevator.getCurrentFloor();
                elevator.moveUp();

                if(currentFloor >= 1 && currentFloor <= floors.size()){
                    Floor floorObj = floors.get(currentFloor-1);
                    if (!floorObj.getWaitingPassengers().isEmpty()) {
                        boardingPassengers(elevator, floorObj);
                        System.out.println("Passenger was picked up on floor " + currentFloor);
                    }
                }
            }
            
        } else if (elevator.getCurrentFloor() > destinationFloor) {
            while (elevator.getCurrentFloor() > destinationFloor) {
                int currentFloor = elevator.getCurrentFloor();
                elevator.moveDown();
            
                if (currentFloor > 0){
                    Floor floorObj = floors.get(currentFloor-1);
                    if (!floorObj.getWaitingPassengers().isEmpty()) {
                        boardingPassengers(elevator, floorObj);
                        System.out.println("Passenger was picked up on floor " + currentFloor);
                    }
                }
            }
        }
    }      

    //Passengers are boarded if the waitingPassengers queue is not empty on a floor
    private static void boardingPassengers(Elevator elevator, Floor floor){
        while (!floor.getWaitingPassengers().isEmpty()) {
            Passenger passenger = floor.getWaitingPassengers().poll();
            elevator.boardPassengers(passenger);
        }
    }

    //Finds the longest, avg, and shortest times of each passenger to their prefered floor
    public static void simulationStatistic(Deque<Passenger> passengers){
        long totalDestinationTime = 0;
        long longestTime = Long.MIN_VALUE;
        long shortestTime = Long.MAX_VALUE;

        //For every passenger, the total time traveled until destination is calculated and stored as either longest or shortest
        for (Passenger passenger : passengers) {
            long destinationTime = passenger.getDestinationTime() - passenger.getArrivalTime();
            totalDestinationTime += destinationTime;

            if (destinationTime > longestTime) {
                longestTime = destinationTime;
            }

            if (destinationTime < shortestTime && destinationTime >= 0) {
                shortestTime = destinationTime;
            }
        }
        //Prints the average when the total is a positive number to ensure it makes sense
        int totalPassengers = passengers.size();
        if (totalDestinationTime >= 0) {
            long averageTime = totalDestinationTime / totalPassengers;
            System.out.println("Average Time: " + averageTime + " milliseconds");
        }
        //Prints shortest and longest wait times
        System.out.println("Longest Time: " + longestTime + " milliseconds");
        System.out.println("Shortest Time: " + shortestTime + " milliseconds");
    }    
}

