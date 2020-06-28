package server;

import java.sql.*;
import java.util.ArrayList;

public class AuthServer {
    private static Connection connect;
    private static Statement statement;
    public static void connection(){
        try {
            Class.forName("org.sqlite.JDBC");
                connect = DriverManager.getConnection("jdbc:sqlite:mainDB.db");
            statement = connect.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // authentification
    public static String getNickByLoginAndPass(String login,String pass){
        String sql = String.format("SELECT nickname FROM main where login = '%s' and password = '%s'",login,pass);
        try {
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()){
                return rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    // get nick by login
    public static String getLoginByNick(String nickname){
        String sql = String.format("SELECT login FROM main where nickname = '%s'",nickname);
        try {
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()){
                return rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //check if login is already in use
    public static boolean loginIsBusy(String login){
        String sql = String.format("SELECT login FROM main where login = '%s'",login);
        try {
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //check if nickname is already in use
    public static boolean nickIsBusy(String nick){
        String sql = String.format("SELECT login FROM main where nickname = '%s'",nick);
        try {
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //its simple registrtion
    public static int putNickByLoginAndPass(String login, String nick, String pass){
        String sql = String.format("INSERT INTO main (nickname, login, password) " + "VALUES ('%s', '%s', '%s')",nick, login, pass);
        try {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    //update nick for user
    public static void updateNickname(String login, String newNick){
        String sql = String.format("UPDATE main SET nickname = '%s' WHERE login = '%s'",newNick, login);
        try {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

        //get black list for user
        public static String getBlackList(String login){
        String sql = String.format("SELECT blackList FROM main where login = '%s'",login);
        try {
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()){
                return rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    //update blacklist
    public static void updateBlackeList(String login, ArrayList<String> newBlackList){
        StringBuilder stringBL = new StringBuilder();
        for (String s: newBlackList) {
            stringBL.append(s);
            stringBL.append(";");
        }
        String sql = String.format("UPDATE main SET blackList = '%s' WHERE login = '%s'",stringBL.toString(), login);
        try {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void disconnect(){
        try {
            connect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
