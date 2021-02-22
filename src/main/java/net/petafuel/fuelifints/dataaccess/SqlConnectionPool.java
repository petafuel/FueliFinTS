package net.petafuel.fuelifints.dataaccess;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Hashtable;

public class SqlConnectionPool {
    private static final Logger LOG = LogManager.getLogger(SqlConnectionPool.class);
    private final Hashtable<Connection, Boolean> connections = new Hashtable<>();


    public synchronized Connection getConnection() throws SQLException {
        Connection con;
        Enumeration<Connection> cons = connections.keys();

        synchronized (connections) {
            while (cons.hasMoreElements()) {
                con = cons.nextElement();
                Boolean b = connections.get(con);
                if (b == Boolean.FALSE) {
                    // So we found an unused connection.
                    // Test its integrity with a quick setAutoCommit(true) call.
                    // For production use, more testing should be performed,
                    // such as executing a simple query.
                    try {
                        con.setAutoCommit(true);
                    } catch (SQLException e) {
                        // Problem with the connection, replace it.
                        connections.remove(con);
                        con = createNew();
                    }
                    // Update the Hashtable to show this one's taken
                    connections.put(con, Boolean.TRUE);
                    // Return the connection
                    return con;
                }
            }
            // If we get here, there were no free connections.  Make one more.
            // A more robust connection pool would have a maximum size limit,
            // and would reclaim connections after some timeout period
            con = createNew();
            connections.put(con, Boolean.TRUE);
            return con;
        }
    }

    public synchronized void putBack(Connection returned) {
        if (returned != null && connections.containsKey(returned)) {
            connections.put(returned, Boolean.FALSE);
        }
    }

    protected Connection createNew() throws SQLException {
        return null;
    }
}
