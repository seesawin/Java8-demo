package functional;

import java.util.Comparator;
import java.util.function.BinaryOperator;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleFunction;
import java.util.function.DoubleToIntFunction;
import java.util.function.DoubleToLongFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;
import java.util.function.ToDoubleBiFunction;
import java.util.function.UnaryOperator;

public class FunctionTest {

    public static void main(String[] args) {
        base();
        doubleTest();
        testVoid();
    }

    public static void base() {
        Function<String, Integer> function1 = Integer::parseInt;
        Function<Integer, String> function2 = String::valueOf;
        Function<String, String> function3 = x -> x + " end";
        final var result = function1.andThen(function2).andThen(function3).apply("99999");
        System.out.println(result);
        System.out.println("-----");

        BinaryOperator<Integer> binaryOperator1 = (x, y) -> x - y;
        final var r1 = binaryOperator1.apply(1, 2);
        System.out.println(r1);
        System.out.println("-----");

        UnaryOperator<String> uo1 = x -> x + "ufo";
        System.out.println(uo1);
        System.out.println("-----");

        BinaryOperator<Integer> bi = BinaryOperator.minBy(Comparator.naturalOrder());
        System.out.println(bi.apply(2, 3));
        System.out.println("-----");

        BinaryOperator<Integer> bi2 = BinaryOperator.maxBy(Comparator.naturalOrder());
        System.out.println(bi2.apply(2, 3));
        System.out.println("base end...");
    }

    public static void doubleTest() {
        DoubleFunction<String> df1 = x -> String.valueOf(x * 100);
        System.out.println(df1.apply(2.2));
        System.out.println("-----");

        DoubleToIntFunction dti1 = x -> (int) x + 123;
        System.out.println(dti1.applyAsInt(3.14));
        System.out.println("-----");

        DoubleToLongFunction dti2 = x -> (long) x + 123;
        System.out.println(dti2.applyAsLong(111111));
        System.out.println("-----");

        DoubleUnaryOperator duo1 = x -> {
            System.out.println("duo1 x:" + x);
            return (x + 100) * 50 - 200;
        };
        System.out.println(duo1.applyAsDouble(0.12));
        System.out.println("-----");

        DoubleUnaryOperator duo2 = x -> {
            System.out.println("duo2 x:" + x);
            return x - x;
        };
        System.out.println(duo1.compose(duo2).applyAsDouble(0.12));
        System.out.println(duo1.andThen(duo2).applyAsDouble(1.111));
        System.out.println("-----");

        ToDoubleBiFunction<String, Integer> tdbf1 = (x, y) -> new Double(x) + new Double(y);
        System.out.println(tdbf1.applyAsDouble("1111", 2222));
        System.out.println("-----");

        DoubleBinaryOperator dbo1 = (x, y) -> x + y;
        System.out.println(dbo1.applyAsDouble(1.1, 2.2));
        System.out.println("doubleTest end...");
    }

    private static void testVoid() {
        Function<Void, String> sf = (x) -> "123";
        Function<String, String> sf2 = x -> x + "_456";

        final var result = sf.andThen(sf2).apply(null);
        System.out.println(result);
    }

}
