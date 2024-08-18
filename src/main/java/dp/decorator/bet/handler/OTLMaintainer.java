package dp.decorator.bet.handler;

import dp.decorator.bet.bo.BetHandlerDTO;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public final class OTLMaintainer implements IBetHandler {

    private final Object redis;
    private IBetHandler next;

    @Override
    public BigDecimal handle(BetHandlerDTO betHandlerDTO) {
        System.out.println("OTL status WAITING");

        try {
            System.out.println("OTL insert PROCESSING");
        } catch (Exception e) {
            if (e.getMessage() == "ALREADY_PROCESSING") {
                throw new RuntimeException(e);
            }
            if (e.getMessage() == "ALREADY_SUCCESS") {
                return BigDecimal.valueOf(100L);
            }
            if (e.getMessage() == "ALREADY_FAILED") {
                System.out.println("OTL update PROCESSING");
            }
        }

        return doHandle(betHandlerDTO);
    }

    private BigDecimal doHandle(BetHandlerDTO betHandlerDTO) {
        try {
            return next.handle(betHandlerDTO);
        } catch (Exception e) {
            System.out.println("OTL insert FAILED");
            throw new RuntimeException(e);
        }
    }

    @Override
    public IBetHandler setNext(IBetHandler next) {
        this.next = next;
        return this;
    }

}
