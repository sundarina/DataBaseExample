
import java.sql.*;

public class Map {

    private Connection con = null;
    private Statement stmt = null;

    public Map(String DBName, String ip, int port) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        // создает экземляр класса (Driver) и регестрирует статическим блоком(выполняется автоматически),
        // который будет загружен из файла/сайта/из драйвера

       /** ВОЗМОЖНОСТЬ МОДИФИКАЦИИИ КЛАССА БЕЗ ИСХОДНОГО КОДА И ДИНАМИЧЕСКИ ИМЕНЯТЬ КЛАСС ВО ВРЕМЯ ВЫПОЛНЕНИЯ КОМАНДЫ
        * static {
        try {
            java.sql.DriverManager.registerDriver(new Driver());
        } catch (SQLException e) {
            throw new RuntimeException("Can't register driver!");
            }
        }*/


        String url = "jdbc:mysql://" + ip + ":" + port + "/" + DBName;
        con = DriverManager.getConnection(url, "root", "92e3579a");
        stmt = con.createStatement();
    }

    public void stop() throws SQLException {
        con.close();
    }

    public boolean addCountry(int id, String name) {
        String sql = "INSERT INTO COUNTRIES (ID_CO, NAME)" + "  VALUES (" + id + ", ' " + name + " ')";


        try {
            stmt.executeUpdate(sql);
            System.out.println("The Country " + name + " successfully added");
            return true;
        } catch (SQLException e) {
            System.out.println("ERROR! The Country " + name + "does not add!");
            System.out.println("    >>>> " + e.getMessage());
            return false;
        }
    }

    public boolean deleteCountry(int id) {
        String sql = "DELETE FROM COUNTRIES WHERE ID_CO = " + id;
        int c = 0;
        try {
            c = stmt.executeUpdate(sql);

            if (c > 0) {
                System.out.println("The country with id " + id + "successfully deleted");
                return true;
            } else {
                System.out.println("The country with id " + id + " does not find");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("EROR in deleting country with id " + id);
            System.out.println("     >>>> " + e.getMessage());
            return false;
        }
    }


    public void showCountries() {
        String sql = "SELECT ID_CO, NAME FROM COUNTRIES";
        try {
            ResultSet rs = stmt.executeQuery(sql); //множество результатов из бд
            System.out.println("COUNTRY LIST:");
            while (rs.next()) {
                int id = rs.getInt("ID_CO");
                String name = rs.getString("NAME");
                System.out.println(" » " + id + " - " + name);
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println(
                    "ОШИБКА при получении списка стран");
            System.out.println("    >>>> " + e.getMessage());
        }
    }


}

