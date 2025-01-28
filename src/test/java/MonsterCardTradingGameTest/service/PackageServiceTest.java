package MonsterCardTradingGameTest.service;


import mctg.model.User;
import mctg.model.Card;
import mctg.model.UserData;
import mctg.persistence.DataAccessException;
import mctg.persistence.UnitOfWork;
import mctg.persistence.repository.PackageRepository;
import mctg.persistence.repository.PackageRepositoryImpl;
import MonsterCardTradingGameTest.persistence.*;

import org.junit.jupiter.api.*;
import org.postgresql.core.ConnectionFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;



public class PackageServiceTest {
    static PackageRepository packageRepository;
    static PackageRepositoryTest packageRepositoryTest;

    @Test
    void createPackageTest(){
        String jdbcUrl = "jdbc:h2:~/mctg;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;INIT=RUNSCRIPT FROM 'classpath:createPackageTest.sql'";
        UnitOfWork unitOfWork = new UnitOfWork(jdbcUrl);
        packageRepository = new PackageRepositoryImpl(unitOfWork);
        packageRepositoryTest = new PackageRepositoryTestImpl(unitOfWork);
        Card card = new Card();
        card.setId("ed1dc1bc-f0aa-4a0c-8d43-1402189b33c8");
        card.setName("WaterGoblin");
        card.setDamage(10);
        List<Card> cards = new ArrayList<>();
        cards.add(card);
        card = new Card();
        card.setId("65ff5f23-1e70-4b79-b3bd-f6eb679dd3b5");
        card.setName("Dragon");
        card.setDamage(50);
        cards.add(card);
        card = new Card();
        card.setId("55ef46c4-016c-4168-bc43-6b9b1e86414f");
        card.setName("WaterSpell");
        card.setDamage(20);
        cards.add(card);
        card = new Card();
        card.setId("f3fad0f2-a1af-45df-b80d-2e48825773d9");
        card.setName("Ork");
        card.setDamage(45);
        cards.add(card);
        card = new Card();
        card.setId("8c20639d-6400-4534-bd0f-ae563f11f57a");
        card.setName("WaterSpell");
        card.setDamage(25);
        cards.add(card);
        String token = "admin-mtcgToken";
        packageRepository.createPackage(cards,token);
        List<Card> createdCards = packageRepositoryTest.getCreatedPackageTest();
        assertEquals("ed1dc1bc-f0aa-4a0c-8d43-1402189b33c8",createdCards.get(0).getId());
        assertEquals("WaterGoblin",createdCards.get(0).getName());
        assertEquals(10,createdCards.get(0).getDamage());
        assertEquals("zero",createdCards.get(0).getBought());
        assertEquals(1,createdCards.get(0).getPid());
        assertEquals("Water",createdCards.get(0).getElementType());

        assertEquals("65ff5f23-1e70-4b79-b3bd-f6eb679dd3b5",createdCards.get(1).getId());
        assertEquals("Dragon",createdCards.get(1).getName());
        assertEquals(50,createdCards.get(1).getDamage());
        assertEquals("zero",createdCards.get(1).getBought());
        assertEquals(1,createdCards.get(1).getPid());
        assertEquals("Fire",createdCards.get(1).getElementType());

        assertEquals("55ef46c4-016c-4168-bc43-6b9b1e86414f",createdCards.get(2).getId());
        assertEquals("WaterSpell",createdCards.get(2).getName());
        assertEquals(20,createdCards.get(2).getDamage());
        assertEquals("zero",createdCards.get(2).getBought());
        assertEquals(1,createdCards.get(2).getPid());
        assertEquals("Water",createdCards.get(2).getElementType());

        assertEquals("f3fad0f2-a1af-45df-b80d-2e48825773d9",createdCards.get(3).getId());
        assertEquals("Ork",createdCards.get(3).getName());
        assertEquals(45,createdCards.get(3).getDamage());
        assertEquals("zero",createdCards.get(3).getBought());
        assertEquals(1,createdCards.get(3).getPid());
        assertEquals("Normal",createdCards.get(3).getElementType());

        assertEquals("8c20639d-6400-4534-bd0f-ae563f11f57a",createdCards.get(4).getId());
        assertEquals("WaterSpell",createdCards.get(4).getName());
        assertEquals(25,createdCards.get(4).getDamage());
        assertEquals("zero",createdCards.get(4).getBought());
        assertEquals(1,createdCards.get(4).getPid());
        assertEquals("Water",createdCards.get(4).getElementType());

    }
}
