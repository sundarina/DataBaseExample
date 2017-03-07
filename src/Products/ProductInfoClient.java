package Products;

/**
 * Created by sun on 28.02.17.
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;

public class ProductInfoClient extends JDialog {

    private static final long serialVersionUID = 1L;
    // Создаем DAO-объект
    ProductDAO productDAO = new ProductDAO();
    // Объявление элементов управления
    private JLabel lbSelectId = new JLabel("Выбор товара по Id");
    private JLabel lbId = new JLabel("Id");
    private JLabel lbDescription = new JLabel("Описание");
    private JLabel lbRate = new JLabel("Цена");
    private JLabel lbQuantity = new JLabel("Остаток");


    private JTextField txtId = new JTextField();
    private JTextField txtDescription = new JTextField();
    private JTextField txtRate = new JTextField();
    private JTextField txtQuantity = new JTextField();
    private JComboBox comboId = new JComboBox();


    private JButton btnClear = new JButton("Очистить");
    private JButton btnAdd = new JButton("Добавить");
    private JButton btnBook = new JButton("Добавить в заказ: ");
    private JButton btnRemove = new JButton("Удалить");
    private JButton btnShowOrder = new JButton("Сформировать заказ");

    private JLabel labelOrd = new JLabel("Заказ №: ");
    private JLabel labelTxt = new JLabel("Cписок продуктов: ");
    private JLabel labelSum = new JLabel("Общая сумма покупки: ");

    private JTextField orderId = new JTextField((int) (Math.random() * 100) + "");

    private JTextArea textArea = new JTextArea();
    private JTextField textField = new JTextField();
    private JButton bntXML = new JButton("Распечатать");

    JScrollPane scroll = new JScrollPane(textArea);


    //Add Textarea in to middle panel

    /**
     * Создает экземпляр диалога
     *
     * @param args
     */
    public static void main(String[] args) {
        new ProductInfoClient();
    }

    /**
     * Конструктор диалога
     */
    public ProductInfoClient() {

        try {
            productDAO.clearBooking();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            productDAO.createBooking();
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.setTitle("Информация о товарах");
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setLayout(new GridLayout(9, 2));
        this.setBounds(100, 50, 400, 200);
// Добавление элементов управления в диалог
        this.add(lbSelectId);
        this.add(comboId);
        this.add(lbId);
        this.add(txtId);
        this.add(lbDescription);
        this.add(txtDescription);
        this.add(lbRate);
        this.add(txtRate);
        this.add(lbQuantity);
        this.add(txtQuantity);
        this.add(btnAdd);
        this.add(btnBook);
        this.add(btnRemove);
        this.add(btnClear);
        this.add(btnShowOrder);

// Описание обработчиков событий
        comboId.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showProductData();
            }
        });
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addProduct();
            }
        });
        btnBook.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendingProduct();
            }
        });
        btnRemove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeProduct();
            }
        });
        btnClear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearProductInfo();
            }
        });

        btnShowOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                showOrder();
            }
        });
// Обновляем список идентификаторов товаров
        refreshIdList();
// Отображаем диалог на экране
        this.setVisible(true);
    }

    /**
     * Считывает список идентификаторов товаров
     * и заполняет список
     */
    private void refreshIdList() {
        try {
// получаем список идентификаторов
            List<Integer> productIds = productDAO.getProductIds();
// очищаем список
            comboId.removeAllItems();
// заполняем список полученными данными
            for (Integer productId : productIds) {
                comboId.addItem(productId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    /**
     * Отображает данные о товаре по
     * выбранному в списке идентификатору
     */
    protected void showProductData() {
        try {
// забираем значение, выбранное в списке
// идентификаторов товаров
            Integer productId = (Integer) comboId.getSelectedItem();
            if (productId != null) {
// получаем товар по идентификатору
                List<Product> product =
                        productDAO.getProductById(productId);
// заполняем текстовые поля значениями
// параметров товара
                for (int i = 0; i < product.size(); i++) {
                    txtId.setText(String.valueOf(product.get(i).getId()));
                    txtDescription.setText(product.get(i).getDescription());
                    txtRate.setText(String.valueOf(product.get(i).getRate()));
                    txtQuantity.setText(String.valueOf(product.get(i).getQuantity()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    /**
     * Добавляет новый товар на основе
     * данных текстовых полей
     */
    protected void addProduct() {
        try {
// создаем новый объект-товар
// на основе данных диалога
            Product product = new Product(
                    Integer.parseInt(txtId.getText()),
                    txtDescription.getText(),
                    Float.parseFloat(txtRate.getText()),
                    Integer.parseInt(txtQuantity.getText()));
// сохраняем товар в БД
            productDAO.addProduct(product);
// обновляем список идентификаторов
            refreshIdList();
// устанавливаем текущим добавленный товар
            comboId.setSelectedItem(
                    Integer.parseInt(txtId.getText()));
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    /**
     * Обновляет информацию о товаре на основе
     * данных текстовых полей
     */
    protected void sendingProduct() {
        try {

            // формируем объект-товар
            // на основе данных диалога
            Product product = new Product(
                    Integer.parseInt(txtId.getText()),
                    txtDescription.getText(),
                    Float.parseFloat(txtRate.getText()),
                    Integer.parseInt(txtQuantity.getText()));
            // обновляем данные о товаре в БД
            productDAO.setProduct(product);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    /**
     * Удаляет выбранный товар
     */
    protected void removeProduct() {
        try {// удаляем товар из БД
            productDAO.removeProduct(
                    Integer.parseInt(txtId.getText()));
// обновляем список идентификаторов товаров
            refreshIdList();
// отображаем данные по первому товару в списке
            showProductData();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    /**
     * Очищает данные в текстовых полях
     */
    protected void clearProductInfo() {
        try {
            txtId.setText("");
            txtDescription.setText("");
            txtRate.setText("");
            txtQuantity.setText("");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    protected void showOrder() {
        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());

        p.add(scroll);

        JFrame frame = new JFrame();
        frame.setTitle("Ваш заказ");
        frame.setBounds(100, 50, 400, 200);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));

        frame.add(panel);
        panel.add(labelOrd);
        panel.add(orderId);
        panel.add(new Label("**************   "));
        panel.add(new Label("**************"));
        panel.add(labelTxt);
        panel.add(p);
        panel.add(labelSum);
        panel.add(textField);
        panel.add(bntXML);

        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        String str = "";

        int sum = 0;
        try {

            for (int j = 1; j < productDAO.countRowBooking() + 1; j++) {
                List<Product> product =
                        productDAO.getBookingProductById(j);


                for (int i = 0; i < product.size(); i++) {
                    str += String.valueOf(product.get(i).getDescription() + ", " + product.get(i).getQuantity() + "шт" + "\n");
                    sum += product.get(i).getQuantity() * product.get(i).getRate();
                }
            }
            textArea.setText(str);
            textField.setText(String.valueOf(sum));
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage());
        }

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
}
