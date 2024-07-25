package dp.decorator;

public class Magazine extends AbstractMagazine {
    public Magazine(Gun gun) {
        super(gun);
    }

    @Override
    public void fire() {
        System.out.println("砰*10");
    }
}
