package stream;

import entity.Dish;
import util.DataBuilder;

import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DefaultCollector {
    public static void main(String[] args) {
        testDefaultCollector();
    }

    /*
     * 預定義的收集器
     * 下面簡單演示基本的內建收集器
     * */
    public static void testDefaultCollector() {
        System.out.println("testDefaultCollector start...");
        List<Dish> dishes = DataBuilder.getDishs();

        /*
        最大值，最小值，平均值
        為啥返回Optional？ 如果stream為null怎麼辦, 這時候Optinal就很有意義了
        * */
        Optional<Dish> mostCalorieDish = dishes.stream().max(Comparator.comparingInt(Dish::getCalories));
        Optional<Dish> minCalorieDish = dishes.stream().min(Comparator.comparingInt(Dish::getCalories));

        Double avgCalories = dishes.stream().collect(Collectors.averagingInt(Dish::getCalories));
        System.out.println("max: " + mostCalorieDish.get().getCalories());
        System.out.println("min: " + minCalorieDish.get().getCalories());
        System.out.println("averagingInt: " + avgCalories);
        System.out.println("--------------------------------");

        /*
        這幾個簡單的統計指標都有Collectors內建的收集器函式，
        尤其是針對數字型別拆箱函式，將會比直接操作包裝型別開銷小很多。
        * */
        IntSummaryStatistics summaryStatistics = dishes.stream().collect(Collectors.summarizingInt(Dish::getCalories));
        double average = summaryStatistics.getAverage();
        long count = summaryStatistics.getCount();
        int max = summaryStatistics.getMax();
        int min = summaryStatistics.getMin();
        long sum = summaryStatistics.getSum();
        System.out.println("IntSummaryStatistics max: " + max);
        System.out.println("IntSummaryStatistics min: " + min);
        System.out.println("IntSummaryStatistics average: " + average);
        System.out.println("IntSummaryStatistics count: " + count);
        System.out.println("IntSummaryStatistics sum: " + sum);
        System.out.println("--------------------------------");

        System.out.println("testDefaultCollector end...");
    }
}
