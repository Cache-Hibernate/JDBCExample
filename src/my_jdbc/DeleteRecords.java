package my_jdbc;

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
public class DeleteRecords {

    public static Connection                   connection = null;
    public static Statement                     statement = null;
    private final static String                    DB_URL = "jdbc:mysql://localhost:3306/bookstore?characterEncoding=utf8";
    private final static String                      USER = "root";
    private final static String                    PASSWD = "1978";
    // Так мы делаем удаление записи из таблицы в базе:
//    private final static String      DELETE_RECORDS_QUERY = "DELETE FROM books WHERE id=1";
    private final static String      DELETE_RECORDS_QUERY = "DELETE FROM books WHERE author='B2'";

    public static void main(String[] args) {
        try {
            // Загружаем драйвер
            Class.forName("com.mysql.jdbc.Driver");

            // Нужно создать подключение к БД у MySQL есть база 'bookstore', к ней и будем создавать соединение.
            connection = DriverManager.getConnection(DB_URL, USER, PASSWD);
            statement  = connection.createStatement();

            // Обратите внимание, что удаление строки из таблицы с помощью execute().
            int count = statement.executeUpdate( DELETE_RECORDS_QUERY );
            System.out.println("******************************");
            System.out.println("Delete(s) = " + count);
            System.out.println("******************************");
        } catch (SQLException se) { // Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) { // Handle errors for Class.forName
            e.printStackTrace();
        } finally { // finally block used to close resources
            try {
                if (statement != null)
                    connection.close();
            } catch (SQLException se) { // do nothing
            } try {
                if (connection != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

}
