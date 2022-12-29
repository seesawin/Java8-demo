package time;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

/**
 * 什麼是ZonedDateTime？
 * <p>
 * ISO-8601國際標準日曆系統中帶有時區的日期時間。它儲存所有的日期和時間欄位，精度為納秒，以及一個時區，帶有用於處理不明確的本地日期時間的時區偏移量。
 * <p>
 * 這個API可以處理從LocalDateTime -> Instant -> ZonedDateTime的轉換，其中用zone時區來表示偏移量（並非直接用offset哦）。
 * 兩個時間點之間的轉換會涉及到使用從ZoneId訪問的規則計算偏移量（換句話說：偏移量並非寫死而是根據規則計算出來的）。
 * <p>
 * 獲取瞬間的偏移量很簡單，因為每個瞬間只有一個有效的偏移量。但是，獲取本地日期時間的偏移量並不簡單。存在這三種情況：
 * <p>
 * 1.正常情況：有一個有效的偏移量。對於一年中的絕大多數時間，適用正常情況，即本地日期時間只有一個有效的偏移量
 * 2.時間間隙情況：沒有有效偏移量。這是由於夏令時開始時從“冬季”改為“夏季”而導致時鐘向前撥的時候。在間隙中，沒有有效偏移量
 * 3.重疊情況：有兩個有效偏移量。這是由於秋季夏令時從“夏季”到“冬季”的變化，時鐘會向後撥。在重疊部分中，有兩個有效偏移量
 * <p>
 * 這三種情況如果要自己處理，估計頭都大了。這就是使用JSR 310的優勢，ZonedDateTime全幫你搞定，讓你使用無憂。
 * <p>
 * ZonedDateTime可簡單認為是LocalDateTime和ZoneId的組合。而ZoneOffset是其內建的動態計算出來的一個次要資訊，以確保輸出一個瞬時值而存在，
 * 畢竟在某個瞬間偏移量ZoneOffset肯定是確定的。
 * <p>
 * ZonedDateTime也可以理解為儲存的狀態相當於三個獨立的物件：LocalDateTime、ZoneId和ZoneOffset。
 * 某個瞬間 = LocalDateTime + ZoneOffset。ZoneId 確定了偏移量如何改變的規則。所以偏移量我們並不能自由設定（不提供set方法，構造時也不行），因為它由ZoneId來控制的。
 * <p>
 * <p>
 * <p>
 * OffsetDateTime和ZonedDateTime的區別
 * <p>
 * LocalDateTime、OffsetDateTime、ZonedDateTime這三個哥們，LocalDateTime好理解，一般都沒有異議。但是很多同學對OffsetDateTime和ZonedDateTime傻傻分不清，這裡說說它倆的區別。
 * <p>
 * 1.OffsetDateTime = LocalDateTime + 偏移量ZoneOffset；ZonedDateTime = LocalDateTime + 時區ZoneId
 * 2.OffsetDateTime可以隨意設定偏移值，但ZonedDateTime無法自由設定偏移值，因為此值是由時區ZoneId控制的
 * 3.OffsetDateTime無法支援夏令時等規則，但ZonedDateTime可以很好的處理夏令時調整
 * 4.OffsetDateTime得益於不變性一般用於資料庫儲存、網路通訊；而ZonedDateTime得益於其時區特性，一般在指定時區裡顯示時間非常方便，無需認為干預規則
 * 5.OffsetDateTime代表一個瞬時值，而ZonedDateTime的值是不穩定的，需要在某個瞬時根據當時的規則計算出來偏移量從而確定實際值
 * <p>
 * 總的來說，OffsetDateTime和ZonedDateTime的區別主要在於ZoneOffset和ZoneId的區別。如果你只是用來傳遞資料，請使用OffsetDateTime，若你想在特定時區裡做時間顯示那麼請務必使用ZonedDateTime。
 * <p>
 * 總結
 * 1.所有日期/時間都是不可變的型別，所以若需要比較的話，請不要使用==，而是用equals()方法。
 * 2.任何時候，構造一個日期時間（包括它們三）請永遠務必顯示的指定時區，哪怕是預設時區。這麼做的目的就是明確程式碼的意圖，消除語義上的不確定性。
 * 比如若沒指定時區，那到底是寫程式碼的人欠考慮了呢，還是就是想用預設時區呢？總之顯示指定絕大部分情況下比隱式“指定”語義上好得多。
 */
public class ZonedDateTimeTest {
    public static void main(String[] args) {
        test1();
        test2();
        test3();
        test4();
    }

    /**
     * 構造
     */
    public static void test1() {
        System.out.println("當前位置偏移量的本地時間：" + ZonedDateTime.now());
        System.out.println("紐約時區的本地時間：" + ZonedDateTime.of(LocalDateTime.now(), ZoneId.of("America/New_York")));

        System.out.println("北京實現對應的紐約時區的本地時間：" + ZonedDateTime.now(ZoneId.of("America/New_York")));
    }

    /**
     * 轉換： LocalDateTime -> ZonedDateTime
     */
    public static void test2() {
        LocalDateTime localDateTime = LocalDateTime.of(2021, 01, 17, 18, 00, 00);
        System.out.println("當前時區（北京）時間為：" + localDateTime);

        // 轉換為偏移量為 -4的OffsetDateTime時間
        // 1、-4地方的晚上18點
        System.out.println("紐約時區晚上18點：" + ZonedDateTime.of(localDateTime, ZoneId.of("America/New_York")));
        System.out.println("紐約時區晚上18點（方式二）：" + localDateTime.atZone(ZoneId.of("America/New_York")));
        // 2、北京時間晚上18:00 對應的-4地方的時間點
        System.out.println("北京地區此時間對應的紐約的時間：" + ZonedDateTime.ofInstant(localDateTime.toInstant(ZoneOffset.ofHours(8)), ZoneOffset.ofHours(-4)));
        System.out.println("北京地區此時間對應的紐約的時間：" + ZonedDateTime.ofInstant(localDateTime, ZoneOffset.ofHours(8), ZoneOffset.ofHours(-4)));
    }

    /**
     * 轉換： OffsetDateTime -> ZonedDateTime
     * <p>
     * 本例有值得關注的點：
     * <p>
     * atZoneSameInstant()：將此日期時間與時區結合起來建立ZonedDateTime，以確保結果具有相同的Instant
     * 所有偏移量-4 -> -5，時間點也從19 -> 18，確保了Instant保持一致嘛
     * <p>
     * atZoneSimilarLocal(符合夏令時間)：將此日期時間與時區結合起來建立ZonedDateTime，以確保結果具有相同的本地時間
     * 所以直接效果和toLocalDateTime()是一樣的，但是它會盡可能的保留偏移量（所以你看-4變為了-5，保持了真實的偏移量）
     */
    public static void test3() {
        OffsetDateTime offsetDateTime = OffsetDateTime.of(LocalDateTime.now(), ZoneOffset.ofHours(-4));
        System.out.println("-4偏移量時間為：" + offsetDateTime);

        // 轉換為ZonedDateTime的表示形式
        System.out.println("ZonedDateTime的表示形式：" + offsetDateTime.toZonedDateTime());
        System.out.println("ZonedDateTime的表示形式：" + offsetDateTime.atZoneSameInstant(ZoneId.of("America/New_York")));
        System.out.println("ZonedDateTime的表示形式：" + offsetDateTime.atZoneSimilarLocal(ZoneId.of("America/New_York")));
    }

    /**
     * 紐約夏令時間
     * <p>
     * 年份   |   切換時間       |   時區變化   |        時鐘變化        | 變化後時區
     * 2021  |03-13 周天 03:00 | EST -> EDT  |向前調1小時(夏令時間開始)  | UTC-04:00
     * 2021  |11-07 周天 01:00 | EDT -> EST  |向後調1小時(夏令時間結束)  | UTC-05:00
     * <p>
     * 向前：靠近UTC+0
     * 向後：遠離UTC+0
     */
    public static void test4() {
        OffsetDateTime offsetDateTime = OffsetDateTime.of(LocalDateTime.of(2021, 5, 05, 18, 00, 00), ZoneOffset.ofHours(-4));
        System.out.println("-4偏移量時間為：" + offsetDateTime);

        // 轉換為ZonedDateTime的表示形式
        System.out.println("ZonedDateTime的表示形式：" + offsetDateTime.toZonedDateTime());
        System.out.println("ZonedDateTime的表示形式：" + offsetDateTime.atZoneSameInstant(ZoneId.of("America/New_York")));
        System.out.println("ZonedDateTime的表示形式：" + offsetDateTime.atZoneSimilarLocal(ZoneId.of("America/New_York")));
    }
}
