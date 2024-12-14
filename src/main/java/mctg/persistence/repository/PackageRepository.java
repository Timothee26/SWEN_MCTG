package mctg.persistence.repository;
import mctg.model.Card;

import java.util.List;

public interface PackageRepository {
    void createPackage(List<Card> cards);
}
