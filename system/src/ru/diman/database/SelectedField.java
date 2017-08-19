/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.diman.database;

/**
 * Класс, представляющий собой описание одного поля выборки
 * @author Димитрий
 *
 */
public class SelectedField {

    // имя таблицы
    private String tableName;

    // имя поля
    private String fieldName;

    // имя алиаса
    private String aliasName;

    /**
     * Конструктор
     * @param tableName имя таблицы
     * @param fieldName имя поля
     * @param aliasName имя алиаса
     */
    public SelectedField(String tableName, String fieldName, String aliasName) {
        this.tableName = tableName;
        this.fieldName = fieldName;
        this.aliasName = aliasName;
    }

    /**
     * Имя таблицы
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * Имя поля
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * Имя алиаса
     */
    public String getAliasName() {
        return aliasName;
    }

    /**
     * Проверка на корректность
     */
    public boolean isCorrect() {
        return (tableName != "") && (fieldName != "");
    }
}
