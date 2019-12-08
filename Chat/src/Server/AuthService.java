package Server;

import java.sql.*;

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

    public static void disconnect(){
        try {
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}