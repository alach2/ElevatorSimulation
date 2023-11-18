class Passenger {
    
    private int currentFloor;
    private int destination;
    private long arrivalTime;
    private long destinationTime;

     public Passenger(int currentFloor, int destination){
        this.currentFloor = currentFloor;
        this.destination = destination;
        this.arrivalTime = System.currentTimeMillis(); //tracks the arrival time of every passenger
     }

     public int getDestination(){
        return destination;
     }
     public int getCurrentFloor(){
        return currentFloor;
     }
     public long getArrivalTime(){
        return arrivalTime;
     }
     public long getDestinationTime(){
        return destinationTime;
     }
     public void setDestinationTime(){
        this.destinationTime = System.currentTimeMillis(); //tracks the total time till pass arrives on destination
     }
}
