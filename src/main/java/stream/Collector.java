package stream;

import entity.Dish;
import entity.Type;
import util.DataBuilder;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Collector {
    public static void main(String[] args) {
        testCollector();
    }

    /*
     * 連線收集器
     * */
    public static void testCollector() {
        System.out.println("testCollector start...");
        List<Dish> dishes = DataBuilder.getDishs();

        //join directly
        String join1 = dishes.stream().map(Dish::getName).collect(Collectors.joining());
        System.out.println("join directly: " + join1);

        //join by comma
        String join2 = dishes.stream().map(Dish::getName).collect(Collectors.joining(", "));
        System.out.println("join by comma: " + join2);

        //toList
        List<String> names = dishes.stream().map(Dish::getName).collect(Collectors.toList());
        System.out.println("toList: " + names);

        //toSet
        Set<Type> types = dishes.stream().map(Dish::getType).collect(Collectors.toSet());
        System.out.println("toSet: " + types);

        //toMap, Exception in thread "main" java.lang.IllegalStateException: Duplicate key Dish{name='pork', isExists=false, calories=800, type=MEAT}
        try {
            Map<Type, Dish> byType = dishes.stream().collect(Collectors.toMap(Dish::getType, Function.identity()));
            /*
            有時候可能需要將一個陣列轉為map，做快取，方便多次計算獲取。
            toMap提供的方法k和v的生成函式。(注意，上述demo是一個坑，不可以這樣用！！！, 請使用toMap(Function, Function, BinaryOperator))

            上面幾個幾乎是最常用的收集器了，也基本夠用了。但作為初學者來說，理解需要時間。想要真正明白為什麼這樣可以做到收集，
            就必須檢視內部實現，可以看到，這幾個收集器都是基於java.util.stream.Collectors.CollectorImpl，
            也就是開頭提到過了Collector的一個實現類。後面自定義收集器會學習具體用法。
            * */
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("testCollector end...");
    }
}