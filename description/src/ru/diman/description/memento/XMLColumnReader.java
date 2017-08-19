/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.diman.description.memento;

import java.util.ArrayList;
import java.util.List;
import ru.diman.description.Column;
import ru.diman.description.column.types.AutoSize;
import ru.diman.description.column.types.ColumnType;
import ru.diman.description.column.types.ValueType;
import ru.diman.description.memento.converters.AutoSizeConverter;
import ru.diman.description.memento.converters.ReadOnlyConverter;
import ru.diman.description.memento.converters.ValueTypeConverter;
import ru.diman.description.memento.converters.VisibleConverter;
import ru.diman.description.memento.impl.PropertyMapping;
import ru.diman.xml.XMLNode;

/**
 *
 * @author Admin
 */
public class XMLColumnReader implements DataReader<Column>{

    /**
     * Узел с данными
     */
    private XMLNode columnNode;

    /**
     * Карта сохранения
     *  Первый параметр - имя свойства в Column
     *  Второй параметр - имя свойства в XML - документе
     */
    private List<PropertyMapping> map;

    /**
     * Конструктор
     * @param columnNode узел XML документа
     */
    public XMLColumnReader(XMLNode columnNode) {
        this.columnNode = columnNode;

        // создаем набор маппингов свойств
        this.map = new ArrayList<PropertyMapping>();

        // свойства DBColumn
        map.add(new PropertyMapping("Name", "Name/@X", String.class));
        map.add(new PropertyMapping("AliasName", "Alias/@X", String.class));
        map.add(new PropertyMapping("TableName", "Params/_TableName/@X", String.class));
        map.add(new PropertyMapping("ForeignTableName", "Params/_ForeignTableName/@X", String.class));
        map.add(new PropertyMapping("ForeignKey", "Params/_ForeignKey/@X", String.class));
        map.add(new PropertyMapping("ValueType", "ValueType/@X", ValueType.class, new ValueTypeConverter()));
        map.add(new PropertyMapping("ValueSize", "ValueSize/@X", Integer.class));

        // свойства Column
        map.add(new PropertyMapping("Caption", "Caption/@X", String.class));
        map.add(new PropertyMapping("Visible", "Required/@X", Boolean.class, new VisibleConverter()));
        map.add(new PropertyMapping("ReadOnly", "Options/@X", Boolean.class, new ReadOnlyConverter()));
        map.add(new PropertyMapping("Width", "Width/@X", Integer.class));
        map.add(new PropertyMapping("Level", "Level/@X", Integer.class));
        map.add(new PropertyMapping("TextFormat", "TextFormat/@X", String.class));
        map.add(new PropertyMapping("AutoSize", "AutoSize/@X", AutoSize.class, new AutoSizeConverter()));
        map.add(new PropertyMapping("ScriptOnChange", "OnChangeJavaScript/@X", String.class));
        map.add(new PropertyMapping("PickList", "TotalName/@X", String.class));
    }

    @Override
    public void loadData(Column column) {

        // обходим все свойства
        for (int i = 0; i < map.size(); i++) {

            // получаем очередной маппинг для свойства
            PropertyMapping mapping = map.get(i);

            // получаем имя узла, в котором хранится данное свойство
            String nodeName = mapping.getColumnNode();

            // получаем значение узла
            String propertyValue = columnNode.getChildAttribute(nodeName);

            // устанавливаем значение свойства
            mapping.setValue(column, propertyValue);
        }

        // тип колонки
        String calcField = columnNode.getChildAttribute("CalcField/@X");
        if ((calcField.isEmpty()) || (calcField.equals("0"))){
            String groupType = columnNode.getChildAttribute("Group/@X");
            if (groupType.equals("1")){
                
                String s = columnNode.getChildAttribute("Params/ТипПоля/@X");
                //System.out.println(s);
                if ((s != null) && (s.equalsIgnoreCase("Счетчик"))){
                    column.setColumnType(ColumnType.INCREMENT_KEY);
                }
                else {
                    column.setColumnType(ColumnType.KEY);
                }
            }
            else{
                column.setColumnType(ColumnType.VALUE);
            }
        }
    }
}
