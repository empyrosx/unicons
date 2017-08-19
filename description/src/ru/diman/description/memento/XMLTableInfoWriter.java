/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.diman.description.memento;

import ru.diman.description.Column;
import ru.diman.description.TableInfo;
import ru.diman.xml.XMLBuilder;

/**
 *
 * @author Admin
 */
public class XMLTableInfoWriter implements DataWriter<TableInfo> {

    /**
     * Строитель
     */
    private XMLBuilder builder;

    /**
     * Конструктор
     * @param builder
     */
    public XMLTableInfoWriter(XMLBuilder builder) {
        this.builder = builder;
    }

    @Override
    public void saveData(TableInfo ti) {

        builder.addChild("Columns");

        for (int i = 0; i < ti.getCount(); i++){

            // получаем очередную колонку
            Column column = ti.getItem(i);

			// создаем узел для неё
            builder.addToParent("Columns", "Column" + String.valueOf(i));

            // сохраняем данные колонки
            DataWriter<Column> memento = new XMLColumnWriter(builder);
            memento.saveData(column);
        }
    }
}
