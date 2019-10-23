package entity;

public enum CaloricLevel {
    DIET(1),
    NORMAL(2),
    FAT(3);

    int value;

    CaloricLevel(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "CaloricLevel{" +
                "value=" + value +
                '}';
    }
}
