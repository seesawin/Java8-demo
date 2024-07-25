package dp.observer.java;


import java.util.Observable;
import java.util.Observer;

public class Viewer implements Observer {
    @Override
    public void update(Observable o, Object arg) {
        System.out.println(Viewer.class.getName() + " : " + arg);
    }
}
