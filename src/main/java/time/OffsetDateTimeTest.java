package time;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * 什麼是OffsetDateTime？
 * <p>
 * ISO-8601日曆系統中與UTC偏移量有關的日期時間。OffsetDateTime是一個帶有偏移量的日期時間型別。儲存有精確到納秒的日期時間，以及偏移量。
 * 可以簡單理解為 OffsetDateTime = LocalDateTime + ZoneOffset。
 * <p>
 * OffsetDateTime、ZonedDateTime和Instant它們三都能在時間線上以納秒精度儲存一個瞬間（請注意：LocalDateTime是不行的），也可理解為某個時刻。
 * OffsetDateTime和Instant可用於模型的欄位型別，因為它們都表示瞬間值並且還不可變，所以適合網路傳輸或者資料庫持久化。
 * <p>
 * ZonedDateTime不適合網路傳輸/持久化，因為即使同一個ZoneId時區，不同地方獲取到瞬時值也有可能不一樣
 */
public class OffsetDateTimeTest {
    public static void main(String[] args) {
        test1();
        test2();
        test3();
        test4();
        test5();
    }

    /**
     * 最大/最小值
     */
    public static void test1() {
        OffsetDateTime min = OffsetDateTime.MIN;
        OffsetDateTime max = OffsetDateTime.MAX;

        System.out.println("OffsetDateTime最小值：" + min);
        System.out.println("OffsetDateTime最大值：" + max);
        System.out.println(min.getOffset() + ":" + min.getYear() + "-" + min.getMonthValue() + "-" + min.getDayOfMonth());
        System.out.println(max.getOffset() + ":" + max.getYear() + "-" + max.getMonthValue() + "-" + max.getDayOfMonth());
    }

    /**
     * 構造
     */
    public static void test2() {
        System.out.println("當前位置偏移量的本地時間：" + OffsetDateTime.now());
        System.out.println("偏移量-4（紐約）的本地時間：：" + OffsetDateTime.of(LocalDateTime.now(), ZoneOffset.of("-4")));

        System.out.println("紐約時區的本地時間：" + OffsetDateTime.now(ZoneId.of("America/New_York")));
    }

    /**
     * 格式化
     */
    public static void test3() {
        OffsetDateTime now = OffsetDateTime.now(ZoneId.systemDefault());
        System.out.println("格式化輸出（本地化輸出，中文環境）：" + DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.SHORT).format(now));

        String dateTimeStrParam = "2021-01-17T18:00:00+07:00";
        System.out.println("解析後輸出：" + OffsetDateTime.parse(dateTimeStrParam));
    }

    /**
     * 轉換： LocalDateTime -> OffsetDateTime
     */
    public static void test4() {
        LocalDateTime localDateTime = LocalDateTime.of(2021, 01, 17, 18, 00, 00);
        System.out.println("當前時區（北京）時間為：" + localDateTime);

        // 通過此例值得注意的是：LocalDateTime#atOffset()/atZone()只是增加了偏移量/時區，本地時間是並沒有改變的。
        // 轉換為偏移量為 -4的OffsetDateTime時間
        // 1、-4地方的晚上18點
        System.out.println("-4偏移量地方的晚上18點：" + OffsetDateTime.of(localDateTime, ZoneOffset.ofHours(-4)));
        System.out.println("-4偏移量地方的晚上18點（方式二）：" + localDateTime.atOffset(ZoneOffset.ofHours(-4)));

        // 若想實現本地時間到其它偏移量的對應的時間只能通過其ofInstant()系列構造方法。
        // 2、北京時間晚上18:00 對應的-4地方的時間點
        System.out.println("當前地區對應的-4地方的時間：" + OffsetDateTime.ofInstant(localDateTime.toInstant(ZoneOffset.ofHours(8)), ZoneOffset.ofHours(-4)));
    }

    /**
     * 轉換： OffsetDateTime -> LocalDateTime
     */
    public static void test5() {
        final var localDateTime = LocalDateTime.now();
        OffsetDateTime offsetDateTime = OffsetDateTime.of(localDateTime, ZoneOffset.ofHours(-4));
        System.out.println("-4偏移量時間為：" + offsetDateTime);

        // 轉為LocalDateTime 注意：時間還是未變的哦
        System.out.println("LocalDateTime的表示形式：" + offsetDateTime.toLocalDateTime());
        System.out.println("當前地區對應的+0地方的時間：" + OffsetDateTime.ofInstant(localDateTime.toInstant(ZoneOffset.ofHours(8)), ZoneOffset.ofHours(0)));
    }


}
