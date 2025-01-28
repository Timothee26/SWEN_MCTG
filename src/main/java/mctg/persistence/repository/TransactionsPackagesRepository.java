package mctg.persistence.repository;

import java.util.List;

public interface TransactionsPackagesRepository {
    public List<String> buyPackage(String token);
    int findCoins(String username);
    void updateCoins(String username);
}
