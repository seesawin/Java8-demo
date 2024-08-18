package dp.decorator.bet.handler;

import dp.decorator.bet.bo.BetHandlerDTO;
import dp.decorator.bet.bo.ConfirmBet;

import java.math.BigDecimal;

public class ConfirmBetCreator implements IBetHandler {

    private IBetHandler next;

    @Override
    public BigDecimal handle(BetHandlerDTO betHandlerDTO) {
        System.out.println("create confirm bet");
        betHandlerDTO.setConfirmBet(ConfirmBet.builder().build());

        return next.handle(betHandlerDTO);
    }

    @Override
    public IBetHandler setNext(IBetHandler next) {
        this.next = next;
        return this;
    }

}