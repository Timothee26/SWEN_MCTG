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
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class BattlesServiceTest {
    static BattlesRepository battlesRepository;
    static BattlesRepositoryTest battlesRepositoryTest;

    @Test
    void IfSpellTest() {
        String jdbcUrl = "jdbc:h2:~/mctg;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;INIT=RUNSCRIPT FROM 'classpath:battlesTest.sql'";
        UnitOfWork unitOfWork = new UnitOfWork(jdbcUrl);
        battlesRepository = new BattlesRepositoryImpl(unitOfWork);
        battlesRepositoryTest = new BattlesRepositoryTestImpl(unitOfWork);
        Card fireSpell = battlesRepositoryTest.getCardTest("spell1");
        Card dragon = battlesRepositoryTest.getCardTest("creature1");
        float damage = battlesRepository.checkTypeAndEffectiveness(fireSpell, dragon);
        assertEquals(40, damage);
    }
    @Test
    void WaterVsFireTest() {
        String jdbcUrl = "jdbc:h2:~/mctg;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;INIT=RUNSCRIPT FROM 'classpath:battlesTest.sql'";
        UnitOfWork unitOfWork = new UnitOfWork(jdbcUrl);
        battlesRepository = new BattlesRepositoryImpl(unitOfWork);
        battlesRepositoryTest = new BattlesRepositoryTestImpl(unitOfWork);
        Card waterSpell = battlesRepositoryTest.getCardTest("spell2");
        Card dragon = battlesRepositoryTest.getCardTest("creature2");
        float damage = battlesRepository.checkTypeAndEffectiveness(waterSpell, dragon);
        assertEquals(60, damage);
    }

    @Test
    void FireVsNormalTest() {
        String jdbcUrl = "jdbc:h2:~/mctg;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;INIT=RUNSCRIPT FROM 'classpath:battlesTest.sql'";
        UnitOfWork unitOfWork = new UnitOfWork(jdbcUrl);
        battlesRepository = new BattlesRepositoryImpl(unitOfWork);
        battlesRepositoryTest = new BattlesRepositoryTestImpl(unitOfWork);
        Card fireSpell = battlesRepositoryTest.getCardTest("spell3");
        Card golem = battlesRepositoryTest.getCardTest("creature3");
        float damage = battlesRepository.checkTypeAndEffectiveness(fireSpell, golem);

        assertEquals(80, damage);
    }
    @Test
    void NormalVsWaterTest() {
        String jdbcUrl = "jdbc:h2:~/mctg;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;INIT=RUNSCRIPT FROM 'classpath:battlesTest.sql'";
        UnitOfWork unitOfWork = new UnitOfWork(jdbcUrl);
        battlesRepository = new BattlesRepositoryImpl(unitOfWork);
        battlesRepositoryTest = new BattlesRepositoryTestImpl(unitOfWork);
        Card normalSpell = battlesRepositoryTest.getCardTest("spell4");
        Card kraken = battlesRepositoryTest.getCardTest("creature4");
        float damage = battlesRepository.checkTypeAndEffectiveness(normalSpell, kraken);

        assertEquals(90, damage);
    }
    @Test
    void FireVsWaterTest() {
        String jdbcUrl = "jdbc:h2:~/mctg;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;INIT=RUNSCRIPT FROM 'classpath:battlesTest.sql'";
        UnitOfWork unitOfWork = new UnitOfWork(jdbcUrl);
        battlesRepository = new BattlesRepositoryImpl(unitOfWork);
        battlesRepositoryTest = new BattlesRepositoryTestImpl(unitOfWork);
        Card fireSpell = battlesRepositoryTest.getCardTest("spell5");
        Card kraken = battlesRepositoryTest.getCardTest("creature5");
        float damage = battlesRepository.checkTypeAndEffectiveness(fireSpell, kraken);

        assertEquals(20, damage);
    }

    @Test
    void NormalVsFireTest() {
        String jdbcUrl = "jdbc:h2:~/mctg;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;INIT=RUNSCRIPT FROM 'classpath:battlesTest.sql'";
        UnitOfWork unitOfWork = new UnitOfWork(jdbcUrl);
        battlesRepository = new BattlesRepositoryImpl(unitOfWork);
        battlesRepositoryTest = new BattlesRepositoryTestImpl(unitOfWork);
        Card normalSpell = battlesRepositoryTest.getCardTest("spell6");
        Card dragon = battlesRepositoryTest.getCardTest("creature6");
        float damage = battlesRepository.checkTypeAndEffectiveness(normalSpell, dragon);

        assertEquals(25, damage);
    }

    @Test
    void WaterVsNormalTest() {
        String jdbcUrl = "jdbc:h2:~/mctg;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;INIT=RUNSCRIPT FROM 'classpath:battlesTest.sql'";
        UnitOfWork unitOfWork = new UnitOfWork(jdbcUrl);
        battlesRepository = new BattlesRepositoryImpl(unitOfWork);
        battlesRepositoryTest = new BattlesRepositoryTestImpl(unitOfWork);
        Card waterSpell = battlesRepositoryTest.getCardTest("spell7");
        Card golem = battlesRepositoryTest.getCardTest("creature7");
        float damage = battlesRepository.checkTypeAndEffectiveness(waterSpell, golem);

        assertEquals(30, damage);
    }

    @Test
    void KnightVsWaterSpellTest() {
        String jdbcUrl = "jdbc:h2:~/mctg;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;INIT=RUNSCRIPT FROM 'classpath:battlesTest.sql'";
        UnitOfWork unitOfWork = new UnitOfWork(jdbcUrl);
        battlesRepository = new BattlesRepositoryImpl(unitOfWork);
        battlesRepositoryTest = new BattlesRepositoryTestImpl(unitOfWork);
        Card knight = battlesRepositoryTest.getCardTest("creature8");
        Card waterSpell = battlesRepositoryTest.getCardTest("spell8");

        float damage = battlesRepository.checkTypeAndEffectiveness(waterSpell, knight);

        assertEquals(25, damage);
    }

    @Test
    void GoblinVsDragonTest() {
        String jdbcUrl = "jdbc:h2:~/mctg;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;INIT=RUNSCRIPT FROM 'classpath:battlesTest.sql'";
        UnitOfWork unitOfWork = new UnitOfWork(jdbcUrl);
        battlesRepository = new BattlesRepositoryImpl(unitOfWork);
        battlesRepositoryTest = new BattlesRepositoryTestImpl(unitOfWork);
        Card goblin = battlesRepositoryTest.getCardTest("special1");
        Card dragon = battlesRepositoryTest.getCardTest("special2");
        boolean result = battlesRepository.checkSpecialties(goblin, dragon);

        assertTrue(result);
    }

    @Test
    void WizzardVsOrkTest() {
        String jdbcUrl = "jdbc:h2:~/mctg;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;INIT=RUNSCRIPT FROM 'classpath:battlesTest.sql'";
        UnitOfWork unitOfWork = new UnitOfWork(jdbcUrl);
        battlesRepository = new BattlesRepositoryImpl(unitOfWork);
        battlesRepositoryTest = new BattlesRepositoryTestImpl(unitOfWork);
        Card wizzard = battlesRepositoryTest.getCardTest("special3");
        Card ork = battlesRepositoryTest.getCardTest("special4");

        boolean result = battlesRepository.checkSpecialties(wizzard, ork);

        assertTrue(result);
    }

    @Test
    void DragonVsDragonTest() {
        String jdbcUrl = "jdbc:h2:~/mctg;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;INIT=RUNSCRIPT FROM 'classpath:battlesTest.sql'";
        UnitOfWork unitOfWork = new UnitOfWork(jdbcUrl);
        battlesRepository = new BattlesRepositoryImpl(unitOfWork);
        battlesRepositoryTest = new BattlesRepositoryTestImpl(unitOfWork);
        Card dragon1 = battlesRepositoryTest.getCardTest("special5");
        Card dragon2 = battlesRepositoryTest.getCardTest("special6");

        boolean result = battlesRepository.checkSpecialties(dragon1, dragon2);

        assertTrue(result);
    }

    @Test
    void KrakenVsSpellTest() {
        String jdbcUrl = "jdbc:h2:~/mctg;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;INIT=RUNSCRIPT FROM 'classpath:battlesTest.sql'";
        UnitOfWork unitOfWork = new UnitOfWork(jdbcUrl);
        battlesRepository = new BattlesRepositoryImpl(unitOfWork);
        battlesRepositoryTest = new BattlesRepositoryTestImpl(unitOfWork);
        Card kraken = battlesRepositoryTest.getCardTest("special7");
        Card spell = battlesRepositoryTest.getCardTest("special8");

        boolean result = battlesRepository.checkSpecialties(kraken, spell);

        assertTrue(result);
    }

    @Test
    void FireElveVsDragonTest() {
        String jdbcUrl = "jdbc:h2:~/mctg;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;INIT=RUNSCRIPT FROM 'classpath:battlesTest.sql'";
        UnitOfWork unitOfWork = new UnitOfWork(jdbcUrl);
        battlesRepository = new BattlesRepositoryImpl(unitOfWork);
        battlesRepositoryTest = new BattlesRepositoryTestImpl(unitOfWork);
        Card fireElve = battlesRepositoryTest.getCardTest("special9");
        Card dragon = battlesRepositoryTest.getCardTest("special10");

        boolean result = battlesRepository.checkSpecialties(fireElve, dragon);

        assertTrue(result);
    }

    @Test
    void NoSpecialInteractionTest() {
        String jdbcUrl = "jdbc:h2:~/mctg;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;INIT=RUNSCRIPT FROM 'classpath:battlesTest.sql'";
        UnitOfWork unitOfWork = new UnitOfWork(jdbcUrl);
        battlesRepository = new BattlesRepositoryImpl(unitOfWork);
        battlesRepositoryTest = new BattlesRepositoryTestImpl(unitOfWork);
        Card warrior = battlesRepositoryTest.getCardTest("special11");
        Card fireSpell = battlesRepositoryTest.getCardTest("special12");

        boolean result = battlesRepository.checkSpecialties(warrior, fireSpell);

        assertFalse(result);
    }

    @Test
    void battlesTest(){
        String jdbcUrl = "jdbc:h2:~/mctg;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;INIT=RUNSCRIPT FROM 'classpath:battlesTest.sql'";
        UnitOfWork unitOfWork = new UnitOfWork(jdbcUrl);
        battlesRepository = new BattlesRepositoryImpl(unitOfWork);
        battlesRepositoryTest = new BattlesRepositoryTestImpl(unitOfWork);
        List<String> tokens = new ArrayList<>();
        tokens.add("test-mtcgToken");
        tokens.add("neu-mtcgToken");

        List<String> log = battlesRepository.battles(tokens);
        assertTrue(log.contains("Player 1 won\n"));

    }




}
