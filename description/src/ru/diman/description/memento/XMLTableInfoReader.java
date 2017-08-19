/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.diman.description.memento;

import ru.diman.description.Column;
import ru.diman.description.TableInfo;
import ru.diman.description.column.ColumnImpl;
import ru.diman.xml.XMLNode;

/**
 *
 * @author Admin
 */
public class XMLTableInfoReader implements DataReader<TableInfo> {

    /**
     * Узел XML документа
     */
    private XMLNode tableNode;

    /**
     * Конструктор
     * @param tableNode информационный узел
     */
    public XMLTableInfoReader(XMLNode tableNode) {
        this.tableNode = tableNode;
    }

    @Override
    public void loadData(TableInfo ti) {

        // получаем узел с информацией о колонках раскладки
        XMLNode columnsNode = tableNode.getChildNode("Columns");

        // если этого узла нет, то делать нечего
        if (columnsNode == null) {
            return;
        }

        // загрузка колонок
        for (int i = 0; i < columnsNode.getChildCount(); i++) {

            // получаем очередную колонку
            XMLNode columnNode = columnsNode.getChild(i);

            // создаем новую колонку
            ColumnImpl ci = new ColumnImpl();

            // загружаем атрибуты
            DataReader<Column> memento = new XMLColumnReader(columnNode);
            memento.loadData(ci);

            // добавляем
            ti.addColumn(ci);
        }
        
        // загрузка параметров
        XMLNode paramsNode = tableNode.getChildNode("Params");
        if (paramsNode != null){

            for (int i = 0; i < paramsNode.getChildCount(); i++){

                // получаем узел очередного параметра
                XMLNode paramNode = paramsNode.getChild(i);

                // получаем имя параметра
                String paramName = paramNode.getName();

                // получаем значение параметра
                Object paramValue = paramNode.getAttribute("X");

                // сохраняем параметр
                ti.setParam(paramName, paramValue);
            }

        }
    }
}
