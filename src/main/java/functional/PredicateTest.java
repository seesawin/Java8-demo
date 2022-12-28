package functional;

import java.util.function.BiPredicate;
import java.util.function.DoublePredicate;
import java.util.function.Predicate;

public class PredicateTest {

    public static void main(String[] args) {
        base();
        doubleTest();
    }

    public static void base() {
        Predicate<Integer> p1 = x -> x > 10;
        System.out.println(p1.test(1));
        System.out.println(p1.negate().test(-20));
        System.out.println("-----");

        Predicate<Integer> p2 = x -> x > 5;
        System.out.println(p1.or(p2).test(6));
        System.out.println("-----");

        Predicate<Integer> p3 = x -> x > 11;
        System.out.println(p1.and(p3).test(12));
        System.out.println("-----");

        Predicate<Integer> p4 = Predicate.isEqual(90000);
        System.out.println(p4.test(90000));
        System.out.println("-----");

        Predicate<Integer> p5 = Predicate.not(p1);
        System.out.println(p5.test(9));
        System.out.println("-----");

        BiPredicate<Integer, Integer> bip1 = (x, y) -> x + y > 0;
        BiPredicate<Integer, Integer> bip2 = (x, y) -> x + y > 4;
        System.out.println(bip1.test(1, 1));
        System.out.println(bip1.and(bip2).test(1, 2));
        System.out.println("-----");

        BiPredicate<Integer, Integer> bip3 = (x, y) -> x + y < 0;
        System.out.println(bip1.or(bip3).test(1, 2));
        System.out.println("-----");

        System.out.println(bip3.negate().test(1, -2));
        System.out.println("base end...");
    }

    public static void doubleTest() {
        DoublePredicate dp1 = x -> x * 100 < 0;
        System.out.println(dp1.test(2.2));
        System.out.println("doubleTest end...");
    }

}
