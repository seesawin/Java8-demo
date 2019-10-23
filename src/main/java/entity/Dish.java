package entity;

public class Dish {
    String name;
    boolean isExists;
    Integer calories;
    Type type;

    public Dish(String name, boolean isExists, Integer calories, Type type) {
        this.name = name;
        this.isExists = isExists;
        this.calories = calories;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isExists() {
        return isExists;
    }

    public void setExists(boolean exists) {
        isExists = exists;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "name='" + name + '\'' +
                ", isExists=" + isExists +
                ", calories=" + calories +
                ", type=" + type +
                '}';
    }
}
