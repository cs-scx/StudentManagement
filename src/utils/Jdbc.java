package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Jdbc {
    public final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    public final String DB_URL = "jdbc:mysql://localhost:3306/StudentInfoManagement?serverTimezone=UTC";
    public final String USER = "root";
    public final String PASS = "zxl2413853996";

    public Connection connection;
    public Statement statement;
    public PreparedStatement preparedStatement;

    public Jdbc(){
         connection = null;
         statement = null;
         preparedStatement = null;
    }

    public void Destroy(){
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
}
