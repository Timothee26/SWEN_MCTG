package MonsterCardTradingGameTest.service;

import mctg.model.User;
import mctg.model.Card;
import mctg.model.UserData;
import mctg.persistence.DataAccessException;
import mctg.persistence.UnitOfWork;
import mctg.persistence.repository.CardsRepository;
import mctg.persistence.repository.CardsRepositoryImpl;
import MonsterCardTradingGameTest.persistence.*;

import mctg.persistence.repository.PackageRepositoryImpl;
import mctg.persistence.repository.UserRepositoryImpl;
import org.junit.jupiter.api.*;
import org.postgresql.core.ConnectionFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CardsServiceTest {
    static CardsRepository cardsRepository;
    static CardsRepositoryTest cardsRepositoryTest;

    @Test
    void cardExistsNotTest(){
        String jdbcUrl = "jdbc:h2:~/mctg;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;INIT=RUNSCRIPT FROM 'classpath:createPackageTest.sql'";
        UnitOfWork unitOfWork = new UnitOfWork(jdbcUrl);
        cardsRepository = new CardsRepositoryImpl(unitOfWork);
        cardsRepositoryTest = new CardsRepositoryTestImpl(unitOfWork);
        String cardid = "dsjbfksdjfb";

        DataAccessException exception = assertThrows(DataAccessException.class, () -> {
            cardsRepository.cardExists(cardid);
        });

        assertTrue(exception.getMessage().contains("Could not select card"));
    }

    @Test
    void cardExistsTest(){
        String jdbcUrl = "jdbc:h2:~/mctg;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;INIT=RUNSCRIPT FROM 'classpath:cardsTest.sql'";
        UnitOfWork unitOfWork = new UnitOfWork(jdbcUrl);
        cardsRepository = new CardsRepositoryImpl(unitOfWork);
        cardsRepositoryTest = new CardsRepositoryTestImpl(unitOfWork);
        String cardid = "ed1dcsdfc1bc-f0aa-4a0c-8d43-1402189b33c8";

        assertTrue(cardsRepository.cardExists(cardid));
    }

    @Test
    void updateUserTest() {
        String jdbcUrl = "jdbc:h2:~/mctg;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;INIT=RUNSCRIPT FROM 'classpath:cardsTest.sql'";
        UnitOfWork unitOfWork = new UnitOfWork(jdbcUrl);
        cardsRepository = new CardsRepositoryImpl(unitOfWork);
        cardsRepositoryTest = new CardsRepositoryTestImpl(unitOfWork);
        String cardId = "ed1dcsdfc1bc-f0aa-4a0c-8d43-1402189b33c8";
        String token = "test";
        boolean success = cardsRepository.updateUser(cardId, token);
        List<Card> card = cardsRepositoryTest.getUpdateUserTest(cardId);

        assertFalse(card.isEmpty(), "Die Liste sollte nicht leer sein");
        assertTrue(success);
        assertEquals(cardId, card.get(0).getId());
        assertEquals("Dragon", card.get(0).getName());
        assertEquals(50, card.get(0).getDamage());
        assertEquals(0, card.get(0).getPid());
        assertEquals(token, card.get(0).getBought());
        assertEquals("Fire", card.get(0).getElementType());
    }

    @Test
    void getCardTest(){
        String jdbcUrl = "jdbc:h2:~/mctg;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;INIT=RUNSCRIPT FROM 'classpath:cardsTest.sql'";
        UnitOfWork unitOfWork = new UnitOfWork(jdbcUrl);
        cardsRepository = new CardsRepositoryImpl(unitOfWork);
        cardsRepositoryTest = new CardsRepositoryTestImpl(unitOfWork);
        String cardId = "ed1dcsdfc1bc-f0aa-4a0c-8d43-1402189b33c8";
        Card card = cardsRepository.getCard(cardId);
        assertEquals(cardId, card.getId());
        assertEquals("Dragon", card.getName());
        assertEquals(50, card.getDamage());
        assertEquals(0, card.getPid());
        assertEquals("zero", card.getBought());
    }

    @Test
    void showCardsTest(){
        String jdbcUrl = "jdbc:h2:~/mctg;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;INIT=RUNSCRIPT FROM 'classpath:cardsTest.sql'";
        UnitOfWork unitOfWork = new UnitOfWork(jdbcUrl);
        cardsRepository = new CardsRepositoryImpl(unitOfWork);
        cardsRepositoryTest = new CardsRepositoryTestImpl(unitOfWork);
        String token = "zero-mtcgToken";
        List<Card> cards = cardsRepository.showCards(token);
        assertEquals("ed1dcsdfc1bc-f0aa-4a0c-8d43-1402189b33c8", cards.get(0).getId());
        assertEquals("Dragon", cards.get(0).getName());
        assertEquals(50, cards.get(0).getDamage());
        assertEquals(0, cards.get(0).getPid());
        assertEquals("Fire", cards.get(0).getElementType());

        assertEquals("edsjhdvj1dcsdfc1bc-f0aa-4a0c-8d43-1402189b33c8", cards.get(1).getId());
        assertEquals("WaterSpell", cards.get(1).getName());
        assertEquals(20, cards.get(1).getDamage());
        assertEquals(0, cards.get(1).getPid());
        assertEquals("Water", cards.get(1).getElementType());
    }

    @Test
    public void getUsernameTestNotLoggedIn(){
        String jdbcUrl = "jdbc:h2:~/mctg;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;INIT=RUNSCRIPT FROM 'classpath:cardsTest.sql'";
        UnitOfWork unitOfWork = new UnitOfWork(jdbcUrl);
        cardsRepository = new CardsRepositoryImpl(unitOfWork);
        cardsRepositoryTest = new CardsRepositoryTestImpl(unitOfWork);
        String token = "notloggedin-mtcgToken";
        DataAccessException exception = assertThrows(DataAccessException.class, () -> {
            cardsRepository.getUsername(token);
        });
        assertTrue(exception.getMessage().contains("User not found"));
    }

    @Test
    public void getUsernameTestLoggedIn(){
        String jdbcUrl = "jdbc:h2:~/mctg;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;INIT=RUNSCRIPT FROM 'classpath:cardsTest.sql'";
        UnitOfWork unitOfWork = new UnitOfWork(jdbcUrl);
        cardsRepository = new CardsRepositoryImpl(unitOfWork);
        cardsRepositoryTest = new CardsRepositoryTestImpl(unitOfWork);
        String token = "zero-mtcgToken";
        String username = cardsRepository.getUsername(token);
        assertEquals("zero", username);
    }
}
