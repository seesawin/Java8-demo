package dp.decorator.bet.bo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BetHandlerDTO {
    private Player player;
    private ConfirmBet confirmBet;
    private String newBalance;
}
