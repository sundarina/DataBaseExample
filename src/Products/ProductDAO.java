package Products;

/**
 * Created by sun on 28.02.17.
 */


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO { //from
    // Вспомогательный метод получения соединения
    private Connection getConnectionFrom() throws Exception {
        // Подгрузка драйвера БД Derby
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        // Получение соединения с БД
        String url = "jdbc:mysql://localhost:3306/products?autoReconnect=true&useSSL=false";
        return DriverManager.getConnection(
                url, "root", "92e3579a");
    }

    /**
     * Возвращает список идентификаторов товаров
     *
     * @return
     */
    public List<Integer> getProductIds() throws Exception {
        List<Integer> productIds = new ArrayList<Integer>();
        // Получение соединения с БД
        ResultSet rs = null;
        Connection con = getConnectionFrom();
        con.setAutoCommit(false);
        // Выполнение SQL-запроса
        try {
            rs = con.createStatement().executeQuery(
                    "Select id From product");
            con.commit();
            while (rs.next()) {
                // Из каждой строки выборки выбираем
                // результат и помещаем в коллекцию
                productIds.add(rs.getInt(1));
            }
        } catch (SQLException e) {
            System.err.println("SQLState: " + e.getSQLState()

                    + "Error Message: " + e.getMessage());
            con.rollback();
            // Перечисляем результаты выборки

        } finally {
            rs.close();
            con.close();
        }

        // Закрываем выборку и соединение с БД

        return productIds;
    }

    /**
     * Возвращает товар по идентификатору
     *
     * @return
     */
    public List<Product> getProductById(int id) throws Exception {
        List<Product> products = new ArrayList<Product>();
        // Получение соединения с БД
        Connection con = getConnectionFrom();
        con.setAutoCommit(false);
        ResultSet rs = null;
        // Подготовка SQL-запроса
        PreparedStatement st = con.prepareStatement(
                "Select description, rate, quantity " +
                        "From product " +
                        "Where id = ?");
        // Указание значений параметров запроса
        st.setInt(1, id); //вместо ? вставляем по порядковому номеру начина с 1 нужный индекс

        try {
            // Выполнение запроса
            rs = st.executeQuery();
            Product product = null;
            con.commit();
            // Перечисляем результаты выборки
            while (rs.next()) {
                // Из каждой строки выборки выбираем результаты,// формируем новый объект Product
                // и помещаем его в коллекцию
                product = new Product(
                        id,
                        rs.getString(1), //вытягиваем с первой колонки
                        rs.getFloat(2), //второй колонки
                        rs.getInt(3)); //третьей
                products.add(product);
            }
        } catch (SQLException e) {
            System.err.println("SQLState: " + e.getSQLState()

                    + "Error Message: " + e.getMessage());
            con.rollback();
        } finally {
            rs.close();
            con.close();
        }
        // Закрываем выборку и соединение с БД

        return products;
    }


    public List<Product> getBookingProductById(int id) throws Exception {
        List<Product> products = new ArrayList<Product>();

        // Получение соединения с БД
        Connection con = getConnectionFrom();
        con.setAutoCommit(false);
        ResultSet rs = null;
        // Подготовка SQL-запроса
        PreparedStatement st = con.prepareStatement(
                "Select description, rate, quantity " +
                        "From booking " +
                        "Where id = ?");
        // Указание значений параметров запроса
        st.setInt(1, id); //вместо ? вставляем по порядковому номеру начина с 1 нужный индекс

        try {
            // Выполнение запроса
            rs = st.executeQuery();
            Product product = null;
            con.commit();
            // Перечисляем результаты выборки
            while (rs.next()) {
                // Из каждой строки выборки выбираем результаты,// формируем новый объект Product
                // и помещаем его в коллекцию
                product = new Product(
                        id,
                        rs.getString(1), //вытягиваем с первой колонки
                        rs.getFloat(2), //второй колонки
                        rs.getInt(3)); //третьей

                products.add(product);
            }
        } catch (SQLException e) {
            System.err.println("SQLState: " + e.getSQLState()

                    + "Error Message: " + e.getMessage());
            con.rollback();
        } finally {
            rs.close();
            con.close();
        }
        // Закрываем выборку и соединение с БД

        return products;
    }


    /**
     * Добавляет новый товар
     *
     * @param product
     * @throws Exception
     */
    public void addProduct(Product product) throws Exception {
        // Получение соединения с БД
        Connection con = getConnectionFrom();
        con.setAutoCommit(false);
        // Подготовка SQL-запроса
        PreparedStatement st = con.prepareStatement(
                "Insert into product " +
                        "(id, description, rate, quantity) " +
                        "values (?, ?, ?, ?)");
        // Указание значений параметров запроса
        st.setInt(1, product.getId());
        st.setString(2, product.getDescription());
        st.setFloat(3, product.getRate());
        st.setInt(4, product.getQuantity());
        // Выполнение запроса
        try {
            st.executeUpdate();
            con.commit();
        } catch (SQLException e) {
            System.err.println("SQLState: " + e.getSQLState()

                    + "Error Message: " + e.getMessage());
            con.rollback();
        } finally {
            con.close();
        }

    }

    /**
     * Обновляет данные о товаре
     *
     * @param product
     * @throws Exception
     */
    public void setProduct(Product product) throws Exception {

        final int count = 1;
        int resultFrom = 0;

        int resultTo = 0;

        Connection con = getConnectionFrom();
        con.setAutoCommit(false);

        PreparedStatement st = con.prepareStatement(
                "Update product " +
                        "Set description = ?, rate = ?, quantity = ? " +
                        "Where id=?");

        st.setString(1, product.getDescription());
        st.setFloat(2, product.getRate());
        st.setInt(3, product.getQuantity());
        st.setInt(4, product.getId());

        PreparedStatement stB = con.prepareStatement
//                ("Update booking " +
//                        "Set description = ?, rate=  ?, quantity = ? " +
//                        "Where id=?");

        ("Insert into booking " +
                "(id, description, rate, quantity, total_sum) " +
                "values (?, ?, ?, ?, ?)");
        stB.setInt(1, product.getId());
        stB.setString(2, product.getDescription());
        stB.setFloat(3, product.getRate());
        stB.setInt(4, product.getQuantity());
        stB.setFloat(5, product.getRate() * count);


        PreparedStatement stQuantityFrom = con.prepareStatement("Select quantity from product");
        PreparedStatement stQuantityTo = con.prepareStatement("Select quantity from booking");

        try {
            st.executeUpdate();
            stB.executeUpdate();

            ResultSet resFromProducts = stQuantityFrom.executeQuery();
            ResultSet resToBooking = stQuantityTo.executeQuery();

            int accountFrom = 0;
            while (resFromProducts.next()) {
                accountFrom = resFromProducts.getInt(1);
            }

            if (accountFrom >= count) {
                resultFrom = accountFrom - count;
            } else {
                throw new SQLException("Invalid quantity");
            }

            int accountTo = 0;
            while (resToBooking.next()) {
                accountTo = resToBooking.getInt(1);
            }
            resultTo = accountTo + count;

            PreparedStatement stFromUpdate = con.prepareStatement("Update product Set quantity=" + resultFrom + " Where id=?");
            stFromUpdate.setInt(1, product.getId());
            PreparedStatement stToUpdate = con.prepareStatement("Update booking Set quantity=" + count + " where id= ?");
            stToUpdate.setInt(1, product.getId());

            stFromUpdate.executeUpdate();
            stToUpdate.executeUpdate();

            con.commit();
        } catch (SQLException e) {
            System.err.println("SQLState: " + e.getSQLState()
                    + "Error Message: " + e.getMessage());
            con.rollback();
        } finally {
            con.close();
        }
    }

    public void removeProduct(int id) throws Exception {
        // Получение соединения с БД
        Connection con = getConnectionFrom();
        con.setAutoCommit(false);
        // Подготовка SQL-запроса
        PreparedStatement st = con.prepareStatement(
                "Delete from product " +
                        "Where id = ?");
        // Указание значений параметров запроса
        st.setInt(1, id);
        // Выполнение запроса
        try {
            st.executeUpdate();
            con.commit();
        } catch (SQLException e) {
            System.err.println("SQLState: " + e.getSQLState()
                    + "Error Message: " + e.getMessage());
            con.rollback();
        } finally {
            con.close();
        }
    }

    public void clearBooking() throws Exception {
        Connection con = getConnectionFrom();
        con.setAutoCommit(false);
        PreparedStatement clearBooking = null;
        //Statement statement = con.createStatement();
      //  clearBooking = con.prepareStatement("DELETE FROM booking where id=?");
        clearBooking = con.prepareStatement("DROP TABLE products.booking");

        int rowCount = 0;
        try {
           // ResultSet rs = statement.executeQuery("Select count(*) from booking");
//
//            while (rs.next()) {
//                rowCount = rs.getInt(1);
//            }
//            rs.close();
//
//            for (int i = 1; i <= rowCount; i++) {
//                clearBooking.setInt(1, i);
//                clearBooking.executeUpdate();
//                con.commit();

            clearBooking.executeUpdate();
            con.commit();
        } catch (SQLException e) {
            System.err.println("SQLState: " + e.getSQLState()
                    + "Error Message: " + e.getMessage());
            con.rollback();
        } finally {
            con.close();
        }
    }

    public int countRowBooking() throws Exception {
        Connection con = getConnectionFrom();
        con.setAutoCommit(false);

        con.setAutoCommit(false);

        Statement statement = con.createStatement();

        int rowCount = 0;
        try {
            ResultSet rs = statement.executeQuery("Select count(*) from booking");
            while (rs.next()) {
                rowCount = rs.getInt(1);
            }
            rs.close();
            con.commit();
        } catch (SQLException e) {
            System.err.println("SQLState: " + e.getSQLState()
                    + "Error Message: " + e.getMessage());
            con.rollback();
        } finally {

            con.close();
        }
        return rowCount;
    }

    public void createBooking() throws Exception {
        Connection con = getConnectionFrom();
        con.setAutoCommit(false);
        PreparedStatement createBooking = null;

        try {
            createBooking = con.prepareStatement("CREATE TABLE products.booking (id INT NOT NULL, description VARCHAR(100) NOT NULL, rate FLOAT NOT NULL, quantity INT NOT NULL, total_sum FLOAT NOT NULL, PRIMARY KEY (id))");
            createBooking.executeUpdate();
            con.commit();
        } catch (SQLException e) {
            System.err.println("SQLState: " + e.getSQLState()
                    + "Error Message: " + e.getMessage());
            con.rollback();
        } finally {
            con.close();
        }
    }

}
