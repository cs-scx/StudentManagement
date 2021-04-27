package dao;

import model.Department;
import utils.Jdbc;

import javax.imageio.plugins.jpeg.JPEGImageReadParam;
import javax.swing.*;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DepartmentDao {
    public ArrayList<Department> query_all_department() {
        Jdbc jdbc = new Jdbc();
        PreparedStatement preparedStatement = jdbc.preparedStatement;
        ArrayList<Department> results = new ArrayList<>();
        String sql = "select * from Department order by Dno;";
        try{
            Class.forName(jdbc.JDBC_DRIVER);
            jdbc.connection = DriverManager.getConnection(jdbc.DB_URL, jdbc.USER, jdbc.PASS);
            preparedStatement = jdbc.connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Department temp = new Department();
                temp.setDname(resultSet.getString("Dname"));
                temp.setDno(resultSet.getString("Dno"));
                results.add(temp);
            }
            resultSet.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            jdbc.Destroy();
        }
        return results;
    }

    public int insert_department(String dno, String dname) {
        int flag = 0;
        Jdbc jdbc = new Jdbc();
        PreparedStatement preparedStatement = jdbc.preparedStatement;
        String sql = "insert into Department values(?,?);";
        try {
            Class.forName(jdbc.JDBC_DRIVER);
            jdbc.connection = DriverManager.getConnection(jdbc.DB_URL, jdbc.USER, jdbc.PASS);
            preparedStatement = jdbc.connection.prepareStatement(sql);
            preparedStatement.setString(1, dno);
            preparedStatement.setString(2, dname);
            flag = preparedStatement.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            jdbc.Destroy();
        }
        return flag;
    }

    public int delete_department(String dno) {
        int flag = 0;
        Jdbc jdbc = new Jdbc();
        PreparedStatement preparedStatement = jdbc.preparedStatement;
        String sql = "delete from Department where Dno = ?;";
        try {
            Class.forName(jdbc.JDBC_DRIVER);
            jdbc.connection = DriverManager.getConnection(jdbc.DB_URL, jdbc.USER, jdbc.PASS);
            preparedStatement = jdbc.connection.prepareStatement(sql);
            preparedStatement.setString(1, dno);
            flag = preparedStatement.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            jdbc.Destroy();
        }
        return flag;
    }

    public int alter_department(String dno, String after_dno, String after_dname) {
        int flag = 0;
        Jdbc jdbc = new Jdbc();
        PreparedStatement preparedStatement = jdbc.preparedStatement;
        String sql = "update Department set Dno = ?,Dname = ? where Dno = ?;";
        try {
            Class.forName(jdbc.JDBC_DRIVER);
            jdbc.connection = DriverManager.getConnection(jdbc.DB_URL, jdbc.USER, jdbc.PASS);
            preparedStatement = jdbc.connection.prepareStatement(sql);
            preparedStatement.setString(1, after_dno);
            preparedStatement.setString(2, after_dname);
            preparedStatement.setString(1, dno);
            flag = preparedStatement.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            jdbc.Destroy();
        }
        return flag;
    }
}
