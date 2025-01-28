package MonsterCardTradingGameTest.service;

import mctg.model.User;
import mctg.model.Card;
import mctg.model.UserData;
import mctg.persistence.DataAccessException;
import mctg.persistence.UnitOfWork;
import mctg.persistence.repository.DeckRepository;
import mctg.persistence.repository.DeckRepositoryImpl;
import MonsterCardTradingGameTest.persistence.*;

import mctg.persistence.repository.PackageRepository;
import org.junit.jupiter.api.*;
import org.postgresql.core.ConnectionFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class DeckServiceTest {
    static DeckRepository deckRepository;
    static DeckRepositoryTest DeckRepositoryTest;
}
