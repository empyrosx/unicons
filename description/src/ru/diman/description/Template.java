package ru.diman.description;

import ru.diman.description.matrix.DataSet;

/**
 * Интерфейс для управления страницей шаблона формы ввода
 * @author Диман
 */
public interface Template{

    /**
     * Получение имени страницы
     * @return имя страницы
     */
    public String getPageName();

    /**
     * Установка имени страницы
     * @param pageName новое имя страницы
     */
    public void setPageName(String pageName);

    /**
     * Получение раскладки страницы
     * @return раскладку страницы
     */
    public TableInfo getTableInfo();

    /**
     * Установка раскладки страницы
     * @param ti новая раскладка
     */
    public void setTableInfo(TableInfo ti);

    /**
     * Получение матрицы шаблона
     * @return
     */
    public DataSet getMatrix();

    /**
     * Установка матрицы
     * @param value
     */
    public void setMatrix(DataSet value);

    /**
     * Получение набора строковых правил
     * @return
     */
    public String getRowRules();

    /**
     * Установка набора строковых правил
     * @param rules набор правил
     */
    public void setRowRules(String rules);

    /**
     * Получение набора линейных правил
     * @return
     */
    public String getLineRules();

    /**
     * Установка набора линейных правил
     * @param rules набор правил
     */
    public void setLineRules(String rules);
    /**
     * Возможность добавления новых строк
     */
    public boolean canAddRows();
}
