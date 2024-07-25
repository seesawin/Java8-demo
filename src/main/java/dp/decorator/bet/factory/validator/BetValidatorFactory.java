package dp.decorator.bet.factory.validator;

public class BetValidatorFactory {

    public Runnable getValidator(String type) {
        return () -> {
            System.out.println("fc validate 1");
            System.out.println("fc validate 2");
            System.out.println("fc validate 3");
        };
    }
}
