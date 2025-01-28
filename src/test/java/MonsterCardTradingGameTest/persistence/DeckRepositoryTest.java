package MonsterCardTradingGameTest.persistence;

import mctg.model.User;

import java.util.Collection;
import java.util.List;
import mctg.model.Card;
public interface DeckRepositoryTest {
    List<Card> getCreateDeckTest();
    List<Card> getCreateDeckTestIdList(String token);
}
