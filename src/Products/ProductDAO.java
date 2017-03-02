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
        String url = "jdbc:mysql://localhost:3306/products";
        return DriverManager.getConnection(
                url, "root", "92e3579a");
    }

//    private Connection getConnectionTo () throws Exception {
//        // Подгрузка драйвера БД Derby
//        Class.forName("com.mysql.jdbc.Driver").newInstance();
//        // Получение соединения с БД
//        String url = "jdbc:mysql://localhost:3306/products";
//        return DriverManager.getConnection(
//                url, "root", "92e3579a");
//    }
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
                    "Select id From products");
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
                        "From products " +
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
                        rs.getString(1), //вытягиваем с перво колонки
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
                "Insert into products " +
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
        // Получение соединения с БД
        Connection con = getConnectionFrom();
        con.setAutoCommit(false);
        // Подготовка SQL-запроса
        PreparedStatement st = con.prepareStatement(
                "Update products " +
                        "Set description = ?, rate = ?, quantity = ? " +
                        "Where id=?");
        // Указание значений параметров запроса
        st.setString(1, product.getDescription());
        st.setFloat(2, product.getRate());
        st.setInt(3, product.getQuantity());
        st.setInt(4, product.getId());

        PreparedStatement stB = con.prepareStatement(
                "Update booking " +
                        "Set description = ?, rate=  ?, quantity = ? " +
                        "Where id=?");
        // Указание значений параметров запроса
        stB.setString(1, product.getDescription());
        stB.setFloat(2, product.getRate());
        stB.setInt(3, product.getQuantity());
        stB.setInt(4, product.getId());


        //prepcon для заказов
        //кон для заказа


        // Выполнение запроса
        try {
            st.executeUpdate();
            stB.executeUpdate();
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
                "Delete from products " +
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
}
