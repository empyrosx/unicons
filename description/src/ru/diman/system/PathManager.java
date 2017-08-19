/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.diman.system;

/**
 *
 * @author Диман
 */
public class PathManager {

    private static String templatePath = "D:/work/unicons/resources/";

    /**
     * Общий путь к новым отчетам
     * @return 
     */
    public static String getTemplatePath(){
        return templatePath;
    }

    /**
     * Путь к папке "Структура"
     * @return
     */
    public static String getStructurePath(){
        return templatePath + "structure/";
    }

    /**
     * Путь к шаблонам справочников
     * @return
     */
    public static String getHandBookPath(){
        return templatePath + "Справочники/";
    }

    /**
     * Путь к шаблонам представлений
     * @return
     */
    public static String getViewPath(){
        return templatePath + "Представления/";
    }
} 