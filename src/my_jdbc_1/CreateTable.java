package my_jdbc_1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Aleksandr Konstantinovitch
 * @version 1.0
 * @date 21/01/2015
 * {@link http://javatalks.ru/topics/7147}
 * {@link http://www.tutorialspoint.com/jdbc/jdbc-drop-database.htm}
 * {@link http://www.tutorialspoint.com/jdbc/updating-result-sets.htm}
 * {@link http://www.quizful.net/post/using-jdbc}
 * {@link http://devcolibri.com/477}
 * {@link http://javaxblog.ru/article/java-jdbc-1/}
 * {@link http://www.mkyong.com/jdbc/jdbc-preparestatement-example-update-a-record/}
 */
public class CreateTable {

    public static Connection         dbConnection = null;
    public static Statement             statement = null;
    private static final String         DB_DRIVER = "com.mysql.jdbc.Driver";
    private static final String     DB_CONNECTION = "jdbc:mysql://localhost:3306/bookstore?characterEncoding=utf8";
    private static final String           DB_USER = "root";
    private static final String       DB_PASSWORD = "1978";
    // Так мы создаем таблицу в базе:
    public final static String CREATE_TABLE_QUERY = "CREATE TABLE books (" +
            " id int(11) NOT NULL auto_increment," +
            " title varchar(50) default NULL," +
            " comment varchar(100) default NULL," +
            " price double default NULL," +
            " author varchar(50) default NULL," +
            " PRIMARY KEY (id)" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";


    public static void main(String[] argv) {
        try {
            createTable();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void createTable() throws SQLException {
        try {
            dbConnection = dbConnection();
            statement    = dbConnection.createStatement();

            // Обратите внимание, что создаем таблицу с помощью execute().
            statement.execute( CREATE_TABLE_QUERY );
        } catch (SQLException e) { // Handle errors for JDBC
            System.out.println(e.getMessage());
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (dbConnection != null) {
                dbConnection.close();
            }
        }
    }

    private static Connection dbConnection() {
        Connection dbConnection = null;
        try {
            // Загружаем драйвер
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) { // Handle errors for Class.forName
            e.printStackTrace();
        }

        try {
            // Нужно создать подключение к БД у MySQL есть база 'bookstore', к ней и будем создавать соединение.
            dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
            return dbConnection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return dbConnection;
    }

}
