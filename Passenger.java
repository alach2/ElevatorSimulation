class Passenger {
    
    private int currentFloor;
    private int destination;
    private long arrivalTime;
    private long conveyanceTime;

     public Passenger(int currentFloor, int destination){
        this.currentFloor = currentFloor;
        this.destination = destination;
        this.arrivalTime = System.currentTimeMillis();
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
     public long getConveyanceTime(){
        return conveyanceTime;
     }
     public void setConveyanceTime(){
        this.conveyanceTime = System.currentTimeMillis();
     }
}
