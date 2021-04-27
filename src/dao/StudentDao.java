package dao;

import model.Student;
import utils.Jdbc;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudentDao {
    public ArrayList<Student> query_all_student() {
        Jdbc jdbc = new Jdbc();
        PreparedStatement preparedStatement = jdbc.preparedStatement;
        String sql = "select * from Student order by Sno;";
        ArrayList<Student> results = new ArrayList<>();
        try {
            Class.forName(jdbc.JDBC_DRIVER);
            jdbc.connection = DriverManager.getConnection(jdbc.DB_URL, jdbc.USER, jdbc.PASS);
            preparedStatement = jdbc.connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Student temp = new Student();
                temp.setClno(resultSet.getString("Clno"));
                temp.setSage(resultSet.getInt("Sage"));
                temp.setSname(resultSet.getString("Sname"));
                temp.setSno(resultSet.getString("Sno"));
                temp.setSsex(resultSet.getString("Ssex"));
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

    public int insert_student(String Sno, String Sname, String Ssex, int Sage, String Clno) {
        int flag = 0;
        Jdbc jdbc = new Jdbc();
        PreparedStatement preparedStatement = jdbc.preparedStatement;
        String sql = "insert into Student values(?,?,?,?,?);";
        try {
            Class.forName(jdbc.JDBC_DRIVER);
            jdbc.connection = DriverManager.getConnection(jdbc.DB_URL, jdbc.USER, jdbc.PASS);
            preparedStatement = jdbc.connection.prepareStatement(sql);
            preparedStatement.setString(1, Sno);
            preparedStatement.setString(2, Sname);
            preparedStatement.setString(3, Ssex);
            preparedStatement.setInt(4, Sage);
            preparedStatement.setString(5, Clno);
            flag = preparedStatement.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            jdbc.Destroy();
        }
        return flag;
    }

    public int delete_student(String sno) {
        int flag = 0;
        Jdbc jdbc = new Jdbc();
        PreparedStatement preparedStatement = jdbc.preparedStatement;
        String sql = "delete from Student where Sno = ?;";
        try {
            Class.forName(jdbc.JDBC_DRIVER);
            jdbc.connection = DriverManager.getConnection(jdbc.DB_URL, jdbc.USER, jdbc.PASS);
            preparedStatement = jdbc.connection.prepareStatement(sql);
            preparedStatement.setString(1, sno);
            flag = preparedStatement.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            jdbc.Destroy();
        }
        return flag;
    }

    public int alter_class(String sno, String after_sno, String after_sname, String after_ssex, int after_sage, String after_clno) {
        int flag = 0;
        Jdbc jdbc = new Jdbc();
        PreparedStatement preparedStatement = jdbc.preparedStatement;
        String sql = "update Student set Sno = ?,Sname = ?,Ssex = ?,Sage = ?,Clno = ? where Sno = ?;";
        try {
            Class.forName(jdbc.JDBC_DRIVER);
            jdbc.connection = DriverManager.getConnection(jdbc.DB_URL, jdbc.USER, jdbc.PASS);
            preparedStatement = jdbc.connection.prepareStatement(sql);
            preparedStatement.setString(1, after_sno);
            preparedStatement.setString(2, after_sname);
            preparedStatement.setString(3, after_ssex);
            preparedStatement.setInt(4, after_sage);
            preparedStatement.setString(5, after_clno);
            preparedStatement.setString(6, sno);
            flag = preparedStatement.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            jdbc.Destroy();
        }
        return flag;
    }
}
