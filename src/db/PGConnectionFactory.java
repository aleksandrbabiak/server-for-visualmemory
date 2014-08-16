package db;


import org.postgresql.ds.PGPoolingDataSource;
import org.postgresql.jdbc2.optional.PoolingDataSource;

import java.sql.Connection;
import java.sql.SQLException;


public class PGConnectionFactory implements ConnectionFactory {

    private static PGPoolingDataSource dataSource;
    private static String user, pass, host, base;

    static {
        user = DBConfig.DB_USER;
        pass = DBConfig.DB_USER_PASSWORD;
        base = DBConfig.BASE_NAME;
        host = DBConfig.DB_HOST;
        dataSource = new PoolingDataSource();
        dataSource.setUser(user);
        dataSource.setPassword(pass);
        dataSource.setServerName(host);
        dataSource.setDatabaseName(base);
        dataSource.setMaxConnections(100);
    }


    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }


    public void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}