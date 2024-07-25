package dp.decorator.balance;

import java.math.BigDecimal;

public interface IWallet {
    BigDecimal getBalance(String uidA);
}
