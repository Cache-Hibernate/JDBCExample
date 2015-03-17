package my_jdbc_1;

import java.sql.*;

/**
 * @author Aleksandr Konstantinovitch
 * @version 1.0
 * @date 23/01/2015
 * {@link http://www.tutorialspoint.com/jdbc/jdbc-sql-syntax.htm}
 * {@link http://www.tutorialspoint.com/jdbc/updating-result-sets.htm}
 ** {@link http://www.quizful.net/post/using-jdbc}
 *********************************************************************
 * SQL> CREATE TABLE Employees
 * (
 * id INT NOT NULL,
 * age INT NOT NULL,
 * first VARCHAR(255),
 * last VARCHAR(255),
 * PRIMARY KEY ( id )
 * );
 */
public class ResultsetRecords {

    public static Connection            conn = null;
    public static Statement             stmt = null;
    public static final String     DB_DRIVER = "com.mysql.jdbc.Driver";
    public static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/bookstore";
    public static final String       DB_USER = "root";
    public static final String   DB_PASSWORD = "1978";
    // STEP 5: Execute a query
    public static String SELECT_TABLE_QUERY = "SELECT id, first, last, age FROM Employees";


    public static void main(String[] args) {
        try{
            // (Connecting to database...)
            conn = dbConnection();

            // STEP 4: Execute a query to create statment with required arguments for RS example.
            // TYPE_FORWARD_ONLY ......... курсор может двигаться только вперед в наборе результатов
            // TYPE_SCROLL_INSENSITIVE ... курсор можно прокручивать вперед и назад, и результирующий набор не чувствителен к изменениям, сделанные другими к базе данных, которые происходят после
            // TYPE_SCROLL_SENSITIVE ..... Курсор можно прокручивать вперед и назад, и результирующий набор является чувствительным к изменениям, сделанные другими к базе данных, которые происходят после
            // CONCUR_READ_ONLY .......... Создает только для чтения результирующего набора, это по умолчанию
            // CONCUR_UPDATABLE .......... Создает обновляемый набор результатов
            /**
             * Класс 'Statement' - представляет встроенную команду SQL для доступа к базе данных.
             * При закрытии Statement автоматически закрываются все связанные с ним открытые объекты ResultSet.
             * *********************************************************************
             * public boolean execute(String sql) throws SQLException        // позволяет вам выполнить Statement, когда неизвестно заранее, является SQL-строка запросом или обновлением.
             * public ResultSet executeQuery(String sql) throws SQLException // используется для выполнения запросов на извлечение данных. Он возвращает ResultSet
             * public int executeUpdate(String sql) throws SQLException      // используется для выполнения обновлений. Он возвращает количество обновленных строк.
             * public int[ ] executeBatch(String sql) throws SQLException    // вызов-выполнение хранимой процедуры.
             * public ResultSet getResultSet() throws SQLException           // возвращает текущий ResultSet, для каждого результата его следует вызывать только однажды (его не нужно вызывать после обращения к executeQuery()).
             * public void close() throws SQLException                       // вручную закрывает объект Statement, обычно Statement автоматически закрывается при закрытии Connection (не все разработчики JDBC-драйверов придерживаются этих конвенций).
             * *********************************************************************
             */
            System.out.println("Creating statement...\n");
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            /**
             * Класс 'ResultSet' - представляет результирующий набор базы данных, обеспечивает приложению построчный доступ к результатам запросов в базе данных.
             * Во время обработки запроса ResultSet поддерживает указатель на текущую обрабатываемую строку. Приложение последовательно перемещается по результатам, пока они не будут все обработаны.
             * *********************************************************************
             * public boolean absolute(int row) throws SQLException       // перемещает курсор на заданное число строк от начала, если число положительно, и от конца - если отрицательно.
             * public void afterLast() throws SQLException                // перемещает курсор в конец результирующего набора за последнюю строку.
             * public void beforeFirst() throws SQLException              // перемещает курсор в начало результирующего набора перед первой строкой.
             * public void deleteRow() throws SQLException                // удаляет текущую строку из результирующего набора и базы данных.
             * public ResultSetMetaData getMetaData() throws SQLException // предоставляет объект метаданных для данного ResultSet, содержит информацию о результирующие таблице: количество столбцов, их заголовок и т.д.
             * public int getRow() throws SQLException                    // возвращает номер текущей строки.
             * public boolean next() throws SQLException                  // позволяют переместиться в результирующем наборе на одну строку вперед.
             * public boolean previous() throws SQLException              // позволяют переместиться в результирующем наборе на одну строку назад.
             * *********************************************************************
             */
            // (List result set for reference....)
            System.out.println("List result set for reference....");
            ResultSet rs = stmt.executeQuery(SELECT_TABLE_QUERY);
            myPrintRecords(rs);

            // (Data Update from 'ResultSet'...)
            System.out.println("Data Update from 'ResultSet'...");
            // STEP 6: Loop through result set and add 5 in age Move to BFR postion so while-loop works properly
            rs.beforeFirst();
            // STEP 7: Data Update from 'ResultSet'
            while(rs.next()){
                // Retrieve by column name
                int newAge = rs.getInt("age") + 5;
                rs.updateDouble( "age", newAge );
                rs.updateRow();
            }
            myPrintRecords(rs);

            // (Inserting a new record...)
            // Insert a record into the table. Move to insert row and add column data with updateXXX()
            System.out.println("Inserting a new record...");
            rs.moveToInsertRow();
            rs.updateInt("id",2);
            rs.updateString("first","John");
            rs.updateString("last","Paul");
            rs.updateInt("age",40);
            //Commit row
            rs.insertRow();
            myPrintRecords(rs);

            // (List the record before deleting...)
            // Delete second record from the table. Set position to second record first
            rs.absolute( 2 );
            System.out.println("List the record before deleting...");
            // Retrieve by column name and display values
            System.out.println("+--------+---------+------------+-----------+");
            String strOut = "|   " + rs.getInt("id") + "      ";
            strOut = strOut.substring(0,9);
            strOut += strOut = "|   " + rs.getInt("age") + "      ";
            strOut = strOut.substring(0,19);
            strOut += strOut = "|   " + rs.getString("first") + "      ";
            strOut = strOut.substring(0,32);
            strOut += strOut = "|   " + rs.getString("last") + "      ";
            strOut = strOut.substring(0,41) + "   |";
            System.out.println( strOut );
            System.out.println("+--------+---------+------------+-----------+");
            // Delete row
            rs.deleteRow();
            System.out.println("List result set after deleting one records...");
            myPrintRecords(rs);

            // (Close(s) connection to database...)
            // STEP 8: Clean-up environment
            System.out.println("Close(s) connection to database...");
            rs.close();
            stmt.close();
            conn.close();
        } catch(SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch(Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            // finally block used to close resources
            try {
                if(conn != null)
                    conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public static void myPrintRecords(ResultSet rs) throws SQLException {
        System.out.println("+--------+---------+------------+-----------+");
        System.out.println("|   ID   |   AGE   |    FIRST   |    LAST   |");
        System.out.println("+--------+---------+------------+-----------+");
        // Ensure we start with first row
        rs.beforeFirst();
        while(rs.next()){
            // Retrieve by column name & Display values
            String strOut = "|   " + rs.getInt("id") + "      ";
            strOut = strOut.substring(0,9);
            strOut += strOut = "|   " + rs.getInt("age") + "      ";
            strOut = strOut.substring(0,19);
            strOut += strOut = "|   " + rs.getString("first") + "      ";
            strOut = strOut.substring(0,32);
            strOut += strOut = "|   " + rs.getString("last") + "      ";
            strOut = strOut.substring(0,41) + "   |";
            System.out.println( strOut );
            System.out.println("+--------+---------+------------+-----------+");
        }
        System.out.println();
    }

    private static Connection dbConnection() {
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
        Connection dbConnection = null;
        try {
            // Загружаем драйвер (STEP 2: Register JDBC driver)
            Class.forName(DB_DRIVER);
            System.out.println("Driver loading success !");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
//            System.out.println(e.getMessage());
        } catch (Exception e) { // Handle errors for Class.forName
            e.printStackTrace();
        }

        try {
            // STEP 3: Open a connection
            System.out.println("Connecting to database...");
            // Нужно создать подключение к БД у MySQL есть база 'bookstore', к ней и будем создавать соединение.
            dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
            return dbConnection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return dbConnection;
    }
}