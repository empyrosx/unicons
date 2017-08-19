/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.diman.description.memento;

import ru.diman.description.Column;
import ru.diman.description.TableInfo;
import ru.diman.description.Template;
import ru.diman.description.column.types.ValueType;
import ru.diman.description.matrix.DataRowSet;
import ru.diman.description.matrix.DataSet;
import ru.diman.description.tableinfo.TableInfoImpl;
import ru.diman.xml.XMLNode;

/**
 *
 * @author Admin
 */
public class XMLTemplateReader implements DataReader<Template>{

    /**
     * Узел с данными
     */
    private XMLNode node;

    /**
     * Конструктор
     * @param node узел с данными
     */
    public XMLTemplateReader(XMLNode node) {
        this.node = node;
    }

    @Override
    public void loadData(Template template) {

        // получаем имя страницы
        String pageName = node.getChildAttribute("Options/PageName/@X");
        template.setPageName(pageName);

        // получаем набор строковых правил
        String rowRules = node.getChildAttribute("Options/RulesR/@X");
        template.setRowRules(rowRules);

        // получаем набор линейных правил
        String lineRules = node.getChildAttribute("Options/RulesL/@X");
        template.setLineRules(lineRules);

        // раскладка
        XMLNode tableInfoNode = node.selectNode("TableInfo");
        TableInfo ti = new TableInfoImpl();
        DataReader<TableInfo> memento = new XMLTableInfoReader(tableInfoNode);
        memento.loadData(ti);
        template.setTableInfo(ti);

        // матрица
        XMLNode cellsNode = node.getChildNode("Matrix/Strms/S0/Cells");
        DataSet matrix = new DataRowSet(ti);

        if (cellsNode != null) {
            
            // получаем набор строк отчета
            for (int i = 0; i < cellsNode.getChildCount(); i++){

                // получаем очередную строку
                XMLNode rowNode = cellsNode.getChild(i);

                matrix.addRow(matrix.getRowCount());

                for (int j = 0; j < rowNode.getChildCount(); j++){

                    XMLNode valueNode = rowNode.getChild(j);

                    int columnIndex = Integer.parseInt(valueNode.getName().substring(1));
                    String value = valueNode.getAttribute("X");

                    Column column = ti.getItem(columnIndex);
                    ValueType vt = column.getValueType();

                    if (vt.equals(ValueType.INTEGER)){
                        matrix.setValue(i, columnIndex, Integer.valueOf(value));
                    }
                    else if (vt.equals(ValueType.FLOAT)){
                        matrix.setValue(i, columnIndex, Double.valueOf(value));
                    }
                    else {
                        matrix.setValue(i, columnIndex, value);
                    }
                }
            }
        }
        template.setMatrix(matrix);
    }
}
