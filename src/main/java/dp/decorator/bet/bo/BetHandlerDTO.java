package dp.decorator.bet.bo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BetHandlerDTO {
    private Player player;
    private TemplateBet templateBet;
    private ConfirmBet confirmBet;
}
