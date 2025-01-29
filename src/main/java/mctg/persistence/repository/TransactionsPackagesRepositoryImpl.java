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

    public List<Integer> getPid() {
        String sql = "SELECT pid FROM userdb.package WHERE bought = ?";
        List<Integer> pids = new ArrayList<>();
        try (PreparedStatement stmt = this.unitOfWork.prepareStatement(sql)) {
            stmt.setString(1, "zero");
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                int pid = resultSet.getInt("pid");
                pids.add(pid);
                System.out.println("Found pid: " + pid);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Select nicht erfolgreich", e);
        }
        return pids;
    }

    public int findCoins(String username) {
        try (PreparedStatement stmt = this.unitOfWork.prepareStatement("""
                select coins from userdb."user" where Username = ?"""))
        {
            stmt.setString(1, username);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                System.out.println("findcoins" +resultSet.getInt("coins"));
                return resultSet.getInt("coins");
            } else {
                return 0;
            }
        } catch (SQLException e) {
            throw new DataAccessException("Select nicht erfolgreich", e);
        }
    }
    public void updateCoins(String username) {
        int coins = findCoins(username);
        if (coins-5 >= 0) {
            try (PreparedStatement stmt = this.unitOfWork.prepareStatement("""
                    UPDATE userdb."user" set coins = ? where Username = ?""")) {
                stmt.setInt(1, coins - 5);
                stmt.setString(2, username);
                int rowsInserted = stmt.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Row updated successfully.");
                } else {
                    System.out.println("No rows updated.");
                }

            } catch (SQLException e) {
                throw new DataAccessException("Select nicht erfolgreich", e);
            }
            unitOfWork.commitTransaction();
        }else{
            throw new DataAccessException("not enough coins");
        }
    }

    public String getUsername(String token){
        System.out.println("token :" + token);
        System.out.println("in der abfrage");
        String sql = "SELECT * from userdb.login where token = ?";
        try(PreparedStatement stmt = this.unitOfWork.prepareStatement(sql)){
            stmt.setString(1, token);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                System.out.println("in der while");
                User user = User.builder()
                        .username(resultSet.getString(1))
                        .token(resultSet.getString(2))
                        .build();
                System.out.println("user.token:" + user.getToken());
                if(user.getToken().contains(token)){
                    System.out.println("user gefunden");
                    return user.getUsername();
                }
            }
        }catch (SQLException e) {
            throw new DataAccessException("Select nicht erfolgreich", e);
        }
        throw new DataAccessException("User not found");
    }


    public List<String> buyPackage(String token){
        String username = getUsername(token);
        System.out.println("buyPackage" +username);
        int coins = findCoins(username);
        System.out.println("buyPackage" +coins);
        List<Integer> pids = getPid();
        if (pids.isEmpty()) {
            System.out.println("No packages available to buy.");
            return null;
        }
        if (coins >= 5){
            Random rand = new Random();
            int n = rand.nextInt(pids.size());
            int selectedPid = pids.get(n);
            System.out.println("buyPackage random" + selectedPid);
            String sql = "UPDATE userdb.package set bought=? where pid=?";
            try(PreparedStatement stmt = this.unitOfWork.prepareStatement(sql)){
                stmt.setString(1, username);
                stmt.setInt(2, selectedPid);
                int rowsInserted = stmt.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Row updated successfully.");
                } else {
                    System.out.println("No rows updated.");
                }
            }catch (SQLException e){
                throw new DataAccessException("Could not insert into database",e);
            }
            unitOfWork.commitTransaction();
            updateCoins(username);
        }
        return null;
    }



}
