import java.sql.*; // Imports Java SQL library

// This class manages the connection to the database and executes queries
public class DatabaseConnection {

    // Database credentials
    private final String url = "jdbc:oracle:thin:@oracle.cs.torontomu.ca:1521:orcl"; // Database URL
    private final String username = "akuganen";  // Oracle username
    private final String password = "02152297";  // Oracle password

    private static DatabaseConnection instance; // Singleton instance
    private Connection connection;  // Persistent connection object

    // Private constructor to prevent direct instantiation
    private DatabaseConnection() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            this.connection = DriverManager.getConnection(url, username, password);
            this.connection.setAutoCommit(false); // Disable auto-commit
            System.out.println("Connected!"); // Only printed once
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Failed to connect to the database.");
            e.printStackTrace();
        }
    }

    // Public method to get the singleton instance
    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    // Verify Connection
    public boolean isConnected(){
        if (connection != null){ // Connection exisits if not null
            return true;
        }
        else{
            return false;
        }
    }

    // Execute a DDL/DML statement (e.g., DROP TABLE, INSERT, UPDATE)
    public void executeUpdate(String query) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query); // Use executeUpdate for DDL/DML
        }
    }

    // Execute a SELECT query and return the ResultSet
    public ResultSet executeQuery(String query) throws SQLException {
        Statement statement = connection.createStatement();
        return statement.executeQuery(query); // Caller is responsible for closing the ResultSet
    }

    // Commit the transaction
    public void commit() throws SQLException {
        if (connection != null) {
            connection.commit();
        }
    }

    // Rollback the transaction
    public void rollback() throws SQLException {
        if (connection != null) {
            connection.rollback();
        }
    }

    // Close the connection and clean up resources
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("Failed to close the connection.");
            e.printStackTrace();
        }
    }
}
