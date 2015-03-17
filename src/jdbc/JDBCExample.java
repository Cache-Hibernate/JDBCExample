package jdbc;

import java.sql.*;

public class JDBCExample {

    public static void main(String []args){
//        selestMySQL();
        createTableMySQL();
    }



    public static void createTableMySQL(){
        String createTableQuery = "CREATE TABLE books (" +
                " id int(11) NOT NULL auto_increment," +
                " title varchar(50) default NULL," +
                " comment varchar(100) default NULL," +
                " price double default NULL," +
                " author varchar(50) default NULL," +
                " PRIMARY KEY (id)" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";

        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //Подключаемся к новосозданной базе. Значение параметров после "?" ясно из их имен.
            String url = "jdbc:mysql://localhost:3306/bookstore?characterEncoding=utf8";
            connection = DriverManager.getConnection(url, "root", "1978");
            statement = connection.createStatement();
            statement.executeUpdate(createTableQuery);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // позакрываем теперь все
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void selestMySQL(){
        String   USER = "root";                              // Логин пользователя
        String PASSWD = "1111";                              // Пароль пользователя
        String    URL = "jdbc:mysql://localhost:3306/mysql"; // URL адрес
//        String DRIVER = "com.mysql.jdbc.Driverr";          // Имя драйвера

        try {
            Class.forName("com.mysql.jdbc.Driver");  // Регистрируем драйвер
            System.out.println("MySQL JDBC Driver!");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your MySQL JDBC Driver?");
            e.printStackTrace();
            return;
        }


        Connection conn = null; // Соединение с БД
        try {
            conn         = DriverManager.getConnection(URL, USER, PASSWD); // Установка соединения с БД
            Statement st = conn.createStatement();                         // Готовим запрос
            ResultSet rs = st.executeQuery("select * from help_topic");    // Выполняем запрос к БД, результат в переменной rs

            while(rs.next()){
                // Последовательно для каждой строки выводим значение из колонки ColumnName
                String strOut = "";
                strOut += rs.getString("help_topic_id") + " .................";
                strOut = strOut.substring(0,10) + " ";

                strOut += rs.getString("name");
                strOut += " .......................................................";
                strOut = strOut.substring(0,35) + " ";

                strOut += rs.getString("url");
                System.out.println( strOut );
            }
        } catch(Exception e){
            e.printStackTrace();
        } finally { // Обязательно необходимо закрыть соединение
            try {
                if(conn != null)
                    conn.close();
                System.out.println("Close connection!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
