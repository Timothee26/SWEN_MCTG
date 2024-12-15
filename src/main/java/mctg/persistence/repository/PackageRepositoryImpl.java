package mctg.persistence.repository;

import mctg.persistence.DataAccessException;
import mctg.persistence.UnitOfWork;
import mctg.model.Card;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PackageRepositoryImpl implements PackageRepository {
    private UnitOfWork unitOfWork;

    public PackageRepositoryImpl(UnitOfWork unitOfWork){

        this.unitOfWork = unitOfWork;
    }

    public int getPid() {
        String sql = "SELECT max(pid) AS max_pid FROM userdb.package";
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

    @Override
    public void createPackage(List<Card> cards){
        int pid = getPid()+1;
        for(Card card : cards){
            String sql = "INSERT INTO userdb.package (id, name, damage, pid, bought) VALUES (?, ?, ?, ?, ?)";
            try(PreparedStatement stmt = this.unitOfWork.prepareStatement(sql)){
                stmt.setString(1, card.getId());
                stmt.setString(2, card.getName());
                stmt.setInt(3, card.getDamage());
                stmt.setInt(4,pid);
                stmt.setString(5,"zero");
                int rowsInserted = stmt.executeUpdate();

                if (rowsInserted > 0) {
                    System.out.println("Row inserted successfully.");
                } else {
                    System.out.println("No rows inserted.");
                }
            } catch (SQLException e){
                throw new DataAccessException("Could not insert into database",e);
            }
            unitOfWork.commitTransaction();
        }
    }
}
