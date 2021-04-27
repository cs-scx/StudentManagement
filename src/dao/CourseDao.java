package dao;

import model.Course;
import model.Course_avg;
import model.Course_fail_rate;
import model.Course_ranking;
import utils.Jdbc;

import javax.swing.*;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CourseDao {
    public ArrayList<Course> query_all_course() {
        ArrayList<Course> result = new ArrayList<>();
        Jdbc jdbc = new Jdbc();
        PreparedStatement preparedStatement = jdbc.preparedStatement;
        String sql = "select * from Course order by cno;";
        try {
            Class.forName(jdbc.JDBC_DRIVER);
            jdbc.connection = DriverManager.getConnection(jdbc.DB_URL, jdbc.USER, jdbc.PASS);
            preparedStatement = jdbc.connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Course temp = new Course();
                temp.setCcredit(resultSet.getInt("Ccredit"));
                temp.setCname(resultSet.getString("Cname"));
                temp.setCno(resultSet.getString("Cno"));
                temp.setCteacher(resultSet.getString("Cteacher"));
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

    public int insert_course(String Cno, String Cname, String Cteacher, double Ccredit) {
        int flag = 0;
        Jdbc jdbc = new Jdbc();
        PreparedStatement preparedStatement = jdbc.preparedStatement;
        String sql = "insert into Course values(?,?,?,?);";
        try {
            Class.forName(jdbc.JDBC_DRIVER);
            jdbc.connection = DriverManager.getConnection(jdbc.DB_URL, jdbc.USER, jdbc.PASS);
            preparedStatement = jdbc.connection.prepareStatement(sql);
            preparedStatement.setString(1,Cno);
            preparedStatement.setString(2,Cname);
            preparedStatement.setString(3,Cteacher);
            preparedStatement.setDouble(4,Ccredit);
            flag = preparedStatement.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            jdbc.Destroy();
        }
        return flag;
    }

    public int delete_course(String Cno) {
        int flag = 0;
        Jdbc jdbc = new Jdbc();
        PreparedStatement preparedStatement = jdbc.preparedStatement;
        String sql = "delete from Course where Cno = ?;";
        try {
            Class.forName(jdbc.JDBC_DRIVER);
            jdbc.connection = DriverManager.getConnection(jdbc.DB_URL, jdbc.USER, jdbc.PASS);
            preparedStatement = jdbc.connection.prepareStatement(sql);
            preparedStatement.setString(1, Cno);
            flag = preparedStatement.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            jdbc.Destroy();
        }
        return flag;
    }

    public int alter_course(String cno, String after_cno, String after_cname, String after_cteacher, double after_ccredit) {
        int flag = 0;
        String sql = "update Course set Cno = ?,Cname = ?,Cteacher = ?,Ccredit = ? where Cno = ?;";
        Jdbc jdbc = new Jdbc();
        PreparedStatement preparedStatement = jdbc.preparedStatement;
        try {
            Class.forName(jdbc.JDBC_DRIVER);
            jdbc.connection = DriverManager.getConnection(jdbc.DB_URL, jdbc.USER, jdbc.PASS);
            preparedStatement = jdbc.connection.prepareStatement(sql);
            preparedStatement.setString(1, after_cno);
            preparedStatement.setString(2, after_cname);
            preparedStatement.setString(3, after_cteacher);
            preparedStatement.setDouble(4, after_ccredit);
            preparedStatement.setString(5, cno);
            flag = preparedStatement.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            jdbc.Destroy();
        }
        return flag;
    }

    public ArrayList<Course_avg> course_avg() {
        ArrayList<Course_avg> course_avg = new ArrayList<>();
        String sql = "select SC.Cno Cno,Cname,avg(Grade) Avg from Course,SC where Course.Cno = SC.Cno group by cno order by Cno;";
        Jdbc jdbc = new Jdbc();
        PreparedStatement preparedStatement = jdbc.preparedStatement;
        try {
            Class.forName(jdbc.JDBC_DRIVER);
            jdbc.connection = DriverManager.getConnection(jdbc.DB_URL, jdbc.USER, jdbc.PASS);
            preparedStatement = jdbc.connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Course_avg temp = new Course_avg();
                temp.setCno(resultSet.getString("Cno"));
                temp.setCname(resultSet.getString("Cname"));
                temp.setAvg(resultSet.getDouble("Avg"));
                course_avg.add(temp);
            }
            resultSet.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            jdbc.Destroy();
        }
        return course_avg;
    }

    public ArrayList<Course_fail_rate> fail_rate() {
       String sql = "select Cno,(select Cname from Course where Cno = x.Cno) Cname,cast(100.0*(select count(Sno) from SC where Grade < 60 and Cno = x.Cno)/(select count(Sno) from SC where Cno = x.Cno) as decimal(18,2)) Rate from SC x group by Cno order by Cno;";
       ArrayList<Course_fail_rate> fail_rate = new ArrayList<>();
       Jdbc jdbc = new Jdbc();
       PreparedStatement preparedStatement = jdbc.preparedStatement;
       try {
           Class.forName(jdbc.JDBC_DRIVER);
           jdbc.connection = DriverManager.getConnection(jdbc.DB_URL, jdbc.USER, jdbc.PASS);
           preparedStatement = jdbc.connection.prepareStatement(sql);
           ResultSet resultSet = preparedStatement.executeQuery();
           while (resultSet.next()) {
               Course_fail_rate temp = new Course_fail_rate();
               temp.setCname(resultSet.getString("Cname"));
               temp.setCno(resultSet.getString("Cno"));
               temp.setFail_rate(resultSet.getDouble("Rate"));
               fail_rate.add(temp);
           }
           resultSet.close();
       } catch (ClassNotFoundException | SQLException e) {
           e.printStackTrace();
       } finally {
           jdbc.Destroy();
       }
        return fail_rate;
    }

    public ArrayList<Course_ranking> course_ranking(String cno) {
        ArrayList<Course_ranking> course_ranking = new ArrayList<>();
        String sql = "select Student.Sno Sno,Dname,Clname,Sname,Ssex,Sage,Grade from Department,Class,Student,SC where Student.Sno = SC.Sno and Class.Clno = Student.Clno and Department.Dno = class.Dno and Cno = '"+cno+"' order by Grade desc;";
        Jdbc jdbc = new Jdbc();
        PreparedStatement preparedStatement = jdbc.preparedStatement;
        try {
            Class.forName(jdbc.JDBC_DRIVER);
            jdbc.connection = DriverManager.getConnection(jdbc.DB_URL, jdbc.USER, jdbc.PASS);
            preparedStatement = jdbc.connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Course_ranking temp = new Course_ranking();
                temp.setClname(resultSet.getString("Clname"));
                temp.setDname(resultSet.getString("Dname"));
                temp.setGrade(resultSet.getDouble("Grade"));
                temp.setSno(resultSet.getString("Sno"));
                temp.setSage(resultSet.getInt("Sage"));
                temp.setSsex(resultSet.getString("Ssex"));
                temp.setSage(resultSet.getInt("Sage"));
                course_ranking.add(temp);
            }
            resultSet.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            jdbc.Destroy();
        }
        return course_ranking;
    }
}
