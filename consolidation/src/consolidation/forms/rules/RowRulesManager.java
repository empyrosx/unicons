package consolidation.forms.rules;

import consolidation.forms.model.TableInfoModel;
import consolidation.forms.rules.base.RowValues;
import consolidation.forms.rules.base.ValuesImpl;
import consolidation.forms.rules.row.RowRule;
import consolidation.forms.rules.row.RowRuleItem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import ru.diman.swing.table.ImagesManager;

public class RowRulesManager {

    // набор правил
    private RowRule[] rowRules;
    
    // модель
    private TableInfoModel model;
    
    // определяется для каждой колонки набор правил, которые надо пересчитать
    private HashMap<String, ArrayList<RowRule>> columnRules;

    /**
     * менеджер картинок на панели слева
     */
    private ImagesManager imagesManager;

    /**
     * Картинка для отображения наличия ошибки в правиле
     */
    private static Icon yellowIcon = new ImageIcon("g:\\Templates\\yellow.GIF");

    /**
     * Конструктор
     * @param rowRules
     * @param model
     * @param imagesManager
     */
    public RowRulesManager(RowRule[] rowRules, TableInfoModel model, ImagesManager imagesManager) {
        this.rowRules = rowRules;
        this.model = model;
        this.imagesManager = imagesManager;
        this.columnRules = createColumnRules();
    }

    private HashMap<String, ArrayList<RowRule>> createColumnRules() {

        // создаем карту
        HashMap<String, ArrayList<RowRule>> result = new HashMap<String, ArrayList<RowRule>>();

        for (int ruleIndex = 0; ruleIndex < rowRules.length; ruleIndex++) {
            RowRule rule = rowRules[ruleIndex];
            if (rule != null) {

                Iterator<RowRuleItem> iter = rule.iterator();

                // пропускаем первый элемент
                iter.next();

                // обходим все элементы правой части правила
                while (iter.hasNext()) {

                    // получаем очередной элемент
                    RowRuleItem ruleItem = iter.next();

                    // получаем набор правил для текущего элемента
                    ArrayList<RowRule> columnRowRules = result.get(ruleItem.getColumnName());

                    // если правил ещё нет, то создаем набор для них
                    if (columnRowRules == null) {
                        columnRowRules = new ArrayList<RowRule>();
                        result.put(ruleItem.getColumnName(), columnRowRules);
                    }

                    // добавляем правило
                    columnRowRules.add(rule);
                }
            }
        }

        return result;
    }

    private void recalculateColumn(int row, String columnName) {

        // получаем набор правил, в которых участвует данная колонка
        ArrayList<RowRule> columnRowRules = columnRules.get(columnName);

        // если правила есть, то
        if (columnRowRules != null) {

            // получаем итератор для обхода всех правил
            Iterator<RowRule> iter = columnRowRules.iterator();

            // проходим по всем правилам
            while (iter.hasNext()) {

                // получаем текущее правило
                RowRule rule = iter.next();

                // пересчитываем сумму
                recalculateRule(row, rule);

                // получаем имя пересчитанной колонки
                String recalculatedColumnName = rule.getColumnName();

                // запускаем пересчет правил, в которых она участвует
                recalculateColumn(row, recalculatedColumnName);
            }
        }
    }

    /**
     * Пересчет всех строковых правил, в которых участвует колонка с именем columnName
     * для строки c индексом row
     * @param row номер строки
     * @param columnName имя колонки
     */
    public void recalculate(int row, String columnName) {

        // запускаем пересчет правил, в которых участвует данная колонка
        recalculateColumn(row, columnName);
    }

    /**
     * Проверка всех строковых контрольных соотношений для одной строки отчета
     * @param row номер строки
     */
    public void check(int row) {

        // есть ли ошибки
        boolean isRowError = false;

        // создаем объект доступа к данным
        RowValues values = new ValuesImpl(model, row);

        // обходим все строковые правила
        for (int i = 0; i < rowRules.length; i++) {

            // получаем очередное правило
            RowRule rowRule = rowRules[i];

            // выполняем проверку очередного правила
            isRowError = !rowRule.check(values) || isRowError;
        }

        //model.setRowError(row, isRowError);
        if (isRowError) {
            imagesManager.addImage(row, yellowIcon);
        } else {
            imagesManager.removeImage(row, yellowIcon);
        }
    }

    /**
     * Пересчет строкового контрольного соотношения
     * @param row номер строки, для которого нужно пересчитать контрольное соотношение
     * @param rowRule строковое контрольное соотношения
     */
    private void recalculateRule(int row, RowRule rowRule) {

        // создаем объект доступа к данным
        RowValues values = new ValuesImpl(model, row);

        // вычисляем контрольное соотношение
        rowRule.recalculate(values);
    }

    /**
     * Проверка выполнения колоночных правил для всех строк отчета
     */
    public void check() {

        for (int i = 0; i < model.getRowCount(); i++) {
            check(i);
        }
    }
}
