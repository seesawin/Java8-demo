package stream;

import entity.Fruits;
import util.DataBuilder;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Sort {
    public static void main(String[] args) {
        testSort();
    }

    public static void testSort() {
        System.out.println("testSort start...");
        List<Fruits> fruitBeans = DataBuilder.getFruitBeans();

        /*
        二次排序
        * */
        final List<Fruits> collect = fruitBeans.stream()
                .sorted(Comparator.comparing(Fruits::getPrice) //ASC
                        .thenComparing(Fruits::getName)
                        .reversed()) // Descending
                .collect(Collectors.toList());
        System.out.println(collect);
        System.out.println("---------------------------------------");

        System.out.println("testSort end...");
    }
}
