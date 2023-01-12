package time;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateTest {
    public static void main(String[] args) throws ParseException {
//        test1();
//        test2();
//        test3();
//        test4();
//        test5();
//        test6();
//        test7();
//        test9();
//        test10();
    }

    /**
     * 第一个：标准的UTC时间（CST就代表了偏移量 +0800）
     * 第二个：本地时间，根据本地时区显示的时间格式
     * 第三个：GTM时间，也就是格林威治这个时候的时间，可以看到它是凌晨2点（北京时间是上午10点哦）
     * <p>
     * 第二个、第三个其实在JDK 1.1就都标记为@Deprecated过期了，基本禁止再使用。若需要转换为本地时间 or GTM时间输出的话，请使用格式化器java.text.DateFormat去处理。
     */
    public static void test1() {
        Date currDate = new Date();
        System.out.println(currDate.toString());
        // 已经@Deprecated
        System.out.println(currDate.toLocaleString());
        // 已经@Deprecated
        System.out.println(currDate.toGMTString());
        System.out.println("-----");
    }

    /**
     * 注意：两个时间表示的应该是同一时刻，也就是常说的时间
     */
    public static void test2() {
        String patternStr = "yyyy-MM-dd HH:mm:ss";
        // 北京时间（new出来就是默认时区的时间）
        Date bjDate = new Date();

        // 得到纽约的时区
        TimeZone newYorkTimeZone = TimeZone.getTimeZone("America/New_York");
        // 根据此时区 将北京时间转换为纽约的Date
        DateFormat newYorkDateFormat = new SimpleDateFormat(patternStr);
        newYorkDateFormat.setTimeZone(newYorkTimeZone);
        System.out.println("这是北京时间：" + new SimpleDateFormat(patternStr).format(bjDate));
        System.out.println("这是纽约时间：" + newYorkDateFormat.format(bjDate));
        System.out.println("-----");
    }

    public static void test3() {
        String[] availableIDs = TimeZone.getAvailableIDs();
        System.out.println("可用zoneId总数：" + availableIDs.length);
        for (String zoneId : availableIDs) {
            System.out.println(zoneId);
        }
        System.out.println("-----");
    }

    /**
     * 设置默认时区
     * <p>
     * 1.API方式： 强制将时区设为北京时区TimeZone.setDefault(TimeZone.getDefault().getTimeZone("GMT+8"));
     * 2.JVM参数方式：-Duser.timezone=GMT+8
     * 3.运维设置方式：将操作系统主机时区设置为北京时区，这是推荐方式，可以完全对开发者无感，也方便了运维统一管理
     */
    public static void test4() {
        System.out.println(TimeZone.getTimeZone("GMT+08:00").getID());
        System.out.println(TimeZone.getDefault().getID());

        // 纽约时间
        System.out.println(TimeZone.getTimeZone("GMT-05:00").getID());
        System.out.println(TimeZone.getTimeZone("America/New_York").getID());
        System.out.println("-----");
    }

    public static void test5() throws ParseException {
        String patterStr = "yyyy/MM/dd";
        DateFormat dateFormat = new SimpleDateFormat(patterStr);
//        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));

        String birthdayStr = "1988/09/11";
        // 字符串 -> Date -> 字符串
        Date birthday = dateFormat.parse(birthdayStr);
        long birthdayTimestamp = birthday.getTime();
        System.out.println("老王的生日是：" + birthday);
        System.out.println("老王的生日的时间戳是：" + birthdayTimestamp);

        System.out.println("==============程序经过一番周转，我的同时 方法入参传来了生日的时间戳=============");
        // 字符串 -> Date -> 时间戳 -> Date -> 字符串
        birthday = new Date(birthdayTimestamp);
        System.out.println("老王的生日是：" + birthday);
        System.out.println("老王的生日的时间戳是：" + dateFormat.format(birthday));
        System.out.println("-----");
    }

    /**
     * Date时区无关性
     * 类Date表示一个特定的时间瞬间，精度为毫秒。既然表示的是瞬间/时刻，那它必然和时区是无关的
     * <p>
     * 也就是说，同一个毫秒值，根据时区/偏移量的不同可以展示多地的时间，这就证明了Date它的时区无关性。
     * 确切的说：Date对象里存的是自格林威治时间（ GMT）1970年1月1日0点至Date所表示时刻所经过的毫秒数，是个数值。
     */
    public static void test6() {
        String patterStr = "yyyy-MM-dd HH:mm:ss";
        Date currDate = new Date(System.currentTimeMillis());

        // 北京时区
        DateFormat bjDateFormat = new SimpleDateFormat(patterStr);
        bjDateFormat.setTimeZone(TimeZone.getDefault());
        // 纽约时区
        DateFormat newYorkDateFormat = new SimpleDateFormat(patterStr);
        newYorkDateFormat.setTimeZone(TimeZone.getTimeZone("America/New_York"));
        // 伦敦时区
        DateFormat londonDateFormat = new SimpleDateFormat(patterStr);
        londonDateFormat.setTimeZone(TimeZone.getTimeZone("Europe/London"));

        System.out.println("毫秒数:" + currDate.getTime() + ", 北京本地时间:" + bjDateFormat.format(currDate));
        System.out.println("毫秒数:" + currDate.getTime() + ", 纽约本地时间:" + newYorkDateFormat.format(currDate));
        System.out.println("毫秒数:" + currDate.getTime() + ", 伦敦本地时间:" + londonDateFormat.format(currDate));
        System.out.println("-----");
    }

    /**
     * 读取字符串为Date类型
     * 这是开发中极其常见的一种需求：client请求方扔给你一个字符串如"2021-01-15 18:00:00"，然后你需要把它转为Date类型，怎么破？
     * 问题来了，光秃秃的扔给我个字符串说是15号晚上6点时间，我咋知道你指的是北京的晚上6点，还是东京的晚上6点呢？还是纽约的晚上6点呢？
     * 因此，对于字符串形式的日期时间，只有指定了时区才有意义。也就是说字符串 + 时区 才能精确知道它是什么时刻，否则是存在歧义的。
     * 也许你可能会说了，自己平时开发中前端就是扔个字符串给我，然后我就给格式化为一个Date类型，并没有传入时区参数，运行这么久也没见出什么问题呀。如下所示：
     * <p>
     * 看起来结果没问题。事实上，这是因为默认情况下你们交互双发就达成了契约：双方均使用的是北京时间（时区），既然是相同时区，所以互通有无不会有任何问题。不信你把你接口给海外用户调试试？
     * 对于格式化器来讲，虽然说编程过程中一般情况下我们并不需要给DateFormat设置时区（那就用默认时区呗）就可正常转换。但是作为高手的你必须清清楚楚，明明白白的知道这是由于交互双发默认有个相同时区的契约存在。
     *
     * @throws ParseException
     */
    public static void test7() throws ParseException {
        String patterStr = "yyyy-MM-dd HH:mm:ss";

        // 模拟请求参数的时间字符串
        String dateStrParam = "2020-01-15 18:00:00";

        // 模拟服务端对此服务换转换为Date类型
        DateFormat dateFormat = new SimpleDateFormat(patterStr);
        System.out.println("格式化器用的时区是：" + dateFormat.getTimeZone().getID());
        Date date = dateFormat.parse(dateStrParam);
        System.out.println(date);
        System.out.println("-----");
    }

    public static void test9() throws ParseException {
        String patternStr = "G GG GGGGG E EE EEEEE a aa aaaaa";
        Date currDate = new Date();

        System.out.println("↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓中文地区模式↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓");
        System.out.println("====================Date->String====================");
        DateFormat dateFormat = new SimpleDateFormat(patternStr, Locale.CHINA);
        System.out.println(dateFormat.format(currDate));

        System.out.println("====================String->Date====================");
        String dateStrParam = "公元 公元 公元 星期六 星期六 星期六 下午 下午 下午";
        System.out.println(dateFormat.parse(dateStrParam));

        System.out.println("↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓英文地区模式↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓");
        System.out.println("====================Date->String====================");
        dateFormat = new SimpleDateFormat(patternStr, Locale.US);
        System.out.println(dateFormat.format(currDate));

        System.out.println("====================String->Date====================");
        dateStrParam = "AD ad bC Sat SatUrday sunDay PM PM Am";
        System.out.println(dateFormat.parse(dateStrParam));
    }

    public static void test10() {
        long currMillis = System.currentTimeMillis();

        java.util.Date date = new Date(currMillis);
        java.sql.Date sqlDate = new java.sql.Date(currMillis);
        java.sql.Time time = new Time(currMillis);
        java.sql.Timestamp timestamp = new Timestamp(currMillis);

        System.out.println("java.util.Date：" + date);
        System.out.println("java.sql.Date：" + sqlDate);
        System.out.println("java.sql.Time：" + time);
        System.out.println("java.sql.Timestamp：" + timestamp);
    }

}
