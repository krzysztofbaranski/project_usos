package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by krzysztof on 05/02/14.
 */
public class Database {
    private static boolean _lock = false;
    public static Connection connection = null;
    public static long timeToClose = 1; // in minutes


    /**
     *
     * Make connection to database
     *
     */
    public static void connect() throws SQLException {
        // laduje sterownik
        try {
            Class.forName("org.postgresql.Driver").newInstance();
        } catch(InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }


        try {
            connection = DriverManager.getConnection("jdbc:postgresql:" + Settings.dbUrl, Settings.username, Settings.passwd);
        } catch(SQLException e) {
            throw new SQLException("Can't connect to database...");
        }

        Thread closeConnection = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while(connection != null && !connection.isClosed()) {
                        try {
                            Thread.sleep(1000 * 60 * timeToClose);
                        } catch(InterruptedException e) {
                            e.printStackTrace();
                        }

                        if(!_lock && !connection.isClosed()) {
                            connection.close();
                        }
                    }
                } catch(SQLException e) {
                    e.printStackTrace();
                }
            }

        });

        closeConnection.setDaemon(true);
        closeConnection.start();
    }

    /**
     *
     * Lock
     *
     * zapobiega zamknieciu polaczenia, nalezy zalozyc przed czytaniem z bazy
     */
    public static void lock() {
        _lock = true;
    }

    /**
     *
     * Unlock
     *
     */
    public static void unlock() {
        _lock = false;
    }
}
