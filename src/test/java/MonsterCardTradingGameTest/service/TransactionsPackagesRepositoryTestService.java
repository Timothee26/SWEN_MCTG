package MonsterCardTradingGameTest.service;

import mctg.model.User;
import mctg.model.Card;
import mctg.model.UserData;
import mctg.persistence.DataAccessException;
import mctg.persistence.UnitOfWork;
import mctg.persistence.repository.CardsRepositoryImpl;
import mctg.persistence.repository.PackageRepository;
import mctg.persistence.repository.TransactionsPackagesRepository;
import mctg.persistence.repository.TransactionsPackagesRepositoryImpl;
import MonsterCardTradingGameTest.persistence.*;

import org.junit.jupiter.api.*;
import org.postgresql.core.ConnectionFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class TransactionsPackagesRepositoryTestService {
    static TransactionsPackagesRepository transactionsPackagesRepository;
    static TransactionsPackagesRepositoryRepositoryTest transactionsPackagesRepositoryTest;

    @Test
    void findCoinsTest(){
        String jdbcUrl = "jdbc:h2:~/mctg;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;INIT=RUNSCRIPT FROM 'classpath:transactionsPackages.sql'";
        UnitOfWork unitOfWork = new UnitOfWork(jdbcUrl);
        transactionsPackagesRepository = new TransactionsPackagesRepositoryImpl(unitOfWork);
        transactionsPackagesRepositoryTest = new TransactionsPackagesRepositoryRepositoryTestImpl(unitOfWork);
        String username = "test";

        assertEquals(20,transactionsPackagesRepository.findCoins(username));
    }

   @Test
    void updateCoinsTest(){
        String jdbcUrl = "jdbc:h2:~/mctg;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;INIT=RUNSCRIPT FROM 'classpath:transactionsPackages.sql'";
        UnitOfWork unitOfWork = new UnitOfWork(jdbcUrl);
        transactionsPackagesRepository = new TransactionsPackagesRepositoryImpl(unitOfWork);
        transactionsPackagesRepositoryTest = new TransactionsPackagesRepositoryRepositoryTestImpl(unitOfWork);
        String username = "test";
        transactionsPackagesRepository.updateCoins(username);
        int coins = transactionsPackagesRepositoryTest.getUpdateCoinsTest(username);

        assertEquals(15,coins);
    }

    @Test
    void updateCoinsTestNEC(){
        String jdbcUrl = "jdbc:h2:~/mctg;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;INIT=RUNSCRIPT FROM 'classpath:transactionsPackages.sql'";
        UnitOfWork unitOfWork = new UnitOfWork(jdbcUrl);
        transactionsPackagesRepository = new TransactionsPackagesRepositoryImpl(unitOfWork);
        transactionsPackagesRepositoryTest = new TransactionsPackagesRepositoryRepositoryTestImpl(unitOfWork);
        String username = "testNOC";

        DataAccessException exception = assertThrows(DataAccessException.class, () -> {
            transactionsPackagesRepository.updateCoins(username);
        });

        assertTrue(exception.getMessage().contains("not enough coins"));

    }

    @Test
    void buyPackageTest(){
        String jdbcUrl = "jdbc:h2:~/mctg;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;INIT=RUNSCRIPT FROM 'classpath:transactionsPackages.sql'";
        UnitOfWork unitOfWork = new UnitOfWork(jdbcUrl);
        transactionsPackagesRepository = new TransactionsPackagesRepositoryImpl(unitOfWork);
        transactionsPackagesRepositoryTest = new TransactionsPackagesRepositoryRepositoryTestImpl(unitOfWork);
        String token = "test-mtcg";
        String username = "test";
        transactionsPackagesRepository.buyPackage(token);
        List<Card> cards = transactionsPackagesRepositoryTest.getBuyPackageTest(username);

        assertEquals(5,cards.size());

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

        assertEquals("shjdfkjdb-a1af-45df-b80d-2e48825773d9",cards.get(4).getId());
        assertEquals("Elf",cards.get(4).getName());
        assertEquals(45, cards.get(4).getDamage());
        assertEquals("test",cards.get(4).getBought());
        assertEquals("Normal",cards.get(4).getElementType());

    }

}
