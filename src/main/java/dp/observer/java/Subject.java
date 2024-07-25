package dp.observer.java;

import java.util.Observable;

public class Subject extends Observable {
    public void setMsg(String msg) {
        setChanged();
        notifyObservers(msg);
    }
}
