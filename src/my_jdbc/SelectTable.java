package my_jdbc;

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
public class SelectTable {

    public static Connection               connection = null;
    public static Statement                 statement = null;
    private final static String                DB_URL = "jdbc:mysql://localhost:3306/bookstore?characterEncoding=utf8";
    private final static String                  USER = "root";
    private final static String                PASSWD = "1978";
    // Так мы делаем выборку из таблицы в базе:
    private final static String SELECT_TABLE_QUERY = "SELECT * FROM books";

    public static void main(String[] args) {
        try {
            // Загружаем драйвер
            Class.forName("com.mysql.jdbc.Driver");

            // Нужно создать подключение к БД у MySQL есть база 'bookstore', к ней и будем создавать соединение.
            connection = DriverManager.getConnection(DB_URL, USER, PASSWD);
            statement  = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            // Обратите внимание, что делаем выборку из таблицу с помощью executeQuery().
            ResultSet resultset = statement.executeQuery(SELECT_TABLE_QUERY);

            // получаем информацию из результирующей таблице
            int countColumn = resultset.getMetaData().getColumnCount();
            System.out.println("******************************");
            System.out.println("Count Columns = " + countColumn);
            System.out.println("******************************");

            String author, title, comment;
            int id;
            double price;
            while (resultset.next()) {
                id      = resultset.getInt("id");
                author  = resultset.getString("author");
                title   = resultset.getString("title");
                comment = resultset.getString("comment");
                price   = resultset.getDouble("price");
                System.out.println("******************************");
                System.out.println("ID:      " + id);
                System.out.println("Author:  " + author);
                System.out.println("Title:   " + title);
                System.out.println("Price:   " + price);
                System.out.println("Comment: " + comment);
                System.out.println("******************************");
            }
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