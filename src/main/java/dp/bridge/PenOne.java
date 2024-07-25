package dp.bridge;

public class PenOne extends Pen {
    public void draw(Color color) {
        System.out.println("当前一号笔在使用" + color.use() + "画画");
    }
}
