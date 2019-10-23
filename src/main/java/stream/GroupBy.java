package stream;

import entity.*;
import util.DataBuilder;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GroupBy {
    public static void main(String[] args) {
        testGroupBy();
        testGroupBy2();
    }

    /*
     * 分組
     * */
    public static void testGroupBy() {
        System.out.println("testGroupBy start...");
        List<Dish> dishes = DataBuilder.getDishs();

        /*
        資料庫中經常遇到分組求和的需求，提供了group by原語。在Java裡， 如果按照指令式風格(手動寫迴圈)的方式，
        將會非常繁瑣，容易出錯。而Java8則提供了函式式解法。
        比如，將dish按照type分組。和前面的toMap類似，但分組的value卻不是一個dish，而是一個List。
        * */
        //分組
        Map<Type, List<Dish>> groupingBy = dishes.stream().collect(Collectors.groupingBy(Dish::getType));
        System.out.println("group by:" + groupingBy);

        /*
        public static <T, K> Collector<T, ?, Map<K, List<T>>>
        groupingBy(Function<? super T, ? extends K> classifier)

        引數分類器為Function，旨在接收一個引數，轉換為另一個型別。
        上面的demo就是把stream的元素dish轉成型別Type，然後根據Type將stream分組。其內部是通過HashMap來實現分組的。
        groupingBy(classifier, HashMap::new, downstream);
        * */

        /*
        自定義分類器
        除了按照stream元素自身的屬性函式去分組，還可以自定義分組依據，比如根據熱量範圍分組。
        * */
        Map<CaloricLevel, List<Dish>> dishesByLevel = dishes.stream().collect(Collectors.groupingBy(dish -> DataBuilder.getCaloricLevel(dish)));
        System.out.println("group by definition: " + dishesByLevel);

        //多級分組
        /*
        public static <T, K, A, D>
                Collector<T, ?, Map<K, D>> groupingBy(Function<? super T, ? extends K> classifier,
                Collector<? super T, A, D> downstream) {
            return groupingBy(classifier, HashMap::new, downstream);
        }

        1.classifier還是分類器，就是接收stream的元素型別，返回一個你想要分組的依據，也就是提供分組依據的基數的
        所以T表示stream當前的元素型別，K表示分組依據的元素型別
        2.第二個引數downstream，下游是一個收集器Collector. 這個收集器元素型別是T的子類，容器型別container為A，reduction返回值型別為D。
        3.也就是說分組的K通過分類器提供，分組的value則通過第二個引數的收集器reduce出來

        public static <T, K> Collector<T, ?, Map<K, List<T>>>
            groupingBy(Function<? super T, ? extends K> classifier) {
            return groupingBy(classifier, toList());
        }

        將toList當作reduce收集器，最終收集的結果是一個List<Dish>, 所以分組結束的value型別是List<Dish>。
        那麼，可以類推value型別取決於reduce收集器，而reduce收集器則有千千萬。比如，我想對value再次分組，分組也是一種reduce。
         */

        System.out.println("多級分組 start...");
        Map<Type, Map<CaloricLevel, List<Dish>>> byTypeAndCalory = dishes.stream().collect(
                Collectors.groupingBy(Dish::getType,
                        Collectors.groupingBy(dish -> DataBuilder.getCaloricLevel(dish))));
        byTypeAndCalory.forEach((type, byCalory) -> {
            System.out.println("----------------------------------");
            System.out.println(type);
            byCalory.forEach((level, dishList) -> {
                System.out.println(level);
                System.out.println("stream map1: " + dishList);
            });
        });

        byTypeAndCalory.entrySet().stream()
                .flatMap(e -> {
                    System.out.println("Type: " + e.getKey());
                    return e.getValue().entrySet().stream();
                })
                .flatMap(e -> {
                    System.out.println("CaloricLevel: " + e.getKey());
                    return e.getValue().stream();
                })
                .forEach((dishList) -> {
                    System.out.println("stream Dish: " + dishList);
                });

        // 無限分組
        Map<Type, Map<CaloricLevel, Map<String, Map<Integer, List<Dish>>>>> collect = dishes.stream().collect(
                Collectors.groupingBy(Dish::getType,
                        Collectors.groupingBy(dish -> DataBuilder.getCaloricLevel(dish),
                                Collectors.groupingBy(dish -> dish.getName(),
                                        Collectors.groupingBy(dish -> dish.getCalories())))));

        dishes.stream().collect(
                Collectors.groupingBy(Dish::getType,
                        Collectors.groupingBy(dish -> DataBuilder.getCaloricLevel(dish),
                                Collectors.groupingBy(dish -> dish.getName(),
                                        Collectors.groupingBy(dish -> dish.getCalories(), Collectors.counting())))));

        System.out.println("多級分組 end...");

        /*
        總結：groupingBy的核心引數為K生成器，V生成器。V生成器可以是任意型別的收集器Collector。
        比如，V生成器可以是計算數目的, 從而實現了sql語句中的select count(*) from table A group by Type
        * */
        Map<Type, Long> typesCount = dishes.stream().collect(Collectors.groupingBy(Dish::getType, Collectors.counting()));
        System.out.println("V生成器可以是計算數目的: " + typesCount);

        //sql查詢分組最高分select MAX(id) from table A group by Type
        Map<Type, Optional<Dish>> mostCaloricByType = dishes.stream()
                .collect(Collectors.groupingBy(Dish::getType,
                        Collectors.maxBy(Comparator.comparingInt(Dish::getCalories))));
        System.out.println("查詢分組最高分: " + mostCaloricByType);

        //這裡的Optional沒有意義，因為肯定不是null。那麼只好取出來了。使用collectingAndThen
        Map<Type, Dish> mostCaloricByType2 = dishes.stream()
                .collect(Collectors.groupingBy(Dish::getType,
                        Collectors.collectingAndThen(
                                Collectors.maxBy(Comparator.comparingInt(Dish::getCalories)), Optional::get)));
        System.out.println("使用collectingAndThen, Optional: " + mostCaloricByType2);

        Map<Type, Dish> mostCaloricByType3 = dishes.stream()
                .collect(Collectors.toMap(Dish::getType, Function.identity(),
                        BinaryOperator.maxBy(Comparator.comparingInt(Dish::getCalories))));// 處理衝突的函式(當key相同時取value較大者)
        System.out.println("toMap vs groupingBy: " + mostCaloricByType3);

        /*
        是的，groupingBy就變成toMap了，key還是Type，value還是Dish，但多了一個引數！！
        這裡迴應開頭的坑，開頭的toMap演示是為了容易理解，真那麼用則會被搞死。我們知道把一個List重組為Map必然會面臨k相同的問題。當K相同時，
        v是覆蓋還是不管呢？前面的demo的做法是當k存在時，再次插入k則直接丟擲異常：
        Map<Type, Dish> byType = dishes.stream().distinct().collect(Collectors.toMap(Dish::getType, Function.identity()));
        java.lang.IllegalStateException: Duplicate key Dish(name=pork, vegetarian=false, calories=800, type=MEAT)
        at java.util.stream.Collectors.lambda$throwingMerger$0(Collectors.java:133)

        正確的做法是提供處理衝突的函式，在本demo中，處理衝突的原則就是找出最大的，正好符合我們分組求最大的要求。
        (真的不想搞Java8函式式學習了，感覺到處都是效能問題的坑)
        * */

        // 繼續資料庫sql對映，分組求和select sum(score) from table a group by Type
        Map<Type, Integer> totalCaloriesByType = dishes.stream()
                .collect(Collectors.groupingBy(Dish::getType, Collectors.summingInt(Dish::getCalories)));
        System.out.println("group by and get sum: " + totalCaloriesByType);

        /*
        然而常常和groupingBy聯合使用的另一個收集器是mapping方法生成的。
        這個方法接收兩個引數：一個函式對流中的元素做變換，另一個則將變換的結果物件收集起來。
        其目的是在累加之前對每個輸入元素應用一個對映函式，這樣就可以讓接收特定型別元素的收集器適應不同型別的物件。
        我麼來看一個使用這個收集器的實際例子。比如你想得到，對於每種型別的Dish，選單中都有哪些CaloricLevel。
        我們可以把groupingBy和mapping收集器結合起來，如下所示:

        這裡的toSet預設採用的HashSet，也可以手動指定具體實現toCollection(HashSet::new)
        * */
        Map<Type, Set<CaloricLevel>> caloricLevelsByType = dishes.stream()
                .collect(Collectors.groupingBy(Dish::getType,
                        Collectors.mapping(dish -> DataBuilder.getCaloricLevel(dish),
                                Collectors.toSet())));
        System.out.println("group by and map to the specified container: " + caloricLevelsByType);

        /*
        分割槽是分組的特殊情況：由一個謂詞(返回一個布林值的函式)作為分類函式，
        它稱為分割槽函式。分割槽函式返回一個布林值，這意味著得到的分組Map的鍵型別是Boolean，於是它最多可以分為兩組：true or false.
        例如，如果你是素食者，你可能想要把選單按照素食和非素食分開：
        * */
        Map<Boolean, List<Dish>> partitionedMenu = dishes.stream().collect(Collectors.partitioningBy(Dish::isExists));
        System.out.println("partitioningBy: " + partitionedMenu);

        // 當然，使用filter可以達到同樣的效果：
        List<Dish> vegetarianDishes = dishes.stream().filter(Dish::isExists).collect(Collectors.toList());

        /*
        分割槽相對來說，優勢就是儲存了兩個副本，當你想要對一個list分類時挺有用的。
        同時，和groupingBy一樣，partitioningBy一樣有過載方法，可以指定分組value的型別。

        先partitioningBy再groupBy
        * */
        Map<Boolean, Map<Type, List<Dish>>> vegetarianDishesByType = dishes.stream()
                .collect(Collectors.partitioningBy(Dish::isExists,
                        Collectors.groupingBy(Dish::getType)));
        System.out.println("先partitioningBy再groupBy: " + vegetarianDishesByType);

        Map<Boolean, Integer> vegetarianDishesTotalCalories = dishes.stream()
                .collect(Collectors.partitioningBy(Dish::isExists,
                        Collectors.summingInt(Dish::getCalories)));
        System.out.println("先partitioningBy再summingInt: " + vegetarianDishesTotalCalories);

        Map<Boolean, Dish> mostCaloricPartitionedByVegetarian = dishes.stream()
                .collect(Collectors.partitioningBy(Dish::isExists,
                        Collectors.collectingAndThen(
                                Collectors.maxBy(Comparator.comparingInt(Dish::getCalories)), Optional::get)));
        System.out.println("先partitioningBy再收集再取想要的條件: " + mostCaloricPartitionedByVegetarian);

        /*
        作為使用partitioningBy收集器的最後一個例子，我們把選單資料模型放在一邊，
        來看一個更加複雜也更為有趣的例子：將陣列分為質數和非質數。
        首先，定義個質數分割槽函式：

        然後找出1到100的質數和非質數
        * */
        Map<Boolean, List<Integer>> partitionPrimes = IntStream.rangeClosed(2, 100).boxed()
                .collect(Collectors.partitioningBy(i -> DataBuilder.isPrime(i)));
        System.out.println("partitionPrimes: " + partitionPrimes);

        System.out.println("testGroupBy end...");
    }

    public static void testGroupBy2() {
        System.out.println("testGroupBy2 start...");
        List<String> fruits = DataBuilder.getFruits();
        /*
        1. Group By, Count and Sort
        1.1 Group by a List and display the total count of it.
        * */
        Map<String, Long> result = fruits.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        System.out.println("Group By, Count and Sort: " + result);
        System.out.println("---------------------------------------");

        //Sort a map and add to finalMap
        Map<String, Long> finalMap = new LinkedHashMap<>();
        result.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .forEachOrdered(e -> finalMap.put(e.getKey(), e.getValue()));
        System.out.println("Sort a map and add to finalMap: " + finalMap);
        System.out.println("---------------------------------------");

        /*
        2.2 Group by the name + Count or Sum the Qty.
        * */
        List<Fruits> fruitBeans = DataBuilder.getFruitBeans();
        Map<String, Long> counting = fruitBeans.stream().collect(Collectors.groupingBy(Fruits::getName,
                Collectors.counting()));
        System.out.println("Group by the name + Count or Sum the Qty: " + counting);
        System.out.println("---------------------------------------");

        Map<String, Integer> sum = fruitBeans.stream().collect(Collectors.groupingBy(Fruits::getName,
                Collectors.summingInt(Fruits::getQty)));
        System.out.println(sum);
        System.out.println("---------------------------------------");

        /*
        2.3 Group by Price – Collectors.groupingBy and Collectors.mapping example.
        * */
        Map<BigDecimal, List<Fruits>> groupByPriceMap = fruitBeans.stream().collect(Collectors.groupingBy(Fruits::getPrice));
        System.out.println("Group by Price: " + groupByPriceMap);
        System.out.println("---------------------------------------");

        /*
        2.3.1 Having
        * */
        Map<BigDecimal, List<Fruits>> havingPriceMap = fruitBeans.stream()
                .collect(Collectors.groupingBy(Fruits::getPrice))
                .entrySet().stream()
                .filter(entry -> entry.getValue().size() > 3)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        System.out.println("Having: " + havingPriceMap);
        System.out.println("---------------------------------------");

        /*
        2.4 group by price, uses 'mapping' to convert List<Item> to Set<String>
        * */
        Map<BigDecimal, Set<String>> result2 = fruitBeans.stream().collect(Collectors.groupingBy(Fruits::getPrice,
                Collectors.mapping(Fruits::getName,
                        Collectors.toSet())));
        System.out.println("group by price, uses 'mapping: " + result2);
        System.out.println("---------------------------------------");

        /*
        通过多个条件分组
        * */
        Map<List<String>, Integer> collect1 = fruitBeans.stream()
                .collect(Collectors.groupingBy(f -> Arrays.asList(f.getName()), Collectors.summingInt(Fruits::getQty)));
        System.out.println("greoup by multi condition: " + collect1);
        System.out.println("---------------------------------------");

        List<Order> collect2 = collect1.entrySet()
                .stream()
                .map(e -> new Order(e.getKey().get(0), Double.parseDouble(e.getValue().toString())))
                .collect(Collectors.toList());
        System.out.println("map to specified container: " + collect2);
        System.out.println("---------------------------------------");

        /*
        进阶需求
        在实际需求中，我们可能需要对一组对象进行分组，而且分组的条件有多个。
        比如对国家和产品类型进行双重分组，一种比较复杂的方法是先对产品类型进行分组，
        然后对每一个产品类型中的国际名进行分组求和。示例如下：
        * */
        Map<String, List<Fruits>> countMap = fruitBeans.stream().collect(Collectors.groupingBy(o -> o.getName()));

        List<Order> records = new ArrayList<>();
        countMap.keySet().forEach(key -> {
            Map<String, Long> countMap1 = countMap.get(key).stream().collect(Collectors.groupingBy(o -> o.getName(), Collectors.counting()));
            countMap1.entrySet().stream().forEach(fruit -> {
                records.add(new Order(fruit.getKey(), Double.parseDouble(String.valueOf(fruit.getValue()))));
            });
        });

        System.out.println("records: " + records);

        /*
        更好的解决方法
        上面的方法在应对两个字段的分组要求时，还能应付的过来，但如果字段超过两个时，
        每增加一个字段，就会多出很多代码行，显然不太合理。更合理的方法是，增加一个 getKey()方法，
        返回多个字段组成的唯一key，比如通过下划线连接等等。示例如下：
        * */
        // 分组统计
        Map<String, Long> countMap2 = fruitBeans.stream().collect(Collectors.groupingBy(o -> o.getName() + "_" + o.getPrice(), Collectors.counting()));

        List<Order> countRecords = countMap2.keySet().stream().map(key -> {
            Object[] temp = key.split("_");
            String name = String.valueOf(temp[0]);
            Double price = Double.parseDouble(String.valueOf(temp[1]));
            return new Order(name, price);
        }).collect(Collectors.toList());

        System.out.println("testGroupBy2 end...");
    }
}
