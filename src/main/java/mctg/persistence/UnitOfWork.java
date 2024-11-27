package mctg.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UnitOfWork implements AutoCloseable{

    public Connection connection;
    public UnitOfWork() {
        this.connection = DatabaseManager.INSTANCE.getConnection();
        try {
            this.connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new DataAccessException("Autocommit nicht deaktivierbar", e);
        }
    }

    public Connection getConnection() {
        return connection;
    }


    /**
     * the transaction (insert into database) is commited
     * if an error occurs error is thrown with a message
     */
    public void commitTransaction()
    {
        if (this.connection != null) {
            try {
                this.connection.commit();
            } catch (SQLException e) {
                throw new DataAccessException("Commit der Transaktion nicht erfolgreich", e);
            }
        }
    }

    public void rollbackTransaction()
    {
        if (this.connection != null) {
            try {
                this.connection.rollback();
            } catch (SQLException e) {
                throw new DataAccessException("Rollback der Transaktion nicht erfolgreich", e);
            }
        }
    }

    /**
     * the connection to the server is closed and set to null
     * with error handling if error occurs
     */
    public void finishWork()
    {
        if (this.connection != null) {
            try {
                this.connection.close();
                this.connection = null;
            } catch (SQLException e) {
                throw new DataAccessException("Schließen der Connection nicht erfolgreich", e);
            }
        }
    }

    /**
     * connection to database is tested and the statement to for the database is prepared with the SQL statement
     * @param sql
     * @return
     */
    public PreparedStatement prepareStatement(String sql)
    {
        if (this.connection != null) {
            try {
                return this.connection.prepareStatement(sql);
            } catch (SQLException e) {
                throw new DataAccessException("Erstellen eines PreparedStatements nicht erfolgreich", e);
            }
        }
        throw new DataAccessException("UnitOfWork hat keine aktive Connection zur Verfügung");
    }

    @Override
    public void close() throws Exception {
        this.finishWork();
    }
}
