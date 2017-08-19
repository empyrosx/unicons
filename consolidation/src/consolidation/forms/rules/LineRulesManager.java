package consolidation.forms.rules;

import consolidation.forms.model.TableInfoModel;
import consolidation.forms.rules.base.LineValues;
import consolidation.forms.rules.base.RuleSign;
import consolidation.forms.rules.base.ValuesImpl;
import consolidation.forms.rules.line.LineRule;
import consolidation.forms.rules.line.LineRuleItem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import ru.diman.description.Column;
import ru.diman.description.TableInfo;
import ru.diman.description.column.types.ColumnType;
import ru.diman.description.column.types.ValueType;
import ru.diman.swing.table.ImagesManager;

public class LineRulesManager {

    // набор правил
    private LineRule[] lineRules;
    // модель
    private TableInfoModel model;
    // определяется для каждой колонки набор правил, которые надо пересчитать
    private HashMap<Integer, ArrayList<LineRule>> rowRules;
    // менеджер картинок
    private ImagesManager imagesManager;
    private static Icon redIcon = new ImageIcon("g:\\Templates\\red.GIF");

    public LineRulesManager(LineRule[] lineRules, TableInfoModel model, ImagesManager imagesManager) {
        this.lineRules = lineRules;
        this.model = model;
        this.imagesManager = imagesManager;
        this.rowRules = createLineRules();
    }

    private HashMap<Integer, ArrayList<LineRule>> createLineRules() {

        // создаем карту
        HashMap<Integer, ArrayList<LineRule>> result = new HashMap<Integer, ArrayList<LineRule>>();

        for (int ruleIndex = 0; ruleIndex < lineRules.length; ruleIndex++) {
            LineRule rule = lineRules[ruleIndex];
            if (rule != null) {

                Iterator<LineRuleItem> iter = rule.iterator();

                // пропускаем первый элемент
                iter.next();

                // обходим все элементы правой части правила
                while (iter.hasNext()) {

                    // получаем очередной элемент
                    LineRuleItem ruleItem = iter.next();

                    // получаем набор правил для текущего элемента
                    ArrayList<LineRule> columnRowRules = result.get(ruleItem.getRow());

                    // если правил ещё нет, то создаем набор для них
                    if (columnRowRules == null) {
                        columnRowRules = new ArrayList<LineRule>();
                        result.put(ruleItem.getRow(), columnRowRules);
                    }

                    // добавляем правило
                    columnRowRules.add(rule);
                }
            }
        }

        return result;
    }

    private void recalculateRow(String columnName, int row) {

        // получаем набор правил, в которых участвует данная колонка
        ArrayList<LineRule> columnRowRules = rowRules.get(row);

        // если правила есть, то
        if (columnRowRules != null) {

            // получаем итератор для обхода всех правил
            Iterator<LineRule> iter = columnRowRules.iterator();

            // проходим по всем правилам
            while (iter.hasNext()) {

                // получаем текущее правило
                LineRule rule = iter.next();

                // пересчитываем сумму
                recalculateRule(columnName, rule);

                // получаем имя пересчитанной колонки
                int recalculatedRow = rule.getRow();

                // запускаем пересчет правил, в которых она участвует
                recalculateRow(columnName, recalculatedRow);
            }
        }
    }

    /**
     * Пересчет всех строковых правил, в которых участвует колонка с именем columnName
     * для строки c индексом row
     * @param row номер строки
     * @param columnName имя колонки
     */
    public void recalculate(String columnName, int row) {

        // запускаем пересчет правил, в которых участвует данная колонка
        recalculateRow(columnName, row);
    }

    /**
     * Проверка всех линейных контрольных соотношений
     */
    public void check() {

        List<String> columns = new ArrayList<String>();

        // формируем набор колонок для которых должно выполняться правило
        TableInfo ti = model.getTableInfo();
        for (Column ci : ti) {
            if ((ci.getColumnType() == ColumnType.VALUE) && (ci.getValueType() == ValueType.FLOAT)) {
                columns.add(ci.getName());
            }
        }

        imagesManager.removeImages();

        // обходим все строковые правила
        for (int i = 0; i < lineRules.length; i++) {

            // получаем очередное правило
            LineRule lineRule = lineRules[i];

            boolean isRuleError = false;

            for (int columnIndex = 0; columnIndex < columns.size(); columnIndex++) {

                // очередная колонка
                String columnName = columns.get(columnIndex);

                // создаем объект доступа к данным
                LineValues values = new ValuesImpl(model, columnName);

                // выполняем проверку очередного правила
                isRuleError = !lineRule.check(values) || isRuleError;

                if (isRuleError) {
                    break;
                }
            }

            if (isRuleError) {

                imagesManager.addImage(lineRule.getRow(), redIcon);

                // обходим все строки правила и помечаем их как ошибочные
                for (int j = 0; j < lineRule.getCount(); j++) {
                    int row = lineRule.getItem(j).getRow();
                    imagesManager.addImage(row, redIcon);
                }


            }

        }
    }

    /**
     * Пересчет строкового контрольного соотношения
     * @param row номер строки, для которого нужно пересчитать контрольное соотношение
     * @param rowRule строковое контрольное соотношения
     */
    private void recalculateRule(String columnName, LineRule lineRule) {

        // создаем объект доступа к данным
        LineValues values = new ValuesImpl(model, columnName);

        // вычисляем контрольное соотношение
        lineRule.recalculate(values);
    }

    public boolean isRowCalculated(int row){

        // получаем набор правил, в которых участвует данная колонка
        for (int i = 0; i < lineRules.length; i++) {

            LineRule rule = lineRules[i];
            if ((rule.getRow() == row) && (rule.getRuleSign().equals(RuleSign.Confer))) {
                return true;
            }
        }
        return false;
    }
}
