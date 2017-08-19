/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.diman.description.memento;

import java.util.ArrayList;
import java.util.List;
import ru.diman.description.Column;
import ru.diman.description.column.types.ValueType;
import ru.diman.description.memento.converters.ReadOnlyConverter;
import ru.diman.description.memento.converters.ValueTypeConverter;
import ru.diman.description.memento.converters.VisibleConverter;
import ru.diman.description.memento.impl.PropertyMapping;
import ru.diman.xml.XMLBuilder;

/**
 *
 * @author Admin
 */
public class XMLColumnWriter implements DataWriter<Column> {

    /**
     * Строитель
     */
    private XMLBuilder builder;

    /**
     * Карта сохранения
     *  Первый параметр - имя свойства в Column
     *  Второй параметр - имя свойства в XML - документе
     */
    private List<PropertyMapping> map;

    private List<PropertyMapping> params;

    /**
     * Конструктор
     * @param builder
     */
    public XMLColumnWriter(XMLBuilder builder) {
        this.builder = builder;

        // создаем набор маппингов свойств
        this.map = new ArrayList<PropertyMapping>();

        // свойства DBColumn
        map.add(new PropertyMapping("Name", "Name/@X", String.class));
        map.add(new PropertyMapping("AliasName", "Alias/@X", String.class));
        map.add(new PropertyMapping("ValueType", "ValueType/@X", ValueType.class, new ValueTypeConverter()));
        map.add(new PropertyMapping("ValueSize", "ValueSize/@X", Integer.class));

        // свойства Column
        map.add(new PropertyMapping("Caption", "Caption/@X", String.class));
        map.add(new PropertyMapping("Visible", "Required/@X", Boolean.class, new VisibleConverter()));
        map.add(new PropertyMapping("ReadOnly", "Options/@X", Boolean.class, new ReadOnlyConverter()));
        map.add(new PropertyMapping("Width", "Width/@X", Integer.class));
        map.add(new PropertyMapping("Level", "Level/@X", Integer.class));
        map.add(new PropertyMapping("TextFormat", "TextFormat/@X", String.class));

        params = new ArrayList<PropertyMapping>();
        params.add(new PropertyMapping("TableName", "_TableName/@X", String.class));
        params.add(new PropertyMapping("ForeignTableName", "_ForeignTableName/@X", String.class));
        params.add(new PropertyMapping("ForeignKey", "_ForeignKey/@X", String.class));
    }

    @Override
    public void saveData(Column column) {

        String columnNode = builder.getCurrentNode();
        
        // обходим все свойства
        for (int i = 0; i < map.size(); i++) {

            // получаем очередной маппинг для свойства
            PropertyMapping mapping = map.get(i);

            // получаем имя узла, в котором хранится данное свойство
            String nodeName = mapping.getColumnNode();

            // получаем значение свойства
            Object value = mapping.getValue(column);

            if (value != null){

                String[] paths = nodeName.split("/");
                for (int j = 0; j < paths.length; j++){

                    String path = paths[j];
                    if (path.startsWith("@")){
                        builder.addAttribute(path.substring(1), value.toString());
                    }
                    else{
                        if (j == 0){
                            builder.addToParent(columnNode, path);
                        }
                        else {
                            builder.addChild(path);
                        }
                    }
                }
            }
        }

        builder.addToParent(columnNode, "Params");

        // обходим все свойства
        for (int i = 0; i < params.size(); i++) {

            // получаем очередной маппинг для свойства
            PropertyMapping mapping = params.get(i);

            // получаем имя узла, в котором хранится данное свойство
            String nodeName = mapping.getColumnNode();

            // получаем значение свойства
            Object value = mapping.getValue(column);

            if (value != null){

                String[] paths = nodeName.split("/");
                for (int j = 0; j < paths.length; j++){

                    String path = paths[j];
                    if (path.startsWith("@")){
                        builder.addAttribute(path.substring(1), value.toString());
                    }
                    else{
                        builder.addToParent("Params", path);
                    }
                }
            }
        }
    }
}
