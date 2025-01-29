package MonsterCardTradingGameTest.persistence;

import mctg.model.Card;
import mctg.model.Trade;

public interface TradingRepositoryTest {
    Trade getCreatedTradeTest(String tradeId);
    Card getCardTest(String cardId);
}
