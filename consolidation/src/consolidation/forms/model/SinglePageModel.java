/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package consolidation.forms.model;

import consolidation.forms.rules.LineRulesManager;
import consolidation.forms.rules.RowRulesManager;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import ru.diman.description.Column;

/**
 * Модель одной страницы формы ввода
 * @author Admin
 */
public class SinglePageModel {

    /**
     * Модель данных
     */
    TableModel dataModel;
    /**
     * Менеджер колоночных правил
     */
    RowRulesManager rowRulesManager;
    /**
     * Менеджер линейных правил
     */
    LineRulesManager lineRulesManager;

    /**
     * Конструктор
     * @param dataModel модель данных
     */
    public SinglePageModel(TableModel dataModel) {
        this.dataModel = dataModel;
    }

    /**
     * Получение модели данных
     * @return
     */
    public TableModel getDataModel() {
        return dataModel;
    }

    /**
     * Установка менеджера колоночных правил
     * @param rowRulesManager
     */
    public void setRowRulesManager(RowRulesManager rowRulesManager) {
        this.rowRulesManager = rowRulesManager;

        TableModelListener x = new TableModelListener() {

            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) {

                    int columnIndex = e.getColumn();

                    if (columnIndex > -1) {
                        Column ci = ((TableInfoModel) dataModel).getColumn(columnIndex);
                        getRowRulesManager().recalculate(e.getFirstRow(), ci.getName());

                        //getLineRulesManager().recalculate(ci.getName(), e.getFirstRow());
                    }

                    //
                    for (int i = e.getFirstRow(); i <= e.getLastRow(); i++) {
                        getRowRulesManager().check(i);
                    }

                    // проверка всех линейных правил
                    //getLineRulesManager().check();
                }
            }
        };

        dataModel.addTableModelListener(x);

    }

    private RowRulesManager getRowRulesManager() {
        return rowRulesManager;
    }

    /**
     * Установка менеджера колоночных правил
     * @param rowRulesManager
     */
    public void setLineRulesManager(LineRulesManager lineRulesManager) {
        this.lineRulesManager = lineRulesManager;

        TableModelListener x = new TableModelListener() {

            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) {

                    int columnIndex = e.getColumn();

                    if (columnIndex > -1) {
                        Column ci = ((TableInfoModel) dataModel).getColumn(columnIndex);
                        //getRowRulesManager().recalculate(e.getFirstRow(), ci.getName());
                        getLineRulesManager().recalculate(ci.getName(), e.getFirstRow());

                        //getLineRulesManager().recalculate(ci.getName(), e.getFirstRow());
                    }

                    getLineRulesManager().check();

                    // проверка всех линейных правил
                    //getLineRulesManager().check();
                }
            }
        };

        dataModel.addTableModelListener(x);

    }

    private LineRulesManager getLineRulesManager() {
        return lineRulesManager;
    }

}
