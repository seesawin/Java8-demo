package dp.observer.java;

public class Demo {
    public static void main(String[] args) {
        Viewer viewer1 = new Viewer();
        Viewer viewer2 = new Viewer();
        Subject subject = new Subject();
        subject.addObserver(viewer1);
        subject.addObserver(viewer2);
        subject.setMsg("msg change");
    }
}
