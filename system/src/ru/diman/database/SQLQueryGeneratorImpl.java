/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.diman.database;

import ru.diman.description.Column;
import ru.diman.description.TableInfo;

/**
 *
 * @author Admin
 */
public class SQLQueryGeneratorImpl implements SQLQueryGenerator{

    /**
     * Раскладка
     */
    private TableInfo ti;

    /**
     * Ограничения выборки
     */
    private QueryRestrictions restrictions;

    /**
     * Имя ключевого поля
     */
    private String keyFieldName;

    /**
     * Алиас ключевого поля
     */
    private String keyAliasName;

    /**
     * Имя ключевого поля по умолчанию
     */
    protected static final String DEFAULT_KEY_FIELD_NAME = "ID";

    /**
     * Алиас ключевого поля по умолчанию
     */
    protected static final String DEFAULT_KEY_ALIAS_NAME = "RowKey";

    /**
     * Конструктор
     * @param ti раскладка
     * @param restrictions
     */
    public SQLQueryGeneratorImpl(TableInfo ti, QueryRestrictions restrictions) {
        this.ti = ti;
        this.restrictions = restrictions;
        setKeyField(DEFAULT_KEY_FIELD_NAME);
    }

    /**
     * Конструктор
     * @param ti раскладка
     */
    public SQLQueryGeneratorImpl(TableInfo ti) {
        this(ti, null);
    }

    /**
     * Получение запроса на выборку данных
     * @return
     */
    public Query getSelectQuery() {

        // создаем строитель запросов выборки
        SelectSQLBuilder builder = new SelectSQLBuilder(ti, restrictions, keyFieldName);

        // генерируем запрос
        return builder.generateQuery();
    }

    /**
     * Получение запроса на вставку данных
     * @return 
     */
    public Query getInsertQuery(){

        // создаем строитель запросов выборки
        InsertSQLBuilder builder = new InsertSQLBuilder(ti, restrictions);

        // генерируем запрос
        return builder.generateQuery();
    }

    public Query getUpdateQuery() {

        // создаем строитель запросов выборки
        UpdateSQLBuilder builder = new UpdateSQLBuilder(ti, keyFieldName);

        // генерируем запрос
        return builder.generateQuery();
    }

    public void setKeyField(String keyFieldName) {
        this.keyFieldName = keyFieldName;

        Column keyColumn = ti.FindColumnByAlias(keyFieldName);
        if (keyColumn != null){
            int index = ti.IndexOf(keyColumn);
            this.keyAliasName = "Alias" + String.valueOf(index);
        }
        else {
            this.keyAliasName = DEFAULT_KEY_ALIAS_NAME;
        }
    }

}
