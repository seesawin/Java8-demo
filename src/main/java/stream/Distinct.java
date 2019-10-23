package stream;

import entity.Fruits;
import util.DataBuilder;

import java.util.List;
import java.util.stream.Collectors;

public class Distinct {
    public static void main(String[] args) {
        testDistinct();
    }

    public static void testDistinct() {
        System.out.println("testDistinct start...");
        List<Fruits> fruitBeans = DataBuilder.getFruitBeans();

        List<String> collect = fruitBeans.stream()
                .map(fruit -> fruit.getName())
                .distinct().collect(Collectors.toList());
        System.out.println("distinct name: " + collect);
        System.out.println("---------------------------------------");

        System.out.println("testSort end...");
    }
}
