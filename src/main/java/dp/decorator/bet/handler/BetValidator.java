package dp.decorator.bet.handler;

import dp.decorator.bet.bo.BetHandlerDTO;
import dp.decorator.bet.factory.validator.BetValidatorFactory;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class BetValidator implements IBetHandler {

    private final BetValidatorFactory betValidatorFactory;

    private IBetHandler next;

    @Override
    public BigDecimal handle(BetHandlerDTO betHandlerDTO) {
        System.out.println("validate player");
        System.out.println("validate machineType");
        System.out.println("validate balance");
        System.out.println("validate session");

        betValidatorFactory.getValidator("type").run();

        return next.handle(betHandlerDTO);
    }

    @Override
    public IBetHandler setNext(IBetHandler next) {
        this.next = next;
        return this;
    }

}