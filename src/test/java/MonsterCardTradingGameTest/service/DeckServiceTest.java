package MonsterCardTradingGameTest.service;

import mctg.model.User;
import mctg.model.Card;
import mctg.model.UserData;
import mctg.persistence.DataAccessException;
import mctg.persistence.UnitOfWork;
import mctg.persistence.repository.*;
import MonsterCardTradingGameTest.persistence.*;

import org.junit.jupiter.api.*;
import org.postgresql.core.ConnectionFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class DeckServiceTest {
    static DeckRepository deckRepository;
    static DeckRepositoryTest deckRepositoryTest;

    @Test
    void getCardsTest(){
        String jdbcUrl = "jdbc:h2:~/mctg;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;INIT=RUNSCRIPT FROM 'classpath:deckTest.sql'";
        UnitOfWork unitOfWork = new UnitOfWork(jdbcUrl);
        deckRepository = new DeckRepositoryImpl(unitOfWork);
        deckRepositoryTest = new DeckRepositoryTestImpl(unitOfWork);
        List<String> cardIds = new ArrayList<>();

        Card card = new Card();
        card.setId("ed1dc1bc-f0aa-4a0c-8d43-1402189b33c8");
        cardIds.add(card.getId());
        card = new Card();
        card.setId("65ff5f23-1e70-4b79-b3bd-f6eb679dd3b5");
        cardIds.add(card.getId());
        card = new Card();
        card.setId("55ef46c4-016c-4168-bc43-6b9b1e86414f");
        cardIds.add(card.getId());
        card = new Card();
        card.setId("f3fad0f2-a1af-45df-b80d-2e48825773d9");
        cardIds.add(card.getId());
        List<Card> cards = deckRepository.getCards(cardIds);

        assertEquals("ed1dc1bc-f0aa-4a0c-8d43-1402189b33c8", cards.get(0).getId());
        assertEquals("WaterGoblin", cards.get(0).getName());
        assertEquals(25, cards.get(0).getDamage());
        assertEquals(0, cards.get(0).getPid());
        assertEquals("test",cards.get(0).getBought());
        assertEquals("Water",cards.get(0).getElementType());

        assertEquals("65ff5f23-1e70-4b79-b3bd-f6eb679dd3b5",cards.get(1).getId());
        assertEquals("Dragon",cards.get(1).getName());
        assertEquals(50, cards.get(1).getDamage());
        assertEquals(0, cards.get(1).getPid());
        assertEquals("test",cards.get(1).getBought());
        assertEquals("Fire",cards.get(1).getElementType());

        assertEquals("55ef46c4-016c-4168-bc43-6b9b1e86414f",cards.get(2).getId());
        assertEquals("WaterSpell",cards.get(2).getName());
        assertEquals(20, cards.get(2).getDamage());
        assertEquals(0, cards.get(2).getPid());
        assertEquals("test",cards.get(2).getBought());
        assertEquals("Water",cards.get(2).getElementType());

        assertEquals("f3fad0f2-a1af-45df-b80d-2e48825773d9",cards.get(3).getId());
        assertEquals("Ork",cards.get(3).getName());
        assertEquals(45, cards.get(3).getDamage());
        assertEquals(0, cards.get(3).getPid());
        assertEquals("test",cards.get(3).getBought());
        assertEquals("Normal",cards.get(3).getElementType());
    }

    @Test
    void createDeckTestAlreadyExists(){
        String jdbcUrl = "jdbc:h2:~/mctg;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;INIT=RUNSCRIPT FROM 'classpath:deckTest.sql'";
        UnitOfWork unitOfWork = new UnitOfWork(jdbcUrl);
        deckRepository = new DeckRepositoryImpl(unitOfWork);
        deckRepositoryTest = new DeckRepositoryTestImpl(unitOfWork);
        List<String> cardIds = new ArrayList<>();

        Card card = new Card();
        card.setId("ed1dc1bc-f0aa-4a0c-8d43-1402189b33c8");
        cardIds.add(card.getId());
        card = new Card();
        card.setId("65ff5f23-1e70-4b79-b3bd-f6eb679dd3b5");
        cardIds.add(card.getId());
        card = new Card();
        card.setId("55ef46c4-016c-4168-bc43-6b9b1e86414f");
        cardIds.add(card.getId());
        card = new Card();
        card.setId("f3fad0f2-a1af-45df-b80d-2e48825773d9");
        cardIds.add(card.getId());
        String token = "test-mtcgToken";
        deckRepository.createDeck(cardIds,token);
        List<Card> cards = deckRepositoryTest.getCreateDeckTest();

        assertEquals("ed1dc1bc-f0aa-4a0c-8d43-1402189b33c8", cards.get(0).getId());
        assertEquals("WaterGoblin", cards.get(0).getName());
        assertEquals(25, cards.get(0).getDamage());
        assertEquals("test",cards.get(0).getBought());
        assertEquals("Water",cards.get(0).getElementType());

        assertEquals("65ff5f23-1e70-4b79-b3bd-f6eb679dd3b5",cards.get(1).getId());
        assertEquals("Dragon",cards.get(1).getName());
        assertEquals(50, cards.get(1).getDamage());
        assertEquals("test",cards.get(1).getBought());
        assertEquals("Fire",cards.get(1).getElementType());

        assertEquals("55ef46c4-016c-4168-bc43-6b9b1e86414f",cards.get(2).getId());
        assertEquals("WaterSpell",cards.get(2).getName());
        assertEquals(20, cards.get(2).getDamage());
        assertEquals("test",cards.get(2).getBought());
        assertEquals("Water",cards.get(2).getElementType());

        assertEquals("f3fad0f2-a1af-45df-b80d-2e48825773d9",cards.get(3).getId());
        assertEquals("Ork",cards.get(3).getName());
        assertEquals(45, cards.get(3).getDamage());
        assertEquals("test",cards.get(3).getBought());
        assertEquals("Normal",cards.get(3).getElementType());

    }

    @Test
    void createDeckTest(){
        String jdbcUrl = "jdbc:h2:~/mctg;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;INIT=RUNSCRIPT FROM 'classpath:deckTest.sql'";
        UnitOfWork unitOfWork = new UnitOfWork(jdbcUrl);
        deckRepository = new DeckRepositoryImpl(unitOfWork);
        deckRepositoryTest = new DeckRepositoryTestImpl(unitOfWork);
        List<String> cardIds = new ArrayList<>();

        Card card = new Card();
        card.setId("ased1dc1bc-f0aa-4a0c-8d43-1402189b33c8");
        cardIds.add(card.getId());
        card = new Card();
        card.setId("as65ff5f23-1e70-4b79-b3bd-f6eb679dd3b5");
        cardIds.add(card.getId());
        card = new Card();
        card.setId("as55ef46c4-016c-4168-bc43-6b9b1e86414f");
        cardIds.add(card.getId());
        card = new Card();
        card.setId("asf3fad0f2-a1af-45df-b80d-2e48825773d9");
        cardIds.add(card.getId());
        String token = "neu-mtcgToken";
        String username = "neu";
        deckRepository.createDeck(cardIds,token);
        List<Card> cards = deckRepositoryTest.getCreateDeckTestIdList(username);

        assertEquals("ased1dc1bc-f0aa-4a0c-8d43-1402189b33c8", cards.get(0).getId());
        assertEquals("WaterGoblin", cards.get(0).getName());
        assertEquals(25, cards.get(0).getDamage());
        assertEquals("neu",cards.get(0).getBought());
        assertEquals("Water",cards.get(0).getElementType());

        assertEquals("as65ff5f23-1e70-4b79-b3bd-f6eb679dd3b5",cards.get(1).getId());
        assertEquals("Dragon",cards.get(1).getName());
        assertEquals(50, cards.get(1).getDamage());
        assertEquals("neu",cards.get(1).getBought());
        assertEquals("Fire",cards.get(1).getElementType());

        assertEquals("as55ef46c4-016c-4168-bc43-6b9b1e86414f",cards.get(2).getId());
        assertEquals("WaterSpell",cards.get(2).getName());
        assertEquals(20, cards.get(2).getDamage());
        assertEquals("neu",cards.get(2).getBought());
        assertEquals("Water",cards.get(2).getElementType());

        assertEquals("asf3fad0f2-a1af-45df-b80d-2e48825773d9",cards.get(3).getId());
        assertEquals("Ork",cards.get(3).getName());
        assertEquals(45, cards.get(3).getDamage());
        assertEquals("neu",cards.get(3).getBought());
        assertEquals("Normal",cards.get(3).getElementType());

    }

    @Test
    void getDeckTest(){
        String jdbcUrl = "jdbc:h2:~/mctg;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;INIT=RUNSCRIPT FROM 'classpath:deckTest.sql'";
        UnitOfWork unitOfWork = new UnitOfWork(jdbcUrl);
        deckRepository = new DeckRepositoryImpl(unitOfWork);
        deckRepositoryTest = new DeckRepositoryTestImpl(unitOfWork);
        String token = "test-mtcgToken";
        List<Card> cards = deckRepository.getDeck(token);

        assertEquals("ed1dc1bc-f0aa-4a0c-8d43-1402189b33c8", cards.get(0).getId());
        assertEquals("WaterGoblin", cards.get(0).getName());
        assertEquals(25, cards.get(0).getDamage());
        assertEquals("test",cards.get(0).getBought());
        assertEquals("Water",cards.get(0).getElementType());

        assertEquals("65ff5f23-1e70-4b79-b3bd-f6eb679dd3b5",cards.get(1).getId());
        assertEquals("Dragon",cards.get(1).getName());
        assertEquals(50, cards.get(1).getDamage());
        assertEquals("test",cards.get(1).getBought());
        assertEquals("Fire",cards.get(1).getElementType());

        assertEquals("55ef46c4-016c-4168-bc43-6b9b1e86414f",cards.get(2).getId());
        assertEquals("WaterSpell",cards.get(2).getName());
        assertEquals(20, cards.get(2).getDamage());
        assertEquals("test",cards.get(2).getBought());
        assertEquals("Water",cards.get(2).getElementType());

        assertEquals("f3fad0f2-a1af-45df-b80d-2e48825773d9",cards.get(3).getId());
        assertEquals("Ork",cards.get(3).getName());
        assertEquals(45, cards.get(3).getDamage());
        assertEquals("test",cards.get(3).getBought());
        assertEquals("Normal",cards.get(3).getElementType());
    }

    @Test
    public void getUsernameTestNotLoggedIn(){
        String jdbcUrl = "jdbc:h2:~/mctg;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;INIT=RUNSCRIPT FROM 'classpath:deckTest.sql'";
        UnitOfWork unitOfWork = new UnitOfWork(jdbcUrl);
        deckRepository = new DeckRepositoryImpl(unitOfWork);
        deckRepositoryTest = new DeckRepositoryTestImpl(unitOfWork);
        String token = "notloggedin-mtcgToken";
        DataAccessException exception = assertThrows(DataAccessException.class, () -> {
            deckRepository.getUsername(token);
        });
        assertTrue(exception.getMessage().contains("User not found"));
    }

    @Test
    public void getUsernameTestLoggedIn(){
        String jdbcUrl = "jdbc:h2:~/mctg;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;INIT=RUNSCRIPT FROM 'classpath:deckTest.sql'";
        UnitOfWork unitOfWork = new UnitOfWork(jdbcUrl);
        deckRepository = new DeckRepositoryImpl(unitOfWork);
        deckRepositoryTest = new DeckRepositoryTestImpl(unitOfWork);
        String token = "test-mtcgToken";
        String username = deckRepository.getUsername(token);
        assertEquals("test", username);
    }
}
