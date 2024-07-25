package dp.decorator;

public abstract class AbstractMagazine implements Gun {
    private Gun gun;

    public AbstractMagazine(Gun gun) {
        this.gun = gun;
    }

    @Override
    public void fire() {
        gun.fire();
    }
}
