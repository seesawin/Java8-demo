package functional;

import java.util.Random;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

public class SupplierTest {

    public static void main(String[] args) {
        base();
        doubleTest();
    }

    public static void base() {
        BooleanSupplier bs1 = () -> new Random().nextBoolean();
        System.out.println(bs1.getAsBoolean());
        System.out.println("-----");

        BooleanSupplier bs2 = () -> new Random().nextInt() > 0;
        System.out.println(bs2.getAsBoolean());
        System.out.println("base end...");
    }

    public static void doubleTest() {
        DoubleSupplier ds1 = () -> 123.1;
        System.out.println(ds1.getAsDouble());
        System.out.println("doubleTest end...");
    }

}