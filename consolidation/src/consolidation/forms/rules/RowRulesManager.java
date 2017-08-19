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

    // ����� ������
    private RowRule[] rowRules;
    
    // ������
    private TableInfoModel model;
    
    // ������������ ��� ������ ������� ����� ������, ������� ���� �����������
    private HashMap<String, ArrayList<RowRule>> columnRules;

    /**
     * �������� �������� �� ������ �����
     */
    private ImagesManager imagesManager;

    /**
     * �������� ��� ����������� ������� ������ � �������
     */
    private static Icon yellowIcon = new ImageIcon("g:\\Templates\\yellow.GIF");

    /**
     * �����������
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

        // ������� �����
        HashMap<String, ArrayList<RowRule>> result = new HashMap<String, ArrayList<RowRule>>();

        for (int ruleIndex = 0; ruleIndex < rowRules.length; ruleIndex++) {
            RowRule rule = rowRules[ruleIndex];
            if (rule != null) {

                Iterator<RowRuleItem> iter = rule.iterator();

                // ���������� ������ �������
                iter.next();

                // ������� ��� �������� ������ ����� �������
                while (iter.hasNext()) {

                    // �������� ��������� �������
                    RowRuleItem ruleItem = iter.next();

                    // �������� ����� ������ ��� �������� ��������
                    ArrayList<RowRule> columnRowRules = result.get(ruleItem.getColumnName());

                    // ���� ������ ��� ���, �� ������� ����� ��� ���
                    if (columnRowRules == null) {
                        columnRowRules = new ArrayList<RowRule>();
                        result.put(ruleItem.getColumnName(), columnRowRules);
                    }

                    // ��������� �������
                    columnRowRules.add(rule);
                }
            }
        }

        return result;
    }

    private void recalculateColumn(int row, String columnName) {

        // �������� ����� ������, � ������� ��������� ������ �������
        ArrayList<RowRule> columnRowRules = columnRules.get(columnName);

        // ���� ������� ����, ��
        if (columnRowRules != null) {

            // �������� �������� ��� ������ ���� ������
            Iterator<RowRule> iter = columnRowRules.iterator();

            // �������� �� ���� ��������
            while (iter.hasNext()) {

                // �������� ������� �������
                RowRule rule = iter.next();

                // ������������� �����
                recalculateRule(row, rule);

                // �������� ��� ������������� �������
                String recalculatedColumnName = rule.getColumnName();

                // ��������� �������� ������, � ������� ��� ���������
                recalculateColumn(row, recalculatedColumnName);
            }
        }
    }

    /**
     * �������� ���� ��������� ������, � ������� ��������� ������� � ������ columnName
     * ��� ������ c �������� row
     * @param row ����� ������
     * @param columnName ��� �������
     */
    public void recalculate(int row, String columnName) {

        // ��������� �������� ������, � ������� ��������� ������ �������
        recalculateColumn(row, columnName);
    }

    /**
     * �������� ���� ��������� ����������� ����������� ��� ����� ������ ������
     * @param row ����� ������
     */
    public void check(int row) {

        // ���� �� ������
        boolean isRowError = false;

        // ������� ������ ������� � ������
        RowValues values = new ValuesImpl(model, row);

        // ������� ��� ��������� �������
        for (int i = 0; i < rowRules.length; i++) {

            // �������� ��������� �������
            RowRule rowRule = rowRules[i];

            // ��������� �������� ���������� �������
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
     * �������� ���������� ������������ �����������
     * @param row ����� ������, ��� �������� ����� ����������� ����������� �����������
     * @param rowRule ��������� ����������� �����������
     */
    private void recalculateRule(int row, RowRule rowRule) {

        // ������� ������ ������� � ������
        RowValues values = new ValuesImpl(model, row);

        // ��������� ����������� �����������
        rowRule.recalculate(values);
    }

    /**
     * �������� ���������� ���������� ������ ��� ���� ����� ������
     */
    public void check() {

        for (int i = 0; i < model.getRowCount(); i++) {
            check(i);
        }
    }
}
