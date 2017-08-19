/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.diman.description.memento;

import java.io.FileInputStream;
import java.io.InputStream;
import org.xml.sax.InputSource;
import ru.diman.description.Template;
import ru.diman.description.Templates;
import ru.diman.description.template.TemplateImpl;
import ru.diman.xml.XMLNode;
import ru.diman.xml.XMLParser;

/**
 *
 * @author Admin
 */
public class XMLTemplatesReader implements DataReader<Templates>{

    /**
     * Имя файла
     */
    private String fileName;

    /**
     * Конструктор
     * @param fileName имя файла
     */
    public XMLTemplatesReader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void loadData(Templates templates) {

        // создаем парсер
        XMLParser parser = new XMLParser();
        try {
            InputStream is = new FileInputStream(fileName);
            //XMLNode node = parser.parse(new InputSource(new FileInputStream(fileName)));
            XMLNode node = parser.parse(is);

            XMLNode pagesNode = node.selectNode("TemplateList");
            assert pagesNode != null;

            // обходим все страницы шаблона
            for (int i = 0; i < pagesNode.getChildCount(); i++){

                // получаем очередную страницу
                XMLNode pageNode = pagesNode.getChild(i);

                // создаем загрузчик страницы
                DataReader<Template> memento = new XMLTemplateReader(pageNode);

                // создаем новую страницу
                Template page = new TemplateImpl();
                templates.add(page);

                // загружаем данные
                memento.loadData(page);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
