package time;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

/**
 * 什麼是LocalDateTime？
 * <p>
 * ISO-8601日曆系統中不帶時區的日期時間。
 * <p>
 * 說明：ISO-8601日系統是現今世界上絕大部分國家/地區使用的，這就是我們國人所說的公曆，有閏年的特性
 * <p>
 * LocalDateTime是一個不可變的日期-時間物件，它表示一個日期時間，通常被視為年-月-日-小時-分鐘-秒。
 * 還可以訪問其他日期和時間欄位，如day-of-year、day-of-week和week-of-year等等，它的精度能達納秒級別。
 * <p>
 * 該類不儲存時區，所以適合日期的描述，比如用於生日、deadline等等。但是請記住，如果沒有偏移量/時區等附加資訊，一個時間是不能表示時間線上的某一時刻的。
 */
public class LocalDateTImeTest {
    public static void main(String[] args) {
        test1();
        test2();
        test3();
        test4();
    }

    /**
     * 最大/最小值
     */
    public static void test1() {
        LocalDateTime min = LocalDateTime.MIN;
        LocalDateTime max = LocalDateTime.MAX;

        System.out.println("LocalDateTime最小值：" + min);
        System.out.println("LocalDateTime最大值：" + max);
        System.out.println(min.getYear() + "-" + min.getMonthValue() + "-" + min.getDayOfMonth());
        System.out.println(max.getYear() + "-" + max.getMonthValue() + "-" + max.getDayOfMonth());
    }

    /**
     * 構造
     */
    public static void test2() {
        System.out.println("當前時區的本地時間：" + LocalDateTime.now());
        System.out.println("當前時區的本地時間：" + LocalDateTime.of(LocalDate.now(), LocalTime.now()));

        // 注意，最後一個構造傳入了ZoneId，並不是說LocalDateTime和時區有關了，而是告訴說這個Local指的是紐約，細品這句話。
        System.out.println("紐約時區的本地時間：" + LocalDateTime.now(ZoneId.of("America/New_York")));
    }

    /**
     * 計算
     */
    public static void test3() {
        LocalDateTime now = LocalDateTime.now(ZoneId.systemDefault());
        System.out.println("計算前：" + now);

        // 加3天
        LocalDateTime after = now.plusDays(3);
        // 減4個小時
        after = after.plusHours(-3); // 效果同now.minusDays(3);
        System.out.println("計算後：" + after);

        // 計算時間差
        Period period = Period.between(now.toLocalDate(), after.toLocalDate());
        System.out.println("相差天數：" + period.getDays());
        Duration duration = Duration.between(now.toLocalTime(), after.toLocalTime());
        System.out.println("相差小時數：" + duration.toHours());
    }

    /**
     * 格式化
     */
    public static void test4() {
        LocalDateTime now = LocalDateTime.now(ZoneId.systemDefault());
        // System.out.println("格式化輸出：" + DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(now));
        System.out.println("格式化輸出（本地化輸出，中文環境）：" + DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.SHORT).format(now));

        String dateTimeStrParam = "2021-01-17 18:00:00";
        System.out.println("解析後輸出：" + LocalDateTime.parse(dateTimeStrParam, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.US)));
    }

}
