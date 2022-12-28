package functional;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.ObjDoubleConsumer;

public class CustomerTest {

    public static void main(String[] args) {
        base();
        doubleTest();
    }

    public static void base() {
        Consumer<String> action1 = x -> System.out.println(x);
        Consumer<String> action2 = x -> System.out.println(x + "bbb");
        action1.andThen(action2).accept("aaaa");
        System.out.println("-----");

        BiConsumer<String, String> biAction1 = (x, y) -> System.out.println(x + "_" + y);
        BiConsumer<String, String> biAction2 = (x, y) -> System.out.println(x + "=" + y);
        biAction1.andThen(biAction2).accept("a", "b");
        System.out.println("base end...");
    }

    public static void doubleTest() {
        DoubleConsumer dc1 = x -> System.out.println(x);
        dc1.accept(1.2);
        System.out.println("-----");

        ObjDoubleConsumer odc1 = (x, y) -> System.out.println(new StringBuilder().append((String) x).append(y).toString());
        odc1.accept("1", 2.1);
        System.out.println("doubleTest end...");
    }

}
