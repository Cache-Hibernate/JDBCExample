package my_jdbc_1;

import java.sql.*;

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
public class UpdateRecords1 {

    public static Connection              dbConnection = null;
    public static Statement                  statement = null;
    private static final String              DB_DRIVER = "com.mysql.jdbc.Driver";
    private static final String          DB_CONNECTION = "jdbc:mysql://localhost:3306/bookstore?characterEncoding=utf8";
    private static final String                DB_USER = "root";
    private static final String            DB_PASSWORD = "1978";
    // Так мы делаем обновление записи из таблицы в базе:
//    private final static String UPDATE_RECORDS_QUERY = "UPDATE books SET title='b2',comment='b2b2b2b2b2b2b2b2b2',author='B2' WHERE id=6";
    private final static String   UPDATE_RECORDS_QUERY = "UPDATE books SET title='b2',comment='b2b2b2b2b2b2b2b2b2',author='B2' WHERE id IN(1,2,3)";


    public static void main(String[] argv) {
        try {
            updateRecord();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void updateRecord() throws SQLException {
        try {
            dbConnection = dbConnection();
            statement    = dbConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            // Обратите внимание, что обновление строки в таблице с помощью executeUpdate().
            int countRecords = statement.executeUpdate(UPDATE_RECORDS_QUERY);
            System.out.println("******************************");
            System.out.println("Update Record: " + countRecords);
            System.out.println("******************************");
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
