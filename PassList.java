import java.util.ArrayList;
import java.util.Deque;
import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.List;

class PassList {
    
    //This class just sets the passengers up with the correct structure type
    public static Deque<Passenger> createPassengerQueue(String type){
        if (type == "linked") {
            return new LinkedList<>();
        } else {
            return new ArrayDeque<>();
        }
    }
}
    
    




