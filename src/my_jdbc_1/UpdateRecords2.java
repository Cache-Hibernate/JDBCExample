package my_jdbc_1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
 * {@link http://www.javaportal.ru/java/tutorial/tutorialJDBC/preparedstatement.html}
 */
public class UpdateRecords2 {

    public static Connection             dbConnection = null;
    public static PreparedStatement preparedStatement = null;
    private static final String             DB_DRIVER = "com.mysql.jdbc.Driver";
    private static final String         DB_CONNECTION = "jdbc:mysql://localhost:3306/bookstore?characterEncoding=utf8";
    private static final String               DB_USER = "root";
    private static final String           DB_PASSWORD = "1978";
    // Так мы делаем обновление записи из таблицы в базе:
//    private static final String  UPDATE_RECORDS_QUERY = "UPDATE books SET title=?,comment=?,author=? WHERE id=?";
    private static final String  UPDATE_RECORDS_QUERY = "UPDATE books SET title=?,comment=?,author=? WHERE id IN(?)";


    public static void main(String[] argv) {
        try {
            updateRecord();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    private static void updateRecord() throws SQLException {
        try {
            /**
             * Класс 'java.sql.Connection' - представляет в JDBC сеанс работы с базой данных (он предоставляет приложению объекты Statement для этого сеанса).
             * По умолчанию каждая команда выполняется в отдельной транзакции.
             * Объект Connection позволяет отключить функцию "Autocommit" (автоматического завершения транзакции) - в этом случае требуется явно завершить транзакцию, иначе результаты выполнения всех команд будут потеряны.
             * *********************************************************************
             * public void close() throws SQLException                   // позволяет вручную освободить все ресурсы, такие как сетевые соединения и блокировки базы данных, связанные с данным объектом Connection.
             * public Statement createStatement() throws SQLException    // создает объект Statement, связанный с сеансом Connection, для которого экземпляры ResultSet имеют тип только для чтения и перемещения в прямом направлении.
             * public boolean getAutoCommit() throws SQLException        // по умолчанию все объекты Connection находятся в режиме автозавершения. В этом режиме каждая команда завершается сразу после выполнения.
             * public void setAutoCommit(boolean ac) throws SQLException // метод setAutoCommit() используется для отключения автозавершения вручную завершить серию команд в приложении как единую транзакцию.
             * public void commit() throws SQLException                  // делает постоянными изменения, произведенные всеми командами, связанными с данным соединением.
             * public DatabaseMetaData getMetaData() throws SQLException // предоставляет сведения относящиеся к базе данных и данному Connection.
             * *********************************************************************
             */
            dbConnection      = dbConnection();
            /**
             * Экземпляры 'PreparedStatement' - помнят скомпилированные SQL-выражения.
             * объекты 'PreparedStatement' прекомпилированны, исполнение этих запросов может происходить несколько быстрее, чем в объектах Statement.
             */
            preparedStatement = dbConnection.prepareStatement(UPDATE_RECORDS_QUERY);

            preparedStatement.setString(1, "a1");
            preparedStatement.setString(2, "a1a1a1a1a1a1a1a1");
            preparedStatement.setString(3, "A1");
            preparedStatement.setInt(4, 2);

            // Обратите внимание, что обновление строки в таблице с помощью PreparedStatement.executeUpdate().
            int countRecords = preparedStatement.executeUpdate();
            System.out.println("******************************");
            System.out.println("Update Record: " + countRecords);
            System.out.println("******************************");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (dbConnection != null) {
                dbConnection.close();
            }
        }
    }

    private static Connection dbConnection() {
        Connection dbConnection = null;
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {
            dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
            return dbConnection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return dbConnection;
    }

}