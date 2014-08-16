package db;

import java.sql.Connection;


public interface ConnectionFactory {

    public Connection getConnection();

    public void closeConnection(Connection connection);
}
