package stream;

import entity.Dish;
import entity.Fruits;
import util.DataBuilder;

import java.sql.SQLOutput;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Reduce {
    public static void main(String[] args) {
        testReduce();
    }

    /*
     * 自定義歸約reducing
     * */
    public static void testReduce() {
        System.out.println("testReduce start...");
        List<Dish> dishes = DataBuilder.getDishs();

        /*
        前面幾個都是reducing工廠方法定義的歸約過程的特殊情況，其實可以用Collectors.reducing建立收集器。比如，求和
        * */
        Integer totalCalories = dishes.stream().collect(Collectors.reducing(0, Dish::getCalories, (sum1, j) -> sum1 + j));
        System.out.println("Collectors.reducing: " + totalCalories);

        //使用內建函式代替箭頭函式
        Integer totalCalories2 = dishes.stream().collect(Collectors.reducing(0, Dish::getCalories, Integer::sum));
        System.out.println("Collectors.reducing, method reference: " + totalCalories2);

        //直接使用reduce
        Optional<Integer> totalCalories3 = dishes.stream().map(Dish::getCalories).reduce(Integer::sum);
        System.out.println("reduce: " + totalCalories3);

        /*
        根據情況選擇最佳方案
        上面的demo說明，函數語言程式設計通常提供了多種方法來執行同一個操作，使用收集器collect比直接使用stream的api用起來更加複雜，
        好處是collect能提供更高水平的抽象和概括，也更容易重用和自定義。
        我們的建議是，儘可能為手頭的問題探索不同的解決方案，始終選擇最專業的一個，無論從可讀性還是效能來看，這一般都是最好的決定。
        reducing除了接收一個初始值，還可以把第一項當作初始值

        reducing還有一個過載的方法，可以省略第一個引數，意義在於把Stream裡的第一個引數當做初始值。
        public static <T> Collector<T, ?, Optional<T>>
        reducing(BinaryOperator<T> op)

        先看返回值的區別，T表示輸入值和返回值型別，即輸入值型別和輸出值型別相同。還有不同的就是Optional了。這是因為沒有初始值，
        而第一個引數有可能是null， 當Stream的元素是null的時候，返回Optional就很意義了。
        再看引數列表，只剩下BinaryOperator。BinaryOperator是一個三元組函式介面，目標是將兩個同型別引數做計算後返回同型別的值。
        可以按照1>2? 1:2來理解，即求兩個數的最大值。求最大值是比較好理解的一種說法，你可以自定義lambda表示式來選擇返回值。
        那麼，在這裡，就是接收兩個Stream的元素型別T，返回T型別的返回值。用sum累加來理解也可以。
        * */
        //reducing除了接收一個初始值，還可以把第一項當作初始值
        Optional<Dish> mostCalorieDish2 = dishes.stream().collect(Collectors.reducing((d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2));
        System.out.println("Collectors.reducing: " + mostCalorieDish2);
        System.out.println("Collectors.reducing.get: " + mostCalorieDish2.get());

        //考量效率的話，還是要選擇下面這種
        int sum = dishes.stream().mapToInt(Dish::getCalories).sum();
        System.out.println("mapToInt sum: " + sum);

        /*
        reducing
        關於reducing的用法比較複雜，目標在於把兩個值合併成一個值。

        public static <T, U>
        Collector<T, ?, U> reducing(U identity,
        Function<? super T, ? extends U> mapper,
        BinaryOperator<U> op)

        首先看到3個泛型，
        1.U是返回值的型別，比如上述demo中計算熱量的，U就是Integer。
        2.關於T，T是Stream裡的元素型別。由Function的函式可以知道，mapper的作用就是接收一個引數T，然後返回一個結果U。對應demo中Dish。
        3.?在返回值Collector的泛型列表的中間，這個表示容器型別，一個收集器當然需要一個容器來存放資料。
        這裡的？則表示容器型別不確定。事實上，在這裡的容器就是U[]。

        關於引數：
        1.identity是返回值型別的初始值，可以理解為累加器的起點。
        2.mapper則是map的作用，意義在於將Stream流轉換成你想要的型別流。
        3.op則是核心函式，作用是如何處理兩個變數。其中，第一個變數是累積值，可以理解為sum，第二個變數則是下一個要計算的元素。從而實現了累加。
        * */

        /*
        上述的demo中發現reduce和collect的作用幾乎一樣，都是返回一個最終的結果，比如，我們可以使用reduce實現toList效果：
        * */
        List<Integer> calories = dishes.stream().map(Dish::getCalories)
                .reduce(new ArrayList<Integer>(),
                        (List<Integer> l, Integer e) -> {
                            l.add(e);
                            return l;
                        },
                        (List<Integer> l1, List<Integer> l2) -> {
                            l1.addAll(l2);
                            return l1;
                        }
                );
        /*
        <U> U reduce(U identity,
        BiFunction<U, ? super T, U> accumulator,
        BinaryOperator<U> combiner);

        1.U是返回值型別，這裡就是List
        2.BiFunction<U, ? super T, U> accumulator是是累加器，目標在於累加值和單個元素的計算規則。
        這裡就是List和元素做運算，最終返回List。即，新增一個元素到list。
        3.BinaryOperator<U> combiner是組合器，目標在於把兩個返回值型別的變數合併成一個。這裡就是兩個list合併。

        這個解決方案有兩個問題：一個是語義問題，一個是實際問題。
        語義問題在於，reduce方法旨在把兩個值結合起來生成一個新值，它是一個不可變歸約。
        相反，collect方法的設計就是要改變容器，從而累積要輸出的結果。這意味著，上面的程式碼片段是在濫用reduce方法，因為它在原地改變了作為累加器的List。
        錯誤的語義來使用reduce方法還會造成一個實際問題：這個歸約不能並行工作，因為由多個執行緒併發修改同一個資料結構可能會破壞List本身。
        在這種情況下，如果你想要執行緒安全，就需要每次分配一個新的List，而物件分配又會影響效能。這就是collect適合表達可變容器上的歸約的原因，
        更關鍵的是它適合並行操作。

        總結：reduce適合不可變容器歸約，collect適合可變容器歸約。collect適合並行。
        * */


        /**
         * 合併不同屬性至同一物件下
         * */
        List<Dish> aList = DataBuilder.getDishs2();
        List<Dish> bList = DataBuilder.getDishs3();
        // 合併順訓要正確
        List<Dish> collect = Stream.of(aList, bList).flatMap(l -> l.stream()).collect(Collectors.toList());
//        List<Dish> collect = Stream.of(bList, aList).flatMap(l -> l.stream()).collect(Collectors.toList());
        collect.forEach(System.out::println);

        List<Dish> dishDatas = new ArrayList<>();
        Map<String, List<Dish>> collect1 = collect.parallelStream().collect(Collectors.groupingBy(Dish::getName));
        collect1.forEach((k, v) -> {
            System.out.println("k:" + k + ", v:" + v);
            v.stream().reduce((a, b) -> new Dish(a.getName(), a.isExists(), a.getCalories(), b.getType())).ifPresent(dishDatas::add);
        });

        dishDatas.forEach(System.out::println);
        System.out.println("testReduce end...");
    }
}
