package persistence;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class XmlManager extends FileManager {

    /**
     * Creadora.
     */
    public XmlManager() {}

    /**
     * Llegeix un arxiu i el divideix en títol, autor i contingut.
     * @param path Path de l'arxiu que vol pujar l'usuari.
     * @return String[] amb el títol, autor i contingut en les posicions 0, 1 i 2, respectivament.
     * @throws Exception Quan no s'escriu el títol, l'autor i/o el contingut.
     */
    @Override
    public String[] loadFile(String path) throws Exception {
        String [] info = new String [] {"","",""};

        File inputFile = new File(path);

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputFile);
        doc.getDocumentElement().normalize();


        NodeList nList = doc.getElementsByTagName("document");
        Node node = nList.item(0);
        Element element = (Element) node;

        String title = element.getElementsByTagName("title").item(0).getTextContent();
        if (title.isBlank()) throw new Exception("el títol no pot ser buit");
        info[0] = title;

        String author = element.getElementsByTagName("author").item(0).getTextContent();
        if (author.isBlank()) throw new Exception("l'autor no pot ser buit");
        info[1] = author;

        String content = element.getElementsByTagName("content").item(0).getTextContent();
        if (content.isBlank()) throw new Exception("el contingut no pot ser buit");
        info[2] = content;

        return info;
    }


    /**
     * Descarrega el document en un arxiu al path que indica l'usuari.
     * @param title Títol del document.
     * @param author Autor del document.
     * @param content Contingut del document.
     * @param path Path on l'usuari vol descarregar l'arxiu.
     * @throws IOException En cas que hi hagi algun error amb la descàrrega.
     */
    @Override
    public void downloadFile(String title, String author, String content, String path) throws IOException {
        String pathFile = path + "/" + title + "--" + author + ".xml";

        new FileOutputStream(pathFile).close();
        FileOutputStream f = new FileOutputStream(pathFile,true);
        String root = "<document>";
        f.write(root.getBytes());

        String titleLine = "\r\n<title>" + title + "</title>";
        f.write(titleLine.getBytes());

        String authorLine = "\r\n<author>" + author + "</author>";
        f.write(authorLine.getBytes());

        String contentLine = "\r\n<content>" + content + "</content>";
        f.write(contentLine.getBytes());

        root = "\r\n</document>";
        f.write(root.getBytes());

        f.close();

    }

}
