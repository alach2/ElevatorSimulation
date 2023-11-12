class Passenger {
    private String name;
    private int destination;
    private int currFloor;

    public Passenger(String name, int destination, int currFloor){
        name = this.name;
        destination = this.destination;
        currFloor = this.currFloor;
    }   
    public void waiting(int newDestination){
        destination = newDestination;
    }

    public void boarding(){
        currFloor = 0;
    }

    public void arriving(){
        currFloor = destination;
        destination = 0;
    }

    public String getName(){
        return name;
    }

    public int getDestination(){
        return destination;
    }

    public int getCurrentFloor(){
        return currFloor;
    }


}
