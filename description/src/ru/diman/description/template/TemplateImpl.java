package ru.diman.description.template;

import ru.diman.description.TableInfo;
import ru.diman.description.tableinfo.TableInfoImpl;
import ru.diman.description.Template;
import ru.diman.description.matrix.DataSet;

/**
 * Реализация интерфейса Template
 * Содержит метаописание страницы шаблона формы ввода
 * @author Диман
 */
public class TemplateImpl implements Template{

    /**
     * Раскладка страницы
     */
    private TableInfo ti;

    /**
     * Имя страницы
     */
    private String pageName;

    /**
     * Матрица
     */
    private DataSet matrix = null;

    /**
     * Набор строковых правил
     */
    private String rowRules;

    /**
     * Набор линейных правил
     */
    private String lineRules;

    /**
     * Конструктор
     */
    public TemplateImpl() {

        // создаем пустую раскладку по умолчанию
        ti = new TableInfoImpl();

        // имя страницы пустое
        pageName = "";

        // строковые правила
        rowRules = "";
    }

    /**
     * Получение имени страницы
     * @return имя страницы
     */
    @Override
    public String getPageName(){
        return pageName;
    }

    /**
     * Установка имени страницы
     * @param pageName новое имя страницы
     */
    @Override
    public void setPageName(String pageName){
        this.pageName = pageName;
    }

    /**
     * Получение раскладки страницы
     * @return раскладку страницы
     */
    @Override
    public TableInfo getTableInfo(){
        return ti;
    }

    /**
     * Установка раскладки страницы
     * @param ti новая раскладка
     */
    @Override
    public void setTableInfo(TableInfo ti){
        this.ti = ti;
    }

    @Override
    public DataSet getMatrix() {
        return matrix;
    }

    @Override
    public String getRowRules() {
        return rowRules;
    }

    @Override
    public void setRowRules(String rules) {
        this.rowRules = rules;
    }

    @Override
    public void setMatrix(DataSet value) {
        this.matrix = value;
    }

    @Override
    public boolean canAddRows() {
        Object canEditRows = ti.getParam("CanEditRows");
        if (canEditRows != null){
            return !canEditRows.equals("0");
        }
        return false;
    }

    @Override
    public String getLineRules() {
        return lineRules;
    }

    @Override
    public void setLineRules(String rules) {
        lineRules = rules;
    }
}
