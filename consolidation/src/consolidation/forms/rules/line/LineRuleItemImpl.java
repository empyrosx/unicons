package consolidation.forms.rules.line;

import consolidation.forms.rules.base.BaseRuleItemImpl;
import consolidation.forms.rules.base.LineValues;
import consolidation.forms.rules.base.RuleSign;

/**
 * ���������� �������� ��������� ������������ �����������
 * @author �����
 *
 */
public class LineRuleItemImpl extends BaseRuleItemImpl<LineValues> implements LineRuleItem {

    // ����� ������ ��������
    private int row;

    /**
     * �������� �������� ��������� ������������ �����������
     * @param row ����� ������
     * @param ruleSign ���� ������������ �����������
     */
    public LineRuleItemImpl(int row, RuleSign ruleSign) {
        super(ruleSign);
        this.row = row;
    }

    /**
     * ��������� ������ ������ ��������
     *
     * @return
     */
    @Override
    public int getRow() {
        return row;
    }

    @Override
    public Object getValue(LineValues values) {
        Object objValue = values.getValue(row);
        if (objValue != null) {

            // ��������� � �������
            return (Double) objValue;
        } else {
            return new Double(0.0);
        }
    }
}
