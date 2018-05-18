package Utils;

import java.util.ArrayList;
import java.util.List;

public interface Observable {
    List<Observer> observers = new ArrayList<>();
    void addObserver(Observer obs);
    void removeObserver(Observer obs);
    void notifyObservers();
}