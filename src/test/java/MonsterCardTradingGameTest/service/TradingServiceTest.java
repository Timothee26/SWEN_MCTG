package MonsterCardTradingGameTest.service;

import mctg.model.Trade;
import mctg.model.User;
import mctg.model.Card;
import mctg.model.UserData;
import mctg.persistence.DataAccessException;
import mctg.persistence.UnitOfWork;
import mctg.persistence.repository.*;
import MonsterCardTradingGameTest.persistence.*;

import mctg.persistence.repository.TradingRepository;
import mctg.service.TradingService;
import org.junit.jupiter.api.*;
import org.postgresql.core.ConnectionFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class TradingServiceTest {
    static TradingRepository tradingRepository;
    static TradingRepositoryTest tradingRepositoryTest;

    @Test
    void getTradingsOffersTest(){
        String jdbcUrl = "jdbc:h2:~/mctg;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;INIT=RUNSCRIPT FROM 'classpath:tradesTest.sql'";
        UnitOfWork unitOfWork = new UnitOfWork(jdbcUrl);
        tradingRepository = new TradingRepositoryImpl(unitOfWork);
        tradingRepositoryTest = new TradingRepositoryTestImpl(unitOfWork);
        String token = "test-mtcgToken";
        List<Trade> trades =  tradingRepository.getTradingOffers(token);

        assertEquals("1", trades.get(0).getId());
        assertEquals("ed1dc1bc-f0aa-4a0c-8d43-1402189b33c8", trades.get(0).getCardToTrade());
        assertEquals("Water", trades.get(0).getType());
        assertEquals(20, trades.get(0).getMinimumDamage());
        assertEquals("test", trades.get(0).getUser());

        assertEquals("2", trades.get(1).getId());
        assertEquals("as65ff5f23-1e70-4b79-b3bd-f6eb679dd3b5", trades.get(1).getCardToTrade());
        assertEquals("Fire", trades.get(1).getType());
        assertEquals(20, trades.get(1).getMinimumDamage());
        assertEquals("neu", trades.get(1).getUser());

    }

    @Test
    void checkCardOwnerTest(){
        String jdbcUrl = "jdbc:h2:~/mctg;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;INIT=RUNSCRIPT FROM 'classpath:tradesTest.sql'";
        UnitOfWork unitOfWork = new UnitOfWork(jdbcUrl);
        tradingRepository = new TradingRepositoryImpl(unitOfWork);
        tradingRepositoryTest = new TradingRepositoryTestImpl(unitOfWork);
        String username = "neu";
        String cardId = "as65ff5f23-1e70-4b79-b3bd-f6eb679dd3b5";
        assertTrue(tradingRepository.checkCardOwner(username, cardId));
    }

    @Test
    void checkCardOwnerTestFalseOwner(){
        String jdbcUrl = "jdbc:h2:~/mctg;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;INIT=RUNSCRIPT FROM 'classpath:tradesTest.sql'";
        UnitOfWork unitOfWork = new UnitOfWork(jdbcUrl);
        tradingRepository = new TradingRepositoryImpl(unitOfWork);
        tradingRepositoryTest = new TradingRepositoryTestImpl(unitOfWork);
        String username = "test";
        String cardId = "as65ff5f23-1e70-4b79-b3bd-f6eb679dd3b5";
        assertFalse(tradingRepository.checkCardOwner(username, cardId));
    }

    @Test
    void checkCardOwnerTestFalseId(){
        String jdbcUrl = "jdbc:h2:~/mctg;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;INIT=RUNSCRIPT FROM 'classpath:tradesTest.sql'";
        UnitOfWork unitOfWork = new UnitOfWork(jdbcUrl);
        tradingRepository = new TradingRepositoryImpl(unitOfWork);
        tradingRepositoryTest = new TradingRepositoryTestImpl(unitOfWork);
        String username = "neu";
        String cardId = "asd";
        assertFalse(tradingRepository.checkCardOwner(username, cardId));
    }

    @Test
    void checkDeckOwnerTest(){
        String jdbcUrl = "jdbc:h2:~/mctg;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;INIT=RUNSCRIPT FROM 'classpath:tradesTest.sql'";
        UnitOfWork unitOfWork = new UnitOfWork(jdbcUrl);
        tradingRepository = new TradingRepositoryImpl(unitOfWork);
        tradingRepositoryTest = new TradingRepositoryTestImpl(unitOfWork);
        String username = "test";
        String cardId = "ed1dc1bc-f0aa-4a0c-8d43-1402189b33c8";
        assertTrue(tradingRepository.checkDeckOwner(username, cardId));
    }

    @Test
    void checkDeckOwnerTestNotInDeck(){
        String jdbcUrl = "jdbc:h2:~/mctg;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;INIT=RUNSCRIPT FROM 'classpath:tradesTest.sql'";
        UnitOfWork unitOfWork = new UnitOfWork(jdbcUrl);
        tradingRepository = new TradingRepositoryImpl(unitOfWork);
        tradingRepositoryTest = new TradingRepositoryTestImpl(unitOfWork);
        String username = "neu";
        String cardId = "as65ff5f23-1e70-4b79-b3bd-f6eb679dd3b5";
        assertFalse(tradingRepository.checkDeckOwner(username, cardId));
    }

    @Test
    void createTradeTest(){
        String jdbcUrl = "jdbc:h2:~/mctg;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;INIT=RUNSCRIPT FROM 'classpath:tradesTest.sql'";
        UnitOfWork unitOfWork = new UnitOfWork(jdbcUrl);
        tradingRepository = new TradingRepositoryImpl(unitOfWork);
        tradingRepositoryTest = new TradingRepositoryTestImpl(unitOfWork);
        String token = "test-mtcgToken";
        Trade trade = new Trade();
        trade.setCardToTrade("ksjhdfk-1e70-4b79-b3bd-f6eb679dd3b5");
        trade.setUser("test");
        trade.setType("Fire");
        trade.setMinimumDamage(10);
        trade.setId("skdbfsklkn");
        tradingRepository.createTrade(token, trade);
        Trade neuTrade = tradingRepositoryTest.getCreatedTradeTest(trade.getId());

        assertEquals(trade.getId(), neuTrade.getId());
        assertEquals(trade.getCardToTrade(), neuTrade.getCardToTrade());
        assertEquals(trade.getUser(), neuTrade.getUser());
        assertEquals(trade.getType(), neuTrade.getType());
        assertEquals(trade.getMinimumDamage(), neuTrade.getMinimumDamage());
    }

    @Test
    void createTradeTestCardInDeck(){
        String jdbcUrl = "jdbc:h2:~/mctg;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;INIT=RUNSCRIPT FROM 'classpath:tradesTest.sql'";
        UnitOfWork unitOfWork = new UnitOfWork(jdbcUrl);
        tradingRepository = new TradingRepositoryImpl(unitOfWork);
        tradingRepositoryTest = new TradingRepositoryTestImpl(unitOfWork);
        String token = "test-mtcgToken";
        Trade trade = new Trade();
        trade.setCardToTrade("skdjfgk-1e70-4b79-b3bd-f6eb679dd3b5");
        trade.setUser("test");
        trade.setType("Fire");
        trade.setMinimumDamage(10);
        trade.setId("kdjf,n");

        DataAccessException exception = assertThrows(DataAccessException.class, () -> {
            tradingRepository.createTrade(token, trade);
        });
        assertTrue(exception.getMessage().contains("Card is in Deck"));
    }

    @Test
    void createTradeTestWrongOwner(){
        String jdbcUrl = "jdbc:h2:~/mctg;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;INIT=RUNSCRIPT FROM 'classpath:tradesTest.sql'";
        UnitOfWork unitOfWork = new UnitOfWork(jdbcUrl);
        tradingRepository = new TradingRepositoryImpl(unitOfWork);
        tradingRepositoryTest = new TradingRepositoryTestImpl(unitOfWork);
        String token = "test-mtcgToken";
        Trade trade = new Trade();
        trade.setCardToTrade("mqwef-1e70-4b79-b3bd-f6eb679dd3b5");
        trade.setUser("test");
        trade.setType("Fire");
        trade.setMinimumDamage(10);
        trade.setId("kdjf,n");

        DataAccessException exception = assertThrows(DataAccessException.class, () -> {
            tradingRepository.createTrade(token, trade);
        });
        assertTrue(exception.getMessage().contains("You are not the owner of this card"));
    }

    @Test
    void getTradeTest(){
        String jdbcUrl = "jdbc:h2:~/mctg;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;INIT=RUNSCRIPT FROM 'classpath:tradesTest.sql'";
        UnitOfWork unitOfWork = new UnitOfWork(jdbcUrl);
        tradingRepository = new TradingRepositoryImpl(unitOfWork);
        tradingRepositoryTest = new TradingRepositoryTestImpl(unitOfWork);
        String tradId = "1";
        Trade trade = tradingRepository.getTrade(tradId);

        assertEquals("1", trade.getId());
        assertEquals("ed1dc1bc-f0aa-4a0c-8d43-1402189b33c8", trade.getCardToTrade());
        assertEquals("Water", trade.getType());
        assertEquals(20, trade.getMinimumDamage());
        assertEquals("test", trade.getUser());
    }

    @Test
    void deleteTradeTest(){
        String jdbcUrl = "jdbc:h2:~/mctg;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;INIT=RUNSCRIPT FROM 'classpath:tradesTest.sql'";
        UnitOfWork unitOfWork = new UnitOfWork(jdbcUrl);
        tradingRepository = new TradingRepositoryImpl(unitOfWork);
        tradingRepositoryTest = new TradingRepositoryTestImpl(unitOfWork);
        String tradId = "1";
        assertTrue(tradingRepository.deleteTrade(tradId));

        DataAccessException exception = assertThrows(DataAccessException.class, () -> {
            tradingRepository.getTrade(tradId);
        });
        assertTrue(exception.getMessage().contains("Trade with ID " + tradId + " not found"));

    }

    @Test
    void acceptTradingOfferTest(){
        String jdbcUrl = "jdbc:h2:~/mctg;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;INIT=RUNSCRIPT FROM 'classpath:tradesTest.sql'";
        UnitOfWork unitOfWork = new UnitOfWork(jdbcUrl);
        tradingRepository = new TradingRepositoryImpl(unitOfWork);
        tradingRepositoryTest = new TradingRepositoryTestImpl(unitOfWork);
        String token = "test-mtcgToken";
        String cardId = "ksjhdfk-1e70-4b79-b3bd-f6eb679dd3b5";
        String offerId = "2";
        Trade trade = tradingRepository.getTrade(offerId);

        Card card1before = tradingRepositoryTest.getCardTest(cardId);
        Card card2before = tradingRepositoryTest.getCardTest(trade.getCardToTrade());

        assertEquals("test", card1before.getBought());
        assertEquals("Dragon", card1before.getName());
        assertEquals("neu", card2before.getBought());
        assertEquals("FireGoblin", card2before.getName());


        tradingRepository.acceptTradingOffer(token, cardId, offerId);

        Card card1 = tradingRepositoryTest.getCardTest(cardId);
        Card card2 = tradingRepositoryTest.getCardTest(trade.getCardToTrade());

        assertEquals("neu", card1.getBought());
        assertEquals("Dragon", card1.getName());
        assertEquals("test", card2.getBought());
        assertEquals("FireGoblin", card2.getName());

        DataAccessException exception = assertThrows(DataAccessException.class, () -> {
            tradingRepository.getTrade(offerId);
        });
        assertTrue(exception.getMessage().contains("Trade with ID " + offerId + " not found"));
    }

    @Test
    void deleteTradingOfferTest(){
        String jdbcUrl = "jdbc:h2:~/mctg;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;INIT=RUNSCRIPT FROM 'classpath:tradesTest.sql'";
        UnitOfWork unitOfWork = new UnitOfWork(jdbcUrl);
        tradingRepository = new TradingRepositoryImpl(unitOfWork);
        tradingRepositoryTest = new TradingRepositoryTestImpl(unitOfWork);
        String token = "test-mtcgToken";
        String offerId = "1";
        tradingRepository.deleteTradingOffer(token, offerId);
        DataAccessException exception = assertThrows(DataAccessException.class, () -> {
            tradingRepository.getTrade(offerId);
        });
        assertTrue(exception.getMessage().contains("Trade with ID " + offerId + " not found"));
    }

    @Test
    void deleteTradingOfferTestWrongOfferId(){
        String jdbcUrl = "jdbc:h2:~/mctg;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;INIT=RUNSCRIPT FROM 'classpath:tradesTest.sql'";
        UnitOfWork unitOfWork = new UnitOfWork(jdbcUrl);
        tradingRepository = new TradingRepositoryImpl(unitOfWork);
        tradingRepositoryTest = new TradingRepositoryTestImpl(unitOfWork);
        String token = "test-mtcgToken";
        String offerId = "2";
        DataAccessException exception = assertThrows(DataAccessException.class, () -> {
            tradingRepository.deleteTradingOffer(token, offerId);
        });
        assertTrue(exception.getMessage().contains("this is not your trade"));
    }

    @Test
    void deleteTradingOfferTestOfferIdNotExist(){
        String jdbcUrl = "jdbc:h2:~/mctg;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;INIT=RUNSCRIPT FROM 'classpath:tradesTest.sql'";
        UnitOfWork unitOfWork = new UnitOfWork(jdbcUrl);
        tradingRepository = new TradingRepositoryImpl(unitOfWork);
        tradingRepositoryTest = new TradingRepositoryTestImpl(unitOfWork);
        String token = "test-mtcgToken";
        String offerId = "3";
        DataAccessException exception = assertThrows(DataAccessException.class, () -> {
            tradingRepository.deleteTradingOffer(token, offerId);
        });
        assertTrue(exception.getMessage().contains("Trade with ID " + offerId + " not found"));
    }

    @Test
    void deleteTradingOfferTestNotLoggedIn(){
        String jdbcUrl = "jdbc:h2:~/mctg;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;INIT=RUNSCRIPT FROM 'classpath:tradesTest.sql'";
        UnitOfWork unitOfWork = new UnitOfWork(jdbcUrl);
        tradingRepository = new TradingRepositoryImpl(unitOfWork);
        tradingRepositoryTest = new TradingRepositoryTestImpl(unitOfWork);
        String token = "notloggedin-mtcgToken";
        String offerId = "3";
        DataAccessException exception = assertThrows(DataAccessException.class, () -> {
            tradingRepository.deleteTradingOffer(token, offerId);
        });
        assertTrue(exception.getMessage().contains("User not found"));
    }

}
