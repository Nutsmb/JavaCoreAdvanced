package Server;

import java.sql.*;
import java.util.ArrayList;

public class AuthService {
    private static Connection connection;
    private static Statement stmt;

    public static void connect(){
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:main.db");
            stmt = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getNickByLoginAndPass(String _login, String _password){
        String sql = String.format("select nickname from users where login = '%s' and password = '%s'",_login, _password);
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery(sql);
            if(rs.next()){
                return rs.getString("nickname");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<String> getBlacklist(String _nick){
        ArrayList result = new ArrayList();
        String sql = String.format("select nickname from users where id in " +
                                    "(select blacklist_id from blacklist join users on (user_id = id) " +
                                        "where users.nickname = '%s')", _nick);
        ResultSet rs;
        try {
            rs = stmt.executeQuery(sql);
            int columns = rs.getMetaData().getColumnCount();
            while(rs.next()) {
                for (int i = 1; i <= columns; i++) {
                    result.add(rs.getString(i));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void addToBlacklist(String who, String whom){
        String sql = String.format("INSERT INTO blacklist (user_id, blacklist_id)" +
                                    "VALUES (" +
                                        "(select id from users where nickname = '%s'), " +
                                        "(select id from users where nickname = '%s'))", who, whom);
        try {
            stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int signup(String _login, String _password, String _nickname){
        String sql = String.format("INSERT INTO users (login, password, nickname) VALUES ('%s', '%s','%s')", _login,_password,_nickname);
        int rs = 0;
        try {
            rs = stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public static void disconnect(){
        try {
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}