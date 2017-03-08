package Products;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.List;

/**
 * Created by sun on 08.03.17.
 */
public class XMLSave {
    public ProductDAO dao = new ProductDAO();
    List<Product> products;

    public XMLSave() throws Exception {
        products = dao.showBookingProduct();
    }

    public void saveToFile(String filename) {

        DocumentBuilderFactory dbf = null;
        DocumentBuilder db = null;
        Document doc = null;

        dbf = DocumentBuilderFactory.newInstance();
        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        // Создаем "чистый" документ XML
        doc = db.newDocument();
        // Создаем корневой элемент
        Element root = doc.createElement("ProducBooking");
        doc.appendChild(root);

        Element bookElement = null;

        for (int i = 0; i < products.size(); i++) {
            // Создаем объект "страна"
            bookElement = doc.createElement("Products");
            bookElement.setAttribute("id", String.valueOf(products.get(i)));
            bookElement.setAttribute("Description", products.get(i).getDescription());
            bookElement.setAttribute("Rate", String.valueOf(products.get(i).getRate()));
            bookElement.setAttribute("Quantity", String.valueOf(products.get(i).getQuantity()));
            root.appendChild(bookElement);
        }

        Source domSource = new DOMSource(doc);
        Result fileResult = new StreamResult(new File(filename));
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = factory.newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
        transformer.setOutputProperty(OutputKeys.ENCODING, "WINDOWS-1251");
        try {
            transformer.transform(domSource, fileResult);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}
