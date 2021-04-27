package dao;

import model.MyClass;
import utils.Jdbc;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClassDao {
    public ArrayList<MyClass> query_all_class() {
        ArrayList<MyClass> result = new ArrayList<>();
        Jdbc jdbc = new Jdbc();
        String sql = "select * from Class order by Clno;";
        PreparedStatement preparedStatement = jdbc.preparedStatement;
        try {
            Class.forName(jdbc.JDBC_DRIVER);
            jdbc.connection = DriverManager.getConnection(jdbc.DB_URL, jdbc.USER, jdbc.PASS);
            preparedStatement = jdbc.connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                MyClass temp = new MyClass();
                temp.setClname(resultSet.getString("Clname"));
                temp.setClno(resultSet.getString("Clno"));
                temp.setDno(resultSet.getString("Dno"));
                result.add(temp);
            }
            resultSet.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            jdbc.Destroy();
        }
        return result;
    }

    public int insert_class(String clno, String clname, String dno) {
        int flag = 0;
        String sql = "insert into Class values(?,?,?);";
        Jdbc jdbc = new Jdbc();
        PreparedStatement preparedStatement = jdbc.preparedStatement;
        try {
            Class.forName(jdbc.JDBC_DRIVER);
            jdbc.connection = DriverManager.getConnection(jdbc.DB_URL, jdbc.USER, jdbc.PASS);
            preparedStatement = jdbc.connection.prepareStatement(sql);
            preparedStatement.setString(1, clno);
            preparedStatement.setString(2, clname);
            preparedStatement.setString(3, dno);
            flag = preparedStatement.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            jdbc.Destroy();
        }
        return flag;
    }

    public int delete_class(String clno) {
        int flag = 0;
        String sql = "delete from Class where Clno = ?;";
        Jdbc jdbc = new Jdbc();
        PreparedStatement preparedStatement = jdbc.preparedStatement;
        try {
            Class.forName(jdbc.JDBC_DRIVER);
            jdbc.connection = DriverManager.getConnection(jdbc.DB_URL, jdbc.USER, jdbc.PASS);
            preparedStatement = jdbc.connection.prepareStatement(sql);
            preparedStatement.setString(1, clno);
            flag = preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }

    public int alter_class(String clno, String after_clno, String after_clname, String after_dno) {
        int flag = 0;
        Jdbc jdbc = new Jdbc();
        PreparedStatement preparedStatement = jdbc.preparedStatement;
        String sql = "update class set Clno = ?,Clname = ?,Dno = ? where Clno = ?;";
        try {
            Class.forName(jdbc.JDBC_DRIVER);
            jdbc.connection = DriverManager.getConnection(jdbc.DB_URL, jdbc.USER, jdbc.PASS);
            preparedStatement = jdbc.connection.prepareStatement(sql);
            preparedStatement.setString(1, after_clno);
            preparedStatement.setString(2, after_clname);
            preparedStatement.setString(3, after_dno);
            preparedStatement.setString(4, clno);
            flag = preparedStatement.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            jdbc.Destroy();
        }
        return flag;
    }
}
