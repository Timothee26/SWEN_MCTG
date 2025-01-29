package MonsterCardTradingGameTest.persistence;

import mctg.model.Card;

import java.util.List;
public interface TransactionsPackagesRepositoryTest {
    int getUpdateCoinsTest(String username);
    List<Card> getBuyPackageTest(String token);
}
