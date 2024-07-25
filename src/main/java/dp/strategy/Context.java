package dp.strategy;

import lombok.Setter;

public class Context {
    @Setter
    private Strategy strategy;

    public int executeStrategy(int num1, int num2) {
        return strategy.doOperation(num1, num2);
    }
}
