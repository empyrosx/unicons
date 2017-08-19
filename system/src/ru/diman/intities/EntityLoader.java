/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.diman.intities;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.xml.sax.InputSource;
import ru.diman.description.TableInfo;
import ru.diman.description.memento.DataReader;
import ru.diman.description.memento.XMLTableInfoReader;
import ru.diman.description.tableinfo.TableInfoImpl;
import ru.diman.system.PathManager;
import ru.diman.xml.XMLNode;
import ru.diman.xml.XMLParser;

/**
 * Загрузчик сущностей
 * @author Admin
 */
public class EntityLoader {


    /**
     * Получение набора доступных сущностей системы
     * @return
     */
    public static Iterator<Entity> loadEntities(){

        // создаем набор для хранения сущностей
        List<Entity> result = new ArrayList<Entity>();

        // получаем набор файлов шаблонов маппинга
        File[] files = getEntityFiles();

        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            Entity entity = loadEntity(file.getPath());
            if (entity != null){
                result.add(entity);
            }
        }

        return result.iterator();
    }


    /**
     * Получение набора файлов маппинга
     * @return
     */
    private static File[] getEntityFiles() {

        // создаем фильтр для получения всех шаблонов из каталога структурных шаблонов
        FileFilter fileFilter = new FileFilter(){

            public boolean accept(File pathname) {
                return pathname.isFile() && (pathname.getName().toUpperCase().indexOf(".CLM") > 0);
            }
        };

        // получаем список файлов шаблонов структуры
        String filePath = PathManager.getStructurePath();
        Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.ALL, filePath);
        File files = new File(filePath);

        File[] result = files.listFiles(fileFilter);
        if (result == null){
            result = new File[0];
        }
        return result;
    }

    /**
     * СОзлание сущности из файла
     * @param fileName имя файла
     * @return
     */
    private static Entity loadEntity(String fileName) {

        XMLParser parser = new XMLParser();
        try {
            InputStream is = new FileInputStream(fileName);
            XMLNode rootNode = parser.parse(is);

            //XMLNode rootNode = parser.parse(new InputSource(new FileInputStream(fileName)));

            XMLNode fieldsNode = rootNode.getChildNode("Fields");

            DataReader<TableInfo> reader = new XMLTableInfoReader(fieldsNode);
            TableInfo ti = new TableInfoImpl();
            reader.loadData(ti);

            // имя сущности
            String name = fileName.substring(fileName.lastIndexOf("\\") + 1, fileName.length() - 4);

            // имя таблицы
            String tableName = rootNode.getChildAttribute("Params/_TableName/@X");

            return new EntityImpl(name, tableName, ti);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
