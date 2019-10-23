package entity;

public enum Type {
    MEAT(1),
    OTHER(2),
    FISH(3);

    int value;

    Type(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
