package Business;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnect {
    private static final String URL = "localhost";
    private static final String DATABASE = "DSS";
    private static final String USERNAME = "user";
    private static final String PASSWORD = "1234";

    static Connection connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://" + URL
                    + "/" + DATABASE
                    + "?user=" + USERNAME
                    + "&password=" + PASSWORD
                    + "&allowMultiQueries=true");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    static void close(Connection connection) {
        try {
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
