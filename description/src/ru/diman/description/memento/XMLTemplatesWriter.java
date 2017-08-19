/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.diman.description.memento;

import java.io.FileWriter;
import ru.diman.description.Template;
import ru.diman.description.Templates;
import ru.diman.xml.XMLBuilder;

/**
 *
 * @author Admin
 */
public class XMLTemplatesWriter implements DataWriter<Templates>{

    /**
     * Имя файла
     */
    private String fileName;

    /**
     * Конструктор
     * @param fileName имя файла
     */
    public XMLTemplatesWriter(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void saveData(Templates templates) {

        XMLBuilder builder = new XMLBuilder("Template");

        // добавляем элемент для страниц
        builder.addChild("TemplateList");

        for (int i = 0; i < templates.getCount(); i++) {

            // получаем страницу
            Template page = templates.getItem(i);

            // создаем узел в документе
            builder.addToParent("TemplateList", "Template_" + String.valueOf(i));

            // сохраняем страницу
            DataWriter<Template> memento = new XMLTemplateWriter(builder);
            memento.saveData(page);
        }

        try {
            FileWriter w = new FileWriter(fileName);
            w.append(builder.toXML());
            w.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
