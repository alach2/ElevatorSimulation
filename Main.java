import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;
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
        double passengerProbabilty = Double.parseDouble(propertyFile.getProperty("passengers"));
        int elevatorCap = Integer.parseInt(propertyFile.getProperty("elevatorCapacity"));
        int simDuration = Integer.parseInt(propertyFile.getProperty("duration"));

        Elevator elevator = new Elevator(elevatorCap);
        Floor[] floors = new Floor[numFloors];
        for(int i = 0; i < numFloors; i++){
            floors[i] = new Floor(i+1, structureType, passengerProbabilty);
        }

        for (int tick = 0; tick < simDuration && elevator.getCurrentFloor() <= 15; tick++){
            for(Floor floor : floors){
                floor.tick(elevator);
            }
            System.out.println("Tick " + tick + " the elevator is on floor " + elevator.getCurrentFloor());

            elevator.move();

            elevator.boardReleasePassenger();

            elevator.adjustDirection();

        }

        //print every step of the simulation

        //longest time between arrival and destination
        //shortest time between arrival and destination
        //average time between trips 
    }
}
