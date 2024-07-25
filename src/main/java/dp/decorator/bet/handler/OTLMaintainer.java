package dp.decorator.bet.handler;

import dp.decorator.bet.bo.BetHandlerDTO;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public final class OTLMaintainer implements IBetHandler {

    private final IBetHandler next;

    @Override
    public BigDecimal handle(BetHandlerDTO betHandlerDTO) {
        System.out.println("OTL status WAITING");
        try {
            return next.handle(betHandlerDTO);
        } catch (Exception e) {
            System.out.println("OTL status ERROR");
            throw new RuntimeException(e);
        } finally {
            System.out.println("OTL status complete");
        }
    }
}
