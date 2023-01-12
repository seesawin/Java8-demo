package time;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 时区
 * <p>
 * 在JDK 8之前，Java使用java.util.TimeZone来表示时区。而在JDK 8里分别使用了ZoneId表示时区，ZoneOffset表示UTC的偏移量。
 * <p>
 * 值得提前强调，时区和偏移量在概念和实际作用上是有较大区别的，主要体现在：
 * <p>
 * UTC偏移量仅仅记录了偏移的小时分钟而已，除此之外无任何其它信息。举个例子：+08:00的意思是比UTC时间早8小时，没有地理/时区含义，相应的-03:30代表的意思仅仅是比UTC时间晚3个半小时
 * 时区是特定于地区而言的，它和地理上的地区（包括规则）强绑定在一起。比如整个中国都叫东八区，纽约在西五区等等
 * 中国没有夏令时，所有东八区对应的偏移量永远是+8；纽约有夏令时，因此它的偏移量可能是-4也可能是-5哦
 * <p>
 * 综合来看，时区更好用。令人恼火的夏令时问题，若你使用UTC偏移量去表示那么就很麻烦，因为它可变：一年内的某些时期在原来基础上偏移量 +1，
 * 某些时期 -1；但若你使用ZoneId时区去表示就很方便喽，比如纽约是西五区，你在任何时候获取其当地时间都是能得到正确答案的，因为它内置了对夏令时规则的处理，
 * 也就是说啥时候+1啥时候-1时区自己门清，不需要API调用者关心。
 * <p>
 * UTC偏移量更像是一种写死偏移量数值的做法，这在天朝这种没有时区规则（没有夏令时）的国家不会存在问题，东八区和UTC+08:00效果永远一样。
 * 但在一些夏令时国家（如美国、法国等等），就只能根据时区去获取当地时间喽。所以当你不了解当地规则时，最好是使用时区而非偏移量。
 * <p>
 * -----
 * 偏移量ZoneId
 * <p>
 * 它代表一个时区的ID，如Europe/Paris。它规定了一些规则可用于将一个Instant时间戳转换为本地日期/时间LocalDateTime。
 * 上面说了时区ZoneId是包含有规则的，实际上描述偏移量何时以及如何变化的实际规则由java.time.zone.ZoneRules定义。ZoneId则只是一个用于获取底层规则的ID。之所以采用这种方法，是因为规则是由政府定义的，并且经常变化，而ID是稳定的。
 * 对于API调用者来说只需要使用这个ID（也就是ZoneId）即可，而需无关心更为底层的时区规则ZoneRules，和“政府”同步规则的事是它领域内的事就交给它喽。如：夏令时这条规则是由各国政府制定的，而且不同国家不同年一般都不一样，这个事就交由JDK底层的ZoneRules机制自行sync，使用者无需关心。
 * ZoneId在系统内是唯一的，它共包含三种类型的ID：
 * 1.最简单的ID类型：ZoneOffset，它由'Z'和以'+'或'-'开头的id组成。如：Z、+18:00、-18:00
 * 2.另一种类型的ID是带有某种前缀形式的偏移样式ID，例如'GMT+2'或'UTC+01:00'。可识别的（合法的）前缀是'UTC'， 'GMT'和'UT'
 * 3.第三种类型是基于区域的ID（推荐使用）。基于区域的ID必须包含两个或多个字符，且不能以'UTC'、'GMT'、'UT' '+'或'-'开头。基于区域的id由配置定义好的，如Europe/Paris
 *
 *
 * -----
 * 最佳实践
 * 弃用Date，拥抱JSR 310
 *
 * 每每说到JSR 310日期/时间时我都会呼吁，保持惯例我这里继续啰嗦一句：放弃Date甚至禁用Date，使用JSR 310日期/时间吧，它才是日期时间处理的最佳实践。
 *
 * 另外，在使用期间关于制定时区（默认时区时）依旧有一套我心目中的最佳实践存在，这里分享给你：
 *
 * # 永远显式的指定你需要的时区，即使你要获取的是默认时区
 * // 方式一：普通做法
 * LocalDateTime.now();
 * // 方式二：最佳实践
 * LocalDateTime.now(ZoneId.systemDefault());
 *
 * 理由是：这样做能让代码带有明确的意图，消除模棱两可的可能性，即使获取的是默认时区。拿方式一来说吧，它就存在意图不明确的地方：到底是代码编写者忘记指定时区欠考虑了，
 * 还是就想用默认时区呢？这个答案如果不通读上下文是无法确定的，从而造成了不必要的沟通维护成本。因此即使你是要获取默认时区，也请显示的用ZoneId.systemDefault()写上去。
 *
 * # 使用JVM的默认时区需当心，建议时区和当前会话保持绑定
 * 这个最佳实践在特殊场景用得到。这么做的理由是：JVM的默认时区通过静态方法TimeZone#setDefault()可全局设置，
 * 因此JVM的任何一个线程都可以随意更改默认时区。若关于时间处理的代码对时区非常敏感的话，最佳实践是你把时区信息和当前会话绑定，这样就可以不用再受到其它线程潜在影响了，确保了健壮性。
 *
 * 说明：会话可能只是当前请求，也可能是一个Session，具体case具体分析
 */
public class ZoneTest {
    public static void main(String[] args) {
//        test1();
//        test2();
//        test3();
//        test4();
//        test5();
//        test6();
//        test7();
//        test8();
//        test11();
//        test12();
//        test13();
        test14();

    }

    /**
     * 二者结果是一样的，都是Asia/Shanghai。因为ZoneId方法底层就是依赖TimeZone
     */
    public static void test1() {
        // JDK 1.8之前做法
        System.out.println(TimeZone.getDefault());
        // JDK 1.8之后做法
        System.out.println(ZoneId.systemDefault());
    }

    public static void test2() {
        System.out.println(ZoneId.of("Asia/Shanghai"));
        // 报错：java.time.zone.ZoneRulesException: Unknown time-zone ID: Asia/xxx
//        System.out.println(ZoneId.of("Asia/xxx"));
    }

    public static void test3() {
        ZoneId.getAvailableZoneIds().stream().forEach(System.out::println);
    }

    /**
     * 这里第一个参数传的前缀，可用值为："GMT", "UTC", or "UT"。当然还可以传空串，
     * 那就直接返回第二个参数ZoneOffset。若以上都不是就报错
     * <p>
     * 注意：根据偏移量得到的ZoneId内部并无现成时区规则可用，因此对于有夏令营的国家转换可能出问题，一般不建议这么去做。
     */
    public static void test4() {
        ZoneId zoneId = ZoneId.ofOffset("UTC", ZoneOffset.of("+0"));
        System.out.println(zoneId);
        // 必须是大写的Z
        zoneId = ZoneId.ofOffset("UTC", ZoneOffset.of("Z"));
        System.out.println(zoneId);
    }

    public static void test5() {
        System.out.println(ZoneId.from(ZonedDateTime.now()));
        System.out.println(ZoneId.from(ZoneOffset.of("+8")));

        // 报错：java.time.DateTimeException: Unable to obtain ZoneId from TemporalAccessor:
        // 虽然方法入参是TemporalAccessor，但是只接受带时区的类型，LocalXXX是不行的，使用时稍加注意。
        System.out.println(ZoneId.from(LocalDateTime.now()));
        System.out.println(ZoneId.from(LocalDate.now()));
    }

    /**
     * ZoneOffset
     * <p>
     * 距离格林威治/UTC的时区偏移量，例如+02:00。值得注意的是它继承自ZoneId，
     * 所以也可当作一个ZoneId来使用的，当然并不建议你这么去做，请独立使用。
     * <p>
     * 时区偏移量是时区与格林威治/UTC之间的时间差。这通常是固定的小时数和分钟数。世界不同的地区有不同的时区偏移量。
     * 在ZoneId类中捕获关于偏移量如何随一年的地点和时间而变化的规则（主要是夏令时规则），所以继承自ZoneId。
     * <p>
     * 1、最小/最大偏移量：因为偏移量传入的是数字，这个是有限制的哦
     */
    public static void test6() {
        System.out.println("最小偏移量：" + ZoneOffset.MIN);
        System.out.println("最大偏移量：" + ZoneOffset.MAX);
        System.out.println("中心偏移量：" + ZoneOffset.UTC);
        // 超出最大范围
        System.out.println(ZoneOffset.of("+20"));
    }

    /**
     * 2.通过时分秒构造偏移量（使用很方便，推荐）
     * 看来，偏移量是能精确到秒的哈，只不过一般来说精确到分钟已经到顶了
     * <p>
     * 设置默认时区
     * ZoneId并没有提供设置默认时区的方法，但是通过文章可知ZoneId获取默认时区底层依赖的是TimeZone.getDefault()方法，因此设置默认时区方式完全遵照TimeZone的方式即可
     */
    public static void test7() {
        System.out.println(ZoneOffset.ofHours(8));
        System.out.println(ZoneOffset.ofHoursMinutes(8, 8));
        System.out.println(ZoneOffset.ofHoursMinutesSeconds(8, 8, 8));

        System.out.println(ZoneOffset.ofHours(-5));

        // 指定一个精确的秒数  获取实例（有时候也很有用处）
        System.out.println(ZoneOffset.ofTotalSeconds(8 * 60 * 60));
    }

    /**
     * JSR 310时区相关性
     * <p>
     * java.util.Date类型它具有时区无关性，带来的弊端就是一旦涉及到国际化时间转换等需求时，使用Date来处理是很不方便的。
     * <p>
     * JSR 310解决了Date存在的一系列问题：对日期、时间进行了分开表示（LocalDate、LocalTime、LocalDateTime），
     * 对本地时间和带时区的时间进行了分开管理。LocalXXX表示本地时间，也就是说是当前JVM所在时区的时间；ZonedXXX表示是一个带有时区的日期时间，它们能非常方便的互相完成转换。
     */
    public static void test8() {
        // 本地日期/时间
        System.out.println("================本地时间================");
        System.out.println(LocalDate.now());
        System.out.println(LocalTime.now());
        System.out.println(LocalDateTime.now());

        // 时区时间
        System.out.println("================带时区的时间ZonedDateTime================");
        System.out.println(ZonedDateTime.now()); // 使用系统时区
        System.out.println(ZonedDateTime.now(ZoneId.of("America/New_York"))); // 自己指定时区
        System.out.println(ZonedDateTime.now(Clock.systemUTC())); // 自己指定时区
        System.out.println("================带时区的时间OffsetDateTime================");
        System.out.println(OffsetDateTime.now()); // 使用系统时区
        System.out.println(OffsetDateTime.now(ZoneId.of("America/New_York"))); // 自己指定时区
        System.out.println(OffsetDateTime.now(Clock.systemUTC())); // 自己指定时区
    }

    /**
     * 读取字符串为JSR 310类型
     * 一个独立的日期时间类型字符串如2021-05-05T18:00-04:00它是没有任何意义的，因为没有时区无法确定它代表那个瞬间，这是理论当然也适合JSR 310类型喽。
     * 遇到一个日期时间格式字符串，要解析它一般有这两种情况：
     * 1.不带时区/偏移量的字符串：要么不理它说转换不了，要么就约定一个时区（一般用系统默认时区），使用LocalDateTime来解析
     */
    public static void test11() {
        String dateTimeStrParam = "2021-05-05T18:00";
        LocalDateTime localDateTime = LocalDateTime.parse(dateTimeStrParam);
        System.out.println("解析后：" + localDateTime);
    }

    /**
     * 2.带时区字/偏移量的符串
     * <p>
     * 请注意带时区解析后这个结果：字符串参数偏移量明明是-05，为毛转换为ZonedDateTime后偏移量成为了-04呢？？？
     * 在2021.03.14 - 2021.11.07期间，纽约的偏移量是-4，其余时候是-5。本例的日期是2021-05-05处在夏令时之中，因此偏移量是-4，这就解释了为何你显示的写了-5最终还是成了-4
     */
    public static void test12() {
        // 带偏移量 使用OffsetDateTime
        String dateTimeStrParam = "2021-05-05T18:00-04:00";
//        String dateTimeStrParam = "2021-05-05T18:00Z";
        OffsetDateTime offsetDateTime = OffsetDateTime.parse(dateTimeStrParam);
        System.out.println("带偏移量解析后：" + offsetDateTime);

        // 带时区 使用ZonedDateTime
        dateTimeStrParam = "2021-05-05T18:00-05:00[America/New_York]";
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(dateTimeStrParam);
        System.out.println("带时区解析后：" + zonedDateTime);
    }

    /**
     * JSR 310格式化
     * 针对JSR 310日期时间类型的格式化/解析，有个专门的类java.time.format.DateTimeFormatter用于处理。
     * DateTimeFormatter也是一个不可变的类，所以是线程安全的，比SimpleDateFormat靠谱多了吧。另外它还内置了非常多的格式化模版实例供以使用
     * <p>
     * 若想自定义模式pattern，和Date一样它也可以自己指定任意的pattern 日期/时间模式
     * <p>
     * example:
     * 格式化器	示例
     * ofLocalizedDate(dateStyle)	'2021-01-03'
     * ofLocalizedTime(timeStyle)	'10:15:30'
     * ofLocalizedDateTime(dateTimeStyle)	'3 Jun 2021 11:05:30'
     * ISO_LOCAL_DATE	'2021-12-03'
     * ISO_LOCAL_TIME	'10:15:30'
     * ISO_LOCAL_DATE_TIME	'2021-12-03T10:15:30'
     * ISO_OFFSET_DATE_TIME	'2021-12-03T10:15:30+01:00'
     * ISO_ZONED_DATE_TIME	'2021-12-03T10:15:30+01:00[Europe/Paris]'
     */
    public static void test13() {
        System.out.println(DateTimeFormatter.ISO_LOCAL_DATE.format(LocalDate.now()));
        System.out.println(DateTimeFormatter.ISO_LOCAL_TIME.format(LocalTime.now()));
        System.out.println(DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(LocalDateTime.now()));

        LocalDateTime now = LocalDateTime.now(ZoneId.systemDefault());
        System.out.println("格式化輸出（本地化輸出，中文環境）：" + DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.SHORT).format(now));
        System.out.println("格式化輸出（ISO_DATE）：" + DateTimeFormatter.ISO_DATE.format(now));
        System.out.println("格式化輸出（ISO_TIME）：" + DateTimeFormatter.ISO_TIME.format(now));
        System.out.println("格式化輸出（ISO_DATE_TIME）：" + DateTimeFormatter.ISO_DATE_TIME.format(now));
    }

    /**
     * 若想自定义模式pattern，和Date一样它也可以自己指定任意的pattern 日期/时间模式
     * <p>
     * Q/q：季度，如3; 03; Q3; 3rd quarter
     */
    public static void test14() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("第Q季度 yyyy-MM-dd HH:mm:ss", Locale.US);

        // 格式化输出
        System.out.println(formatter.format(LocalDateTime.now()));

        // 解析
        String dateTimeStrParam = "第1季度 2021-01-17 22:51:32";
        LocalDateTime localDateTime = LocalDateTime.parse(dateTimeStrParam, formatter);
        System.out.println("解析后的结果：" + localDateTime);
    }

}
