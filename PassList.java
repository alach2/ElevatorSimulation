import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Random;
import java.util.Deque;
import java.util.ArrayDeque;
import java.util.LinkedList;

class PassList {
    public static Deque<Passenger> createPassengerQueue(String type) {
        if(type == "linked"){
            return new LinkedList<>();
        }else{
            return new ArrayDeque<>();
        }
    }
}
