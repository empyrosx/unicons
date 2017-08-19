package ru.diman.swing.table.header;

import java.util.EventListener;

/**
 * Слушатель изменений для заголовка таблицы
 * @author Admin
 */
public interface TableHeaderListener extends EventListener
{

    /**
     * Колонка изменила видимость
     * @param e
     */
    public void columnChangesVisibility(TableHeaderEvent e);


    /**
     * Заголовок колонки изменился (свернулся или развернулся)
     * @param e
     */
    public void columnChangesCollapse(TableHeaderEvent e);
}

