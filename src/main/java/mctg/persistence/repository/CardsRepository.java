package mctg.persistence.repository;

import java.util.List;

public interface CardsRepository {
    public List<String> showCards(String token);
}
