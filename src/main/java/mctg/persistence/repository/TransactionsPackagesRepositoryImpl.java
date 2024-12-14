package mctg.persistence.repository;

import mctg.model.User;
import mctg.persistence.DataAccessException;
import mctg.persistence.UnitOfWork;
import mctg.model.Card;
import mctg.model.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class TransactionsPackagesRepositoryImpl implements TransactionsPackagesRepository {
    private UnitOfWork unitOfWork;

    public TransactionsPackagesRepositoryImpl(UnitOfWork unitOfWork){

        this.unitOfWork = unitOfWork;
    }

    public int getPid() {
        String sql = "SELECT max(pid) AS max_pid FROM userdb.package WHERE bought = zero";
        try (PreparedStatement stmt = this.unitOfWork.prepareStatement(sql)) {
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("max_pid");
            } else {
                return 0;
            }
        } catch (SQLException e) {
            throw new DataAccessException("Select nicht erfolgreich", e);
        }
    }

    public int findCoins(String username) {
        String sql = "select coins from userdb.user where Username = ?";
        try (PreparedStatement stmt = this.unitOfWork.prepareStatement(sql))
        {
            stmt.setString(1, username);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("coins"); // Erfolgreiche Abfrage
            } else {
                return 0; // Benutzer nicht gefunden, daher keine Coins
            }
        } catch (SQLException e) {
            throw new DataAccessException("Select nicht erfolgreich", e);
        }
    }
    public void updateCoins(String username) {
        String sql = "UPDATE userdb.coins set coins = ? where Username = ?";

        try (PreparedStatement stmt = this.unitOfWork.prepareStatement(sql))
        {
            stmt.setInt(1, findCoins(username)-5);
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Row inserted successfully.");
            } else {
                System.out.println("No rows inserted.");
            }

        } catch (SQLException e) {
            throw new DataAccessException("Select nicht erfolgreich", e);
        }
        unitOfWork.commitTransaction();
    }

    public void buyPackage(String username){
        System.out.println(username);
        int coins = findCoins(username);
        int pid = getPid();
        if (coins >= 5){
            Random rand = new Random();
            int n = rand.nextInt(pid-1);
            String sql = "UPDATE userdb.package set bought=? where pid=?";
            try(PreparedStatement stmt = this.unitOfWork.prepareStatement(sql)){
                stmt.setString(1, username);
                stmt.setInt(2, n+1);
            }catch (SQLException e){
                throw new DataAccessException("Could not insert into database",e);
            }
            updateCoins(username);
            unitOfWork.commitTransaction();
        }

    }



}
