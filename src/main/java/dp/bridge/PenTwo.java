package dp.bridge;

public class PenTwo extends Pen {
    public void draw(Color color) {
        System.out.println("当前二号笔在使用" + color.use() + "画画");
    }
}
