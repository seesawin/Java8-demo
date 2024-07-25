package dp.decorator;

public class Demo {
    public static void main(String[] args) {
        System.out.println("[捡起一把98K]");
        Gun gun = new Kar98K();
        System.out.println("[开炮！]");
        gun.fire();
        System.out.println("[装饰上弹匣]");
        gun = new Magazine(gun);
        System.out.println("[开炮！]");
        gun.fire();

        // 多錢包
        // bet
        // 上鎖
        // 檢查

    }
}
