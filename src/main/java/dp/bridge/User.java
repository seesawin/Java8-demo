package dp.bridge;

public class User {
    public static void main(String[] args) {
        Pen penOne = new PenOne();
        penOne.draw(new Blue());
        penOne.draw(new Red());

        Pen penTwo = new PenTwo();
        penTwo.draw(new Blue());
        penTwo.draw(new Red());
    }
}
