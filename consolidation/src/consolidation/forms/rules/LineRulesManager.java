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

    // ����� ������
    private LineRule[] lineRules;
    // ������
    private TableInfoModel model;
    // ������������ ��� ������ ������� ����� ������, ������� ���� �����������
    private HashMap<Integer, ArrayList<LineRule>> rowRules;
    // �������� ��������
    private ImagesManager imagesManager;
    private static Icon redIcon = new ImageIcon("g:\\Templates\\red.GIF");

    public LineRulesManager(LineRule[] lineRules, TableInfoModel model, ImagesManager imagesManager) {
        this.lineRules = lineRules;
        this.model = model;
        this.imagesManager = imagesManager;
        this.rowRules = createLineRules();
    }

    private HashMap<Integer, ArrayList<LineRule>> createLineRules() {

        // ������� �����
        HashMap<Integer, ArrayList<LineRule>> result = new HashMap<Integer, ArrayList<LineRule>>();

        for (int ruleIndex = 0; ruleIndex < lineRules.length; ruleIndex++) {
            LineRule rule = lineRules[ruleIndex];
            if (rule != null) {

                Iterator<LineRuleItem> iter = rule.iterator();

                // ���������� ������ �������
                iter.next();

                // ������� ��� �������� ������ ����� �������
                while (iter.hasNext()) {

                    // �������� ��������� �������
                    LineRuleItem ruleItem = iter.next();

                    // �������� ����� ������ ��� �������� ��������
                    ArrayList<LineRule> columnRowRules = result.get(ruleItem.getRow());

                    // ���� ������ ��� ���, �� ������� ����� ��� ���
                    if (columnRowRules == null) {
                        columnRowRules = new ArrayList<LineRule>();
                        result.put(ruleItem.getRow(), columnRowRules);
                    }

                    // ��������� �������
                    columnRowRules.add(rule);
                }
            }
        }

        return result;
    }

    private void recalculateRow(String columnName, int row) {

        // �������� ����� ������, � ������� ��������� ������ �������
        ArrayList<LineRule> columnRowRules = rowRules.get(row);

        // ���� ������� ����, ��
        if (columnRowRules != null) {

            // �������� �������� ��� ������ ���� ������
            Iterator<LineRule> iter = columnRowRules.iterator();

            // �������� �� ���� ��������
            while (iter.hasNext()) {

                // �������� ������� �������
                LineRule rule = iter.next();

                // ������������� �����
                recalculateRule(columnName, rule);

                // �������� ��� ������������� �������
                int recalculatedRow = rule.getRow();

                // ��������� �������� ������, � ������� ��� ���������
                recalculateRow(columnName, recalculatedRow);
            }
        }
    }

    /**
     * �������� ���� ��������� ������, � ������� ��������� ������� � ������ columnName
     * ��� ������ c �������� row
     * @param row ����� ������
     * @param columnName ��� �������
     */
    public void recalculate(String columnName, int row) {

        // ��������� �������� ������, � ������� ��������� ������ �������
        recalculateRow(columnName, row);
    }

    /**
     * �������� ���� �������� ����������� �����������
     */
    public void check() {

        List<String> columns = new ArrayList<String>();

        // ��������� ����� ������� ��� ������� ������ ����������� �������
        TableInfo ti = model.getTableInfo();
        for (Column ci : ti) {
            if ((ci.getColumnType() == ColumnType.VALUE) && (ci.getValueType() == ValueType.FLOAT)) {
                columns.add(ci.getName());
            }
        }

        imagesManager.removeImages();

        // ������� ��� ��������� �������
        for (int i = 0; i < lineRules.length; i++) {

            // �������� ��������� �������
            LineRule lineRule = lineRules[i];

            boolean isRuleError = false;

            for (int columnIndex = 0; columnIndex < columns.size(); columnIndex++) {

                // ��������� �������
                String columnName = columns.get(columnIndex);

                // ������� ������ ������� � ������
                LineValues values = new ValuesImpl(model, columnName);

                // ��������� �������� ���������� �������
                isRuleError = !lineRule.check(values) || isRuleError;

                if (isRuleError) {
                    break;
                }
            }

            if (isRuleError) {

                imagesManager.addImage(lineRule.getRow(), redIcon);

                // ������� ��� ������ ������� � �������� �� ��� ���������
                for (int j = 0; j < lineRule.getCount(); j++) {
                    int row = lineRule.getItem(j).getRow();
                    imagesManager.addImage(row, redIcon);
                }


            }

        }
    }

    /**
     * �������� ���������� ������������ �����������
     * @param row ����� ������, ��� �������� ����� ����������� ����������� �����������
     * @param rowRule ��������� ����������� �����������
     */
    private void recalculateRule(String columnName, LineRule lineRule) {

        // ������� ������ ������� � ������
        LineValues values = new ValuesImpl(model, columnName);

        // ��������� ����������� �����������
        lineRule.recalculate(values);
    }

    public boolean isRowCalculated(int row){

        // �������� ����� ������, � ������� ��������� ������ �������
        for (int i = 0; i < lineRules.length; i++) {

            LineRule rule = lineRules[i];
            if ((rule.getRow() == row) && (rule.getRuleSign().equals(RuleSign.Confer))) {
                return true;
            }
        }
        return false;
    }
}
