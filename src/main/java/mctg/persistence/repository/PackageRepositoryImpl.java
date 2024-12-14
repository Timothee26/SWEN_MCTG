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

    @Override
    public void createPackage(List<Card> cards){
        for(Card card : cards){
            String sql = "INSERT INTO userdb.package (id, name, damage) VALUES (?, ?, ?)";
            try(PreparedStatement stmt = this.unitOfWork.prepareStatement(sql)){
                stmt.setString(1, card.getId());
                stmt.setString(2, card.getName());
                stmt.setInt(3, card.getDamage());
                int rowsInserted = stmt.executeUpdate();

                if (rowsInserted > 0) {
                    System.out.println("Register data inserted successfully.");
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
