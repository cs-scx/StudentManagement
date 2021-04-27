package dao;

import model.SC;
import utils.Jdbc;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SCDao {
    public ArrayList<SC> query_all_sc() {
        ArrayList<SC> results = new ArrayList<>();
        Jdbc jdbc = new Jdbc();
        PreparedStatement preparedStatement = jdbc.preparedStatement;
        String sql = "select Student.Sno Sno,Sname,Ssex,Sage,Course.Cno,Cname,Grade from SC,Student,Course where SC.Sno = Student.Sno and Course.Cno = SC.Cno order by Sno;";
        try {
            Class.forName(jdbc.JDBC_DRIVER);
            jdbc.connection = DriverManager.getConnection(jdbc.DB_URL, jdbc.USER, jdbc.PASS);
            preparedStatement = jdbc.connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                SC temp = new SC();
                temp.setSno(resultSet.getString("Sno"));
                temp.setSname(resultSet.getString("Sname"));
                temp.setSsex(resultSet.getString("Ssex"));
                temp.setSage(resultSet.getInt("Sage"));
                temp.setCno(resultSet.getString("Cno"));
                temp.setCname(resultSet.getString("Cname"));
                temp.setGrade(resultSet.getDouble("Grade"));
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

    public int insert_sc(String Sno, String Cno, Double Grade) {
        int flag = 0;
        Jdbc jdbc = new Jdbc();
        PreparedStatement preparedStatement = jdbc.preparedStatement;
        String sql = "insert into sc values(?,?,?);";
        try {
            Class.forName(jdbc.JDBC_DRIVER);
            jdbc.connection = DriverManager.getConnection(jdbc.DB_URL, jdbc.USER, jdbc.PASS);
            preparedStatement = jdbc.connection.prepareStatement(sql);
            preparedStatement.setString(1, Sno);
            preparedStatement.setString(2, Cno);
            preparedStatement.setDouble(3, Grade);
            flag = preparedStatement.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            jdbc.Destroy();
        }
        return flag;
    }

    public int delete_sc(String Sno, String Cno) {
        int flag = 0;
        Jdbc jdbc = new Jdbc();
        PreparedStatement preparedStatement = jdbc.preparedStatement;
        String sql = "delete from sc where sno = ? and cno = ?;";
        try {
            Class.forName(jdbc.JDBC_DRIVER);
            jdbc.connection = DriverManager.getConnection(jdbc.DB_URL, jdbc.USER, jdbc.PASS);
            preparedStatement = jdbc.connection.prepareStatement(sql);
            preparedStatement.setString(1, Sno);
            preparedStatement.setString(2, Cno);
            flag = preparedStatement.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            jdbc.Destroy();
        }
        return flag;
    }

    public int alter_sc(String Sno, String Cno, double after_grade) {
        int flag = 0;
        Jdbc jdbc = new Jdbc();
        PreparedStatement preparedStatement = jdbc.preparedStatement;
        String sql = "update sc set grade = ? where sno = ? and cno = ?;";
        try {
            Class.forName(jdbc.JDBC_DRIVER);
            jdbc.connection = DriverManager.getConnection(jdbc.DB_URL, jdbc.USER, jdbc.PASS);
            preparedStatement = jdbc.connection.prepareStatement(sql);
            preparedStatement.setDouble(1, after_grade);
            preparedStatement.setString(2, Sno);
            preparedStatement.setString(3, Cno);
            flag = preparedStatement.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            jdbc.Destroy();
        }
        return flag;
    }
}
